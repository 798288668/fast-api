package com.cheng.test;

import com.cheng.test.base.BaseTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author fengcheng
 * @version 2020/4/13
 */
public class ToolTest extends BaseTest {

	@Test
	public void test() {
//		int sum = 0;
//		for (int i = 0; i < 15; i++) {
//			int interval = getInterval(i);
//			System.out.println(interval);
//			sum = sum + interval;
//			System.out.println(sum);
//		}
		System.out.println(getResult());
	}

	private int getInterval(int i) {
		return i < 4 ? 1 + i * i : 1 + i * i * i;
	}


	public String getResult() {
		List<String> c = Arrays.asList("6666", "7777", "8888", "9999");
		c.forEach(str ->{
			if("8888".equals(str)){
				return; //跳出当前循环,继续下一轮
			}
			System.out.println(str);

		});
		return "success";
	}


}
