package de.marvinbrieger.toothbrushgame.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Game {

    @Id
    @GeneratedValue
    private Long id;

    @Size(min = 3)
    private String title;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private GamePreferences preferences;

    private String gameCode;

    @OneToOne(cascade = CascadeType.ALL)
    private Player owner;

    private GameStatus gameStatus;

    @OneToMany(mappedBy = "game")
    private List<Player> players;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "game"
    )
    private List<KillAssignment> killAssignments;

    public boolean inPreparation() {
        return gameStatus == GameStatus.PREPARATION;
    }

    public boolean isRunning() {
        return gameStatus == GameStatus.RUNNING;
    }

}
