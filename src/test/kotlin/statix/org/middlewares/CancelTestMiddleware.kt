package statix.org.middlewares

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.coroutines.cancel
import statix.org.Middleware
import statix.org.MiddlewareData

class CancelTestMiddleware : Middleware {
    override suspend fun handleCall(
        call: ApplicationCall,
        receives: MiddlewareData?,
    ): MiddlewareData {
        call.respond(HttpStatusCode.Forbidden)
        call.cancel()
        return MiddlewareData.empty()
    }
}
