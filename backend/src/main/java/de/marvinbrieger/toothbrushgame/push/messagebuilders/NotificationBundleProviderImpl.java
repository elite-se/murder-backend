package de.marvinbrieger.toothbrushgame.push.messagebuilders;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.ResourceBundle;

@AllArgsConstructor
@Service
class NotificationBundleProviderImpl implements NotificationBundleProvider {
    private static final String NOTIFICATIONS_BUNDLE_NAME = "notifications";

    @Override
    public ResourceBundle getBundle(Locale locale) {
        if (locale == null) {
            return ResourceBundle.getBundle(NOTIFICATIONS_BUNDLE_NAME);
        } else {
            return ResourceBundle.getBundle(NOTIFICATIONS_BUNDLE_NAME, locale);
        }
    }
}
