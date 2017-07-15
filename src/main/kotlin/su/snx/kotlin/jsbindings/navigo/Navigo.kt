package su.snx.kotlin.jsbindings.navigo

external class Navigo(root: String?, useHash: Boolean, hash: String) {
    fun navigate(path: String = definedExternally, absolute: Boolean = definedExternally): Navigo
    fun on(callback: (params: dynamic) -> Unit): Navigo
    fun on(callback: (params: dynamic) -> Unit, hooks: Hooks): Navigo
    fun on(path: String, callback: (params: dynamic) -> Unit): Navigo
    fun on(path: String, callback: (params: dynamic) -> Unit, hooks: Hooks): Navigo
    fun on(vararg route: Route): Navigo
    fun on(vararg route: Route, hooks: Hooks): Navigo
    fun off(): Navigo
    fun notFound(handler: (query: String) -> Unit): Navigo
    fun notFound(handler: (query: String) -> Unit, hooks: Hooks): Navigo
    fun resolve(current: String = definedExternally): Boolean
    fun destroy()
    fun updatePageLinks()
    fun generate(name: String): String
    fun link(path: String): String
    fun pause(paused: Boolean = definedExternally)
    fun resume()
    fun disableIfAPINotAvailable()
    fun lastRouteResolved(): LastRoute
    fun getLinkPath(): String
    fun hooks(hooks: Hooks)
}

interface LastRoute {
    val url: String
    val query: String
}

class Route(route: String,  name: String, callback: (params: dynamic) -> Unit) {
    init {
        val jsObject = js("{[]}")
        jsObject["as"] = name
        jsObject["uses"] = callback
        asDynamic()[route] = jsObject
    }

    constructor(route: String, name: String, callback: (params: dynamic) -> Unit, hooks: Hooks)
            : this(route, name, callback) {
        val thisDynamic = asDynamic()[route]
        thisDynamic["hooks"] = hooks
    }
}

interface Hooks {
    fun before(done: (isDone: Boolean) -> Unit, params: dynamic)
    fun after(params: dynamic)
    fun leave(params: dynamic)
    fun already(params: dynamic)
}

abstract class AbstractHooks : Hooks {
    init {
        asDynamic()["before"] = fun(done: (isDone: Boolean) -> Unit, params: dynamic) {
            before(done, params)
        }
        asDynamic()["after"] = fun(params: dynamic) {
            after(params)
        }
        asDynamic()["leave"] = fun(params: dynamic) {
            leave(params)
        }

        asDynamic()["already"] = fun(params: dynamic) {
            already(params)
        }
    }

    override fun before(done: (isDone: Boolean) -> Unit, params: dynamic) {
        done(true)
    }

    override fun after(params: dynamic) {
    }

    override fun leave(params: dynamic) {
    }

    override fun already(params: dynamic) {
    }
}


