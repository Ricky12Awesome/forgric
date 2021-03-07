package me.ricky.forgric.fabric

import me.ricky.forgric.common.*
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.block.MaterialColor
import net.minecraft.item.Item

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
    }
  }

  override fun toNativeItem(item: CommonItem): Item {
    return when (item) {
      is NativeItem -> item.toPlatformItem() as Item
      else -> item.settings
        ?.let(::toNativeSettings)
        ?.let(::Item)
        ?: Item(Item.Settings())
    }
  }

  override fun toNativeBlock(block: CommonBlock): Block {
    Material.Builder(MaterialColor.ICE)

    return when (block) {
      is NativeBlock -> block.totPlatformBlock() as Block
      else -> block.settings
        ?.let(::toNativeSettings)
        ?.let(::Block)
        ?: Block(AbstractBlock.Settings.of(Material.AIR))
    }
  }


}