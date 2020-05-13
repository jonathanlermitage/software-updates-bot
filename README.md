# software-updates-bot
:construction_worker: A bot that looks for popular stuff updates, then push data into this repository as JSON, markdown report, and RSS/Atom feeds. Currently runs on a Raspberry Pi (every hour). See generated reports:  
* [JSON report](https://raw.githubusercontent.com/jonathanlermitage/software-updates-bot/master/report/report.json)
* [Markdown report](report/report.md)
* [Markdown report (ordered by date)](report/report-by-date.md)
* [RSS feed](https://raw.githubusercontent.com/jonathanlermitage/software-updates-bot/master/report/report.rss.xml)
* [Atom feed](https://raw.githubusercontent.com/jonathanlermitage/software-updates-bot/master/report/report.atom.xml)

This bot currently monitors updates for:
* AdoptOpenJDK: JDK8, 11 and 14
* Inkscape
* Les Cast Codeurs (a great French podcast)
* MariaDB: 5.5 to 10.5
* Maven
* NodeJS: LTS and current
* Spring Boot
* VLC

For fun, and maybe to create an IntelliJ plugin that shows alerts when your favorite stuff (SDK, databases, libraries, etc.) can receive updates. Wait and see!
