package de.marvinbrieger.toothbrushgame.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.marvinbrieger.toothbrushgame.exceptions.GameInWrongStateException;
import de.marvinbrieger.toothbrushgame.exceptions.MurderAssignmentNotFoundException;
import de.marvinbrieger.toothbrushgame.exceptions.PlayerAlreadyExistsException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
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

    @Embedded
    private AssignmentList murderAssignments;

    /**
     * Creates a new game with the given game code, the title and preferences as the given game, the status {@link GameStatus#PREPARATION},
     * an empty assignments list and a player list containing only the owner.
     * The owner is a new player (see {@link Player#Player(Player, Game, ApplicationUser)}) based on the owner of the given game,
     * belonging to the user {@code creator}.
     *
     * @param game     An object providing some of the information reused in the created game.
     * @param gameCode The game code of the created game.
     * @param creator  The user creating the game. Will be the user of the owner property of the created game.
     */
    public Game(Game game, String gameCode, ApplicationUser creator) {
        this.title = game.getTitle();
        this.preferences = game.getPreferences();
        this.gameCode = gameCode;
        this.gameStatus = GameStatus.PREPARATION;
        this.players = new ArrayList<>();
        this.owner = new Player(game.getOwner(), this, creator);
        this.murderAssignments = game.getMurderAssignments();

        // owner is also a player
        this.players.add(this.owner);
    }

    public boolean notInPreparation() {
        return gameStatus != GameStatus.PREPARATION;
    }

    @JsonIgnore
    public boolean notRunning() {
        return gameStatus != GameStatus.RUNNING;
    }

    /**
     * Fulfills the assignment with given ID.
     *
     * @param assignmentId ID of the assignment that is fulfilled
     * @throws GameInWrongStateException if the game is not running
     * @throws MurderAssignmentNotFoundException if there is no assignment with that ID that is part of the game
     * @see MurderAssignment#commitMurder()
     */
    public void commitMurder(Long assignmentId) {
        // ensure game is running
        if (this.notRunning())
            throw new GameInWrongStateException(GameStatus.RUNNING, getGameStatus());

        // get the assignment
        MurderAssignment currentAssignment = murderAssignments.findAssignment(assignmentId);

        // commit murder and add new assignment for the killer
        currentAssignment.commitMurder();
        currentAssignment.getTarget().setFailed();
        murderAssignments.newAssignemntForSuccessfullKiller(currentAssignment);
    }

    /**
     * Lets the given player join the game.
     *
     * @param player the joining player
     * @throws GameInWrongStateException if the game is not in preparation
     * @throws PlayerAlreadyExistsException if there is already a player with the same name or user in the game
     */
    public void addPlayer(Player player) {
        // check game state
        if (notInPreparation())
            throw new GameInWrongStateException(GameStatus.PREPARATION, getGameStatus());

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

    /**
     * Starts the game if it currently is in preparation and sets the assignments to the given value.

     * @param assignments the new value of the {@link #murderAssignments} property
     * @throws GameInWrongStateException if the game is not in preparation
     */
    public void start(List<MurderAssignment> assignments) {
        if (notInPreparation())
            throw new GameInWrongStateException(GameStatus.PREPARATION, getGameStatus());

        setGameStatus(GameStatus.RUNNING);
        setMurderAssignments(new AssignmentList(this, assignments));
    }

    /**
     * Ends the game.
     *
     * @throws GameInWrongStateException if the game is not running
     */
    public void end() {
        if (notRunning())
            throw new GameInWrongStateException(GameStatus.RUNNING, getGameStatus());

        setGameStatus(GameStatus.FINISHED);
    }
}
