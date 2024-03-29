plugins {
    kotlin("jvm") version "1.9.21"
    application
}

group = "com.tgad"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
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
