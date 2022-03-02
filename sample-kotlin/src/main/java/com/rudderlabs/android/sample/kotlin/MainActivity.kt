package com.rudderlabs.android.sample.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rudderlabs.android.sample.kotlin.MainApplication.Companion.rudderClient
import com.rudderstack.android.sdk.core.RudderProperty

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        identify()
        sendEvents()
        sendRevenueEvents()
        trackProductsEvent()
        screen()
    }

    private fun identify() {
        rudderClient.identify("Android User");
    }

    private fun sendEvents() {
        rudderClient.track("Custom track events")
    }

    private fun sendRevenueEvents() {
        rudderClient.track("Order Completed",
            RudderProperty()
                .putValue("revenue", 1251)
                .putValue("currency", "INR")

                .putValue("coupon", "coupon_value")
                .putValue("affiliation", "affiliation_value")
                .putValue("shipping", "123")
                .putValue("tax", "124")
                .putValue("order_id", "order_id_1")
                .putValue("category", "category_value")
                .putValue("quantity","4235")
                .putValue("Custom_1", "234.4512")

                .putValue("receipt", "1")
        )
    }

    private fun trackProductsEvent() {
        val map= mapOf("product_id" to "product_id_2", "name" to "name_2", "price" to 2000, "quantity" to "20", "category" to "Category_2", "extra" to "extra_2")
        val map1= mapOf("product_id" to "product_id_1", "name" to "name_1", "price" to "1000","quantity" to "10", "category" to "Category_1", "extra" to "extra_1")
        val list= listOf(map, map1);
        rudderClient.track("Order Completed",
            RudderProperty()

                .putValue("coupon", "coupon_1_1")
                .putValue("currency", "INR")

                .putValue("affiliation", "affiliation_1")
                .putValue("shipping", "123")
                .putValue("tax", "124")
                .putValue("order_id", "order_id_1")

                .putValue("products", list)
//                .putValue("products", map)

                .putValue("Custom_Order_Completed", "Custom_1_2")
                .putValue("Custom_2", "234.4512")

        )
    }

    private fun screen() {
        rudderClient.screen("Main Screen",
        RudderProperty()
            .putValue("Width", 15)
            .putValue("Pixel", "1080p"))
    }
}
