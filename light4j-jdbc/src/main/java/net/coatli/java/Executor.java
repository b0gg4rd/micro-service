package net.coatli.java;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Executor {

  private static final int CORE_POOL_SIZE          = 200;
  private static final int MAXIMUM_POOL_SIZE       = 400;
  private static final int KEEP_ALIVE_TIME         = 200;
  private static final int BLOCKING_QUEUE_CAPACITY = 200;

  private Executor() {
    throw new AssertionError();
  }

  public static ExecutorService EXECUTOR = new ThreadPoolExecutor(
      CORE_POOL_SIZE,
      MAXIMUM_POOL_SIZE,
      KEEP_ALIVE_TIME,
      TimeUnit.MILLISECONDS,
      new LinkedBlockingQueue<Runnable>(BLOCKING_QUEUE_CAPACITY),
      new ThreadPoolExecutor.CallerRunsPolicy());

}
