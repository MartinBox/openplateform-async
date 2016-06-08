package com.smy.common.defer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

public class DeferManager {

	private ExecutorService executorService;
	private final Semaphore semaphore;
	private Map<DeferCallable, Object> resultMap = new LinkedHashMap<DeferCallable, Object>(10);

	/**
	 * 所有任务是否全部执行成功
	 */
	private boolean success = true;

	public DeferManager() {
		super();
		this.executorService = GlobalThreadPool.instance().getExecutorService();
		this.semaphore = new Semaphore(Runtime.getRuntime().availableProcessors() + 1);
	}

	public DeferManager(int threadNums) {
		super();
		this.executorService = GlobalThreadPool.instance().getExecutorService();
		this.semaphore = new Semaphore(threadNums);
	}

	public DeferManager(ExecutorService executorService, int threadNums) {
		super();
		this.executorService = executorService;
		this.semaphore = new Semaphore(threadNums);
	}

	public Promise when(DeferCallable... callables) {
		if (callables != null) {
			// 执行异步任务列表
			executeDeferTaskList(callables);
			// 检查异步任务执行结果
			checkFutureResult();
			// 返回处理结果
			return buildPromise();
		}
		return new Promise();
	}

	private Promise buildPromise() {
		List<DeferResult> deferredResults = new ArrayList<DeferResult>(10);
		for (DeferCallable deferTask : resultMap.keySet()) {
			deferredResults.add((DeferResult) resultMap.get(deferTask));
		}

		return new Promise(success, deferredResults);
	}

	/**
	 * 检查异步调用结果
	 */
	private void checkFutureResult() {
		for (DeferCallable deferTask : resultMap.keySet()) {
			DeferResult result = new DeferResult();
			Future<Object> future = (Future<Object>) resultMap.get(deferTask);
			try {
				Object futureResult = null;
				DeferCallable deferCallable = (DeferCallable) deferTask;
				if (deferCallable.getTimeout() > 0) {// 已定义超时时长
					futureResult = future.get(deferCallable.getTimeout(), deferCallable.getTimeUnit());
				} else {
					futureResult = future.get();
				}

				result.setResult(futureResult);
				result.setSuccess(true);
			} catch (Exception ex) {
				result.setSuccess(false);
				result.setException(ex);

				success = false;// 只要有一个失败，全部任务就标示为失败
			}

			// 保持结果
			resultMap.put(deferTask, result);
		}
	}

	/**
	 * 按顺序执行异步调用
	 *
	 * @param tasks
	 */
	private void executeDeferTaskList(DeferCallable[] tasks) {
		for (final DeferCallable task : tasks) {
			Future<Object> future = executorService.submit(new Callable<Object>() {

				public Object call() throws Exception {
					try {
						semaphore.acquire();
						return task.call();
					} finally {
						semaphore.release();
					}
				}
			});
			resultMap.put(task, future);
		}
	}

}
