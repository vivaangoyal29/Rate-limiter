package org.example.comp;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner sc=new Scanner(System.in);
        String strategy =sc.next();
        RemoteResourceProxy resourceProxy = new RemoteResourceProxy(strategy, 10, 100);

        int totalRequests = 1000000;
        int threadPoolSize= 80;

        AtomicInteger allowed = new AtomicInteger(0);
        AtomicInteger blocked = new AtomicInteger(0);

        CountDownLatch latch = new CountDownLatch(totalRequests);
        long startTime = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);


        for(int i=0;i<totalRequests;i++){
            executor.submit(()->{
                try{
                    LimiterReponse result = resourceProxy.makeRequest();
                    if(result.isAllowed()){
                        allowed.incrementAndGet();
                    }
                    else {
                        blocked.incrementAndGet();
                    }
                }
                finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        long endTime = System.currentTimeMillis();

        System.out.println("Total Requests: " + totalRequests);
        System.out.println("Allowed: " + allowed.get());
        System.out.println("Blocked: " + blocked.get());
        System.out.println("Time Taken: " + (endTime - startTime) + " ms");
    }
}