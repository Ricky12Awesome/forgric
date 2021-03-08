package me.ricky.forgric.fabric

import me.ricky.forgric.common.*
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.block.MaterialColor
import net.minecraft.item.*

open class FabricAdaptor : Adaptor {
  override val registries: FabricRegistries = FabricRegistries(this)
  private val cache = mutableMapOf<Any, Any>()

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

      settings.group?.let(::toNativeItemGroup)?.let(::group)
    }
  }

  override fun toNativeItem(item: CommonItem): Item {
    return cache.getOrPut(item) {
      when (item) {
        is NativeItem -> item.toNativeItem() as Item
        is CommonBlockItem -> BlockItem(
          toNativeBlock(item.block),
          item.settings?.let(::toNativeSettings) ?: Item.Settings()
        )
        else -> item.settings
          ?.let(::toNativeSettings)
          ?.let(::Item)
          ?: Item(Item.Settings())
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
          ?: Block(AbstractBlock.Settings.of(Material.METAL))
      }
    } as Block
  }

  override fun toNativeItemGroup(itemGroup: CommonItemGroup): ItemGroup {
    return cache.getOrPut(itemGroup) {
      when (itemGroup) {
        is VanillaItemGroup -> ItemGroup.GROUPS.first { it.name == itemGroup.id.path }
        is NativeItemGroup -> itemGroup.toNativeGroup() as ItemGroup
        else -> FabricItemGroupBuilder.build(itemGroup.id.toNativeId()) {
          ItemStack(itemGroup.icon?.let(::toNativeItem) ?: Items.AIR)
        }
      }
    } as ItemGroup
  }
}