package de.marvinbrieger.toothbrushgame.push.messagebuilders;

import de.marvinbrieger.toothbrushgame.domain.Game;
import io.github.jav.exposerversdk.ExpoPushMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
class ExpoMessagePreparerImpl implements ExpoMessagePreparer {
    protected static final String TYPE_KEY = "type";
    protected static final String GAME_ID_KEY = "gameId";

    @Override
    public ExpoPushMessage prepareMessage(String type, Game game, String... recipients) {
        ExpoPushMessage msg = new ExpoPushMessage(List.of(recipients));
        msg.title = game.getTitle();
        msg.channelId = type;

        var data = new HashMap<String, String>();
        msg.data = data;
        data.put(TYPE_KEY, type);
        data.put(GAME_ID_KEY, game.getId().toString());

        return msg;
    }
}
