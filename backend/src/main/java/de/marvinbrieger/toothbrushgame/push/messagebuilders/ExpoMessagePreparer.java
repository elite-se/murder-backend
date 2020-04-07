package de.marvinbrieger.toothbrushgame.push.messagebuilders;

import de.marvinbrieger.toothbrushgame.domain.Game;
import io.github.jav.exposerversdk.ExpoPushMessage;

interface ExpoMessagePreparer {
    ExpoPushMessage prepareMessage(String type, Game game, String ...recipients);
}
