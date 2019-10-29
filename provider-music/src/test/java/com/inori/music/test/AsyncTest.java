package com.inori.music.test;

import org.junit.Test;
import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.List;

public class AsyncTest {

    @Test
    public void testAsync() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Thread th = new Thread(() -> System.out.println(finalI));
            threads.add(th);
            th.start();
        }

        for (Thread th :
                threads) {
            th.join();
        }

        System.out.println("end");

    }
}
