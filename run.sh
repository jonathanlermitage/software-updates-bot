#!/bin/bash

# kill previous scrapper if it did not stop correctly (process may hang with some JDK releases)
PID_FILE=./manon.pid
if test -f "$PID_FILE"; then
    echo "$FILE exists, will kill process $PID_FILE"
    kill -9 "$(cat "$PID_FILE")"
    echo "killed"
fi

# run the scrapper
java -Xms64m -Xmx128m -XX:MaxMetaspaceSize=64m -XX:MaxRAM=128m -XX:+TieredCompilation -XX:TieredStopAtLevel=1 -Dfile.encoding=UTF-8 -jar build/libs/software-updates-bot-0.0.1-SNAPSHOT.jar

# TODO try Dynamic Class-Data Sharing with jdk11
#JSA=build/libs/sub.jsa
# run the scrapper
#java -Xms64m -Xmx128m -XX:MaxMetaspaceSize=64m -XX:MaxRAM=128m -XX:+TieredCompilation -XX:TieredStopAtLevel=1 -XX:ArchiveClassesAtExit="$JSA" -XX:SharedArchiveFile="$JSA" -jar build/libs/software-updates-bot-0.0.1-SNAPSHOT.jar

# git logging and push
date >> logs/git.log

git add report/report.json report/report.md report/report-by-date.md report/report.rss.xml report/report.atom.xml >> logs/git.log
git add report/status.md report/status.rss.xml report/status.atom.xml >> logs/git.log

git commit report/report.json report/report.md report/report-by-date.md report/report.rss.xml report/report.atom.xml report/status.md report/status.rss.xml report/status.atom.xml -m"autocommit by bot [ci skip]" >> logs/git.log

git push >> logs/git.log
