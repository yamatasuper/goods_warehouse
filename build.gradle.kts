//import io.ktor.plugin.features.DockerPortMapping
//import io.ktor.plugin.features.DockerPortMappingProtocol
//
//val kotlin_version: String by project
//val logback_version: String by project
//
//plugins {
//    kotlin("jvm") version "2.0.20"
//    id("io.ktor.plugin") version "3.0.0-rc-1"
//}
//
//group = "com.goods.warehouse"
//version = "0.0.1"
//
//application {
//    mainClass.set("io.ktor.server.netty.EngineMain")
//
//    val isDevelopment: Boolean = project.ext.has("development")
//    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
//}
//
//repositories {
//    mavenCentral()
//}
//
//dependencies {
//    implementation("io.ktor:ktor-server-core-jvm")
//    implementation("io.ktor:ktor-server-content-negotiation-jvm")
//    implementation("io.ktor:ktor-server-netty-jvm")
//    implementation("ch.qos.logback:logback-classic:$logback_version")
//    implementation("io.ktor:ktor-server-config-yaml")
//    testImplementation("io.ktor:ktor-server-test-host-jvm")
//    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
//}

import io.ktor.plugin.features.*

        plugins {
            application
            alias(libs.plugins.kotlin.jvm)
            alias(libs.plugins.kotlin.serialization)
            id("io.ktor.plugin") version "2.3.8"
        }

group = "com.goods.warehouse"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

ktor {
    docker {
        jreVersion.set(JavaVersion.VERSION_21)
        localImageName.set("goods.warehouset-docker-image")
        imageTag.set("0.0.1-preview")

        portMappings.set(
            listOf(
                DockerPortMapping(outsideDocker = 80, insideDocker = 8080, DockerPortMappingProtocol.TCP)
            )
        )
    }

    fatJar {
        archiveFileName.set("fat.jar")
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.cio)
    implementation(libs.ktor.server.content)
    implementation(libs.ktor.server.kotlinx)
    implementation(libs.ktor.server.netty)

    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)

    implementation(libs.postgress)
    implementation(libs.hikari)

    implementation(libs.bcrypt)

    implementation(libs.logback)
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.tests)
}

tasks.create("stage") {
    dependsOn("installDist")
}
