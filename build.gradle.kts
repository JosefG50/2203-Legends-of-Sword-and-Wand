plugins {
    id("com.utopia-rise.godot-kotlin-jvm") version "0.14.3-4.5.1"
}

repositories {
    mavenCentral()
    google()
    mavenLocal()
}

godot {
    isRegistrationFileHierarchyEnabled.set(true)
    registrationFileBaseDir.set(projectDir.resolve("scripts"))
    isRegistrationFileGenerationEnabled.set(true)
}

