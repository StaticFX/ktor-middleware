package statix.org.middlewares

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.coroutines.CancellationException
import statix.org.Middleware

class CancelTestMiddleware : Middleware {
    override suspend fun handleCall(
        call: ApplicationCall,
        cancel: (exception: CancellationException?) -> Unit,
    ) {
        call.respond(HttpStatusCode.Forbidden)
        cancel(CancellationException("Forbidden"))
    }
}
