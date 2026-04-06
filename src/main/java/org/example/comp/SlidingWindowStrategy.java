package org.example.comp;

import java.util.*;


public class SlidingWindowStrategy implements LimitingStrategy {
    int maxRequests;
    long windowSizeInMillis;
    Queue<Long> requestTimestamps;

    public SlidingWindowStrategy(int maxRequests, long windowSizeInMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInMillis;
        requestTimestamps = new LinkedList<>();
    }

    @Override
    public boolean allowRequest(){
        synchronized(requestTimestamps) {
            long currentTime = System.currentTimeMillis();
            long windowStartTime = currentTime - windowSizeInMillis;
            while(!requestTimestamps.isEmpty() && requestTimestamps.peek() <  windowStartTime) {
                requestTimestamps.poll();
            }
            if(requestTimestamps.size() >= maxRequests){
                return false;
            }
            requestTimestamps.add(currentTime);
            return true;

        }
    }
}
