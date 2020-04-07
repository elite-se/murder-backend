package de.marvinbrieger.toothbrushgame.services;

import de.marvinbrieger.toothbrushgame.domain.*;
import de.marvinbrieger.toothbrushgame.persistence.GameRepository;
import de.marvinbrieger.toothbrushgame.persistence.MurderAssignmentRepository;
import de.marvinbrieger.toothbrushgame.services.exceptions.GameNotFoundException;
import de.marvinbrieger.toothbrushgame.services.exceptions.MurderAssignmentNotFoundException;
import de.marvinbrieger.toothbrushgame.services.exceptions.NotYourAssignmentException;
import de.marvinbrieger.toothbrushgame.services.exceptions.UserNotFoundException;
import de.marvinbrieger.toothbrushgame.services.interfaces.CurrentUserService;
import de.marvinbrieger.toothbrushgame.services.interfaces.MurderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class MurderServiceImpl implements MurderService {
    private final MurderAssignmentRepository murderAssignmentRepository;
    private final GameRepository gameRepository;
    private final CurrentUserService currentUserService;

    private void addMurder(MurderAssignment murderAssignment) {
        Murder murder = new Murder(null, Instant.now(), murderAssignment);
        murderAssignment.setMurder(murder);
        murderAssignment.setAssignmentStatus(MurderAssignmentStatus.FULFILLED);
    }

    private MurderAssignment findSuccessor(List<MurderAssignment> assignments, MurderAssignment source) {
        for (MurderAssignment potentialSuccessor : assignments)
            if (source.hasSuccessor(potentialSuccessor))
                return potentialSuccessor;

        throw new IllegalArgumentException();
    }

    private void addNewMurderAssignment(Game game, MurderAssignment currentAssignment) {
        Player killer = currentAssignment.getKiller();
        MurderAssignment targetsAssignment = findSuccessor(game.getMurderAssignments(), currentAssignment);

        MurderAssignment killersNewMission = new MurderAssignment(null, game, killer, targetsAssignment.getTarget(),
                MurderAssignmentStatus.PENDING, null);

        game.getMurderAssignments().add(killersNewMission);
    }

    @Override
    public Murder commitMurder(Long gameId, Long assignmentId) {
        Game game = gameRepository.findByIdAndGameStatus(gameId, GameStatus.RUNNING)
                .orElseThrow(() -> new GameNotFoundException(gameId));

        MurderAssignment currentMurderAssignment = murderAssignmentRepository
                .findByIdAndAssignmentStatus(assignmentId, MurderAssignmentStatus.PENDING)
                .map(murderAssignment -> {
                    addMurder(murderAssignment);
                    return murderAssignmentRepository.save(murderAssignment);
                })
                .orElseThrow(() -> new MurderAssignmentNotFoundException(assignmentId));

        try {
            if (!currentUserService.getCurrentUser().equals(currentMurderAssignment.getKiller().getUser()))
                throw new NotYourAssignmentException();
        } catch (UserNotFoundException e) {
            throw new NotYourAssignmentException(e);
        }

        addNewMurderAssignment(game, currentMurderAssignment);
        gameRepository.save(game);
        return currentMurderAssignment.getMurder();
    }

}
