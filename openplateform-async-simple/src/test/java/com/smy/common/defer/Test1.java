package com.smy.common.defer;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class Test1 extends AbstractThreadTest {

	final static int arrayLength = 100000;

	@Test
	public void testHash() {
		int[] intArray = buildArray(arrayLength);
		int sum = 0;
		for (int i = 0; i < arrayLength; i++) {
			sum += intArray[i];
		}
		System.out.println("testHash sum=" + sum);
		Assert.assertTrue(true);
	}

	@Test
	public void testHash2() {
		int[] intArray = buildArray(10);
		DeferManager dm = new DeferManager();
		dm.when(new SumTask(0, intArray), new SumTask(1, intArray), new SumTask(2, intArray)).success(new SuccessCallback() {

			public void callback(List<DeferResult> results) {
				int sum = 0;
				for (DeferResult result : results) {
					sum += (Integer) result.getResult();
				}
				System.out.println("testHash2 sum=" + sum);
			}

		});
		Assert.assertTrue(true);
	}

	private static int[] buildArray(int size) {
		int[] intArray = new int[size];
		for (int i = 0; i < intArray.length; i++) {
			intArray[i] = (i + 1);
		}
		return intArray;
	}

	static class SumTask extends DeferCallable<Integer> {

		private int numCode;
		private int[] intArray;

		private SumTask(int numCode, int[] intArray) {
			this.numCode = numCode;
			this.intArray = intArray;
		}

		public Integer call() throws Exception {
			int sum = 0;
			for (int i = 0; i < intArray.length; i++) {
				if (i % 3 == numCode) {
					sum += intArray[i];
				}
			}

			return sum;
		}

	}
}
