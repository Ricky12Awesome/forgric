package me.ricky.forgric.common

data class ItemSettings(
  var maxCount: Int = 64,
  var maxDamage: Int = 0,
  var group: CommonItemGroup? = null,
  var recipeReminder: Any? = null, // TODO Recipe Reminder
  var rarity: Any? = null, // TODO Rarity
  var foodComponent: Any? = null, // TODO Food
  var isFireProof: Boolean = false,
) {
  companion object Builder {
    inline operator fun invoke(build: ItemSettings.() -> Unit): ItemSettings {
      return ItemSettings().apply(build)
    }
  }
}

interface CommonItem {
  val settings: ItemSettings?
}

interface CommonBlockItem : CommonItem {
  val block: CommonBlock
}

fun interface NativeItem : CommonItem {
  override val settings: ItemSettings? get() = null

  fun toNativeItem(): Any
}

open class SimpleItem(override val settings: ItemSettings) : CommonItem
open class SimpleBlockItem(override val block: CommonBlock, override val settings: ItemSettings) : CommonBlockItem