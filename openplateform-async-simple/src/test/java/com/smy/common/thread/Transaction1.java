package com.smy.common.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Assert;
import org.junit.Test;

public class Transaction1 extends AbstractThreadTest{

	/**
	 * 说明：
	 * 主线程有事务，把耗时或远程方法在异步线程中执行。异步线程的执行成败，不影响主线程事务。主线程不必等待异步线程的结束
	 *
	 * 典型场景：
	 * 1，注册时发生邮件；
	 *
	 */
	@Test
	public void testSuccess(){

		//1，异步操作
		getGlobalThreadPool().execute(new Runnable() {
			public void run() {
				doSomthingSuccess("testSuccess",1);
			}
		});

		//2，主操作：
		doSomthingSuccess("testSuccess",1);

		System.out.println("testSuccess threadName:" + Thread.currentThread().getName());
		Assert.assertTrue(true);
	}

	@Test
	public void testFail(){

		//1，异步操作
		getGlobalThreadPool().execute(new Runnable() {
			public void run() {
				doSomthingFail("testFail",1);
			}
		});

		//2，主操作：
		doSomthingSuccess("testFail",1);

		System.out.println("testFail threadName:" + Thread.currentThread().getName());
		Assert.assertTrue(true);
	}
}
