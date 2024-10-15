package statix.org

import io.ktor.server.application.*

/**
 * Uses to intercept the given application call
 *
 * Middlewares can be used for a single block or as a global middleware depending on the configuration.
 * @see Middlewares
 * @see middleware
 */
interface Middleware {
    /**
     * Called before the route is called to intercept the handling of the call
     * @param call the call which is getting intercepted
     * @param receives helper object to traverse data along different middlewares
     * @see MiddlewareGroup
     *
     * @return the data produced by this call which can be traversed to the next middleware
     * @see MiddlewareData.empty
     */
    suspend fun handleCall(
        call: ApplicationCall,
        receives: MiddlewareData?,
    ): MiddlewareData
}

/**
 * Helper object to hold data along a middleware group
 * @see MiddlewareGroup
 */
interface MiddlewareData {
    companion object {
        fun empty(): MiddlewareData = EmptyMiddlewareData()
    }
}

class EmptyMiddlewareData : MiddlewareData
