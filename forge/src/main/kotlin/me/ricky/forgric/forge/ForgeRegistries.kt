package me.ricky.forgric.forge

import me.ricky.forgric.common.CommonRegistries
import me.ricky.forgric.common.CommonRegistry
import me.ricky.forgric.common.Id
import net.minecraft.util.ResourceLocation
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.registries.ForgeRegistryEntry
import thedarkcolour.kotlinforforge.forge.MOD_BUS

fun Id.toNativeId() = ResourceLocation(namespace, path)

class ForgeRegistry<C : Any, T : ForgeRegistryEntry<T>>(
  val toNative: (C) -> T,
  val toCommon: (T) -> C = { TODO("Forge Common Adapter") },
) : CommonRegistry<C> {
  private val _registered = mutableMapOf<Id, C>()
  val registered get(): Map<Id, C> = _registered

  override fun set(id: Id, value: C) {
    _registered[id] = value
  }

  override fun get(id: Id): C {
    return _registered[id]!!
  }

  fun onRegister(event: RegistryEvent.Register<T>) {
    registered
      .map { (id, value) -> toNative(value).setRegistryName(id.toNativeId()) }
      .forEach(event.registry::register)
  }
}

@Suppress("UNCHECKED_CAST")
class ForgeRegistries(private val adaptor: ForgeAdaptor) : CommonRegistries {
  companion object {
    val registries = mutableMapOf<CommonRegistries.Key<*>, CommonRegistry<*>>()
  }

  init {
    registries[CommonRegistries.Key.Item] = ForgeRegistry(adaptor::toNativeItem).also {
      MOD_BUS.addGenericListener(it::onRegister)
    }
  }

  override fun <T : Any> get(key: CommonRegistries.Key<T>): CommonRegistry<T> {
    return registries[key] as CommonRegistry<T>
  }
}