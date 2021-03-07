plugins {
  id("fabric-loom") version Fabric.Loom.version
}

dependencies {
  minecraft("com.mojang", "minecraft", Minecraft.version)
  mappings("net.fabricmc", "yarn", Fabric.YarnMappings.version, classifier = "v2")

  modImplementation("net.fabricmc", "fabric-loader", Fabric.Loader.version)

  modApi(Fabric.Mods.modmenu)

  include(project(":common"))
  api(project(":common"))

  includeApi(kotlin("stdlib"))
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
    filesMatching("fabric.mod.json") {
      expand(
        "modid" to Info.id,
        "name" to Info.name,
        "version" to Info.version,
        "description" to Info.description,
        "kotlinVersion" to Kotlin.version,
        "fabricApiVersion" to Fabric.API.version
      )
    }
  }
}

publishing {
  publications {
    create<MavenPublication>("maven") {
      artifacts {
        artifact(tasks["sourcesJar"]) {
          builtBy(tasks["remapSourcesJar"])
        }

        artifact(tasks["javadocJar"])
        artifact(tasks["remapJar"])
      }
    }

    repositories {
      mavenLocal()
    }
  }
}

fun DependencyHandlerScope.includeApi(notation: String, config: Action<ExternalModuleDependency>) {
  include(notation, config)
  modApi(notation, config)
}

fun DependencyHandlerScope.includeApi(notation: Any) {
  include(notation)
  modApi(notation)
}