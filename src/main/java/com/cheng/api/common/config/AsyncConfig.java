package com.cheng.api.common.config;

import com.cheng.api.common.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncExecutionAspectSupport;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * @author fengcheng
 * @version 2020/6/27
 */
@Slf4j
@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {

	private static final int THREAD_SIZE = Runtime.getRuntime().availableProcessors();

	@Bean(name = AsyncExecutionAspectSupport.DEFAULT_TASK_EXECUTOR_BEAN_NAME)
	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		//核心线程池大小
		executor.setCorePoolSize(THREAD_SIZE);
		//最大线程数
		executor.setMaxPoolSize(THREAD_SIZE * 2);
		//队列容量
		executor.setQueueCapacity(200);
		//活跃时间
		executor.setKeepAliveSeconds(5);
		//线程名字前缀
		executor.setThreadNamePrefix("AsyncExecutor-");
		// rejection-policy：当pool已经达到max size的时候，如何处理新任务
		// CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setAwaitTerminationSeconds(5);
		executor.initialize();
		return executor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return (ex, method, params) -> log
				.error("Async method: {} has uncaught exception, params: {}, exception: {}", method.getName(),
						JsonUtils.toJson(params), ex);
	}
}
