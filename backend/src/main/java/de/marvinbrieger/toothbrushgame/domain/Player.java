package de.marvinbrieger.toothbrushgame.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Data
@Entity
public class Player {

    @Id
    @GeneratedValue
    private Long id;

    @Size(min = 3)
    private String playerName;

    public Player(String playerName) {
        this.playerName = playerName;
    }

}
