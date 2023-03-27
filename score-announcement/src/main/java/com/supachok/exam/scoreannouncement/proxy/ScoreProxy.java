package com.supachok.exam.scoreannouncement.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.supachok.exam.scoreannouncement.dto.Score;

@FeignClient(name = "score", url = "${SCORE_URI:http://localhost}:8200")
public interface ScoreProxy {
	@GetMapping("/scores")
	public List<Score> retrieveScore(@RequestParam String schoolYear,@RequestParam Long surveyId);
	

}
