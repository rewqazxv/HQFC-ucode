@file:Suppress("EXPERIMENTAL_API_USAGE")

plugins {
    kotlin("multiplatform") version "1.3.72"
}

group = "cn.edu.cczu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        val jvmJar by tasks.getting(org.gradle.jvm.tasks.Jar::class) {
            doFirst {
                manifest {
                    attributes["Main-Class"] = "MainKt"
                }
            }
        }
    }

    js().browser {
        dceTask {
            keep("ucode.encode", "ucode.decode")
        }
        distribution {
            directory = File("$projectDir/docs/")
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        val jvmMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        val jsMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }
    }
}
