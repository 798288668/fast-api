package com.cheng.test;

import com.cheng.test.base.SpringBaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 *
 * @author fengcheng
 * @version 2020/6/29
 */
public class AyncTest extends SpringBaseTest {

	@Test
	public void testAsyncAnnotationForMethodsWithReturnType() throws InterruptedException, ExecutionException {
		System.out.println("Invoking an asynchronous method. " + Thread.currentThread().getName());
		Future<String> future = asyncMethodWithReturnType();

		while (true) {  ///这里使用了循环判断，等待获取结果信息
			if (future.isDone()) {  //判断是否执行完毕
				System.out.println("Result from asynchronous process - " + future.get());
				break;
			}
			System.out.println("Continue doing something else. ");
			Thread.sleep(1000);
		}
	}

	@Async
	public void asyncMethodWithVoidReturnType() {
		System.out.println("Execute method asynchronously. " + Thread.currentThread().getName());
	}

	@Async
	public Future<String> asyncMethodWithReturnType() {
		System.out.println("Execute method asynchronously - " + Thread.currentThread().getName());
		try {
			Thread.sleep(2000);
			return new AsyncResult<String>("hello world !!!!");
		} catch (InterruptedException e) {
			//
		}

		return null;
	}

}
