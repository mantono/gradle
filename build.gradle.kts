fun version(artifact: String): String {
	return project.ext["version.${artifact.toLowerCase()}"]?.toString()
		?: throw IllegalStateException("No version found for artifact '$artifact'")
}

plugins {
	id("io.gitlab.arturbosch.detekt") version "1.0.0.RC6-2"
	id("org.jmailen.kotlinter") version "1.21.0"
	id("org.sonarqube") version "2.6.2"
	id("application") apply true
	id("org.jetbrains.kotlin.jvm") version "1.3.30" apply true
	id("java") apply true
	id("maven") apply true
	id("idea") apply true
}

application {
	mainClassName = "com.mantono.MainKt"
}

group = "com.mantono"
version = "0.1.0"
description = "Generate a new Gradle project from a template"

defaultTasks = listOf("run")

repositories {
	mavenLocal()
	jcenter()
	mavenCentral()
	maven(url = "https://jitpack.io")
}

dependencies {
	implementation("org.jetbrains.kotlin", "kotlin-stdlib-jdk8", version("kotlin"))
	implementation("com.github.mantono", "ask-nicely", "e3f5ac2")
}

tasks {
	compileKotlin {
		sourceCompatibility = version("jvm")
		kotlinOptions {
			jvmTarget = version("jvm")
		}
	}

	wrapper {
		description = "Generates gradlew[.bat] scripts for faster execution"
		gradleVersion = version("gradle")
	}
}
