package com.supachok.exam.score.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.supachok.exam.score.dto.AnswerSheet;

@FeignClient(name = "answer", url = "${ANSWER_URI:http://localhost}:8100")
public interface AnswerProxy {
	
	@GetMapping("/answer-sheets")
	public List<AnswerSheet> retrieveAnswerSheets(
			@RequestParam Long surveyId,@RequestParam String schoolYear);
	
}
