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
	mainClassName = "{{project_group}}.MainKt"
}

group = "{{project_group}}"
version = "0.1.0"
description = "{{project_description}}"

defaultTasks = listOf("test")

repositories {
	mavenLocal()
	jcenter()
	mavenCentral()
	maven(url = "https://dl.bintray.com/kotlin/ktor")
	maven(url = "https://jitpack.io")
}

dependencies {
	compile("org.jetbrains.kotlin", "kotlin-stdlib-jdk8", version("kotlin")
	compile("org.jetbrains.kotlinx", "kotlinx-coroutines-jdk8", version("coroutines")
	
	// Logging
	implementation("io.github.microutils", "kotlin-logging", "1.6.20")
	// Enable for applications
	// runtime("ch.qos.logback", "logback-classic", "1.2.3")

	// Ktor
	implementation("io.ktor", "ktor-server-core", version("ktor"))
	implementation("io.ktor", "ktor-server-netty", version("ktor"))
	implementation("io.ktor", "ktor-client-auth-basic", version("ktor"))
	implementation("io.ktor", "ktor-auth", version("ktor"))
	implementation("io.ktor", "ktor-jackson", version("ktor"))
	implementation("io.ktor", "ktor-auth-jwt", version("ktor"))

	// Jackson
	implementation("com.fasterxml.jackson.core", "jackson-core", version("jackson"))
	implementation("com.fasterxml.jackson.module", "jackson-module-kotlin", version("jackson"))
	implementation("com.fasterxml.jackson.datatype", "jackson-datatype-jsr310", version("jackson"))

	// Other
	implementation("com.mantono", "pyttipanna", "1.0.0")
	implementation("com.auth0", "java-jwt", "3.7.0")

	// Junit
	testCompile("org.junit.jupiter", "junit-jupiter-api", version("junit"))
	testRuntime("org.junit.jupiter", "junit-jupiter-engine", version("junit"))
}

tasks {
	test {
		useJUnitPlatform()

		// Show test results.
		testLogging {
			events("passed", "skipped", "failed")
		}
		reports {
			junitXml.isEnabled = false
			html.isEnabled = true
		}
	}

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
