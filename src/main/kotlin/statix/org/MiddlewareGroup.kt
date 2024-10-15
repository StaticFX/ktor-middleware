package statix.org

/**
 * Consists of multiple middlewares to model a group, but is still a single middleware
 */
interface MiddlewareGroup : Middleware {
    /**
     * Represents this group as a list
     * @return the list of middlewares
     */
    fun group(): List<Middleware>
}
