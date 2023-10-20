package id.my.mufidz.mandirinews.base


interface ViewState {
    val state: State
}

interface ViewAction

interface ActionResult

enum class State {
    LOADING, ERROR, IDLE
}