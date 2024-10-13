package statix.org

import io.ktor.server.application.*
import kotlinx.coroutines.CancellationException

interface Middleware {
    suspend fun handleCall(
        call: ApplicationCall,
        cancel: (exception: CancellationException?) -> Unit,
    )
}
