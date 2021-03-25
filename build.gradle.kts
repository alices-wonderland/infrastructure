plugins {
  idea
  java
  jacoco
  `maven-publish`

  id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
  id("io.gitlab.arturbosch.detekt") version "1.16.0"
  id("com.github.ben-manes.versions") version "0.38.0"
  kotlin("jvm") version "1.4.31"

  id("org.springframework.boot") version "2.5.0-M3"
  id("io.spring.dependency-management") version "1.0.11.RELEASE"
  kotlin("plugin.spring") version "1.4.31"
}

object Versions {
  const val JAVA = "11"
}

allprojects {
  apply(plugin = "idea")
  apply(plugin = "java")
  apply(plugin = "jacoco")

  apply(plugin = "org.jlleitschuh.gradle.ktlint")
  apply(plugin = "io.gitlab.arturbosch.detekt")

  apply(plugin = "com.github.ben-manes.versions")
  apply(plugin = "org.jetbrains.kotlin.jvm")

  group = "com.ukonnra.wonderland.infrastructure"
  version = "0.0.1-SNAPSHOT"

  repositories {
    jcenter()
    mavenLocal()
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
  }

  ktlint {
    version.set("0.41.0")
  }

  detekt {
    failFast = true
    config = files("$rootDir/detekt.yml")
    autoCorrect = true
    buildUponDefaultConfig = true
    reports {
      xml.enabled = true
      html.enabled = true
      txt.enabled = false
    }
  }

  tasks.detekt {
    jvmTarget = Versions.JAVA
  }

  tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
      xml.isEnabled = true
      html.isEnabled = true
      csv.isEnabled = false
    }
  }
}

subprojects {
  apply(plugin = "org.springframework.boot")
  apply(plugin = "org.jetbrains.kotlin.plugin.spring")
  apply(plugin = "io.spring.dependency-management")

  apply(plugin = "maven-publish")

  dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  }

  tasks.bootJar {
    enabled = false
  }

  tasks.jar {
    enabled = true
  }

  java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11

    withSourcesJar()
    withJavadocJar()
  }

  tasks.test {
    finalizedBy(tasks.jacocoTestReport)
    useJUnitPlatform()
  }

  tasks.compileKotlin {
    kotlinOptions {
      jvmTarget = Versions.JAVA
      freeCompilerArgs = listOf("-Xjsr305=strict")
    }
  }

  tasks.compileTestKotlin {
    kotlinOptions {
      jvmTarget = Versions.JAVA
      freeCompilerArgs = listOf("-Xjsr305=strict")
    }
  }

  publishing.publications.register("release", MavenPublication::class) {
    from(components["java"])
    pom {
      licenses {
        license {
          name.set("The Apache License, Version 2.0")
          url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
        }
      }
    }
  }
}

val codeCoverageReport = tasks.register<JacocoReport>("codeCoverageReport") {
  subprojects.map { it.tasks.test }.forEach {
    dependsOn(it)
  }

  executionData(fileTree(project.rootDir.absolutePath).include("**/build/jacoco/*.exec"))

  subprojects.forEach {
    sourceSets(it.sourceSets["main"])
  }

  reports {
    xml.isEnabled = true
    xml.destination = file("$buildDir/reports/jacoco/report.xml")
    html.isEnabled = true
    csv.isEnabled = false
  }
}

tasks.check {
  dependsOn(codeCoverageReport)
}
