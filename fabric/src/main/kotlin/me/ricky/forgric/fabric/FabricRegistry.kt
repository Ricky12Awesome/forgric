package me.ricky.forgric.fabric

import me.ricky.forgric.common.*
import net.minecraft.util.Identifier
import net.minecraft.util.registry.MutableRegistry
import net.minecraft.util.registry.Registry

fun Id.toNativeId() = Identifier(namespace, path)

class FabricRegistry<C : Any, T>(
  val registry: MutableRegistry<T>,
  val toNative: (C) -> T,
) : CommonRegistry<C> {
  val toCommon = mutableMapOf<Id, C>()

  override fun set(id: Id, value: C) {
    Registry.register(registry, id.toNativeId(), toNative(value))
    toCommon[id] = value
  }

  override fun get(id: Id): C {
    return toCommon[id]!!
  }
}

class FabricRegistries(private val adaptor: FabricAdaptor) : CommonRegistries {
  val registries = mutableMapOf<CommonRegistries.Key<*>, CommonRegistry<*>>(
    CommonRegistries.Key.Item to FabricRegistry(Registry.ITEM, adaptor::toNativeItem),
    CommonRegistries.Key.Block to FabricRegistry(Registry.BLOCK, adaptor::toNativeBlock)
  )

  override fun <T : Any> get(key: CommonRegistries.Key<T>): CommonRegistry<T> {
    return registries[key] as CommonRegistry<T>
  }
}