object Versions {
  const val GUAVA = "30.1.1-jre"
  const val SPRING = "5.3.5"
  const val REACTOR = "3.4.4"
}

dependencies {
  implementation("org.springframework:spring-web:${Versions.SPRING}")

  implementation("com.google.guava:guava:${Versions.GUAVA}")
  implementation("io.projectreactor:reactor-core:${Versions.REACTOR}")

  testImplementation(project(":infrastructure-testsuite"))
}
