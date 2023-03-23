# software-updates-bot
:robot: A bot that looks for popular stuff updates, then push data into this repository as JSON, markdown report, and RSS/Atom feeds. Currently runs on a Raspberry Pi (every hour). See generated reports:  
* [JSON report](https://raw.githubusercontent.com/jonathanlermitage/software-updates-bot/master/report/report.json)
* [Markdown report](report/report.md)
* [Markdown report (ordered by date)](report/report-by-date.md)
* [RSS feed](https://raw.githubusercontent.com/jonathanlermitage/software-updates-bot/master/report/report.rss.xml)
* [Atom feed](https://raw.githubusercontent.com/jonathanlermitage/software-updates-bot/master/report/report.atom.xml)

This bot currently monitors updates for:
* 7+ Taskbar Tweaker
* Adoptium (previously AdoptOpenJDK): every LTS and the latest version
* Golang SDK
* GPG4Win
* Gradle
* Inkscape
* Intel Graphics, Wi-Fi and Bluetooth drivers for Windows
* K-Lite Codec Pack Basic
* Les Cast Codeurs (a great French podcast)
* MariaDB
* Maven
* Nilesoft Shell (a powerful context menu manager for MS Windows File Explorer)
* NodeJS: LTS and current
* PostgreSQL
* Python 3
* Spring Boot
* ThrottleStop
* VeraCrypt
* Visual C++ Redistributable Runtimes All-in-One
* VLC

Update checkers code is here: [source code](https://github.com/jonathanlermitage/software-updates-bot/tree/master/src/main/kotlin/biz/lermitage/sub/service/checker/impl). They are based on Jsoup scrapper, or simply by consumming JSON API.  
Don't hesitate to submit new checkers. 

---

:warning: deprecated, the task now runs on a [GitHub workflow](https://github.com/jonathanlermitage/software-updates-bot/actions/workflows/run-bot.yml) :warning:

Scheduled tasks on my RaspberryPi (`crontab -u pi -e`, `/etc/init.d/cron reload`):

```bash
SHELL=/bin/bash
BASH_ENV="/home/pi/.bashrc"

# run software-updates-bot hourly
0 * * * * eval $(ssh-agent -s) && ssh-add ~/.ssh/id_rsa_************* && cd ~/projects/software-updates-bot/ && git fetch origin && git reset --hard origin && ./run.sh && pkill -f ssh-agent

# rotate software-updates-bot git log At 00:40 on Monday  https://crontab.guru/#40_0_*_*_1
40 0 * * 1 rm ~/projects/software-updates-bot/logs/git.log.gz && gzip ~/projects/software-updates-bot/logs/git.log && rm ~/projects/software-updates-bot/logs/git.log
```

Please note that this script is absolutely not optimized at all (especially the ssh part). It works, and I'm fine with that :-D
