# RTNThreedRenderer

RTNThreedRenderer is a React Native library for rendering glTF models on Android. This library provides a simple way to incorporate 3D models into your React Native applications, allowing you to enhance user experiences with 3D graphics.

## Features

- Render glTF 2.0 models in your React Native application.
- Support for textures, animations, and more.

## Getting Started

### Installation

## Android:

1. Add kotlin in your android folder

   Go to android folder => build.gradle

   ```
   ext {
       .....
       kotlinVersion = '1.8.20'
       }
   dependencies {
       ....
       classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
   }
   ```

   Go to android => app => build.gradle
   At the top of the file add

   `apply plugin: 'kotlin-android'`

   then go to the bottom and in dependencies

   ```
   dependencies {
    .....
    implementation "org.jetbrains.kotlin:kotlin-stdlib:1.8.20"
   }
   ```

   ```

   ```

2. Install the package using npm or yarn:

   ```
   npm install rtnthreedrenderer
   # or
   yarn add rtnthreedrenderer
   ```

3. To use import the lib
   `import RTNThreedRenderer from 'rtn-threed-renderer/js/RTNThreedRendererNativeComponent';`

   ````
   <RTNThreedRenderer
     fileNameWithExtension="filename.glb"
     url="my_netowrk_url_where_glb_model_is_located"
     animationCount={0}

   />```
   ````

## RTNThreedRenderer currently supports glb models. It's essential to provide the filename with its extension, as the library caches the file upon the initial download. If you update the URL, make sure to also update the 'fileNameWithExtension' prop accordingly.
