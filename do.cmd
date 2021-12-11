@echo off

if [%1] == [help] (
  echo  w $V:    set gradle wrapper
  echo  fixgit:  fix permission flag on git index for required files
  echo  test:    run unit tests
  echo  cv:      check dependencies and Gradle updates
  echo  p:       generate runnable JAR
  echo  dep:     generate the project dependency report
  echo  oga:     check for deprecated groupId and artifactId couples
)

if [%1] == [w] (
  gradle wrapper --gradle-version=%2 --no-daemon
)
if [%1] == [fixgit] (
  echo git update-index --chmod=+x gradlew
  git update-index --chmod=+x gradlew
  echo git update-index --chmod=+x build.sh
  git update-index --chmod=+x build.sh
  echo git update-index --chmod=+x run.sh
  git update-index --chmod=+x run.sh
)
if [%1] == [test] (
  gradlew clean cleanTest test --warning-mode all
)
if [%1] == [cv] (
  gradlew dependencyUpdates
)
if [%1] == [p] (
  gradlew clean bootJar --warning-mode all
)
if [%1] == [dep] (
  gradlew htmlDependencyReport
)
if [%1] == [oga] (
  gradlew biz-lermitage-oga-gradle-check
)
