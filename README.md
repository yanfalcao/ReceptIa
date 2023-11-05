# ReceptIa

<h1 align="center">
  <br>
  <img src="https://i.imgur.com/CGHUQIh.png" alt="Guiautismo" width="30%">
  <br>
</h1>

<p align="center">App to create recipes based on your favorite ingredients using ChatGPT API.</p>

<p align="center">
  <a href="http://makeapullrequest.com">
    <img src="https://img.shields.io/badge/contribuition-welcome-brightgreen.svg" alt="PRs Welcome">
  </a>
  <a href="https://www.repostatus.org/#active">
    <img src="https://www.repostatus.org/badges/latest/active.svg" alt="Project Status: Active – The project has reached a stable, usable state and is being actively developed." />
  </a>  
</p>

## Application

<div style="display:flex;">
  <img alt="App image" src="https://i.imgur.com/hZnLQ4w.jpg" width="23%">
  <img alt="App image" src="https://i.imgur.com/G1fRXGC.jpg" width="23%">
  <img alt="App image" src="https://i.imgur.com/ODttPqF.jpg" width="23%">
  <img alt="App image" src="https://i.imgur.com/mtWfCeE.jpg" width="23%">
</div>

## **Description**

App developed to test my knowledge in Android Native and use new technologies and best practices. The app is designed to create recipes using artificial intelligence, specifically ChatGPT API, these recipes are generated based on your ingredients preferences, creating something perfect for you.

The app sends to the AI, information about ingredients that you are allergic to, intolerant of, or dislike. Being inclusive of all the users.

## Features

:heavy_check_mark: Login with Google.

:heavy_check_mark: Create recipes using ChatGPT API.

:heavy_check_mark: Details of your recipes.

:heavy_check_mark: Historic of your recipes with filters.

:heavy_check_mark: Favorite your recipes.

## Tools

<a href="https://developer.android.com/?hl=pt-br/" target="_blank"> 
  <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/3/31/Android_robot_head.svg/2560px-Android_robot_head.svg.png" alt="Android" width="60" height="40"/> 
</a>
<a href="https://developer.android.com/jetpack/compose?hl=pt-br" target="_blank"> 
  <img src="https://optimise2.assets-servd.host/gratis-creeper/production/blog/jetpack-compose-icon-rectangular.png?w=1500&auto=compress%2Cformat&fit=crop&dm=1664908177&s=fb3fbec73e74dfb24e5f0604f31f8f4f" alt="Android Compose" width="60" height="40"/> 
</a> 
<a href="https://developer.android.com/studio" target="_blank"> 
  <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/e/e3/Android_Studio_Icon_%282014-2019%29.svg/1200px-Android_Studio_Icon_%282014-2019%29.svg.png" alt="androidStudio" width="40" height="40"/> 
</a>
<a href="https://firebase.google.com/?hl=pt" target="_blank">
  <img src="https://www.gstatic.com/mobilesdk/160503_mobilesdk/logo/2x/firebase_96dp.png" alt="firebase" width="40" height="40"/> 
</a>
<a href="https://platform.openai.com/docs/guides/gpt" target="_blank">
  <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/0/04/ChatGPT_logo.svg/768px-ChatGPT_logo.svg.png" alt="ChatGPT API" width="40" height="40"/> 
</a>
<a href="https://kotlinlang.org/" target="_blank">
  <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/7/74/Kotlin_Icon.png/1024px-Kotlin_Icon.png" alt="Kotlin" width="40" height="40"/> 
</a>

## Getting Started

1. [Install Android Studio](https://developer.android.com/studio/install.html),
if you don't already have it;
2. Download or clone the [ReceptIa Project](https://github.com/yanfalcao/ReceptIa);
3. [Add Firebase to your Android project](https://firebase.google.com/docs/android/setup), following the intructions of <b>Option 1</b>, and the <b>Step 1</b>, <b>Step 2</b> and <b>Step 3.1</b>;
4. If you haven't yet specified your app's SHA fingerprint in Firebase, do so from the [Settings page](https://console.firebase.google.com/project/_/settings/general/?_gl=1*glgr71*_ga*ODkwNzc5NTgyLjE2OTc0OTY4Mzc.*_ga_CW55HF8NVT*MTY5OTE0MDQxNy4xNS4xLjE2OTkxNDUwNjYuMTEuMC4w) of the Firebase console. Refer to [Authenticating Your Client](https://developers.google.com/android/guides/client-auth) for details on how to get your app's SHA fingerprint.
5. Enable Google as a sign-in method in the Firebase console:
   a. In the [Firebase console](https://console.firebase.google.com/?_gl=1*1t4rqnk*_ga*ODkwNzc5NTgyLjE2OTc0OTY4Mzc.*_ga_CW55HF8NVT*MTY5OTE0MDQxNy4xNS4xLjE2OTkxNDU0MTYuNjAuMC4w), open the Auth section.
   b. On the Sign in method tab, enable the Google sign-in method and click Save.
6. When prompted in the console, download the updated Firebase config file (google-services.json), which now contains the OAuth client information required for Google sign-in.
7. Move this updated config file into your Android Studio project, replacing the now-outdated corresponding config file. (See [Add Firebase to your Android project.](https://firebase.google.com/docs/android/setup#add-config-file))
8. In your firebase console open <b>Authentication</b>. Click <b>Sign-in method</b>, then click <b>Google</b>. Finally open <b>Web SDK Configuration</b> and save the <b>Web Client Id</b>;
9. Now create your developer account on [OpenAI platform](https://platform.openai.com/)
10. Click <b>Settings</b>. Open the <b>Manage Account</b>, then click <b>API Keys</b>. Finally, create your <b>secret key</b> and save its value;
11. Open the ReceptIA into Android Studio;
12. Open the ```local.propeties``` file. Add the parameters below with the values saved on the last steps:
  ```
    GPT_API_KEY={YOUR_OPEN_AI_SECRET_KEY}
	  WEB_CLIENT_ID={YOUR_FIREBASE_WEB_CLIENT_ID} 
  ```
13. Build and run.



## Developer

| ![Yan Falcão](https://avatars.githubusercontent.com/u/33384608?s=150&u=e409234f3e92d08fdf5427560d3181c690ceb86f&v=4)|
|:---------------------:|
|  [Yan Falcão](https://github.com/yanfalcao/)   |
