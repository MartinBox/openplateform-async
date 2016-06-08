package com.smy.common.defer;

import java.util.concurrent.TimeUnit;

public abstract class DeferCallable<T> {

	/**
	 * 异步超时时长，单位：毫秒 小于或等于0，表示不超时
	 */
	private long timeout = 0;
	private final static TimeUnit UNIT = TimeUnit.MILLISECONDS;

	public DeferCallable() {

	}

	public abstract T call() throws Exception;

	/**
	 * 设置超时，单位：毫秒
	 *
	 * @param timeout
	 * @return
	 */
	public DeferCallable<T> timeout(long timeout) {
		this.timeout = timeout;
		return this;
	}

	public long getTimeout() {
		return timeout;
	}

	public TimeUnit getTimeUnit() {
		return UNIT;
	}
}
