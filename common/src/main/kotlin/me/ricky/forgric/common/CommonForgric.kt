package me.ricky.forgric.common

const val MOD_ID = "forgric"

object CommonForgric : Initializer {
  override fun Adaptor.onInitialize() {
    val id = DomainId(MOD_ID)
    val items = registries[CommonRegistries.Key.Item]
    val blocks = registries[CommonRegistries.Key.Block]
    val testItems = SimpleItemGroup(Id(MOD_ID, "test_items"), null)
    val settings = ItemSettings(maxCount = 32, isFireProof = true, group = testItems)
    val blockSettings = BlockSettings(material = MaterialSettings(MaterialColor.BLACK))
    val blockItemSettings = ItemSettings(maxCount = 16, isFireProof = true, group = testItems)

    items[id("test_item")] = SimpleItem(settings)
    blocks[id("test_block")] = SimpleBlock(blockSettings)
    items[id("test_block")] = SimpleBlockItem(blocks[id("test_block")], blockItemSettings)
  }
}