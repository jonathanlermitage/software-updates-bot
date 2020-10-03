# software-updates-bot
:robot: A bot that looks for popular stuff updates, then push data into this repository as JSON, markdown report, and RSS/Atom feeds. Currently runs on a Raspberry Pi (every hour). See generated reports:  
* [JSON report](https://raw.githubusercontent.com/jonathanlermitage/software-updates-bot/master/report/report.json)
* [Markdown report](report/report.md)
* [Markdown report (ordered by date)](report/report-by-date.md)
* [RSS feed](https://raw.githubusercontent.com/jonathanlermitage/software-updates-bot/master/report/report.rss.xml)
* [Atom feed](https://raw.githubusercontent.com/jonathanlermitage/software-updates-bot/master/report/report.atom.xml)

This bot currently monitors updates for:
* 7+ Taskbar Tweaker
* AdoptOpenJDK: JDK8, 11, 14, and 15
* Gradle
* Inkscape
* Les Cast Codeurs (a great French podcast)
* MariaDB: 5.5 to 10.5
* Maven
* NodeJS: LTS and current
* PostgreSQL: 9.4 to 13
* Python: 2 and 3
* Red Hat Enterprise Linux
* Spring Boot
* VLC

Update checkers code is here: [source code](https://github.com/jonathanlermitage/software-updates-bot/tree/master/src/main/kotlin/biz/lermitage/sub/service/checker/impl). They are based on Jsoup scrapper, or simply by consumming JSON API.  
Don't hesitate to submit new checkers. 
