package com.rudderlabs.android.sample.kotlin

import android.app.Application
import com.rudderstack.android.integration.singular.SingularIntegrationFactory
import com.rudderstack.android.sdk.core.RudderClient
import com.rudderstack.android.sdk.core.RudderConfig
import com.rudderstack.android.sdk.core.RudderLogger

class MainApplication : Application() {
    companion object {
        lateinit var rudderClient: RudderClient
        const val WRITE_KEY = "25YduA2CZ5DyZmmXK2IbJzo1OVq"
        const val DATA_PLANE_URL = "https://1043-2405-201-8000-6165-356c-589e-7084-f5a1.ngrok.io"
        const val CONTROL_PLANE_URL = "https://7aa4-2405-201-8000-6165-356c-589e-7084-f5a1.ngrok.io"
    }

    override fun onCreate() {
        super.onCreate()
        rudderClient = RudderClient.getInstance(
            this,
            WRITE_KEY,
            RudderConfig.Builder()
                .withDataPlaneUrl(DATA_PLANE_URL)
                .withControlPlaneUrl(CONTROL_PLANE_URL)
                .withLogLevel(RudderLogger.RudderLogLevel.NONE)
                .withFactory(SingularIntegrationFactory.FACTORY)
                .withTrackLifecycleEvents(false)
                .build()
        )
    }
}