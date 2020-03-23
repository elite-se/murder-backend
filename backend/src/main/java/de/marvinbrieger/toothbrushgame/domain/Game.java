package de.marvinbrieger.toothbrushgame.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

/**
 *
 */
@Data
@Entity
public class Game {

    @Id
    @GeneratedValue
    private Long id;

    private boolean deleted;

    @Size(min = 3)
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    private GamePreferences preferences;

    private String gameCode;

    @OneToOne(cascade = CascadeType.ALL)
    private Player owner;

    @OneToMany(mappedBy = "game")
    private List<Player> players;

}
