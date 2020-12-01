#!/bin/bash

java -jar build/libs/software-updates-bot-0.0.1-SNAPSHOT.jar
git add report/report.json report/report.md report/report-by-date.md report/report.rss.xml report/report.atom.xml >> logs/git.log
git add report/status.md report/status.rss.xml report/status.atom.xml >> logs/git.log
git commit report/report.json report/report.md report/report-by-date.md report/report.rss.xml report/report.atom.xml report/status.md report/status.rss.xml report/status.atom.xml -m"autocommit by bot [ci skip]" >> logs/git.log
git push >> logs/git.log
