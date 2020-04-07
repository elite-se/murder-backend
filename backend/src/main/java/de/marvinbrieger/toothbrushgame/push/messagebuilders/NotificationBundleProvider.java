package de.marvinbrieger.toothbrushgame.push.messagebuilders;

import java.util.Locale;
import java.util.ResourceBundle;

interface NotificationBundleProvider {
    /**
     * Returns a bundle with messages for push notifications for the given locale
     *
     * @param locale locale to retrieve the notifications for
     * @return the bundle
     */
    ResourceBundle getBundle(Locale locale);
}
