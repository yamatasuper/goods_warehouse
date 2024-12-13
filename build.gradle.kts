//import io.ktor.plugin.features.*
//import org.jetbrains.dokka.gradle.DokkaTask

//plugins {
//    application
//    alias(libs.plugins.kotlin.jvm)
//    alias(libs.plugins.kotlin.serialization)
//    alias(libs.plugins.dokka)
//    id("io.ktor.plugin") version "2.3.8"
//}
//
//group = "com.goods.product"
//version = "0.0.1"
//
//application {
//    mainClass.set("com.goods.product.ApplicationKt")
//}
//
//ktor {
//    docker {
//        jreVersion.set(JavaVersion.VERSION_21)
//        localImageName.set("goods.product-docker-image")
//        imageTag.set("0.0.1-preview")
//
//        portMappings.set(
//            listOf(
//                DockerPortMapping(outsideDocker = 80, insideDocker = 8080, DockerPortMappingProtocol.TCP)
//            )
//        )
//    }
//
//    fatJar {
//        archiveFileName.set("fat.jar")
//    }
//}
//
//sourceSets {
//    main {
//        kotlin {
//            srcDir("src/main/kotlin")
//        }
//        resources {
//            srcDir("src/main/resources")
//        }
//    }
//}
//
//repositories {
//    mavenCentral()
//    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
//}
//
//dependencies {
//    implementation(libs.ktor.server.core)
//    implementation(libs.ktor.server.cio)
//    implementation(libs.ktor.server.content)
//    implementation(libs.ktor.server.kotlinx)
//    implementation(libs.ktor.server.netty)
//    implementation(libs.ktor.server.status.pages)
//
//    // Swagger and OpenAPI
//    implementation(libs.ktor.server.swagger)
//    implementation(libs.ktor.server.openapi)
//    implementation(libs.swagger.annotations)
//    implementation(libs.swagger.core)
//    implementation(libs.jackson.dataformat.yaml)
//
//    // Exposed
//    implementation(libs.exposed.core)
//    implementation(libs.exposed.dao)
//    implementation(libs.exposed.jdbc)
//
//    // Database and connection pooling
//    implementation(libs.postgress)
//    implementation(libs.hikari)
//
//    // Security
//    implementation(libs.bcrypt)
//
//    // Logging
//    implementation(libs.logback)
//    implementation(libs.slf4j.api)
//
//    // Test dependencies
//    testImplementation(libs.ktor.server.tests)
//    testImplementation(libs.kotlin.tests)
//    testImplementation(libs.junit.jupiter)
//    testImplementation(libs.exposed.core)
//    testImplementation(libs.exposed.dao)
//    testImplementation(libs.exposed.jdbc)
//    testImplementation(libs.h2)
//}
//
//tasks.create("stage") {
//    dependsOn("installDist")
//}
//
//tasks.withType<Copy> {
//    duplicatesStrategy = DuplicatesStrategy.EXCLUDE // Или DuplicatesStrategy.INCLUDE
//}

plugins {
    id("org.springframework.boot") version "3.0.0"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
}

group = "com.goods.product"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-logging")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-json")
    implementation("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    // Включение зависимости для Swagger
    implementation("io.springfox:springfox-boot-starter:3.0.0")

    // Для работы с Exposed
    implementation("org.jetbrains.exposed:exposed-core:0.37.3")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.37.3")

    // Для bcrypt
    implementation("at.favre.lib:bcrypt:0.10.2")

    // Тестирование
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}



