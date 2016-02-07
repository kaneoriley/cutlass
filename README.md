[![Release](https://jitpack.io/v/com.github.oriley-me/cutlass.svg)](https://jitpack.io/#com.github.oriley-me/cutlass) [![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0) [![Build Status](https://travis-ci.org/oriley-me/cutlass.svg?branch=master)](https://travis-ci.org/oriley-me/cutlass) [![Dependency Status](https://www.versioneye.com/user/projects/56b6abea0a0ff5002c8603c6/badge.svg?style=flat)](https://www.versioneye.com/user/projects/56b6abea0a0ff5002c8603c6)

# Cutlass

TODO: Actual README

# Gradle Dependency

Firstly, you need to add JitPack.io to your repositories list in the root projects build.gradle:

```gradle
repositories {
    maven { url "https://jitpack.io" }
}
```

Then, add the following to your module dependencies:

```gradle
dependencies {
    provided 'me.oriley.cutlass:cutlass-annotations:0.1'
    apt 'me.oriley.cutlass:cutlass-processor:0.1'
    compile 'me.oriley.cutlass:cutlass-runtime:0.1'
}
```
