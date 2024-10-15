package statix.org.middlewares

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import statix.org.Middleware
import statix.org.MiddlewareData

class SingleTestMiddleware : Middleware {
    override suspend fun handleCall(
        call: ApplicationCall,
        receives: MiddlewareData?,
    ): MiddlewareData {
        call.respond(HttpStatusCode.OK)
        return MiddlewareData.empty()
    }
}
