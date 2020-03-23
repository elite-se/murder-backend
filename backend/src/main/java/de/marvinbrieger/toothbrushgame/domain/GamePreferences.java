package de.marvinbrieger.toothbrushgame.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class GamePreferences {

    @Id
    @GeneratedValue
    private Long id;

    private boolean dailyReassignment;

    private boolean noAttestors;

    private String furtherRules;

}
