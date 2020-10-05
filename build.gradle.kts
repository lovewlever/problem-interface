import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.3.3.RELEASE"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72"
}

group = "com.self.problem"
version = "0.5.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.1.3")
    implementation("org.springframework.session:spring-session-core")
    implementation(group= "mysql", name="mysql-connector-java", version="8.0.21")
    implementation(group= "com.google.code.gson", name= "gson", version= "2.8.6")
    implementation(group="io.jsonwebtoken", name= "jjwt", version= "0.9.1")
    implementation(group= "javax.xml.bind", name= "jaxb-api", version= "2.3.1")
    implementation(group= "eu.bitwalker", name= "UserAgentUtils", version= "1.21")
    implementation(group= "org.springframework.boot", name= "spring-boot-starter-websocket", version= "2.3.3.RELEASE")
    implementation(group= "cn.afterturn", name= "easypoi-spring-boot-starter", version= "4.2.0")


    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.springframework.security:spring-security-test")

}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
