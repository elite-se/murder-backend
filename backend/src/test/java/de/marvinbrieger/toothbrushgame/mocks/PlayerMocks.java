package de.marvinbrieger.toothbrushgame.mocks;

import de.marvinbrieger.toothbrushgame.domain.ApplicationUser;
import de.marvinbrieger.toothbrushgame.domain.Player;

import java.util.HashSet;

public class PlayerMocks {
    public static final ApplicationUser USER_MARVIN = new ApplicationUser(1L, "marvins device", "marvins password", null, new HashSet<>(),
            null);
    public static final ApplicationUser USER_ELIAS = new ApplicationUser(2L, "elias device", "elias password", null, new HashSet<>(), null);

    public static final Player ELIAS = new Player(null, null, "Elias", USER_ELIAS, null);

    public static final Player STORED_ELIAS = new Player(1L, null, "Elias", USER_ELIAS, null);

    public static final Player MARVIN = new Player(null, null, "Marvin", USER_MARVIN, null);

    public static final Player STORED_MARVIN = new Player(2L, null, "Marvin", USER_MARVIN, null);

    public static final Player ALEX = new Player(null, null, "Alex", null, null);

    public static final Player STORED_ALEX = new Player(3L, null, "Alex", null, null);

    public static final Player KIPF = new Player(null, null, "Kipf", null, null);

    public static final Player STORED_KIPF = new Player(4L, null, "Kipf", null, null);

    public static final Player WINTER = new Player(null, null, "Winter", null, null);

    public static final Player STORED_WINTER = new Player(5L, null, "Winter", null, null);

    public static final Player NEUMANN = new Player(null, null, "Neumann", null, null);

    public static final Player STORED_NEUMANN = new Player(6L, null, "Neumann", null, null);

    public static final Player SCHELLY = new Player(null, null, "Schelly", null, null);

    public static final Player STORED_SCHELLY = new Player(7L, null, "Schelly", null, null);

}
