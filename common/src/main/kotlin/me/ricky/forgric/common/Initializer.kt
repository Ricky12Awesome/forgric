package me.ricky.forgric.common

fun interface Initializer {
  fun Adaptor.onInitialize()
}

fun interface ClientInitializer {
  fun Adaptor.onInitializeClient()
}

fun interface ServerInitializer {
  fun Adaptor.onInitializeServer()
}

fun Initializer.onInitialize(adaptor: Adaptor) = adaptor.onInitialize()
fun ClientInitializer.onInitializeClient(adaptor: Adaptor) = adaptor.onInitializeClient()
fun ServerInitializer.onInitializeServer(adaptor: Adaptor) = adaptor.onInitializeServer()