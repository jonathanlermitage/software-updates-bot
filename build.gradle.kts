import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val detektVersion = "1.17.1"

plugins {
    val kotlinVersion = "1.5.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    id("org.springframework.boot") version "2.5.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.github.ben-manes.versions") version "0.39.0"
    id("project-report") // https://docs.gradle.org/current/userguide/project_report_plugin.html
    id("io.gitlab.arturbosch.detekt") version "1.17.1"
}

group = "biz.lermitage"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.google.code.gson:gson:2.8.7")
    implementation("com.jayway.jsonpath:json-path:2.5.0")
    implementation("org.jsoup:jsoup:1.13.1")
    implementation("commons-io:commons-io:2.9.0")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("com.rometools:rome:1.15.0")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.retry:spring-retry:1.3.1")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")
}

detekt {
    toolVersion = detektVersion
    config = files("./detekt-config.yml")
    buildUponDefaultConfig = true

    reports {
        html.enabled = false
        xml.enabled = false
        txt.enabled = false
    }
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }
    withType<KotlinCompile> {
        kotlinOptions {
            javaParameters = true
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }
    withType<DependencyUpdatesTask> {
        checkForGradleUpdate = true
        gradleReleaseChannel = "current"
        outputFormatter = "plain"
        outputDir = "build"
        reportfileName = "dependencyUpdatesReport"
        revision = "release"
    }
    withType<Detekt> {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

fun isNonStable(version: String): Boolean {
    if (listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().endsWith(it) }) {
        return false
    }
    return listOf("alpha", "Alpha", "ALPHA", "b", "beta", "Beta", "BETA", "rc", "RC", "M", "EA", "pr", "atlassian").any {
        "(?i).*[.-]${it}[.\\d-]*$".toRegex().matches(version)
    }
}

tasks.named("dependencyUpdates", DependencyUpdatesTask::class.java).configure {
    resolutionStrategy {
        componentSelection {
            all {
                if (isNonStable(candidate.version)) {
                    println(" - [ ] ${candidate.module}:${candidate.version} candidate rejected")
                    reject("Not stable")
                } else {
                    println(" - [X] ${candidate.module}:${candidate.version} candidate accepted")
                }
            }
        }
    }
}
