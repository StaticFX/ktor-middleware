val kotlin_version: String = "2.0.21"
val ktor_version: String = "3.0.0"

plugins {
    kotlin("jvm") version "2.0.21"
    `maven-publish`
    id("org.jetbrains.dokka") version "1.9.20"
}

group = "statix.org"
version = "1.0.8"
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

tasks.register<Jar>("sourcesJar") {
    from(sourceSets.main.get().allSource)
    archiveClassifier.set("sources")
}

tasks.register<Jar>("javadocJar") {
    dependsOn(tasks.dokkaJavadoc)
    from(tasks.dokkaJavadoc.get().outputDirectory)
    archiveClassifier.set("javadoc")
}

tasks.test {
    useJUnitPlatform()
}
