package de.marvinbrieger.toothbrushgame.services;

import de.marvinbrieger.toothbrushgame.domain.GameStatus;
import de.marvinbrieger.toothbrushgame.domain.Murder;
import de.marvinbrieger.toothbrushgame.domain.MurderAssignment;
import de.marvinbrieger.toothbrushgame.domain.MurderAssignmentStatus;
import de.marvinbrieger.toothbrushgame.persistence.GameRepository;
import de.marvinbrieger.toothbrushgame.persistence.MurderAssignmentRepository;
import de.marvinbrieger.toothbrushgame.services.exceptions.GameNotFoundException;
import de.marvinbrieger.toothbrushgame.services.exceptions.MurderAssignmentNotFoundException;
import de.marvinbrieger.toothbrushgame.services.exceptions.NotYourAssignmentException;
import de.marvinbrieger.toothbrushgame.services.interfaces.CurrentUserService;
import de.marvinbrieger.toothbrushgame.services.interfaces.MurderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MurderServiceImpl implements MurderService {
    private final MurderAssignmentRepository murderAssignmentRepository;
    private final GameRepository gameRepository;
    private final CurrentUserService currentUserService;


    @Override
    public Murder commitMurder(Long gameId, Long assignmentId) {
        MurderAssignment currentMurderAssignment = murderAssignmentRepository
                .findByIdAndAssignmentStatus(assignmentId, MurderAssignmentStatus.PENDING)
                .orElseThrow(() -> new MurderAssignmentNotFoundException(assignmentId));

        if (!currentUserService.getCurrentUser().equals(currentMurderAssignment.getKiller().getUser()))
            throw new NotYourAssignmentException();

        gameRepository.findByIdAndGameStatus(gameId, GameStatus.RUNNING)
                .map(game -> {
                    game.commitMurder(assignmentId);
                    gameRepository.save(game);
                    return game;
                })
                .orElseThrow(() -> new GameNotFoundException(gameId));

        return currentMurderAssignment.getMurder();
    }

}
