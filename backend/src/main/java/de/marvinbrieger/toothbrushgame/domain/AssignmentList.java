package de.marvinbrieger.toothbrushgame.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.marvinbrieger.toothbrushgame.exceptions.MurderAssignmentNotFoundException;
import de.marvinbrieger.toothbrushgame.webservice.mapping.FilteredMurderAssignmentsSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AssignmentList {

    @Transient
    private Game game;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "game"
    )
    @JsonSerialize(using = FilteredMurderAssignmentsSerializer.class)
    private List<MurderAssignment> murderAssignments;

    public MurderAssignment findAssignment(Long assignmentId) {
        return this.murderAssignments.parallelStream()
                .filter(assignment -> assignment.getId().equals(assignmentId))
                .findAny()
                .orElseThrow(() -> new MurderAssignmentNotFoundException(assignmentId));
    }

    private MurderAssignment findSuccessor(MurderAssignment source) {
        return murderAssignments.parallelStream()
                .filter(source::hasSuccessor)
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public void newAssignemntForSuccessfullKiller(MurderAssignment currentAssignment) {
        Player killer = currentAssignment.getKiller();
        MurderAssignment victimsAssignment = findSuccessor(currentAssignment);

        MurderAssignment killersNewMission = new MurderAssignment(null, game, killer, victimsAssignment.getTarget(),
                MurderAssignmentStatus.PENDING, null);

        getMurderAssignments().add(killersNewMission);
    }

}
