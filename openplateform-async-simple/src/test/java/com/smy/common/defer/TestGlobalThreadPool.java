package com.smy.common.defer;

import org.junit.Assert;
import org.junit.Test;

public class TestGlobalThreadPool extends AbstractThreadTest {
	@Test
	public void testThreadPool() {
		for (int i = 0; i < 200; i++) {
			new Thread(new Runnable() {
				public void run() {
					System.out.println(GlobalThreadPool.instance().getThreadPoolId());
				}
			}).start();

		}
		try {
			Thread.sleep(1000*6);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertTrue(true);
	}
}
