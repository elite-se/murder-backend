package de.marvinbrieger.toothbrushgame.push;

import de.marvinbrieger.toothbrushgame.domain.MurderAssignment;
import de.marvinbrieger.toothbrushgame.push.interfaces.ExpoPushService;
import de.marvinbrieger.toothbrushgame.push.interfaces.PushNotificationService;
import io.github.jav.exposerversdk.ExpoPushMessage;
import io.github.jav.exposerversdk.ExpoPushTicket;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PushNotificationServiceImpl implements PushNotificationService {
    private static final Logger logger = LoggerFactory.getLogger(ExpoPushServiceImpl.class);
    private ExpoPushService pushService;

    @Override
    public void pushMurderAssignments(Collection<MurderAssignment> murderAssignments) {
        var messages = murderAssignments.parallelStream()
                .map(PushNotificationServiceImpl::buildMessage)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        pushService.sendMessagesAsync(messages).thenAccept(PushNotificationServiceImpl::logFailures);
    }

    private static ExpoPushMessage buildMessage(MurderAssignment assignment) {
        var token = assignment.getKiller().getUser().getPushToken();
        if (token == null) return null;

        ExpoPushMessage msg = new ExpoPushMessage(token);
        msg.title = assignment.getGame().getTitle();
        msg.body = String.format("Your victim: %s", assignment.getTarget().getPlayerName());

        var data = new HashMap<String, String>();
        data.put("type", "MURDER_ASSIGNMENT");
        msg.data = data;

        return msg;
    }

    private static void logFailures(List<ExpoPushTicket> expoPushTickets) {
        expoPushTickets.forEach(PushNotificationServiceImpl::logFailure);
    }

    private static void logFailure(ExpoPushTicket expoPushTicket) {
        if (!"ok".equalsIgnoreCase(expoPushTicket.status)) {
            logger.info("Failed to deliver push message: ${}", expoPushTicket.message);
        }
    }

}
