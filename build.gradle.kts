plugins {
	java
	id("org.springframework.boot") version "4.0.6"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

dependencyManagement {
	imports {
		mavenBom("io.github.resilience4j:resilience4j-bom:2.4.0")
	}

}


tasks.withType<Test> {
	useJUnitPlatform()
	repositories {
		mavenCentral()
	}

	dependencies {
		implementation("org.springframework.boot:spring-boot-starter-webmvc")
		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
		implementation("org.springframework.boot:spring-boot-starter-cache")
		implementation("io.github.resilience4j:resilience4j-spring-boot3:2.3.0")
		implementation("org.springframework.boot:spring-boot-starter-webflux")
		implementation("org.springframework.boot:spring-boot-starter-restclient")
		compileOnly("org.projectlombok:lombok")
		runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
		annotationProcessor("org.projectlombok:lombok")
		testImplementation("org.springframework.boot:spring-boot-starter-security-oauth2-resource-server-test")
		testImplementation("org.springframework.boot:spring-boot-starter-security-test")
		testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
		testCompileOnly("org.projectlombok:lombok")
		testRuntimeOnly("org.junit.platform:junit-platform-launcher")
		testAnnotationProcessor("org.projectlombok:lombok")
	}
}
