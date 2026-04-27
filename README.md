## System Design

The project implements a thread-safe rate-limiting mechanism using the **Proxy Design Pattern**. The `RemoteResourceProxy` acts as an intermediary, ensuring that requests to the `RemoteResource` are validated against a `RateLimiter` before execution.

### UML Diagram

```mermaid
classDiagram
    class LimitingStrategy {
        <<interface>>
        +allowRequest() boolean
    }

    class FixedWindowStrategy {
        -int maxRequests
        -long windowSizeInMillis
        -int count
        -long windowStart
        +allowRequest() boolean
    }

    class SlidingWindowStrategy {
        -int maxRequests
        -long windowSizeInMillis
        -Queue~Long~ requestTimestamps
        +allowRequest() boolean
    }

    class RateLimiter {
        -LimitingStrategy strategy
        +setStrategy(LimitingStrategy strategy)
        +processRequest() void
    }

    class RateLimiterFactory {
        +createLimiter(String strategy, int maxRequests, int windowSizeInMillis) RateLimiter
    }

    class RemoteResource {
        +fetch() String
    }

    class RemoteResourceProxy {
        -RateLimiter limiter
        -RemoteResource remoteResource
        +makeRequest() LimiterReponse
    }

    LimitingStrategy <|.. FixedWindowStrategy : implements
    LimitingStrategy <|.. SlidingWindowStrategy : implements
    RateLimiter o-- LimitingStrategy : uses
    RemoteResourceProxy --> RateLimiter : delegates to
    RemoteResourceProxy --> RemoteResource : protects
    RateLimiterFactory ..> RateLimiter : creates
    RateLimiterFactory ..> FixedWindowStrategy : instantiates
    RateLimiterFactory ..> SlidingWindowStrategy : instantiates
