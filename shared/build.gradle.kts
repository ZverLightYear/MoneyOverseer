plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    kotlin("plugin.serialization").version("1.9.21")
    id("app.cash.sqldelight") version "2.0.1"
}

repositories {
    google()
    mavenCentral()
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("com.jetbrains.handson.kmm.shared.cache")
        }
    }
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    val coroutinesVersion = "1.7.3"
    val ktorVersion = "2.3.5"
    val dateTimeVersion = "0.4.1"

    sourceSets {
        commonMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
            implementation("io.ktor:ktor-client-core:$ktorVersion")
            implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
            implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:$dateTimeVersion")
//            implementation("app.cash.sqldelight:sqlite-driver:2.0.1")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        androidMain.dependencies {
            implementation("io.ktor:ktor-client-android:$ktorVersion")
            implementation("app.cash.sqldelight:android-driver:2.0.1")
        }

        iosMain.dependencies {
            implementation("io.ktor:ktor-client-darwin:$ktorVersion")
            implementation("app.cash.sqldelight:native-driver:2.0.1")
        }
    }
}

android {
    namespace = "com.zverolands.moneyoverseer"
    compileSdk = 34
    defaultConfig {
        minSdk = 33
    }
}
