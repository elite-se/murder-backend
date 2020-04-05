package de.marvinbrieger.toothbrushgame.push;

import de.marvinbrieger.toothbrushgame.domain.ApplicationUser;
import de.marvinbrieger.toothbrushgame.domain.Game;
import de.marvinbrieger.toothbrushgame.domain.Player;
import de.marvinbrieger.toothbrushgame.push.interfaces.ExpoPushService;
import de.marvinbrieger.toothbrushgame.push.interfaces.GameEndedNotificationService;
import io.github.jav.exposerversdk.ExpoPushMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameEndedNotificationServiceImpl extends BaseNotificationService implements GameEndedNotificationService {
    private static final String TYPE_VALUE = "GAME_ENDED";
    private static final String NOTIFICATION_GAME_END_BODY = "notification.gameEnd.body";

    public GameEndedNotificationServiceImpl(ExpoPushService pushService) {
        super(pushService);
    }


    @Override
    public void pushGameEnding(Game game) {
        var messages = game.getPlayers().stream()
                .map(Player::getUser)
                .collect(Collectors.groupingBy(user -> Optional.ofNullable(user.getLocale())))
                .entrySet().parallelStream()
                .map(entry -> buildMessage(entry.getKey().orElse(null), entry.getValue(), game))
                .collect(Collectors.toUnmodifiableList());
        sendAndLogFailures(messages);
    }

    private static ExpoPushMessage buildMessage(Locale locale, List<ApplicationUser> users, Game game) {
        var tokens = users.parallelStream()
                .map(ApplicationUser::getPushToken)
                .filter(Objects::nonNull)
                .toArray(String[]::new);

        ExpoPushMessage msg = prepareMessage(TYPE_VALUE, game, tokens);

        var bundle = getNotificationMessages(locale);
        msg.body = bundle.getString(NOTIFICATION_GAME_END_BODY);

        return msg;
    }
}
