plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	kotlin("plugin.jpa") version "1.9.25"
	id("org.springframework.boot") version "3.3.5"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "dev.playground"
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
	// starters
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")

	// kotlin
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	// database
	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.postgresql:postgresql")

	// testing
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	//testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	//testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// assertions for testing in java
	testImplementation("org.assertj:assertj-core:3.26.3")

	// mocking lib for Kotlin
	testImplementation("io.mockk:mockk:1.13.13")
	
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
