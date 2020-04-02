package de.marvinbrieger.toothbrushgame.domain;

/**
 * Represents the status of a game.
 *
 * <p>A game is in PREPARATION immediately after a person created it. This is
 * the phase when players join the game regularly. After the owner of the game
 * started it, the game is in RUNNING. It is FINISHED after the owner ended it.
 * It can then not longer be found under the game code.</p>
 */
public enum GameStatus {

    PREPARATION, RUNNING, FINISHED

}
