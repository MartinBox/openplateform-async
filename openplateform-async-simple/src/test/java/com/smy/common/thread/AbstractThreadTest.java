package com.smy.common.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Before;

public class AbstractThreadTest {
	private ExecutorService threadPool;
	@Before
	public void setUp() throws Exception {
		threadPool = Executors.newFixedThreadPool(3);
	}

	@After
	public void tearDown() throws Exception {
		threadPool.shutdown();
	}

	protected ExecutorService getGlobalThreadPool(){
		return threadPool;
	}

	@SuppressWarnings("static-access")
	protected void sleep(int seconds){
		try {
			Thread.currentThread().sleep(seconds*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected String doSomthingSuccess(String method, int seconds){
		sleep(seconds);
		System.out.println(method+".doSomthingSuccess threadName:" + Thread.currentThread().getName());
		return "OK";
	}

	protected <T> T doSomthingSuccess(String method, int seconds, T result){
		sleep(seconds);
		System.out.println(method+".doSomthingSuccess threadName:" + Thread.currentThread().getName());
		return result;
	}

	protected String doSomthingFail(String method, int seconds){
		sleep(seconds);
		System.out.println(method+".doSomthingFail threadName:" + Thread.currentThread().getName());
		throw new RuntimeException("方法执行出错");
	}

	protected <T> T doSomthingFail(String method, int seconds, T result){
		sleep(seconds);
		System.out.println(method+".doSomthingFail threadName:" + Thread.currentThread().getName());
		throw new RuntimeException("方法执行出错");
	}
}
