package statix.org.middlewares

import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.coroutines.cancel
import statix.org.Middleware
import statix.org.MiddlewareData

class DataPassingMiddleware : Middleware {
    override suspend fun handleCall(
        call: ApplicationCall,
        receives: MiddlewareData?,
    ): MiddlewareData {
        val data = SingleValueMiddlewareData()
        data.data = "Test"
        return data
    }
}

class DataReceivingMiddleware : Middleware {
    override suspend fun handleCall(
        call: ApplicationCall,
        receives: MiddlewareData?,
    ): MiddlewareData {
        if (receives == null) call.cancel()
        val data = receives as SingleValueMiddlewareData
        call.respondText { data.data }
        return MiddlewareData.empty()
    }
}

class SingleValueMiddlewareData : MiddlewareData {
    var data: String = ""
}
