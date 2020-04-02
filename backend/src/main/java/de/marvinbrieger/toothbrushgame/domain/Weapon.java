package de.marvinbrieger.toothbrushgame.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Weapon {

    @Id
    @GeneratedValue
    private Long id;

    private String weaponName;

}
