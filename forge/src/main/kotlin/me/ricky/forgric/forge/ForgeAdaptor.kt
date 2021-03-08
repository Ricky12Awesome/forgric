package me.ricky.forgric.forge

import me.ricky.forgric.common.*
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.state.properties.BlockStateProperties

open class ForgeAdaptor : Adaptor {
  override val registries: CommonRegistries = ForgeRegistries(this)

  override fun toNativeSettings(settings: MaterialSettings): Material {
    return Material.Builder(MaterialColor.COLORS[settings.color.id]).apply {
      if (!settings.isSolid) notSolid()
      if (settings.hasMovement) doesNotBlockMovement()
      if (settings.isLiquid) liquid()
      if (settings.isReplaceable) replaceable()
    }.build()
  }

  override fun toNativeSettings(settings: BlockSettings): AbstractBlock.Properties {
    return AbstractBlock.Properties.create(toNativeSettings(settings.material))
  }

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

  override fun toNativeBlock(block: CommonBlock): Any {
    return when (block) {
      is NativeBlock -> block.toNativeBlock() as Block
      else -> block.settings
        ?.let(::toNativeSettings)
        ?.let(::Block)
        ?: Block(AbstractBlock.Properties.create(Material.AIR))
    }
  }

  override fun toNativeItemGroup(itemGroup: CommonItemGroup): ItemGroup {
    return when (itemGroup) {
      is VanillaItemGroup -> ItemGroup.GROUPS.first { it.path == itemGroup.id.path }
      is NativeItemGroup -> itemGroup.toNativeGroup() as ItemGroup
      else -> object : ItemGroup(itemGroup.id.asTranslationString()) {
        override fun createIcon(): ItemStack = ItemStack(itemGroup.icon?.let(::toNativeItem) ?: Items.AIR)
      }
    }
  }
}