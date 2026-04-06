package org.example.comp;

public class FixedWindowStrategy implements LimitingStrategy {
    private int maxRequests;
    private long windowSizeInMillis;
    private int count;
    private long windowStart;
    public FixedWindowStrategy(int maxRequests, long windowSizeInMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInMillis;
        this.count = 0;
        this.windowStart = System.currentTimeMillis();
    }
    @Override
    public boolean allowRequest() {
        synchronized (this) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - windowStart >= windowSizeInMillis) {
                windowStart = currentTime;
                count = 0;
            }
            if (count < maxRequests) {
                count++;
                return true;
            }
            return false;
        }
    }
}
