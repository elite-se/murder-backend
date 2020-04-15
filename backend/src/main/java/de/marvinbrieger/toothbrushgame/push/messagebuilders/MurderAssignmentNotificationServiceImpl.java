package de.marvinbrieger.toothbrushgame.push.messagebuilders;

import de.marvinbrieger.toothbrushgame.domain.MurderAssignment;
import de.marvinbrieger.toothbrushgame.push.expobroadcasting.ExpoPushService;
import io.github.jav.exposerversdk.ExpoPushMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class MurderAssignmentNotificationServiceImpl implements MurderAssignmentNotificationService {
    private static final String TYPE_VALUE = "MURDER_ASSIGNMENT";
    private static final String NOTIFICATION_ASSIGNMENT_BODY = "notification.assignment.body";
    private final ExpoPushService pushService;
    private final NotificationBundleProvider notificationBundleProvider;
    private final ExpoMessagePreparer expoMessagePreparer;

    @Override
    public void pushMurderAssignments(Collection<MurderAssignment> murderAssignments) {
        pushService.sendMessagesAndLogFailures(murderAssignments.parallelStream()
                .map(this::buildMessage)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
    }

    private ExpoPushMessage buildMessage(MurderAssignment assignment) {
        var user = assignment.getKiller().getUser();
        var token = user.getPushToken();
        if (token == null) return null;

        ExpoPushMessage msg = expoMessagePreparer.prepareMessage(TYPE_VALUE, assignment.getGame(), token);

        var bundle = notificationBundleProvider.getBundle(user.getLocale());
        msg.body = String.format(bundle.getString(NOTIFICATION_ASSIGNMENT_BODY), assignment.getTarget().getPlayerName());

        return msg;
    }
}
