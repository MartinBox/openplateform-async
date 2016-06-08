package com.smy.common.defer;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 全局线程池
 * @author 周江
 *
 */
public class GlobalThreadPool {

	private static ExecutorService executorService;
	private static String threadPoolId;//用于测试多线程并发下，是否同个示例

	private static class GlobalThreadPoolHolder {

		public static GlobalThreadPool instance = new GlobalThreadPool();
	}

	private GlobalThreadPool() {
		executorService = Executors.newFixedThreadPool(100);
		threadPoolId = UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static GlobalThreadPool instance() {
		return GlobalThreadPoolHolder.instance;
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public String getThreadPoolId() {
		return threadPoolId;
	}
}
