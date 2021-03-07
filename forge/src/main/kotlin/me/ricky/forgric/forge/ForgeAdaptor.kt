package me.ricky.forgric.forge

import me.ricky.forgric.common.*
import net.minecraft.item.Item

open class ForgeAdaptor : Adaptor {
  override val registries: CommonRegistries = ForgeRegistries(this)
  override fun toNativeSettings(settings: ItemSettings): Item.Properties {
    return Item.Properties().apply {
      maxStackSize(settings.maxCount)
      if (settings.maxDamage > 0) maxDamage(settings.maxDamage)
      if (settings.isFireProof) isImmuneToFire
    }
  }

  override fun toNativeItem(item: CommonItem): Item {
    return when (item) {
      is NativeItem -> item.toPlatformItem() as Item
      else -> item.settings
        ?.let(::toNativeSettings)
        ?.let(::Item)
        ?: Item(Item.Properties())
    }
  }

  override fun toNativeSettings(settings: BlockSettings): Any {
    TODO("Not yet implemented")
  }

  override fun toNativeSettings(settings: MaterialSettings): Any {
    TODO("Not yet implemented")
  }

  override fun toNativeBlock(block: CommonBlock): Any {
    TODO("Not yet implemented")
  }

}