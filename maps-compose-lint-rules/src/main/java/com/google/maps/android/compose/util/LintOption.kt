// Copyright (C) 2023 Salesforce, Inc.
// SPDX-License-Identifier: Apache-2.0
package com.google.maps.android.compose.util

import com.android.tools.lint.client.api.Configuration

/**
 * A layer of indirection for implementations of option loaders without needing to extend from
 * Detector. This goes along with [OptionLoadingDetector].
 */
public interface LintOption {
    public fun load(configuration: Configuration)
}
