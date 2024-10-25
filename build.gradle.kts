val kotlin_version: String = "2.0.21"
val ktor_version: String = "3.0.0"

plugins {
    `maven-publish`
    kotlin("jvm") version "2.0.21"
    id("com.gradleup.shadow") version "8.3.0"
    id("org.jetbrains.dokka") version "1.9.20"
}

group = "statix.org"
version = "1.1.0"
val projectVersion: String = version.toString()

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")
}

subprojects {
    apply(plugin = "org.jetbrains.dokka")
}

kotlin {
    jvmToolchain(21)
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks {
    shadowJar {
        minimize()
    }

    build {
        dependsOn(shadowJar)
    }
}

tasks.test {
    useJUnitPlatform()
}
