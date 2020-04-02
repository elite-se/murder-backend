package de.marvinbrieger.toothbrushgame.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
