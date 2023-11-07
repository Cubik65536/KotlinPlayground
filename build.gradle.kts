val ktorVersion = "2.3.5"
val logbackVersion = "1.4.11"

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
    // Networking and JSON parsing
    implementation("io.ktor:ktor-client-core:$ktorVersion")
//    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation("com.beust:klaxon:5.5")

    // Logging
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

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