package com.cheng.test;

import com.cheng.api.common.util.ThreadPoolUtil;
import com.cheng.test.base.SpringBaseTest;
import org.junit.Test;

import java.util.stream.IntStream;

/**
 *
 * @author fengcheng
 * @version 2020/6/29
 */
public class EnvTest extends SpringBaseTest {

	@Test
	public void testThread() {
		IntStream.range(0, 100).forEach(i -> {
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ThreadPoolUtil.run(() -> System.out.println(i + " test thread " + Thread.currentThread().getName()));
		});

	}

}
