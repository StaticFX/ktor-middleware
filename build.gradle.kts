import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinJvm
import com.vanniktech.maven.publish.SonatypeHost

val kotlin_version: String = "2.0.21"
val ktor_version: String = "3.0.0"

plugins {
    kotlin("jvm") version "2.0.21"
    id("com.vanniktech.maven.publish") version "0.28.0"
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

mavenPublishing {
    coordinates(
        groupId = "io.github.staticfx",
        artifactId = "ktor-middleware",
        version = projectVersion,
    )

    configure(
        KotlinJvm(
            javadocJar = JavadocJar.Dokka("dokkaHtml"),
            sourcesJar = true,
        ),
    )

    pom {
        name.set("KTOR Middleware")
        description.set("Middlewares for KTOR")
        inceptionYear.set("2024")
        url.set("https://github.com/StaticFX/ktor-middleware")

        developers {
            developer {
                name.set("Devin Fritz")
                email.set("devinfritz@gmx.de")
            }
        }

        licenses {
            license {
                name.set("gpl-3.0")
                url.set("https://www.gnu.org/licenses/gpl-3.0.html")
            }
        }

        scm {
            url.set("https://github.com/StaticFX/ktor-middleware")
        }
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
}

tasks.test {
    useJUnitPlatform()
}
