package statix.org

interface MiddlewareGroup : Middleware {
    fun group(): List<Middleware>
}
