package me.ricky.forgric.common

const val MOD_ID = "forgric"

object CommonForgric : Initializer {
  override fun Adaptor.onInitialize() {
    val items = registries[CommonRegistries.Key.Item]
    val testItems = SimpleItemGroup(Id(MOD_ID, "test_items"), null)
    val settings = ItemSettings(maxCount = 32, isFireProof = true, group = testItems)

    items[Id(MOD_ID, "test_item")] = SimpleItem(settings)
  }
}