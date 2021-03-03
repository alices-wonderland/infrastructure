plugins {
  idea
  java
  jacoco
  maven

  id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
  id("io.gitlab.arturbosch.detekt") version "1.16.0-RC2"
  id("com.github.ben-manes.versions") version "0.36.0"
  kotlin("jvm") version "1.4.31"

  id("org.springframework.boot") version "2.5.0-M2"
  id("io.spring.dependency-management") version "1.0.11.RELEASE"
  kotlin("plugin.spring") version "1.4.31"
}

object Versions {
  const val JAVA = "11"

  const val GUAVA = "30.1-jre"
}

group = "com.ukonnra.wonderland"
version = "0.0.1-SNAPSHOT"

repositories {
  jcenter()
  mavenLocal()
  mavenCentral()
  maven { url = uri("https://repo.spring.io/milestone") }
  maven { url = uri("https://repo.spring.io/snapshot") }
}

ktlint {
  version.set("0.40.0")
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

tasks.jacocoTestReport {
  dependsOn(tasks.test)
  reports {
    xml.isEnabled = true
    html.isEnabled = true
    csv.isEnabled = false
  }
}

tasks.test {
  finalizedBy(tasks.jacocoTestReport)
  useJUnitPlatform()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter")
  implementation("org.springframework:spring-web")

  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

  api("com.google.guava:guava:${Versions.GUAVA}")
  implementation("io.projectreactor:reactor-core")

  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
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

tasks.install {
  repositories.withConvention(MavenRepositoryHandlerConvention::class) {
    mavenInstaller {
      pom.project {
        withGroovyBuilder {
          "licenses" {
            "license" {
              "name"("The Apache Software License, Version 2.0")
              "url"("http://www.apache.org/licenses/LICENSE-2.0.txt")
              "distribution"("repo")
            }
          }
        }
      }
    }
  }
}
