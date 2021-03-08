package me.ricky.forgric.fabric

import me.ricky.forgric.common.*
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.block.MaterialColor
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.item.Items

open class FabricAdaptor : Adaptor {
  override val registries: FabricRegistries = FabricRegistries(this)

  override fun toNativeSettings(settings: MaterialSettings): Material {
    return Material.Builder(MaterialColor.COLORS[settings.color.id]).apply {
      if (!settings.isSolid) notSolid()
      if (settings.hasMovement) allowsMovement()
      if (settings.isLiquid) liquid()
      if (settings.isReplaceable) replaceable()
    }.build()
  }

  override fun toNativeSettings(settings: BlockSettings): AbstractBlock.Settings {
    return AbstractBlock.Settings.of(toNativeSettings(settings.material))
  }

  override fun toNativeSettings(settings: ItemSettings): Item.Settings {
    return Item.Settings().apply {
      maxCount(settings.maxCount)
      if (settings.maxDamage > 0) maxDamage(settings.maxDamage)
      if (settings.isFireProof) fireproof()

      settings.group
        ?.let(::toNativeItemGroup)
        ?.let(::group)
    }
  }

  override fun toNativeItem(item: CommonItem): Item {
    return when (item) {
      is NativeItem -> item.toNativeItem() as Item
      else -> item.settings
        ?.let(::toNativeSettings)
        ?.let(::Item)
        ?: Item(Item.Settings())
    }
  }

  override fun toNativeBlock(block: CommonBlock): Block {
    return when (block) {
      is NativeBlock -> block.toNativeBlock() as Block
      else -> block.settings
        ?.let(::toNativeSettings)
        ?.let(::Block)
        ?: Block(AbstractBlock.Settings.of(Material.AIR))
    }
  }

  override fun toNativeItemGroup(itemGroup: CommonItemGroup): ItemGroup {
    return when (itemGroup) {
      is VanillaItemGroup -> ItemGroup.GROUPS.first { it.name == itemGroup.id.path }
      is NativeItemGroup -> itemGroup.toNativeGroup() as ItemGroup
      else -> FabricItemGroupBuilder.build(itemGroup.id.toNativeId()) {
        ItemStack(itemGroup.icon?.let(::toNativeItem) ?: Items.AIR)
      }
    }
  }


}