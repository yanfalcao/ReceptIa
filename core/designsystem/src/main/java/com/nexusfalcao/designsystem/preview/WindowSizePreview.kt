package com.nexusfalcao.designsystem.preview

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Phone - Portrait",
    group = "Window Sizes",
    device = "spec:shape=Normal,width=411,height=891,unit=dp,dpi=480",
)
@Preview(
    name = "Phone - Landscape",
    group = "Window Sizes",
    device = "spec:shape=Normal,width=891,height=411,unit=dp,dpi=480",
)
@Preview(
    name = "Foldable",
    group = "Window Sizes",
    device = "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480",
)
@Preview(
    name = "Tablet - Portrait",
    group = "Window Sizes",
    device = "spec:shape=Normal,width=800,height=1280,unit=dp,dpi=480",
)
@Preview(
    name = "Tablet - Landscape",
    group = "Window Sizes",
    device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480",
)
@Preview(name = "Desktop", group = "Window Sizes", device = Devices.DESKTOP)
annotation class WindowSizePreview
