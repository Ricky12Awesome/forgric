Forgric
---------
The aim of this project is to make it easier 
to make a mod that can support forge and fabric apis.
In common code you'll be able to do simple 
items, blocks, tools, weapons, armor, etc.

# This is still in very early development

Example
--------

##### Common code
```kotlin
const val MOD_ID = "forgric"

object CommonForgric : Initializer {
  override fun Adaptor.onInitialize() {
    val items = registries[CommonRegistries.Key.Item]
    val testItems = SimpleItemGroup(Id(MOD_ID, "test_items"), null)
    val settings = ItemSettings(maxCount = 32, isFireProof = true, group = testItems)

    items[Id(MOD_ID, "test_item")] = SimpleItem(settings)
  }
}
```

##### Fabric code
```kotlin
import me.ricky.forgric.common.CommonForgric

object Forgric : FabricInitializer(CommonForgric)
```

##### Forge Code
```kotlin
import me.ricky.forgric.common.CommonForgric
import me.ricky.forgric.common.MOD_ID
import net.minecraftforge.fml.common.Mod

@Mod(MOD_ID)
object Forgric : ForgeInitializer(CommonForgric)
```