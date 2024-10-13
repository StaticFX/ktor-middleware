package statix.org

fun middlewareGroup(builder: MutableListMiddlewareGroup.() -> Unit): MiddlewareGroup = MutableListMiddlewareGroup().apply(builder)
