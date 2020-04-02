package de.marvinbrieger.toothbrushgame.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.Instant;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Murder {

    @Id
    @GeneratedValue
    private Long id;

    private Instant timeOfCrime;

    @JsonIgnore // avoid loops
    @OneToOne
    private MurderAssignment murderAssignment;

}
