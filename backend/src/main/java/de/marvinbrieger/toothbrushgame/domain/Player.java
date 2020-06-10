package de.marvinbrieger.toothbrushgame.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.marvinbrieger.toothbrushgame.exceptions.NoPendingAssignmentException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.LinkedList;
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

    /**
     * Creates a new player belonging to the given game and the given user, an empty assignments list and the same name as the name of
     * {@code player}.
     *
     * @param player Player object with the data to copy
     * @param game   the game of the new player
     * @param user   belonging to the new player
     */
    public Player(Player player, Game game, ApplicationUser user) {
        this(null, game, player.getPlayerName(), user, new LinkedList<>());
    }

    public void setFailed() {
        getCurrentAssignment().setAssignmentStatus(MurderAssignmentStatus.FAILED);
    }

    @JsonIgnore
    private MurderAssignment getCurrentAssignment() {
        return assignments.parallelStream()
                .filter(assignment -> assignment.getAssignmentStatus() == MurderAssignmentStatus.PENDING)
                .findFirst()
                .orElseThrow(() -> new NoPendingAssignmentException(this));
    }
}
