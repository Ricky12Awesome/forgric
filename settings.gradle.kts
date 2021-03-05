pluginManagement {
  repositories {
    maven("http://maven.fabricmc.net") { name = "Fabric" }
    maven("https://files.minecraftforge.net/maven") { name = "Forge" }

    mavenCentral()
    jcenter()
    gradlePluginPortal()
  }

  resolutionStrategy {
    eachPlugin {
      if (requested.id.id == "net.minecraftforge.gradle") {
        useModule("net.minecraftforge.gradle:ForgeGradle:${requested.version}")
      }
    }
  }
}

rootProject.name = "forgric"

include(":common")
include(":forge")
include(":fabric")

