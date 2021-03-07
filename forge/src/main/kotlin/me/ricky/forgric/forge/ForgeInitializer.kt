package me.ricky.forgric.forge

import me.ricky.forgric.common.Adaptor
import me.ricky.forgric.common.Initializer
import me.ricky.forgric.common.onInitialize

open class ForgeInitializer(
  protected val initializer: Initializer,
  protected val adaptor: Adaptor = ForgeAdaptor(),
  init: Boolean = true
) {
  init {
    if (init) {
      initializeCommon()
    }
  }

  fun initializeCommon() = initializer.onInitialize(adaptor)
}