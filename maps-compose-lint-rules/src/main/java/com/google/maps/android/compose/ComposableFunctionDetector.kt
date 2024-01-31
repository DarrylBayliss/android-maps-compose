// Copyright (C) 2023 Salesforce, Inc.
// Copyright 2022 Twitter, Inc.
// SPDX-License-Identifier: Apache-2.0

package com.google.maps.android.compose

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.android.tools.lint.detector.api.isKotlin
import com.google.maps.android.compose.util.LintOption
import com.google.maps.android.compose.util.isComposable
import org.jetbrains.kotlin.psi.KtFunction
import org.jetbrains.uast.UMethod

public abstract class ComposableFunctionDetector(vararg options: LintOption) :
    OptionLoadingDetector(*options), SourceCodeScanner {

    final override fun getApplicableUastTypes(): List<Class<UMethod>> = listOf(UMethod::class.java)

    final override fun createUastHandler(context: JavaContext): UElementHandler? {
        if (!isKotlin(context.uastFile?.lang)) return null
        return object : UElementHandler() {
            override fun visitMethod(node: UMethod) {
                val ktFunction = node.sourcePsi as? KtFunction ?: return
                if (ktFunction.isComposable) {
                    visitComposable(context, ktFunction)
                }
            }
        }
    }

    public abstract fun visitComposable(context: JavaContext, function: KtFunction)
}
