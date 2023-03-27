package com.supachok.exam.scoreannouncement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ScoreAnnouncementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScoreAnnouncementApplication.class, args);
	}

}
