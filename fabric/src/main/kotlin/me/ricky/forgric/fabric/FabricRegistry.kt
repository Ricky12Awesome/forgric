package me.ricky.forgric.fabric

import me.ricky.forgric.common.*
import net.minecraft.util.Identifier
import net.minecraft.util.registry.MutableRegistry
import net.minecraft.util.registry.Registry

fun Id.toNativeId() = Identifier(namespace, path)

class FabricRegistry<C : Any, T>(
  val registry: MutableRegistry<T>,
  val toNative: (C) -> T,
  val toCommon: (T) -> C = { TODO("Fabric Common Adapter") }
) : CommonRegistry<C> {
  override fun set(id: Id, value: C) {
    Registry.register(registry, id.toNativeId(), toNative(value))
  }

  override fun get(id: Id): C {
    return registry[id.toNativeId()]!!.let(toCommon)
  }
}

class FabricRegistries(private val adaptor: FabricAdaptor) : CommonRegistries {
  override fun <T : Any> get(key: CommonRegistries.Key<T>): CommonRegistry<T> {
    return when (key) {
      CommonRegistries.Key.Item -> FabricRegistry(Registry.ITEM, {
        adaptor.toNativeItem(it as CommonItem)
      })
      CommonRegistries.Key.Block -> FabricRegistry(Registry.BLOCK, {
        adaptor.toNativeBlock(it as CommonBlock)
      })
    }
  }
}