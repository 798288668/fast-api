package com.cheng.test.base;

import com.cheng.api.ApiApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author fengcheng
 * @version 2020/6/29
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApiApplication.class})
public class SpringBaseTest {

	@Before
	public void testBefore() {
		System.out.println("SpringBootTest Start");
		System.out.println("");
		System.out.println("");
	}

	@After
	public void testAfter() {
		System.out.println("");
		System.out.println("");
		System.out.println("SpringBootTest End");
	}

}
