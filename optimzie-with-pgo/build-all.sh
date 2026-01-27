#!/usr/bin/env bash
set -ex

javac Streams.java
native-image -Ob --pgo-instrument Streams -o streams-native
cp streams-native streams-native-without-pgo

# need to obtain instrumented performance info during the run
./streams-native 100000 200

native-image -Ob --pgo Streams -o streams-native
mv streams-native streams-native-with-pgo
