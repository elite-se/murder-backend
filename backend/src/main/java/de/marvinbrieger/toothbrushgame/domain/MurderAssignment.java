package de.marvinbrieger.toothbrushgame.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.marvinbrieger.toothbrushgame.exceptions.MurderAssignmentNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MurderAssignment {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Game game;

    @NotNull
    @ManyToOne
    private Player killer;

    @NotNull
    @ManyToOne
    private Player target;

    private MurderAssignmentStatus assignmentStatus;

    @OneToOne(cascade = CascadeType.ALL)
    private Murder murder;

    @JsonIgnore
    public boolean isPending() {
        return assignmentStatus == MurderAssignmentStatus.PENDING;
    }


    public boolean hasSuccessor(MurderAssignment assignment) {
        return target.equals(assignment.killer) && assignment.isPending();
    }

    public void commitMurder() {
        if (this.getAssignmentStatus() != MurderAssignmentStatus.PENDING)
            throw new MurderAssignmentNotFoundException(this.getId(), MurderAssignmentStatus.PENDING);

        Murder murder = new Murder(null, Instant.now(), this);
        this.setMurder(murder);
        this.setAssignmentStatus(MurderAssignmentStatus.FULFILLED);
    }
}
