package com.nexusfalcao.designsystem.preview

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Phone - Portrait",
    group = "Window Sizes",
    device = Devices.PHONE,
)
@Preview(
    name = "Phone - Landscape",
    group = "Window Sizes",
    device = "spec:shape=Normal,width=891,height=411,unit=dp,dpi=480",
)
@Preview(
    name = "Foldable",
    group = "Window Sizes",
    device = Devices.FOLDABLE,
)
@Preview(
    name = "Tablet - Portrait",
    group = "Window Sizes",
    device = "spec:shape=Normal,width=800,height=1280,unit=dp,dpi=480",
)
@Preview(
    name = "Tablet - Landscape",
    group = "Window Sizes",
    device = Devices.TABLET,
)
@Preview(name = "Desktop", group = "Window Sizes", device = Devices.DESKTOP)
annotation class WindowSizePreview
