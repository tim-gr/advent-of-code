plugins {
    kotlin("jvm") version "2.0.21"
    application
}

group = "com.tgad"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

tasks.test {
    useJUnitPlatform()
    maxHeapSize = "2048m"
}

kotlin {
    jvmToolchain(18)
}

application {
    mainClass.set("MainKt")
}
