#!/bin/bash

git fetch origin
git reset --hard origin/master
./gradlew clean bootJar detekt --no-daemon
