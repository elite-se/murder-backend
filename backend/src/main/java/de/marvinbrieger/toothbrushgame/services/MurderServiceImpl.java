package de.marvinbrieger.toothbrushgame.services;

import de.marvinbrieger.toothbrushgame.domain.Game;
import de.marvinbrieger.toothbrushgame.domain.GameStatus;
import de.marvinbrieger.toothbrushgame.domain.Murder;
import de.marvinbrieger.toothbrushgame.domain.MurderAssignment;
import de.marvinbrieger.toothbrushgame.domain.MurderAssignmentStatus;
import de.marvinbrieger.toothbrushgame.domain.Player;
import de.marvinbrieger.toothbrushgame.persistence.GameRepository;
import de.marvinbrieger.toothbrushgame.persistence.MurderAssignmentRepository;
import de.marvinbrieger.toothbrushgame.services.exceptions.GameNotFoundException;
import de.marvinbrieger.toothbrushgame.services.exceptions.MurderAssignmentNotFoundException;
import de.marvinbrieger.toothbrushgame.services.interfaces.MurderService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class MurderServiceImpl implements MurderService {

    private final MurderAssignmentRepository murderAssignmentRepository;

    private final GameRepository gameRepository;

    MurderServiceImpl(MurderAssignmentRepository murderAssignmentRepository, GameRepository gameRepository) {
        this.murderAssignmentRepository = murderAssignmentRepository;
        this.gameRepository = gameRepository;
    }

    private void addMurder(MurderAssignment murderAssignment) {
        Murder murder = new Murder(null, Instant.now(), murderAssignment);
        murderAssignment.setMurder(murder);
        murderAssignment.setAssignmentStatus(MurderAssignmentStatus.FULLFILLED);
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

        addNewMurderAssignment(game, currentMurderAssignment);
        gameRepository.save(game);

        return currentMurderAssignment.getMurder();
    }

}
