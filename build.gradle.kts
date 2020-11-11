fun version(artifact: String): String {
	val key = "version.${artifact.toLowerCase()}"
	return project.ext[key]?.toString()
		?: throw IllegalStateException("No version found for artifact '$artifact'")
}

fun projectName(): String = project.name.replace("{", "").replace("}", "")

plugins {
	id("io.gitlab.arturbosch.detekt") version "1.0.0.RC6-2"
	id("org.jmailen.kotlinter") version "3.2.0"
	id("org.sonarqube") version "2.6.2"
	id("application") apply true
	id("org.jetbrains.kotlin.jvm") version "1.4.10" apply true
	id("java") apply true
	id("maven-publish")
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
	maven(url = "https://maven.pkg.github.com/")
	maven(url = "https://dl.bintray.com/kotlin/ktor")
	maven(url = "https://jitpack.io")
}

dependencies {
	implementation("org.jetbrains.kotlin", "kotlin-stdlib-jdk8", version("kotlin"))
	implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-jdk8", version("coroutines"))
	
	// Logging
	implementation("io.github.microutils", "kotlin-logging", "1.6.20")
	// Enable for applications
	// runtime("ch.qos.logback", "logback-classic", "1.2.3")

	// Ktor
	version("ktor").let { ktor ->
		implementation(platform("io.ktor:ktor-server-core:$ktor"))
		implementation("io.ktor", "ktor-server-netty", ktor)
		implementation("io.ktor", "ktor-client-auth-basic", ktor)
		implementation("io.ktor", "ktor-auth", ktor)
		implementation("io.ktor", "ktor-jackson", ktor)
		implementation("io.ktor", "ktor-auth-jwt", ktor)
	}

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
	testImplementation("org.junit.jupiter", "junit-jupiter-api", version("junit"))
	testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", version("junit"))
}

publishing {
	repositories {
		maven {
			name = "GithubPackages"
			url = uri("https://maven.pkg.github.com/mantono/${projectName()}")
			credentials {
				username = "mantono"
				password = System.getenv("GITHUB_TOKEN")
			}
		}
	}
	publications {
		register("gpr", MavenPublication::class) {
			this.artifactId = projectName()
			this.groupId = project.group.toString()
			this.version = project.version.toString()
			from(components["java"])
		}
	}
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
