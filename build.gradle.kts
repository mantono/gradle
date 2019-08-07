fun version(artifact: String): String {
	val key: String = "version.${artifact.toLowerCase()}"
	return project.ext[key]?.toString()
		?: throw IllegalStateException("No version found for artifact '$artifact'")
}

plugins {
	id("io.gitlab.arturbosch.detekt") version "1.0.0.RC6-2"
	id("org.jmailen.kotlinter") version "1.21.0"
	id("org.sonarqube") version "2.6.2"
	id("application") apply true
	id("org.jetbrains.kotlin.jvm") version "1.3.21" apply true
	id("java") apply true
	id("maven") apply true
	id("idea") apply true
}

application {
	mainClassName = "com.mantono.MainKt"
}

group = "com.mantono"
version = "0.1.0"
description = "{{project_description}}"

defaultTasks = mutableListOf("test")

repositories {
	mavenLocal()
	jcenter()
	mavenCentral()
	maven(url = "https://dl.bintray.com/kotlin/ktor")
	maven(url = "https://jitpack.io")
}

dependencies {
	implementation("org.jetbrains.kotlin", "kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-jdk8")
	
	// Logging
	implementation("io.github.microutils", "kotlin-logging", "1.6.20")
	// Enable for applications
	// runtime("ch.qos.logback", "logback-classic", "1.2.3")

	// Ktor
	implementation(platform("io.ktor:ktor-server-core:${version("ktor")}"))
	implementation("io.ktor", "ktor-server-netty", version("ktor"))
	implementation("io.ktor", "ktor-client-auth-basic", version("ktor"))
	implementation("io.ktor", "ktor-auth", version("ktor"))
	implementation("io.ktor", "ktor-jackson", version("ktor"))
	implementation("io.ktor", "ktor-auth-jwt", version("ktor"))

	// Jackson
	implementation(platform("com.fasterxml.jackson:jackson-bom:2.9.9"))
	implementation("com.fasterxml.jackson.core", "jackson-core")
	implementation("com.fasterxml.jackson.module", "jackson-module-kotlin")
	implementation("com.fasterxml.jackson.datatype", "jackson-datatype-jsr310")

	// Other
	implementation("com.mantono", "pyttipanna", "1.0.0")
	implementation("com.auth0", "java-jwt", "3.7.0")
	implementation("com.squareup.okhttp3:okhttp:3.14.2")

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
