package de.marvinbrieger.toothbrushgame.push;

import de.marvinbrieger.toothbrushgame.domain.Game;
import de.marvinbrieger.toothbrushgame.push.interfaces.ExpoPushService;
import io.github.jav.exposerversdk.ExpoPushMessage;
import io.github.jav.exposerversdk.ExpoPushTicket;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@AllArgsConstructor
public class BaseNotificationService {
    private static final Logger logger = LoggerFactory.getLogger(BaseNotificationService.class);
    private static final String NOTIFICATIONS_BUNDLE_NAME = "notifications";
    protected static final String TYPE_KEY = "type";
    protected static final String GAME_ID_KEY = "gameId";
    protected final ExpoPushService pushService;

    protected void sendAndLog (List<ExpoPushMessage> messages) {
        pushService.sendMessagesAsync(messages)
                .thenAccept(MurderAssignmentNotificationServiceImpl::logFailures);
    }

    protected static ResourceBundle getNotificationMessages(Locale locale) {
        if (locale == null) {
            return ResourceBundle.getBundle(NOTIFICATIONS_BUNDLE_NAME);
        } else {
            return ResourceBundle.getBundle(NOTIFICATIONS_BUNDLE_NAME, locale);
        }
    }

    protected static void logFailures(List<ExpoPushTicket> expoPushTickets) {
        expoPushTickets.forEach(BaseNotificationService::logFailure);
    }

    protected static void logFailure(ExpoPushTicket expoPushTicket) {
        if (!"ok".equalsIgnoreCase(expoPushTicket.status)) {
            logger.info("Failed to deliver push message: ${}", expoPushTicket.message);
        }
    }

    protected static ExpoPushMessage prepareMessage(String type, Game game, String ...recipients) {
        ExpoPushMessage msg = new ExpoPushMessage(List.of(recipients));
        msg.title = game.getTitle();
        msg.channelId = type;

        var data = new HashMap<String, String>();
        msg.data = data;
        data.put(TYPE_KEY, type);
        data.put(GAME_ID_KEY, game.getId().toString());

        return msg;
    }
}
