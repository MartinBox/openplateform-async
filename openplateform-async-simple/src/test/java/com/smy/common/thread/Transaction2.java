package com.smy.common.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

public class Transaction2 extends AbstractThreadTest {

	/**
	 * 说明： 主线程有事务，把耗时或远程方法在异步线程中执行。异步线程的执行成败，不影响主线程事务。主线程需要等待异步线程的结束
	 *
	 * 典型场景： 1，注册时发生邮件；
	 *
	 */
	@Test
	public void testSuccess1() {

		// 1，异步操作
		Future<String> asyncFuture = getGlobalThreadPool().submit(
				new Callable<String>() {
					public String call() throws Exception {
						return doSomthingSuccess("testSuccess1",1);
					}
				});

		try {
			// 2，主操作：
			String mainResult = doSomthingSuccess("testSuccess1",1);
			String asyncResult = asyncFuture.get();//等待异步完成

			Assert.assertEquals(mainResult, asyncResult);
		} catch (Exception ex) {
			Assert.assertTrue(false);
			ex.printStackTrace();
		}

		System.out.println("testSuccess1 threadName:"
				+ Thread.currentThread().getName());
	}

	@Test
	public void testFail4Timeout() {

		// 1，异步操作
		Future<String> asyncFuture = getGlobalThreadPool().submit(
				new Callable<String>() {
					public String call() throws Exception {
						return doSomthingSuccess("testFail4Timeout",3);
					}
				});

		try {
			// 2，主操作：
			String mainResult = doSomthingSuccess("testFail4Timeout",1);
			String asyncResult = asyncFuture.get(1, TimeUnit.SECONDS);
			System.out.println(">>>>" + asyncResult);
			Assert.assertTrue(false);
		} catch (Exception ex) {
			//TODO: 由于异步处理出错，此时可以对外抛出有业务含义的异常，以便回滚事务
			Assert.assertTrue(true);
			ex.printStackTrace();

		}

		System.out.println("testFail4Timeout threadName:"
				+ Thread.currentThread().getName());
	}

	@Test
	public void testFail4Exception() {

		// 1，异步操作
		Future<String> asyncFuture = getGlobalThreadPool().submit(
				new Callable<String>() {
					public String call() throws Exception {
						return doSomthingFail("testFail4Exception",3);
					}
				});

		try {
			// 2，主操作：
			String mainResult = doSomthingSuccess("testFail4Exception",1);
			String asyncResult = asyncFuture.get(4, TimeUnit.SECONDS);
			System.out.println(">>>>" + asyncResult);
			Assert.assertTrue(false);
		} catch (Exception ex) {
			//TODO: 由于异步处理出错，此时可以对外抛出有业务含义的异常，以便回滚事务
			if(ex instanceof RuntimeException){
				System.out.println("发生运行期异常："+ex.getMessage());
				ex.printStackTrace();
			}
			Assert.assertTrue(true);
		}

		System.out.println("testFail4Exception threadName:"
				+ Thread.currentThread().getName());
	}

}
