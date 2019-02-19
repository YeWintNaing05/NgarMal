package com.team28.borrow.util.custom_view;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class JobExecutor implements Executor {
    private final ThreadPoolExecutor threadPoolExecutor;

    public JobExecutor() {
        this.threadPoolExecutor =
                new ThreadPoolExecutor(
                        3, 5, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new JobThreadFactory());


    }

    @Override
    public void execute(Runnable command) {

    }

    private static class JobThreadFactory implements ThreadFactory {
        private int counter = 0;

        @Override
        public Thread newThread(@NonNull Runnable runnable) {
            return new Thread(runnable, "android_" + counter++);
        }
    }
}
