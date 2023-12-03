plugins {
    kotlin("jvm") version "1.9.20"
}

dependencies {
    testImplementation("io.strikt:strikt-core:0.34.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.0")
    testImplementation(kotlin("test"))
}

sourceSets {
    main {
        kotlin.srcDir("src/main/kotlin")
        resources {
            srcDir("src/main/resources")
        }
    }
    test {
        kotlin.srcDir("src/test/kotlin")
    }
}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }

    test {
        useJUnitPlatform()
    }

    processResources {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}
