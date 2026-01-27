#!/usr/bin/env bash
set -ex

echo "Java Run Result: ============="
java Streams 100000 200

echo "Native Run Without PGO Yet Result: ============="
./streams-native-without-pgo 100000 200

echo "Native Run With PGO Result: ============="
./streams-native-with-pgo 100000 200
