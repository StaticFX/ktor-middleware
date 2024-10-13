package statix.org

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import io.ktor.utils.io.*
import org.junit.jupiter.api.assertThrows
import statix.org.middlewares.CancelTestMiddleware
import statix.org.middlewares.SingleTestMiddleware
import kotlin.test.Test
import kotlin.test.assertEquals

class MiddlewareTest {
    @Test
    fun testRoot() =
        testApplication {
            application {
                routing {
                    get("/") { call.respond(HttpStatusCode.OK) }
                }
            }

            val response = client.get("/")
            assertEquals(HttpStatusCode.OK, response.status)
        }

    @Test
    fun testSingleMiddleware() =
        testApplication {
            application {
                routing {
                    middleware(SingleTestMiddleware()) {
                        get("/") {
                            // middleware should handle this request
                        }
                    }
                }
            }
            val response = client.get("/")
            assertEquals(HttpStatusCode.OK, response.status)
        }

    @Test
    fun testCancellingMiddleware() =
        testApplication {
            application {
                routing {
                    middleware(CancelTestMiddleware()) {
                        get("/") {
                            // middleware should handle this request
                        }
                    }
                }
            }
            assertThrows<CancellationException> { client.get("/") }
        }

    @Test
    fun testRootMiddleware() =
        testApplication {
            application {
                install(Middlewares) {
                    middleware = SingleTestMiddleware()
                }
                routing {
                    get("/") { }
                }
            }
            val response = client.get("/")
            assertEquals(HttpStatusCode.OK, response.status)
        }

    @Test
    fun testMiddlewareGroup() =
        testApplication {
            application {
                install(Middlewares) {
                    middleware =
                        middlewareGroup {
                            this += SingleTestMiddleware()
                            this += CancelTestMiddleware()
                        }
                }
                routing {
                    get("/") { }
                }
            }
            assertThrows<CancellationException> { client.get("/") }
        }
}
