import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val detektVersion = "1.23.8" // IMPORTANT don't forget to update plugin version too
val prjJavaVersion = JavaVersion.VERSION_21

plugins {
    val kotlinVersion = "2.1.21"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    id("org.springframework.boot") version "3.4.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.github.ben-manes.versions") version "0.52.0"
    id("project-report") // https://docs.gradle.org/current/userguide/project_report_plugin.html
    id("io.gitlab.arturbosch.detekt") version "1.23.8" // IMPORTANT set it to detektVersion's value
    id("biz.lermitage.oga") version "1.1.1"
}

group = "biz.lermitage"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = prjJavaVersion
java.targetCompatibility = prjJavaVersion

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.google.code.gson:gson:2.13.1")
    implementation("com.jayway.jsonpath:json-path:2.9.0")
    implementation("org.jsoup:jsoup:1.20.1") // https://jsoup.org/news/
    implementation("commons-io:commons-io:2.19.0")
    implementation("org.apache.commons:commons-lang3:3.17.0")
    implementation("com.rometools:rome:2.1.0") // https://github.com/rometools/rome/releases
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.retry:spring-retry:2.0.11")

    kapt("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")
}

detekt {
    toolVersion = detektVersion
    config.setFrom("./detekt-config.yml")
    buildUponDefaultConfig = true
    ignoreFailures = false
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(prjJavaVersion.toString())
        vendor = JvmVendorSpec.ADOPTIUM
    }
}

kotlin {
    compilerOptions {
        javaParameters = true
        freeCompilerArgs.addAll("-Xjsr305=strict"/*, "-Xuse-k2"*/)
        jvmTarget.set(JvmTarget.fromTarget(prjJavaVersion.toString()))
    }
}

configurations.matching { it.name == "detekt" }.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.jetbrains.kotlin") {
            useVersion("2.0.21") // IMPORTANT update if failed with "detekt was compiled with Kotlin XX but is currently running with YY"
        }
    }
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }
    withType<DependencyUpdatesTask> {
        checkForGradleUpdate = true
        gradleReleaseChannel = "current"
        revision = "release"
        rejectVersionIf {
            isNonStable(candidate.version)
        }
        outputFormatter = closureOf<com.github.benmanes.gradle.versions.reporter.result.Result> {
            unresolved.dependencies.removeIf { it.group.toString() == "org.jetbrains.kotlin" }
            com.github.benmanes.gradle.versions.reporter.PlainTextReporter(project, revision, gradleReleaseChannel)
                .write(System.out, this)
        }
    }
    withType<Detekt> {
        jvmTarget = prjJavaVersion.toString()
        reports {
            html.required.set(false)
            xml.required.set(false)
            txt.required.set(false)
        }
    }
}

fun isNonStable(version: String): Boolean {
    if (listOf("RELEASE", "FINAL", "GA").any { version.uppercase().endsWith(it) }) {
        return false
    }
    return listOf("alpha", "Alpha", "ALPHA", "b", "beta", "Beta", "BETA", "rc", "RC", "M", "EA", "pr", "atlassian").any {
        "(?i).*[.-]${it}[.\\d-]*$".toRegex().matches(version)
    }
}
