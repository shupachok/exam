package com.supachok.exam.score.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supachok.exam.score.entity.Score;
import com.supachok.exam.score.service.ScoreService;

@RestController
public class ScoreController {
	
	@Autowired
	ScoreService scoreService;
	@GetMapping("/scores")
	public List<Score> retrieveScore(String schoolYear,Long surveyId) {
		if(surveyId != null && schoolYear != null)
			return scoreService.findScoreWithParam(schoolYear,surveyId);		
		return scoreService.findAllScore();
	}
	
	@PostMapping("/scores/calculation/{schoolYear}/{surveyId}")
	public ResponseEntity<Object> calculateScore(@PathVariable String schoolYear,@PathVariable Long surveyId) {
		scoreService.calculate(schoolYear,surveyId);
		return ResponseEntity.noContent().build();
	}
}
