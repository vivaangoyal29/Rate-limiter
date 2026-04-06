package org.example.comp;

public class RateLimiter {
    LimitingStrategy strategy;

    public void setStrategy(LimitingStrategy strategy){
        this.strategy = strategy;
    }
    public void processRequest() throws Exception{
        if(strategy.allowRequest()){
            return;
        }
        else{
            throw new Exception("Rate Limit Exceeded");
        }
    }

}
