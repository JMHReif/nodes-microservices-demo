package com.jmhreif.frontend;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class FrontendApplication {
	@Value("${backend.hostname:localhost}")
	private String hostname;

	public static void main(String[] args) {
		SpringApplication.run(FrontendApplication.class, args);
	}

	@Bean
	WebClient client() { return WebClient.create("http://" + hostname + ":8081"); }
}

@RestController
@AllArgsConstructor
@RequestMapping("/reviews")
class MessageController {
	private final WebClient client;

	@GetMapping
	Flux<Review> getReviews() {
		return client.get().uri("/neo").retrieve().bodyToFlux(Review.class);
	}
}

@Data
class Review {
	private Long neoId;
	private String review_id;

	private String book_id, review_text, date_added;
	private Integer rating;
}