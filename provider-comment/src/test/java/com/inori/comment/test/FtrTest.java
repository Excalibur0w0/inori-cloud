package com.inori.comment.test;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.*;

public class FtrTest {


    @Test
    public void main() {

        ExecutorService threadPool = Executors.newCachedThreadPool();
        Future<Integer> future = threadPool.submit(() -> {
            Thread.sleep(1000);
            return new Random().nextInt(100);
        });

        doSomething();

        try {
            System.out.println("is B done : " + future.isDone());
//            System.out.println("result of B : " + future.get());
            System.out.println("is B done : " + future.isDone());
//            int result = future.get();

//            doSomethingWithB(result);
            Thread.sleep(3000);
            System.out.println(future.get());
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private static void doSomethingWithB(int result) {
        // TODO Auto-generated method stub
        System.out.println("BBBB");
    }

    private static void doSomething() {
        // TODO Auto-generated method stub
        System.out.println("AAAAA");

    }
}
