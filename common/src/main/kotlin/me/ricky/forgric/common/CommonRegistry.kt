package me.ricky.forgric.common

data class Id(val namespace: String, val path: String)

interface CommonRegistry<T : Any> {
  operator fun set(id: Id, value: T)
  operator fun get(id: Id): T
}

interface CommonRegistries {
  operator fun <T : Any> get(key: Key<T>): CommonRegistry<T>

  sealed class Key<out T : Any> {
    object Item : Key<CommonItem>()
    object Block : Key<CommonBlock>()
  }
}