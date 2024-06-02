package com.google.maps.android.compose

import com.android.tools.lint.detector.api.AnnotationInfo
import com.android.tools.lint.detector.api.AnnotationUsageInfo
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.uast.UElement
import org.jetbrains.uast.withContainingElements
import java.util.EnumSet

public class GoogleMapsComposableUsedOutsideGoogleMapsContentLambdaDetector : ComposableFunctionDetector(), SourceCodeScanner {
    public companion object {
        public val ISSUE: Issue = Issue.create(
            id = "GoogleMapsComposableUsedOutsideGoogleMaps",
            briefDescription = "Don't use Composables annotated with @GoogleMapsComposable outside of a GoogleMaps content lambda",
            explanation =
            """
            Using a Composable annotated with @GoogleMapsComposable outside of 
            a GoogleMaps content lambda causes crashes. Move the composable
            inside of the GoogleMaps content lambda.
            """,
            category = Category.CORRECTNESS,
            priority = 10,
            severity = Severity.ERROR,
            implementation = Implementation(
                GoogleMapsComposableUsedOutsideGoogleMapsContentLambdaDetector::class.java,
                EnumSet.of(Scope.JAVA_FILE)
            )
        )
    }

    override fun visitComposable(context: JavaContext, function: KtFunction) {
        function.parent
    }

    override fun visitAnnotationUsage(
        context: JavaContext,
        element: UElement,
        annotationInfo: AnnotationInfo,
        usageInfo: AnnotationUsageInfo
    ) {
        val name = annotationInfo.qualifiedName.substringAfterLast('.')
        val message = "`${usageInfo.type.name}` usage associated with " +
                "`@$name` on ${annotationInfo.origin}"

        val location = context.getLocation(element)

        element.withContainingElements.forEach {
            println(it.asLogString())
        }

        context.report(ISSUE, element, location, message)
    }
}
