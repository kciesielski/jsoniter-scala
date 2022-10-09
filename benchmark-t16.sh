#!/bin/bash
sbt -batch -java-home /usr/lib/jvm/graalvm-ee-java17 -Dmacro.settings=print-codecs clean 'jsoniter-scala-benchmarkJVM/jmh:run -jvmArgsAppend "-Dgraal.EarlyGVN=true" -t 16 -p size=128 -prof gc -rf json -rff graalvm-ee-java17-t16.json .*' 2>&1 | tee graalvm-ee-java17-t16.txt
sbt -batch -java-home /usr/lib/jvm/graalvm-ee-java11 -Dmacro.settings=print-codecs clean 'jsoniter-scala-benchmarkJVM/jmh:run -jvmArgsAppend "-Dgraal.EarlyGVN=true" -t 16 -p size=128 -prof gc -rf json -rff graalvm-ee-java11-t16.json .*' 2>&1 | tee graalvm-ee-java11-t16.txt
sbt -batch -java-home /usr/lib/jvm/graalvm-ce-java19 -Dmacro.settings=print-codecs clean 'jsoniter-scala-benchmarkJVM/jmh:run -jvmArgsAppend "-Dgraal.EarlyGVN=true" -t 16 -p size=128 -prof gc -rf json -rff graalvm-ce-java19-t16.json .*' 2>&1 | tee graalvm-ce-java19-t16.txt
sbt -batch -java-home /usr/lib/jvm/graalvm-ce-java17 -Dmacro.settings=print-codecs clean 'jsoniter-scala-benchmarkJVM/jmh:run -jvmArgsAppend "-Dgraal.EarlyGVN=true" -t 16 -p size=128 -prof gc -rf json -rff graalvm-ce-java17-t16.json .*' 2>&1 | tee graalvm-ce-java17-t16.txt
sbt -batch -java-home /usr/lib/jvm/graalvm-ce-java11 -Dmacro.settings=print-codecs clean 'jsoniter-scala-benchmarkJVM/jmh:run -jvmArgsAppend "-Dgraal.EarlyGVN=true" -t 16 -p size=128 -prof gc -rf json -rff graalvm-ce-java11-t16.json .*' 2>&1 | tee graalvm-ce-java11-t16.txt
sbt -batch -java-home /usr/lib/jvm/openjdk-20 -Dmacro.settings=print-codecs clean 'jsoniter-scala-benchmarkJVM/jmh:run -t 16 -p size=128 -prof gc -rf json -rff openjdk-20-t16.json .*' 2>&1 | tee openjdk-20-t16.txt
sbt -batch -java-home /usr/lib/jvm/zulu-17 -Dmacro.settings=print-codecs clean 'jsoniter-scala-benchmarkJVM/jmh:run -t 16 -p size=128 -prof gc -rf json -rff zulu-17-t16.json .*' 2>&1 | tee zulu-17-t16.txt
sbt -batch -java-home /usr/lib/jvm/zulu-11 -Dmacro.settings=print-codecs clean 'jsoniter-scala-benchmarkJVM/jmh:run -t 16 -p size=128 -prof gc -rf json -rff zulu-11-t16.json .*' 2>&1 | tee zulu-11-t16.txt
