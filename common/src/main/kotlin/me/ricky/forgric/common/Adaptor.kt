package me.ricky.forgric.common

interface Adaptor {
  val registries: CommonRegistries

  fun toNativeSettings(settings: MaterialSettings): Any
  fun toNativeSettings(settings: BlockSettings): Any
  fun toNativeSettings(settings: ItemSettings): Any
  fun toNativeItem(item: CommonItem): Any
  fun toNativeBlock(block: CommonBlock): Any
  fun toNativeItemGroup(itemGroup: CommonItemGroup): Any
}