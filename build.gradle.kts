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
    java
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.diffplug.spotless") version "6.21.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-json")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("software.amazon.awssdk:s3:2.20.0")
    implementation("software.amazon.awssdk:aws-core:2.20.0")
    implementation("org.postgresql:postgresql")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")
    implementation("org.jetbrains.exposed:exposed-core:0.37.3")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.37.3")
    implementation("at.favre.lib:bcrypt:0.10.2")
    runtimeOnly("com.h2database:h2:2.3.232")
    implementation("org.liquibase:liquibase-core:4.22.0")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-test-autoconfigure")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework:spring-webflux")
    implementation("io.projectreactor.netty:reactor-netty-http:1.1.4")
    implementation("io.projectreactor:reactor-core:3.5.7")
    implementation("org.springframework.kafka:spring-kafka:3.1.0")
    implementation("org.camunda.bpm.springboot:camunda-bpm-spring-boot-starter:7.20.0")
    implementation("org.camunda.bpm.springboot:camunda-bpm-spring-boot-starter-rest:7.20.0")
    implementation("org.camunda.bpm.springboot:camunda-bpm-spring-boot-starter-webapp:7.20.0")
    implementation("org.camunda.bpm:camunda-engine:7.20.0")
    testImplementation("org.camunda.bpm.assert:camunda-bpm-assert:12.0.0")
    implementation("org.springframework:spring-test")

    // AWS SDK для S3
    implementation("software.amazon.awssdk:s3:2.20.0")

    // Для работы с Multipart файлами
    implementation("commons-io:commons-io:2.11.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks {
    bootJar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}

spotless {
    java {
        target("src/**/*.java")
        googleJavaFormat()
    }
}
