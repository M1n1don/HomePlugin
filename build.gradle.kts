import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "dev.m1n1don"
version = "0.1.1"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    maven ("https://papermc.io/repo/repository/maven-public/")
    maven ("https://oss.sonatype.org/content/groups/public/")
    maven ("https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib"))

    val exposedVersion = "0.39.2"
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

    implementation("com.github.hazae41", "mc-kutils", "master-SNAPSHOT")

    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
}

tasks {
    shadowJar {
        archiveBaseName.set("HomePlugin")
        archiveVersion.set(project.version.toString())
        archiveClassifier.set("")

        mergeServiceFiles()

        manifest {
            attributes(mapOf("Main-Class" to "dev.m1n1don.homeplugin.HomePluginKt"))
        }
    }

    processResources {
        filteringCharset = "UTF-8"
        from(sourceSets["main"].resources.srcDirs) {
            include("**/*.yml")
            duplicatesStrategy = DuplicatesStrategy.INCLUDE
            filter<ReplaceTokens>("tokens" to mapOf("version" to project.version))
            filter<ReplaceTokens>("tokens" to mapOf("name" to "HomePlugin"))
        }
    }

    build {
        dependsOn(shadowJar)
    }
}