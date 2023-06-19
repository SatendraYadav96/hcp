import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.5"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
}

group = "com.squer"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-security")
	//implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.2")
	implementation("com.h2database:h2")
	// https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-cache
	implementation("org.springframework.boot:spring-boot-starter-cache:2.7.0")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.microsoft.sqlserver", "mssql-jdbc", "9.2.1.jre11")
	implementation("org.liquibase:liquibase-core")
	runtimeOnly("com.h2database:h2")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	implementation("org.springdoc:springdoc-openapi-ui:1.6.6")

	implementation("com.google.code.gson:gson:2.9.0")
	implementation("io.jsonwebtoken:jjwt:0.9.1")
	implementation("org.jolokia:jolokia-core:1.7.1")
	// https://mvnrepository.com/artifact/org.apache.velocity/velocity
	implementation("org.apache.velocity:velocity:1.7")
	// https://mvnrepository.com/artifact/com.itextpdf/itextpdf
	implementation("com.itextpdf:itextpdf:5.0.6")
	implementation("com.itextpdf:html2pdf:4.0.5")
	implementation("com.itextpdf:kernel:7.2.5")
	// https://mvnrepository.com/artifact/com.mchange/c3p0
	implementation("com.mchange:c3p0:0.9.5.5")

	// https://mvnrepository.com/artifact/org.json/json
	implementation("org.json:json:20201115")

// https://mvnrepository.com/artifact/com.github.doyaaaaaken/kotlin-csv-jvm
	implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.9.0")












}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}


