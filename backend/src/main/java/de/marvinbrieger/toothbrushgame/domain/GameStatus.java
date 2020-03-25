package de.marvinbrieger.toothbrushgame.domain;

public enum GameStatus {

    PREPARATION, RUNNING, FINISHED;

    public GameStatus getPredGameStatus() {
        if (this == PREPARATION)
            throw new IllegalStateException("there is no predecessor for PREPARATION");

        if (this == RUNNING)
            return PREPARATION;

        return RUNNING;
    }

}
