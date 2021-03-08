plugins {
  id("net.minecraftforge.gradle") version Forge.Gradle.version
}

minecraft {
  mappings(Forge.MCPMappings.channel, Forge.MCPMappings.version)

  runs {
    create("client") {
      workingDirectory(project.file("run"))

      property("forge.logging.markers", "SCAN,REGISTRIES,REGISTRYDUMP")
      property("forge.logging.console.level", "debug")

      mods {
        create(Info.id) {
          source(sourceSets["main"])
        }
      }
    }
  }
}

dependencies {
  minecraft("net.minecraftforge", "forge", Forge.version)

  api(Forge.Mods.kotlinForForge)
  api(project(":common"))
}

tasks {
  val sourcesJar by creating(Jar::class) {
    archiveClassifier.set("sources")

    from(sourceSets["main"].allSource)

    dependsOn(JavaPlugin.CLASSES_TASK_NAME)
  }

  val javadocJar by creating(Jar::class) {
    archiveClassifier.set("javadoc")

    from(project.tasks["javadoc"])

    dependsOn(JavaPlugin.JAVADOC_TASK_NAME)
  }

  processResources {
    filesMatching("META-INF/mods.toml") {
      expand(
        "modid" to Info.id,
        "name" to Info.name,
        "version" to Info.version,
        "description" to Info.description,
        "kotlinVersion" to Kotlin.version
      )
    }
  }
}
