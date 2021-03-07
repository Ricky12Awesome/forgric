package me.ricky.forgric.forge

import me.ricky.forgric.common.CommonRegistries
import me.ricky.forgric.common.CommonRegistry
import me.ricky.forgric.common.Id
import net.minecraft.util.ResourceLocation
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.registries.ForgeRegistryEntry
import thedarkcolour.kotlinforforge.forge.MOD_BUS

fun Id.toPlatformId() = ResourceLocation(namespace, path)

class ForgeRegistry<C : Any, T : ForgeRegistryEntry<T>>(
  val toPlatform: (C) -> T
) : CommonRegistry<C> {
  private val _registered = mutableListOf<T>()
  val registered get(): List<T> = _registered

  override fun register(id: Id, value: C) {
    _registered += toPlatform(value).setRegistryName(id.toPlatformId())
  }

  fun onRegister(event: RegistryEvent.Register<T>) {
    registered.forEach(event.registry::register)
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