plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "top.cubik65536"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // CompareVersion.kt
    implementation("io.github.g00fy2:versioncompare:1.5.0")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}