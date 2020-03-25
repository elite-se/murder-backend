package de.marvinbrieger.toothbrushgame.persistence;

import de.marvinbrieger.toothbrushgame.domain.Game;
import de.marvinbrieger.toothbrushgame.domain.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long>, QuerydslPredicateExecutor<Game> {

    Optional<Game> findByIdAndGameStatus(Long id, GameStatus gameStatus);

}
