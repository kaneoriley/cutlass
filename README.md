[![Release](https://jitpack.io/v/com.github.oriley-me/cutlass.svg)](https://jitpack.io/#com.github.oriley-me/cutlass) [![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0) [![Build Status](https://travis-ci.org/oriley-me/cutlass.svg?branch=master)](https://travis-ci.org/oriley-me/cutlass) [![Dependency Status](https://www.versioneye.com/user/projects/56b6abea0a0ff5002c8603c6/badge.svg?style=flat)](https://www.versioneye.com/user/projects/56b6abea0a0ff5002c8603c6)

# Cutlass
![Logo](artwork/icon.png)

TODO: Actual README

# Gradle Dependency

1. Add JitPack.io to your repositories list in the root projects build.gradle:

```gradle
repositories {
    maven { url "https://jitpack.io" }
}
```

2. Add cutlass-plugin to your buildscript classpath:

```gradle
buildscript {
    dependencies {
        classpath 'me.oriley.cutlass:cutlass-plugin:0.1b'
    }
}
```

3. Apply plugin to your application or library project:

```gradle
apply plugin: 'com.android.application' || apply plugin: 'com.android.library'
apply plugin: 'me.oriley.cutlass-plugin'
```

4. Add the following to your module dependencies:

```gradle
dependencies {
    provided 'me.oriley.cutlass:cutlass-annotations:0.1b'
    apt 'me.oriley.cutlass:cutlass-processor:0.1b'
    compile 'me.oriley.cutlass:cutlass-runtime:0.1b'
}
```
