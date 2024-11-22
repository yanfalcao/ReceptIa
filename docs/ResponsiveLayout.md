# Responsive Layout

The responsive layout of the **Receptia** app is crucial for providing a seamless user experience across a wide range of device sizes and orientations. We ensure the app adapts fluidly to compact, medium and expanded screens, offering an intuitive interface whether viewed on a smartphone, tablet, or desktop.

## Phone

On phones, the main difference is the layout's support for landscape orientation, where the design takes full advantage of the available width. This is achieved by using a [PermanentNavigationDrawer](https://composables.com/material3/permanentnavigationdrawer) for consistent navigation and a [LisDetailsPaneScaffold](https://developer.android.com/develop/ui/compose/layouts/adaptive/list-detail) to provide an adaptive, two-pane layout.

<div style="display:flex;">
  <img align=top alt="App image" src="images/Responsive_phone_landscape_home_2.png" width="55%">
  <img align=top alt="App image" src="images/Responsive_phone_landscape_catalog.png" width="55%">
</div> 

## Foldable

On foldables, the key difference is the use of the [PermanentNavigationDrawer](https://composables.com/material3/permanentnavigationdrawer) in all orientations, as the screen size is significantly larger compared to a phone in portrait mode.

<img align=top alt="App image" src="images/Responsive_foldable_home.png" width="55%">

## Tablet

<div style="display:flex;">
  <img align=top alt="App image" src="images/Responsive_tablet_landscape_catalog.png" width="100%">
  <img align=top alt="App image" src="images/Responsive_tablet_portrait_catalog.png" width="55%">
</div>


