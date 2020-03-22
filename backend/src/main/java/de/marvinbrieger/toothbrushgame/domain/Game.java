package de.marvinbrieger.toothbrushgame.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@Entity
public class Game {

    @Id
    @GeneratedValue
    private Long id;

    private boolean deleted;

    @Size(min = 3)
    private String title;

    private String gameCode;

    @OneToOne(cascade = CascadeType.ALL)
    private Player owner;

    /*private GamePreferences preferences;

    private GameStatus status;

    private Instant startTime;

    private Instant endTime;

    private List<Player> players;

    private List<KillAssignment> killAssignments;

    private List<Kill> kills;

    public GameStatus getGameStatus() {
        if (startTime == null)
            return GameStatus.PREPERATION;
        if (endTime == null)
            return GameStatus.RUNNING;
        return GameStatus.FINISHED;
    }*/

}
