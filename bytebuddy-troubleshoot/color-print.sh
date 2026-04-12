#!/usr/bin/env bash

set -e

echo "hi"

function print() {
    printf "\033[1;35m$1\033[0m\n"
}

print "Starting the native app 🚀"

