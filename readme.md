# KTOR Middleware

Adds Laravel inspired Middlewares to KTOR with simple syntax.

## Add to your project
```shell
implementation("io.github.staticfx:ktor-middleware:1.0.0")
```

### Simple Middleware

```kotlin
class MyMiddleware: Middleware {
    override suspend fun handleCall(call: ApplicationCall, cancel: (exception: CancellationException?) -> Unit) {
        //your code
    }
}
```

### Middleware groups
```kotlin
val group = middlewareGroup { 
    this += MyMiddleware()
}
```

### Use Middleware for specific routes
Simple:

```kotlin
middleware(MyMiddleware()) {
    get("/") {
    }
}
```
Groups:

```kotlin
middleware(MyMiddlewareGroup()) {
    get("/") {
    }
}
```

## Global Middleware
```kotlin
install(Middlewares) {
    middleware = MyMiddleware()
}
```

Or with groups

```kotlin
install(Middlewares) {
    middleware = middlewareGroup {
        this += MyMiddleware()
    }
}
```

### Roadmap
- [x] Add data object to persist data between middlewares
- [ ] Improve experience of the cancel function
- [ ] Add better builders for middlewares
- [ ] Add sample middlewares

