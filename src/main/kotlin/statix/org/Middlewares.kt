package statix.org

import io.ktor.server.application.*
import io.ktor.server.application.hooks.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel

val Middlewares =
    createApplicationPlugin(name = "Middlewares", createConfiguration = ::MiddlewareConfig) {
        val middleware = this.pluginConfig.middleware

        onCall { call ->
            val cancel = { exception: CancellationException? -> call.cancel(exception) }
            middleware.handleCall(call, cancel)
        }
    }

val MiddlewareRoutePlugin =
    createRouteScopedPlugin(name = "Middleware-routes", createConfiguration = ::MiddlewareRouteConfig) {
        val middleware = this.pluginConfig.middleware

        on(CallSetup) {
            val cancel = { exception: CancellationException? -> it.cancel(exception) }
            middleware.handleCall(it, cancel)
        }
    }

class MiddlewareConfig {
    lateinit var middleware: Middleware
}

class MiddlewareRouteConfig {
    lateinit var middleware: Middleware
}

fun Route.middleware(
    middleware: Middleware,
    block: Route.() -> Unit,
): Route {
    val middlewareRoute = createChild(RootRouteSelector())

    install(MiddlewareRoutePlugin) {
        this.middleware = middleware
    }

    middlewareRoute.block()
    return middlewareRoute
}
