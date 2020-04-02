package de.marvinbrieger.toothbrushgame.push;

import de.marvinbrieger.toothbrushgame.domain.MurderAssignment;
import de.marvinbrieger.toothbrushgame.push.interfaces.KillAssignmentPushService;

public class KillAssignmentPushServiceImpl implements KillAssignmentPushService {

    @Override
    public void pushKillAssignment(MurderAssignment murderAssignment) {
        // TODO place a real implementation here
        System.out.println(murderAssignment.getKiller()
                .getPlayerName() + " has to kill " + murderAssignment.getTarget()
                .getPlayerName());
    }

}
