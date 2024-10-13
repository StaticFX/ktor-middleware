val kotlin_version: String by project
val logback_version: String by project
val ktor_version: String by project

plugins {
    kotlin("jvm") version "2.0.21"
    id("io.ktor.plugin") version "3.0.0"
    `maven-publish`
    id("signing")
}

group = "statix.org"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "io.github.staticfx"
            artifactId = "ktor-middleware"
            version = "1.0.0"
        }
    }
}
