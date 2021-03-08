package me.ricky.forgric.forge

import me.ricky.forgric.common.*
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.item.*
import net.minecraft.state.properties.BlockStateProperties

open class ForgeAdaptor : Adaptor {
  override val registries: CommonRegistries = ForgeRegistries(this)
  private val cache = mutableMapOf<Any, Any>()

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
    return cache.getOrPut(item) {
      when (item) {
        is NativeItem -> item.toNativeItem() as Item
        is CommonBlockItem -> BlockItem(
          toNativeBlock(item.block),
          item.settings?.let(::toNativeSettings) ?: Item.Properties()
        )
        else -> item.settings
          ?.let(::toNativeSettings)
          ?.let(::Item)
          ?: Item(Item.Properties())
      }
    } as Item
  }

  override fun toNativeBlock(block: CommonBlock): Block {
    return cache.getOrPut(block) {
      when (block) {
        is NativeBlock -> block.toNativeBlock() as Block
        else -> block.settings
          ?.let(::toNativeSettings)
          ?.let(::Block)
          ?: Block(AbstractBlock.Properties.create(Material.AIR))
      }
    } as Block
  }

  override fun toNativeItemGroup(itemGroup: CommonItemGroup): ItemGroup {
    return cache.getOrPut(itemGroup) {
      when (itemGroup) {
        is VanillaItemGroup -> ItemGroup.GROUPS.first { it.path == itemGroup.id.path }
        is NativeItemGroup -> itemGroup.toNativeGroup() as ItemGroup
        else -> object : ItemGroup(itemGroup.id.asTranslationString()) {
          override fun createIcon(): ItemStack = ItemStack(itemGroup.icon?.let(::toNativeItem) ?: Items.AIR)
        }
      }
    } as ItemGroup
  }
}