package de.marvinbrieger.toothbrushgame.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

@Data
@Entity
public class Player {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore // avoids infinity loops
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Game game;

    @Size(min = 3)
    private String playerName;

}
