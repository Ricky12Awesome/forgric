package me.ricky.forgric.fabric

import me.ricky.forgric.common.*
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.DedicatedServerModInitializer
import net.fabricmc.api.ModInitializer

open class FabricInitializer(
  protected val initializer: Initializer,
  protected val adaptor: FabricAdaptor = FabricAdaptor()
) : ModInitializer {
  override fun onInitialize() = initializer.onInitialize(adaptor)
}

open class FabricClientInitializer(
  protected val initializer: ClientInitializer,
  protected val adaptor: FabricAdaptor = FabricAdaptor()
) : ClientModInitializer {
  override fun onInitializeClient() = initializer.onInitializeClient(adaptor)
}

open class FabricServerInitializer(
  protected val initializer: ServerInitializer,
  protected val adaptor: FabricAdaptor = FabricAdaptor()
) : DedicatedServerModInitializer {
  override fun onInitializeServer() = initializer.onInitializeServer(adaptor)
}