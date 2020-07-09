plugins {
    id("org.jetbrains.kotlin.js") version "1.3.72"
}

group = "cn.edu.cczu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-js"))
}

kotlin.target.browser {
    dceTask {
        keep("ucode.encode", "ucode.decode")
    }
    distribution {
        directory = File("$projectDir/docs/")
    }
}
