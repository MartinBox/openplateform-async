package com.smy.common.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Test;

public class NoTransaction extends AbstractThreadTest {

	@Test
	public void testSuccess1() {
		// 1，异步操作
		Future<String> asyncFuture4Str = getGlobalThreadPool().submit(
				new Callable<String>() {
					public String call() throws Exception {
						return doSomthingSuccess("testSuccess1_String", 1, "result1");
					}
				});
		Future<Integer> asyncFuture4Int = getGlobalThreadPool().submit(
				new Callable<Integer>() {
					public Integer call() throws Exception {
						return doSomthingSuccess("testSuccess1_Integer", 1, 10);
					}
				});
		Future<Long> asyncFuture4Long = getGlobalThreadPool().submit(
				new Callable<Long>() {
					public Long call() throws Exception {
						return doSomthingSuccess("testSuccess1_Long", 1, 30L);
					}
				});

		try {
			// 2，主操作：
			String mainResult = doSomthingSuccess("testSuccess1", 1,"mainResult");
			String stringResult = asyncFuture4Str.get();
			Integer intResult = asyncFuture4Int.get();
			Long longResult = asyncFuture4Long.get();
			System.out.println("threadName:"+Thread.currentThread().getName()+",mainResult="+mainResult+",stringResult="+stringResult+",intResult="+intResult+",longResult="+longResult);
			Assert.assertTrue(true);
		} catch (Exception ex) {
			Assert.assertTrue(false);
			ex.printStackTrace();
		}

		System.out.println("testSuccess1 threadName:"
				+ Thread.currentThread().getName());
	}

//	@Test
//	public void testFail1() {
//		// 1，异步操作
//		Future<String> asyncFuture4Str = getGlobalThreadPool().submit(
//				new Callable<String>() {
//					public String call() throws Exception {
//						return doSomthingSuccess("testFail1_String", 1, "result1");
//					}
//				});
//		Future<String> asyncFuture4Int = getGlobalThreadPool().submit(
//				new Callable<String>() {
//					public String call() throws Exception {
//						return doSomthingFail("testFail1_Integer", 1, "result2");
//					}
//				});
//		Future<Long> asyncFuture4Long = getGlobalThreadPool().submit(
//				new Callable<Long>() {
//					public Long call() throws Exception {
//						return doSomthingSuccess("testFail1_Long", 1, 30L);
//					}
//				});
//
//		try {
//			// 2，主操作：
//			String mainResult = doSomthingSuccess("testFail1", 1,"mainResult");
//			String stringResult = asyncFuture4Str.get();
//			String intResult = asyncFuture4Int.get();
//			Long longResult = asyncFuture4Long.get();
//			System.out.println("threadName:"+Thread.currentThread().getName()+",mainResult="+mainResult+",stringResult="+stringResult+",intResult="+intResult+",longResult="+longResult);
//			Assert.assertTrue(true);
//		} catch (Exception ex) {
//			Assert.assertTrue(false);
//			ex.printStackTrace();
//		}
//
//		System.out.println("testFail1 threadName:"
//				+ Thread.currentThread().getName());
//	}
}
