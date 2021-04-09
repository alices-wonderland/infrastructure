plugins {
  `java-gradle-plugin`
}

dependencies {
  implementation(gradleApi())
  compileOnly(kotlin("gradle-plugin"))
  testImplementation(gradleTestKit())
}

tasks.withType<PluginUnderTestMetadata>().configureEach {
  pluginClasspath.from(configurations.compileOnly)
}

gradlePlugin {
  plugins.register("projectPlugin") {
    id = "com.ukonnra.wonderland.project"
    implementationClass = "com.ukonnra.wonderland.project.gradle.ProjectPlugin"
  }
}
