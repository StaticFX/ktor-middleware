# KTOR Middleware

Adds Laravel inspired Middlewares to KTOR with simple syntax.

## Add to your project
```shell
implementation("io.github.staticfx:ktor-middleware:1.0.1")
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

#### Transfer Data

```kotlin
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

