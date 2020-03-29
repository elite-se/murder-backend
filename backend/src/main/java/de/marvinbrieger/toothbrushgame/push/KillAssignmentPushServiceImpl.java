package de.marvinbrieger.toothbrushgame.push;

import de.marvinbrieger.toothbrushgame.domain.KillAssignment;
import de.marvinbrieger.toothbrushgame.services.interfaces.KillAssignmentPushService;

public class KillAssignmentPushServiceImpl implements KillAssignmentPushService {

    @Override
    public void pushKillAssignment(KillAssignment killAssignment) {
        // TODO place a real implementation here
        System.out.println(killAssignment.getKiller().getPlayerName() + " has to kill " + killAssignment.getTarget().getPlayerName());
    }

}
