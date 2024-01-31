package com.google.maps.android.compose

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue

public class ComposeMapsLintRuleRegistry : IssueRegistry() {

    override val vendor: Vendor = Vendor(
        vendorName = "Google Maps Open Source",
        identifier = "com.google.maps.android.compose.lint",
        feedbackUrl = "https://github.com/googlemaps/android-maps-compose/issues",
    )

    override val api: Int = CURRENT_API

    override val minApi: Int = CURRENT_API

    override val issues: List<Issue> =
        listOf(
            GoogleMapsComposableUsedOutsideGoogleMapsContentLambdaDetector.ISSUE
        )
}
