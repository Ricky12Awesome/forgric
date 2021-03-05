object Kotlin {
  const val version = "1.4.31"
}

object Fabric {
  object Loader {
    const val version = "0.11.2" // https://maven.fabricmc.net/net/fabricmc/fabric-loader/
  }

  object API {
    const val version = "0.31.0+1.16"
  }

  object Loom {
    const val version = "0.6-SNAPSHOT"
  }

  object YarnMappings {
    const val version = "${Minecraft.version}+build.5"
  }
}

object Forge {
  const val version = "${Minecraft.version}-36.0.46"

  object Gradle {
    const val version = "4.0.23"
  }

  object MCPMappings {
    const val channel = "snapshot"
    const val version = "20201028-1.16.3"
  }

  object Mods {
    const val kotlinForForge = "thedarkcolour:kotlinforforge:1.7.0"
  }
}

object Minecraft {
  const val version = "1.16.5"
}

object CurseGradle {
  const val version = "1.4.0"
}
