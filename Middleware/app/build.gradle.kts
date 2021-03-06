/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details take a look at the 'Building Java & JVM projects' chapter in the Gradle
 * User Manual available at https://docs.gradle.org/7.1.1/userguide/building_java_projects.html
 */

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit test framework.
    implementation("org.junit.jupiter:junit-jupiter:5.7.1")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:30.1-jre")

    // This dependency is used to connect to redis database.
    implementation("redis.clients:jedis:2.8.0")

    // https://mvnrepository.com/artifact/javax/javaee-api
    compileOnly("javax:javaee-api:6.0")

    // https://mvnrepository.com/artifact/org.apache.thrift/libthrift
    implementation("org.apache.thrift:libthrift:0.14.2")

    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation("org.slf4j:slf4j-api:2.0.0-alpha1")

    // https://mvnrepository.com/artifact/org.slf4j/slf4j-nop
    implementation("org.slf4j:slf4j-nop:2.0.0-alpha1")
}

application {
    // Define the main class for the application.
    mainClass.set("Middleware.App")
}
tasks.jar{
    manifest {
        attributes["Main-Class"] = "Middleware.App"
    }
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}
tasks.test {
    useJUnitPlatform()
}
