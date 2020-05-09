package de.marvinbrieger.toothbrushgame.push.expobroadcasting;

import io.github.jav.exposerversdk.ExpoPushMessage;
import io.github.jav.exposerversdk.ExpoPushTicket;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * A pretty low-level approach to sending push notifications using the Expo services.
 */
public interface ExpoPushService {
    /**
     * Sends the messages to the Expo push notification delivery server.
     *
     * <p><b>Note: </b> The order of the tickets returned in the future may not match the order of the messages passed.</p>
     *
     * @param messages the messages that will be sent to the server
     * @return a future containing the answers from the Expo server for the separate messages
     */
    CompletableFuture<List<ExpoPushTicket>> sendMessagesAsync(Collection<ExpoPushMessage> messages);

    /**
     * Sends the messages to the Expo push notification delivery server.
     *
     * Any failures reported by the Expo server will be logged.
     *
     * @param messages the messages that will be sent to the server.
     */
    void sendMessagesAndLogFailures(Collection<ExpoPushMessage> messages);
}
