package com.ukonnra.wonderland.project.gradle

open class ProjectPluginExtension(
  var applicationPostfix: Set<String>? = setOf("endpoint"),
  var libraryPostfix: Set<String>? = setOf("core", "proto"),
)
