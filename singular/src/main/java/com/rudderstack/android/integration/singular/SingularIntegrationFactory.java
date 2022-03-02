package com.rudderstack.android.integration.singular;

import static com.rudderstack.android.integration.singular.Utils.getDouble;
import static com.rudderstack.android.integration.singular.Utils.getString;
import static com.rudderstack.android.integration.singular.Utils.isEmpty;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.rudderstack.android.sdk.core.MessageType;
import com.rudderstack.android.sdk.core.RudderClient;
import com.rudderstack.android.sdk.core.RudderConfig;
import com.rudderstack.android.sdk.core.RudderIntegration;
import com.rudderstack.android.sdk.core.RudderLogger;
import com.rudderstack.android.sdk.core.RudderMessage;
import com.singular.sdk.Singular;

import org.json.JSONObject;

import java.util.Map;

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
        if (RudderClient.getApplication() == null) {
            RudderLogger.logError("Application is null. Aborting Singular initialization.");
            return;
        }

        Gson gson = new Gson();
        SingularDestinationConfig destinationConfig = gson.fromJson(
                gson.toJson(config),
                SingularDestinationConfig.class
        );

        // We should check if apiKey and secretKey is not Null. Else Singular SDK will not initialise.
        if (destinationConfig.apiKey != null && destinationConfig.apiSecret != null) {
            Singular.init(RudderClient.getApplication(), destinationConfig.apiKey, destinationConfig.apiSecret);
        }
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
                    String userId = element.getUserId();
                    if (!TextUtils.isEmpty(userId)) {
                        Singular.setCustomUserId(userId);
                    }
                    break;
                case MessageType.TRACK:
                    if (Utils.isEmpty(element.getEventName())) {
                        RudderLogger.logError("Event name is not present.");
                        return;
                    }
                    Map<String, Object> properties = element.getProperties();
                    if (!isEmpty(properties)) {
                        // If it is a revenue event
                        if (properties.containsKey("revenue") && getDouble(properties.get("revenue")) != 0) {
                            String currency = "USD";
                            if (properties.containsKey("currency")) {
                                currency = getString(properties.get("currency"));
                            }
                            Singular.customRevenue(element.getEventName(), currency, getDouble(properties.get("revenue")));
                            return;
                        }
                        Singular.eventJSON(element.getEventName(), new JSONObject(properties));
                        return;
                    }
                    Singular.event(element.getEventName());
                    break;
                case MessageType.SCREEN:
                    if (Utils.isEmpty(element.getEventName())) {
                        RudderLogger.logError("Event name is not present.");
                        return;
                    }
                    if (!isEmpty(element.getProperties())) {
                        Singular.eventJSON("screen view " + element.getEventName(), new JSONObject(element.getProperties()));
                        return;
                    }
                    break;
                default:
                    RudderLogger.logWarn("SingularIntegrationFactory: MessageType is not specified");
                    break;
            }
        }
    }

    @Override
    public void reset() {
        Singular.unsetCustomUserId();
        RudderLogger.logInfo("Reset API is called.");
    }

    @Override
    public RudderClient getUnderlyingInstance() {
        return null;
    }
}
