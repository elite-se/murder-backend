package de.marvinbrieger.toothbrushgame.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class KillAssignment {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Game game;

    @NotNull
    @ManyToOne
    private Player killer;

    @NotNull
    @ManyToOne
    private Player target;

}
