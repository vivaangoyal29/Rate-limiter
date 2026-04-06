package org.example.comp;

public class RateLimiterFactory {
    public static RateLimiter createLimiter(String strategy, int maxRequests, int windowSizeInMillis){
        RateLimiter limiter = new RateLimiter();
        if(strategy.equalsIgnoreCase("fixed")){
            limiter.setStrategy(new FixedWindowStrategy(maxRequests, windowSizeInMillis));
        }
        else if(strategy.equalsIgnoreCase("sliding")){
            limiter.setStrategy(new SlidingWindowStrategy(maxRequests, windowSizeInMillis));
        }
        else{
            throw new IllegalArgumentException("Unsupported strategy type: " + strategy);
        }
        return limiter;
    }
}
