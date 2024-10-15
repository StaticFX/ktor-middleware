package statix.org

import io.ktor.server.application.*
import io.ktor.server.application.hooks.*
import io.ktor.server.routing.*

val Middlewares =
    createApplicationPlugin(name = "Middlewares", createConfiguration = ::MiddlewareConfig) {
        val middleware = this.pluginConfig.middleware

        onCall { call ->
            middleware.handleCall(call, null)
        }
    }

/**
 * Middleware plugin specific for route segments.
 * Can be used to intercept a given route block with the provided middleware
 */
val MiddlewareRoutePlugin =
    createRouteScopedPlugin(name = "Middleware-routes", createConfiguration = ::MiddlewareRouteConfig) {
        val middleware = this.pluginConfig.middleware

        on(CallSetup) {
            middleware.handleCall(it, null)
        }
    }

class MiddlewareConfig {
    lateinit var middleware: Middleware
}

class MiddlewareRouteConfig {
    lateinit var middleware: Middleware
}

/**
 * Intercepts the given routing block with the given middleware
 * @param middleware
 * @param block
 */
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
