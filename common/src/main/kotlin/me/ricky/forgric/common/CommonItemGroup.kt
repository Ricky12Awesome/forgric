package me.ricky.forgric.common

enum class VanillaItemGroup(id: String) : CommonItemGroup {
  BUILDING_BLOCKS("buildingBlocks"),
  DECORATIONS("decorations"),
  REDSTONE("redstone"),
  TRANSPORTATION("transportation"),
  MISC("misc"),
  SEARCH("search"),
  FOOD("food"),
  TOOLS("tools"),
  COMBAT("combat"),
  BREWING("brewing"),
  HOTBAR("hotbar"),
  INVENTORY("inventory");

  override val id: Id = Id("minecraft", id)
  override val icon: CommonItem? = null
}

interface CommonItemGroup {
  val id: Id
  val icon: CommonItem?
}

interface NativeItemGroup : CommonItemGroup {
  fun toNativeGroup(): Any
}

class SimpleItemGroup(
  override val id: Id,
  private val getIcon: () -> CommonItem?
) : CommonItemGroup {
  override val icon get(): CommonItem? = getIcon()

  constructor(id: Id, icon: CommonItem?) : this(id, { icon })
}