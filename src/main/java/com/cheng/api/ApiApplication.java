/*
 * Copyright &copy; cc All rights reserved.
 */

package com.cheng.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cc
 */
@RestController
@EnableScheduling
@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	private final ApplicationEventPublisher publisher;

	public ApiApplication(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	@GetMapping(value = "/common/up")
	public String up() {
		AvailabilityChangeEvent.publish(publisher, this, ReadinessState.ACCEPTING_TRAFFIC);
		return "up";
	}

	@GetMapping(value = "/common/down")
	public String down() {
		AvailabilityChangeEvent.publish(publisher, this, ReadinessState.REFUSING_TRAFFIC);
		return "down";
	}

	@GetMapping(value = "/common/play")
	public String play() throws InterruptedException {
		Thread.sleep(20000);
		return "hey, play with me!";
	}
}
