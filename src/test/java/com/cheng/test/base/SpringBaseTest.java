package com.cheng.test.base;

import com.cheng.api.ApiApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author fengcheng
 * @version 2020/6/29
 */
@SpringBootTest(classes = {ApiApplication.class})
public class SpringBaseTest {

	@BeforeEach
	public void testBefore() {
		System.out.println("SpringBootTest Start");
		System.out.println("");
		System.out.println("");
	}

	@AfterEach
	public void testAfter() {
		System.out.println("");
		System.out.println("");
		System.out.println("SpringBootTest End");
	}

}
