package de.marvinbrieger.toothbrushgame.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.marvinbrieger.toothbrushgame.services.exceptions.GameNotFoundException;
import de.marvinbrieger.toothbrushgame.services.exceptions.MurderAssignmentNotFoundException;
import de.marvinbrieger.toothbrushgame.services.exceptions.PlayerAlreadyExistsException;
import de.marvinbrieger.toothbrushgame.webservice.mapping.FilteredMurderAssignmentsSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Game {

    @Id
    @GeneratedValue
    private Long id;

    @Size(min = 3)
    private String title;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private GamePreferences preferences;

    private String gameCode;

    @OneToOne(cascade = CascadeType.ALL)
    private Player owner;

    private GameStatus gameStatus;

    @OneToMany(mappedBy = "game")
    private List<Player> players;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "game"
    )
    @JsonSerialize(using = FilteredMurderAssignmentsSerializer.class)
    private List<MurderAssignment> murderAssignments;

    public boolean inPreparation() {
        return gameStatus == GameStatus.PREPARATION;
    }

    @JsonIgnore
    public boolean isRunning() {
        return gameStatus == GameStatus.RUNNING;
    }

    private MurderAssignment findSuccessor(MurderAssignment source) {
        for (MurderAssignment potentialSuccessor : murderAssignments)
            if (source.hasSuccessor(potentialSuccessor))
                return potentialSuccessor;

        throw new IllegalArgumentException();
    }

    private void addSucceedingAssignment(MurderAssignment currentAssignment) {
        Player killer = currentAssignment.getKiller();
        MurderAssignment targetsAssignment = findSuccessor(currentAssignment);

        MurderAssignment killersNewMission = new MurderAssignment(null, this, killer, targetsAssignment.getTarget(),
                MurderAssignmentStatus.PENDING, null);

        getMurderAssignments().add(killersNewMission);
    }

    public void commitMurder(Long assignmentId) {
        // ensure game is running
        if (!this.isRunning())
            throw new GameNotFoundException(this.getId(), GameStatus.RUNNING);

        // get the assignment
        MurderAssignment assignment = this.murderAssignments.parallelStream()
                .filter(assig -> assig.getId().equals(assignmentId))
                .findAny()
                .orElseThrow(() -> new MurderAssignmentNotFoundException(assignmentId));

        // commit murder and add new assignment fur the killer
        assignment.commitMurder();
        assignment.getTarget().getCurrentAssignment().setAssignmentStatus(MurderAssignmentStatus.FAILED);
        addSucceedingAssignment(assignment);
    }

    public void addPlayer(Player player) {
        // check game state
        if (!inPreparation())
            throw new GameNotFoundException(this.getId(), GameStatus.PREPARATION);

        // check neither player with same name nor with same user has already joined
        boolean playerNameAlreadyExists = players.parallelStream()
                .anyMatch(p -> p.getPlayerName().equalsIgnoreCase(player.getPlayerName()));
        if (playerNameAlreadyExists)
            throw new PlayerAlreadyExistsException(player.getPlayerName());

        boolean userAlreadyJoined = players.parallelStream()
                .anyMatch(p -> p.getUser().equals(player.getUser()));
        if (userAlreadyJoined)
            throw new PlayerAlreadyExistsException(player.getUser());

        // add player
        players.add(player);
    }
}
