package com.ukonnra.wonderland.project.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

const val EXTENSION_NAME = "wonderlandProjectConfig"
const val TASK_NAME = "wonderlandProject"

class ProjectPlugin : Plugin<Project> {
  override fun apply(project: Project) {
    val extension = project.extensions.create(EXTENSION_NAME, ProjectPluginExtension::class.java)
    println("Extensions: $extension")
    project.dependencies.add("implementation", "javax.servlet:javax.servlet-api:4.0.1")
  }
}
