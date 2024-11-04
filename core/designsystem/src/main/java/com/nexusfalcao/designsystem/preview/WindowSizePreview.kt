package com.nexusfalcao.designsystem.preview

import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Phone - Portrait",
    group = "Window Sizes",
    device = "spec:width=411dp,height=891dp",
    apiLevel = 34,
)
@Preview(
    name = "Phone - Landscape",
    group = "Window Sizes",
    device = "spec:width=891dp,height=411dp,dpi=480",
    apiLevel = 34,
)
@Preview(
    name = "Foldable",
    group = "Window Sizes",
    device = "spec:width=673dp,height=841dp",
    apiLevel = 34,
)
@Preview(
    name = "Tablet - Portrait",
    group = "Window Sizes",
    device = "spec:width=800dp,height=1280dp,dpi=480",
    apiLevel = 34,
)
@Preview(
    name = "Tablet - Landscape",
    group = "Window Sizes",
    device = "spec:width=1280dp,height=800dp,dpi=240",
    apiLevel = 34,
)
@Preview(
    name = "Desktop",
    group = "Window Sizes",
    device = "spec:width=1920dp,height=1080dp,dpi=160",
    apiLevel = 34
)
annotation class WindowSizePreview
