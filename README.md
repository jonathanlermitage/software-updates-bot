[![Stand With Ukraine](https://raw.githubusercontent.com/vshymanskyy/StandWithUkraine/main/banner2-direct.svg)](https://vshymanskyy.github.io/StandWithUkraine/)

# software-updates-bot
:robot: A bot that looks for popular stuff updates, then push data into this repository as JSON, markdown report, and RSS/Atom feeds. Currently runs on a Raspberry Pi (every hour). See generated reports:  
* [JSON report](https://raw.githubusercontent.com/jonathanlermitage/software-updates-bot/master/report/report.json)
* [Markdown report](report/report.md)
* [Markdown report (ordered by date)](report/report-by-date.md)
* [RSS feed](https://raw.githubusercontent.com/jonathanlermitage/software-updates-bot/master/report/report.rss.xml)
* [Atom feed](https://raw.githubusercontent.com/jonathanlermitage/software-updates-bot/master/report/report.atom.xml)

This bot currently monitors updates for:
* 7+ Taskbar Tweaker
* Adoptium (previously AdoptOpenJDK): every LTS version
* Golang SDK
* GPG4Win
* Gradle
* Inkscape
* K-Lite Codec Pack Basic
* Les Cast Codeurs (a great French podcast)
* MariaDB
* Maven
* Nilesoft Shell (a powerful context menu manager for MS Windows File Explorer)
* NodeJS: LTS and current
* PostgreSQL
* Python 3
* Spring Boot
* VeraCrypt
* VLC

> [!NOTE] 
> **June 2024**: [Visual C++ Redistributable Runtimes All-in-One](https://www.techpowerup.com/download/visual-c-redistributable-runtime-package-all-in-one/) and [ThrottleStop](https://www.techpowerup.com/download/techpowerup-throttlestop/) have been removed as the www.techpowerup.com website displays a captcha when coming from a GitHub action. I have no solution for that (if I want to continue to use GitHub actions). Ideas or contributions are highly welcome and appreciated.

Update checkers code is here: [source code](https://github.com/jonathanlermitage/software-updates-bot/tree/master/src/main/kotlin/biz/lermitage/sub/service/checker/impl). They are based on Jsoup scrapper, or simply by consumming JSON API.  
Don't hesitate to submit new checkers. 
