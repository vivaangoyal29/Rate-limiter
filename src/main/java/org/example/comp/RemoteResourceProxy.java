package org.example.comp;

public class RemoteResourceProxy {
    RateLimiter limiter;
    RemoteResource remoteResource;
    RemoteResourceProxy(String strategy, int maxRequests, int windowSizeInMillis){
        this.limiter =RateLimiterFactory.createLimiter(strategy, maxRequests, windowSizeInMillis);
        this.remoteResource = new RemoteResource();
    }

    public LimiterReponse makeRequest(){
        try{
            limiter.processRequest();
            return new LimiterReponse(remoteResource.fetch(), true);
        }
        catch (Exception e){
            return new LimiterReponse(e.getMessage(), false);
        }
    }
}
