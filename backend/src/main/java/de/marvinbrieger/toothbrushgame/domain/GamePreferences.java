package de.marvinbrieger.toothbrushgame.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class GamePreferences {

    @JsonIgnore
    @Id
    @GeneratedValue
    private Long id;

    private boolean dailyReassignment;

    private boolean noAttestors;

    private String furtherRules;

    @OneToMany
    private Set<Weapon> allowedWeapons;

    @JsonProperty()
    public boolean allWeaponsAllowed() {
        return allowedWeapons == null || allowedWeapons.isEmpty();
    }

}
