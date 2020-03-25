package de.marvinbrieger.toothbrushgame.mocks;

import de.marvinbrieger.toothbrushgame.domain.GamePreferences;
import java.util.HashSet;

public class GamePreferencesMocks {

    public static final GamePreferences STANDARD_PREFERENCES
            = new GamePreferences(1L, false, true, "", new HashSet<>());

}
