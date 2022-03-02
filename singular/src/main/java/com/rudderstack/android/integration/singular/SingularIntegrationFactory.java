package com.rudderstack.android.integration.singular;

import androidx.annotation.NonNull;

import com.rudderstack.android.sdk.core.MessageType;
import com.rudderstack.android.sdk.core.RudderClient;
import com.rudderstack.android.sdk.core.RudderConfig;
import com.rudderstack.android.sdk.core.RudderIntegration;
import com.rudderstack.android.sdk.core.RudderLogger;
import com.rudderstack.android.sdk.core.RudderMessage;

public class SingularIntegrationFactory extends RudderIntegration<RudderClient> {
    private static final String SINGULAR_KEY = "Singular";

    public static Factory FACTORY = new Factory() {
        @Override
        public RudderIntegration<?> create(Object settings, RudderClient client, RudderConfig rudderConfig) {
            return new SingularIntegrationFactory(settings, client, rudderConfig);
        }

        @Override
        public String key() {
            return SINGULAR_KEY;
        }
    };

    private SingularIntegrationFactory(Object config, final RudderClient client, RudderConfig rudderConfig) {

    }

    @Override
    public void dump(@NonNull RudderMessage element) {
        try {
            if (element != null) {
                processRudderEvent(element);
            }
        } catch (Exception e) {
            RudderLogger.logError(e);
        }
    }

    private void processRudderEvent(RudderMessage element) {
        if (element.getType() != null) {
            switch (element.getType()) {
                case MessageType.IDENTIFY:
                    break;
                case MessageType.TRACK:
                    break;
                case MessageType.SCREEN:
                    break;
                default:
                    RudderLogger.logWarn("SingularIntegrationFactory: MessageType is not specified");
                    break;
            }
        }
    }

    @Override
    public void flush() {
    }

    @Override
    public void reset() {
        // nothing to do here for Singular
    }

    @Override
    public RudderClient getUnderlyingInstance() {
        return null;
    }
}
