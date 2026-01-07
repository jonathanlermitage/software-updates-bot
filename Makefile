MAKEFLAGS += --no-print-directory --warn-undefined-variables

ifeq ($(OS),Windows_NT)
    mvnw_cmd := mvnw
else
    mvnw_cmd := ./mvnw
endif

default: build


.PHONY: intro
intro:
	@clear
	@echo -e '\n\e[1;34m------ [software-updates-bot] $(shell date) ------\e[0m\n'


.PHONY: log
log: intro ## run "git log" with pretty colors
	git log --pretty=format:"%C(green)%h\\ %C(yellow)[%ad]%Cred%d\\ %Creset%s%C(cyan)\\ [%cn]" --decorate --date=relative


.PHONY: wrapper
wrapper: intro ## set or upgrade maven wrapper to version "v" (example: make wrapper v=3.9.12)
	${mvnw_cmd} -N io.takari:maven:wrapper -Dmaven=${v}


.PHONY: fixgit
fixgit: intro ## fix executable permission flag on git index for required files
	git update-index --chmod=+x mvnw
	@echo -e '\e[1m"mvnw" should now have executable flag on git\e[0m'


.PHONY: build
build: intro ## build project
	${mvnw_cmd} clean package -DskipTests


.PHONY: cv
cv: intro ## check plugins and dependencies stable versions
	${mvnw_cmd} versions:display-property-updates -U


.PHONY: uv
uv: intro ## update plugins and dependencies versions
	${mvnw_cmd} versions:update-properties -U


.PHONY: oga
oga: intro ## check for deprecated groupId and artifactId couples
	${mvnw_cmd} biz.lermitage.oga:oga-maven-plugin:check -Dmaven.plugin.validation=VERBOSE



.PHONY: help
help: intro
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":[^:]*?## "}; {printf "\033[1;38;5;69m%-15s\033[0;38;5;38m %s\033[0m\n", $$1, $$2}'
