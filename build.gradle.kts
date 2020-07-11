@file:Suppress("EXPERIMENTAL_API_USAGE")

plugins {
    kotlin("multiplatform") version "1.3.72"
}

group = "cn.edu.cczu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val pageDir = "$projectDir/docs/"

tasks.clean {
    delete(pageDir)
}

kotlin {
    jvm {
        val jvmJar by tasks.getting(org.gradle.jvm.tasks.Jar::class) {
            manifest {
                attributes["Main-Class"] = "MainKt"
            }
        }
    }

    js().browser {
        dceTask {
            keep("ucode.encode", "ucode.decode")
        }
        distribution {
            directory = File(pageDir)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
            kotlin.srcDir("src/core/kotlin")
        }
        val jvmMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
            kotlin.srcDir("src/cli/kotlin")
        }
        val jsMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
            kotlin.srcDir("src/web/kotlin")
            resources.srcDir("src/web/resources")
        }
    }
}
