package com.cheng.test;

import com.cheng.test.base.BaseTest;
import org.junit.Test;

/**
 *
 * @author fengcheng
 * @version 2020/4/13
 */
public class ToolTest extends BaseTest {

	@Test
	public void test() {
		int sum = 0;
		for (int i = 0; i < 15; i++) {
			int interval = getInterval(i);
			System.out.println(interval);
			sum = sum + interval;
			System.out.println(sum);
		}
	}

	private int getInterval(int i) {
		return i < 4 ? 1 + i * i : 1 + i * i * i;
	}

}
