import io.ktor.plugin.features.*

plugins {
    application
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    id("io.ktor.plugin") version "2.3.8"
}

group = "com.goods.product"
version = "0.0.1"

application {
    mainClass.set("com.goods.product.ApplicationKt")
}

ktor {
    docker {
        jreVersion.set(JavaVersion.VERSION_21)
        localImageName.set("goods.product-docker-image")
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

sourceSets {
    main {
        kotlin {
            srcDir("src/main/kotlin")
        }
        resources {
            srcDir("src/main/resources")
        }
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

    // Swagger and OpenAPI
    implementation(libs.ktor.server.swagger)
    implementation(libs.ktor.server.openapi)
    implementation(libs.swagger.annotations)
    implementation(libs.swagger.core)
    implementation(libs.jackson.dataformat.yaml)

    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)

    implementation(libs.postgress)
    implementation(libs.hikari)

    implementation(libs.bcrypt)

    implementation(libs.logback)
    implementation(libs.slf4j.api)
    implementation(libs.slf4j.simple)

    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.tests)

    testImplementation("io.mockk:mockk:1.12.3")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}


tasks.create("stage") {
    dependsOn("installDist")
}

tasks.withType<Copy> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE // Или DuplicatesStrategy.INCLUDE
}
