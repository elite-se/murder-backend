package de.marvinbrieger.toothbrushgame.persistence;

import de.marvinbrieger.toothbrushgame.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PlayerRepository extends JpaRepository<Player, Long>, QuerydslPredicateExecutor<Player> {

}
