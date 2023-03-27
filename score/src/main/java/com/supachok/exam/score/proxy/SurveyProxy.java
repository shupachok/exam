package com.supachok.exam.score.proxy;

import java.util.HashMap;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "survey", url = "${SURVEY_URI:http://localhost}:8300")
public interface SurveyProxy {
	
	@GetMapping("/surveys/{surveyId}/correctAnswers")
	public HashMap<Long, String> retrieveCorrectAnswerBySurveyId(@PathVariable Long surveyId);

}
