package statix.org

import io.ktor.server.application.*

class MutableListMiddlewareGroup(
    middlewares: List<Middleware> = listOf(),
) : MiddlewareGroup {
    private val middlewares = mutableListOf(*middlewares.toTypedArray())

    override suspend fun handleCall(
        call: ApplicationCall,
        receives: MiddlewareData?,
    ): MiddlewareData {
        var data = receives

        for (middleware in middlewares) {
            data = middleware.handleCall(call, data)
        }

        return data ?: MiddlewareData.empty()
    }

    override fun group(): List<Middleware> = middlewares.toList()

    operator fun plusAssign(middleware: Middleware) {
        this.middlewares += middleware
    }

    operator fun minusAssign(middleware: Middleware) {
        this.middlewares -= middleware
    }
}
