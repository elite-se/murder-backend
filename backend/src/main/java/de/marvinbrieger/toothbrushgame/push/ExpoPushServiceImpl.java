package de.marvinbrieger.toothbrushgame.push;

import de.marvinbrieger.toothbrushgame.push.interfaces.ExpoPushService;
import io.github.jav.exposerversdk.ExpoPushMessage;
import io.github.jav.exposerversdk.ExpoPushTicket;
import io.github.jav.exposerversdk.PushClient;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExpoPushServiceImpl implements ExpoPushService {
    private static final Logger logger = LoggerFactory.getLogger(ExpoPushServiceImpl.class);
    private PushClient pushClient;


    @Override
    public CompletableFuture<List<ExpoPushTicket>> sendMessagesAsync(List<ExpoPushMessage> messages) {
        // chunk messages
        var chunks = pushClient.chunkPushNotifications(messages);

        // send messages async
        List<CompletableFuture<List<ExpoPushTicket>>> replyFutures = new ArrayList<>(chunks.size());
        for (var chunk : chunks) {
            replyFutures.add(pushClient.sendPushNotificationsAsync(chunk));
        }

        // merge, flatten and return the futures
        return mergeFutures(replyFutures)
                .thenApply(lists -> lists.parallelStream()
                        .flatMap(List::stream)
                        .collect(Collectors.toUnmodifiableList()));
    }

    private static <T> CompletableFuture<List<T>> mergeFutures(List<CompletableFuture<T>> futures) {
        return CompletableFuture.supplyAsync(() -> futures.stream().map(future -> {
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toUnmodifiableList()));
    }
}
