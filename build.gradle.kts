import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper

plugins {
  kotlin("jvm") version Kotlin.version
  id("com.github.johnrengelman.shadow") version "6.1.0"
  `maven-publish`
}

allprojects {
  apply<KotlinPluginWrapper>()
  apply<MavenPublishPlugin>()
  apply<ShadowPlugin>()

  group = "me.ricky"
  version = "1.0-SNAPSHOT"

  repositories {
    maven(url = "http://maven.fabricmc.net") { name = "Fabric" }
    maven(url = "https://libraries.minecraft.net/") { name = "Mojang" }
    maven(url = "https://kotlin.bintray.com/kotlinx/") { name = "Kotlinx" }
    maven(url = "https://thedarkcolour.github.io/KotlinForForge/") { name = "KotlinForForge" }
    maven(url = "https://maven.terraformersmc.com/releases//") { name = "TerraformersMC" }
    maven(url = "https://jitpack.io")
    mavenCentral()
    jcenter()
  }

  sourceSets["main"].resources.srcDir(rootProject.file("assets"))

  tasks {
    compileJava {
      targetCompatibility = "1.8"
      sourceCompatibility = "1.8"
    }

    compileKotlin {
      kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf(
          "-Xinline-classes",
          "-Xopt-in=kotlin.RequiresOptIn",
          "-Xopt-in=kotlin.ExperimentalStdlibApi"
        )
      }
    }
  }
}