package de.marvinbrieger.toothbrushgame.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Weapon {

    @Id
    @GeneratedValue
    private Long id;

    private String weaponName;

}
