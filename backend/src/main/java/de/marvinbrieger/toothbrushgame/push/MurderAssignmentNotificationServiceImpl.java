package de.marvinbrieger.toothbrushgame.push;

import de.marvinbrieger.toothbrushgame.domain.MurderAssignment;
import de.marvinbrieger.toothbrushgame.push.interfaces.ExpoPushService;
import de.marvinbrieger.toothbrushgame.push.interfaces.MurderAssignmentNotificationService;
import io.github.jav.exposerversdk.ExpoPushMessage;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MurderAssignmentNotificationServiceImpl extends BaseNotificationService implements MurderAssignmentNotificationService {
    private static final String TYPE_VALUE = "MURDER_ASSIGNMENT";
    private static final String NOTIFICATION_ASSIGNMENT_BODY = "notification.assignment.body";

    public MurderAssignmentNotificationServiceImpl(ExpoPushService pushService) {
        super(pushService);
    }

    @Override
    public void pushMurderAssignments(Collection<MurderAssignment> murderAssignments) {
        super.sendAndLog(murderAssignments.parallelStream()
                .map(MurderAssignmentNotificationServiceImpl::buildMessage)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
    }

    private static ExpoPushMessage buildMessage(MurderAssignment assignment) {
        var user = assignment.getKiller().getUser();
        var token = user.getPushToken();
        if (token == null) return null;

        ExpoPushMessage msg = prepareMessage(TYPE_VALUE, assignment.getGame(), token);

        var bundle = getNotificationMessages(user.getLocale());
        msg.body = String.format(bundle.getString(NOTIFICATION_ASSIGNMENT_BODY), assignment.getTarget().getPlayerName());

        return msg;
    }
}
