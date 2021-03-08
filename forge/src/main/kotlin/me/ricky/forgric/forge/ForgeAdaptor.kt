package me.ricky.forgric.forge

import me.ricky.forgric.common.*
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.Items

open class ForgeAdaptor : Adaptor {
  override val registries: CommonRegistries = ForgeRegistries(this)
  override fun toNativeSettings(settings: ItemSettings): Item.Properties {
    return Item.Properties().apply {
      maxStackSize(settings.maxCount)
      if (settings.maxDamage > 0) maxDamage(settings.maxDamage)
      if (settings.isFireProof) isImmuneToFire

      settings.group?.let(::toNativeItemGroup)?.let(::group)
    }
  }

  override fun toNativeItem(item: CommonItem): Item {
    return when (item) {
      is NativeItem -> item.toNativeItem() as Item
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

  override fun toNativeItemGroup(itemGroup: CommonItemGroup): ItemGroup {
    return when (itemGroup) {
      is VanillaItemGroup -> ItemGroup.GROUPS.first { it.path == itemGroup.id.path }
      is NativeItemGroup -> itemGroup.toNativeGroup() as ItemGroup
      else -> object : ItemGroup(itemGroup.id.path) {
        override fun createIcon(): ItemStack = ItemStack(itemGroup.icon?.let(::toNativeItem) ?: Items.AIR)
      }
    }
  }
}