package statix.org

import io.ktor.server.application.*
import kotlinx.coroutines.CancellationException

class MutableListMiddlewareGroup(
    middlewares: List<Middleware> = listOf(),
) : MiddlewareGroup {
    private val middlewares = mutableListOf(*middlewares.toTypedArray())

    override suspend fun handleCall(
        call: ApplicationCall,
        cancel: (exception: CancellationException?) -> Unit,
    ) {
        middlewares.forEach { it.handleCall(call, cancel) }
    }

    override fun group(): List<Middleware> = middlewares.toList()

    operator fun plusAssign(middleware: Middleware) {
        this.middlewares += middleware
    }

    operator fun minusAssign(middleware: Middleware) {
        this.middlewares -= middleware
    }
}
