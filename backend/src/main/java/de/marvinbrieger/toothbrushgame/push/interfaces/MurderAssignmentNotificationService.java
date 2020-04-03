package de.marvinbrieger.toothbrushgame.push.interfaces;

import de.marvinbrieger.toothbrushgame.domain.MurderAssignment;

import java.util.Collection;

/**
 * Sends push notifications for murder assignments.
 */
public interface MurderAssignmentNotificationService {
    /**
     * Informs the murderers of the given assignments about their victim via push notifications.
     *
     * If some push messages can not be delivered, the reasons will be logged.
     *
     * @param murderAssignments the assignments that contain the information being pushed
     */
    void pushMurderAssignments(Collection<MurderAssignment> murderAssignments);
}
