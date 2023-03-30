# scala-intreactive-cli-template

### Installation 

#### Prerequisites

You need GraalVM installed. If you don't have it, you may check their docs [here](https://www.graalvm.org/java/quickstart/). If you're using SDKMAN!, GraalVM images are available to install easily [here](https://sdkman.io/jdks#grl).
```shell
# See Java versions and pick a GraalVM version, for example 22.1.0.r17-grl
sdk list java

sdk install java 22.1.0.r17-grl

# If you haven't set grl version as default, set it for the current terminal session
sdk use java 22.1.0.r17-grl
```

You need `native-image` installed. You can install it with GraalVM updater.
```shell
gu install native-image
```

#### Building Native Image with GraalVM

1. Build the native image with `show graalvm-native-image:packageBin`.

```shell
sbt 'show graalvm-native-image:packageBin'
# [info] ~/code/scala-intreactive-cli-template/target/graalvm-native-image/sbt-interactive-update
```

