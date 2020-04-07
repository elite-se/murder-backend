package de.marvinbrieger.toothbrushgame.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Player {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore // avoids infinity loops
    @ManyToOne
    private Game game;

    @Size(min = 3)
    private String playerName;

    @JsonIgnore // contains sensitive data
    @ManyToOne
    private ApplicationUser user;

    @JsonIgnore
    @OneToMany(mappedBy = "killer")
    private List<MurderAssignment> assignments;

    @JsonIgnore
    public MurderAssignment getCurrentAssignment() {
        return assignments.parallelStream()
                .filter(assignment -> assignment.getAssignmentStatus() == MurderAssignmentStatus.PENDING)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No pending assignment available"));
    }
}
