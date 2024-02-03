plugins {
    java
    `maven-publish`
    id("io.freefair.lombok") version "8.4"
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    implementation(libs.org.springframework.boot.spring.boot.starter.webflux)
    implementation(libs.org.springframework.boot.spring.boot.starter.data.mongodb.reactive)
    implementation(libs.org.springframework.boot.spring.boot.starter.actuator)
    implementation(libs.javax.validation.validation.api)
    implementation(libs.org.springdoc.springdoc.openapi.starter.webflux.ui)
    testImplementation(libs.org.springframework.boot.spring.boot.starter.test)
    testImplementation(libs.org.springframework.boot.spring.boot.testcontainers)
    testImplementation(libs.org.testcontainers.junit.jupiter)
    testImplementation(libs.org.testcontainers.mongodb)
    compileOnly(libs.org.projectlombok.lombok)
}

group = "org.example.spring"
version = "1.0.0-SNAPSHOT"
description = "team-player"
java.sourceCompatibility = JavaVersion.VERSION_17

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}
