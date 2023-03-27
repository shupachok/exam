package com.supachok.exam.scoreannouncement.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.supachok.exam.scoreannouncement.service.ScoreAnnoncementService;

@RestController
public class ScoreAnnoncementController {
	
	@Autowired
	ScoreAnnoncementService scoreAnnoncementService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private Environment environment;
	
	@PostMapping("/line-message/{schoolYear}/{surveyId}")
	public ResponseEntity<String> sendlinemessage(@PathVariable String schoolYear,@PathVariable Long surveyId) throws JsonProcessingException {
		
		
		String scoreMessage = scoreAnnoncementService.generateScoreMessage(schoolYear, surveyId);
		
		String resourceUrl
		  = environment.getProperty("LINE_MESSAGE_BROADCAST_URI");
		
		HttpHeaders headers = createHttpContentTypeAndAuthorizationHeaders();
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(scoreMessage, headers);
		ResponseEntity<String> responseEntity 
				= restTemplate.exchange(resourceUrl, HttpMethod.POST, httpEntity, String.class);
		
		return responseEntity;
	}
	
	private HttpHeaders createHttpContentTypeAndAuthorizationHeaders() {
		String token = environment.getProperty("LINE_CHANNEL_ACCESS_TOKEN");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Authorization",token);
		return headers;
	}
}
