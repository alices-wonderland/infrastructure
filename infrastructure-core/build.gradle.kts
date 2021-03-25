object Versions {
  const val GUAVA = "30.1.1-jre"
}

dependencies {
  implementation(platform("org.springframework:spring-framework-bom:5.3.5"))
  implementation(platform("io.projectreactor:reactor-bom:2020.0.5"))

  implementation("org.springframework:spring-web")

  implementation("com.google.guava:guava:${Versions.GUAVA}")
  implementation("io.projectreactor:reactor-core")

  testImplementation(project(":infrastructure-testsuite"))
}
