object Versions {
  const val GUAVA = "30.1.1-jre"
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter")
  implementation("org.springframework:spring-web")
  implementation("com.google.guava:guava:${Versions.GUAVA}")
  implementation("io.projectreactor:reactor-core")

  testImplementation(project(":infrastructure-testsuite"))
  testImplementation("org.springframework.boot:spring-boot-starter-test")
}
