package me.ricky.forgric.common

data class BlockSettings(
  val material: MaterialSettings
)

interface CommonBlock {
  val settings: BlockSettings?
}

interface NativeBlock : CommonBlock {
  fun toNativeBlock(): Any
}

data class SimpleBlock(override val settings: BlockSettings) : CommonBlock