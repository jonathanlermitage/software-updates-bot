java -jar build/libs/software-updates-bot-0.0.1-SNAPSHOT.jar
git add report/report.json report/report.md
git commit report/report.json report/report.md -m"autocommit by bot"
git push --force
