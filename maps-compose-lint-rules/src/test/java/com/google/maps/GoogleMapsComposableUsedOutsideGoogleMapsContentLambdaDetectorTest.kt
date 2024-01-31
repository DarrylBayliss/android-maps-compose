package com.google.maps

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import com.google.maps.android.compose.GoogleMapsComposableUsedOutsideGoogleMapsContentLambdaDetector
import org.intellij.lang.annotations.Language
import org.junit.Test

public class GoogleMapsComposableUsedOutsideGoogleMapsContentLambdaDetectorTest {
    @Test
    public fun `passes when a composable annotated with GoogleMapComposable is used within GoogleMaps`() {

        @Language("kotlin")
        val code =
            """
            @Retention(AnnotationRetention.BINARY)
            @ComposableTargetMarker(description = "Google Map Composable")
            @Target(
               AnnotationTarget.FILE,
               AnnotationTarget.FUNCTION,
               AnnotationTarget.PROPERTY_GETTER,
               AnnotationTarget.TYPE,
               AnnotationTarget.TYPE_PARAMETER,
            )
            annotation class GoogleMapComposable                

            @Composable
            fun GoogleMap() { }

            @Composable
            @GoogleMapComposable
            fun Marker() { }
            
            fun Map() {
                GoogleMap(
                  modifier = Modifier.fillMaxSize()) {
                    Marker()
                }
            }
            """.trimIndent()

        lint()
            .files(kotlin(code))
            .issues(GoogleMapsComposableUsedOutsideGoogleMapsContentLambdaDetector.ISSUE)
            .detector(GoogleMapsComposableUsedOutsideGoogleMapsContentLambdaDetector())
            .run()
            .expect("")
    }

    @Test
    public fun `fails when a composable not annotated with GoogleMapComposable is used within GoogleMaps`() {

        @Language("kotlin")
        val code =
            """
            @Retention(AnnotationRetention.BINARY)
            @ComposableTargetMarker(description = "Google Map Composable")
            @Target(
               AnnotationTarget.FILE,
               AnnotationTarget.FUNCTION,
               AnnotationTarget.PROPERTY_GETTER,
               AnnotationTarget.TYPE,
               AnnotationTarget.TYPE_PARAMETER,
            )
            annotation class GoogleMapComposable                

            @Composable
            fun GoogleMap() { }

            @Composable
            fun Box() { }
            
            fun Map() {
                GoogleMap(
                  modifier = Modifier.fillMaxSize()) {
                    Box()()
                }
            }
            """.trimIndent()

        lint()
            .files(kotlin(code))
            .issues(GoogleMapsComposableUsedOutsideGoogleMapsContentLambdaDetector.ISSUE)
            .detector(GoogleMapsComposableUsedOutsideGoogleMapsContentLambdaDetector())
            .run()
            .expect("")
    }
}
