package com.ukonnra.wonderland.project.gradle

import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Files
import java.nio.file.Path

class ProjectPluginTest {
  companion object {
    const val SETTINGS_FILE = "settings.gradle.kts"
    const val BUILD_FILE = "build.gradle.kts"
  }

  @Test
  fun testBasic(@TempDir tempDir: Path) {
    Files.writeString(
      tempDir.resolve(SETTINGS_FILE),
      """
      rootProject.name = "basic-project"
      """.trimIndent()
    )

    Files.writeString(
      tempDir.resolve(BUILD_FILE),
      """
      plugins {
        java
        id("com.ukonnra.wonderland.project")
      }
      """.trimIndent()
    )

    val result = GradleRunner.create()
      .withProjectDir(tempDir.toFile())
      .withArguments(listOf("-q", "dependencies", "--configuration", "implementation", "--stacktrace"))
      .withPluginClasspath()
      .withDebug(true)
      .build()

    println("Result: ${result.output}")
    Assertions.assertTrue(result.output.contains("javax.servlet:javax.servlet-api:4.0.1"))
  }
}
