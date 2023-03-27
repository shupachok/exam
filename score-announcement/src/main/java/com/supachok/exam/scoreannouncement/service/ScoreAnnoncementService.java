package com.supachok.exam.scoreannouncement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supachok.exam.scoreannouncement.dto.Linemessage;
import com.supachok.exam.scoreannouncement.dto.Message;
import com.supachok.exam.scoreannouncement.dto.Score;
import com.supachok.exam.scoreannouncement.proxy.ScoreProxy;

@Service
public class ScoreAnnoncementService {


	@Autowired
	ScoreProxy scoreProxy;
	public String generateScoreMessage(String schoolYear, Long surveyId) throws JsonProcessingException {
		List<Score> retrieveScore = scoreProxy.retrieveScore(schoolYear, surveyId);
		
		StringBuilder result = new StringBuilder();
		result.append("School Year : "+schoolYear).append(System.lineSeparator());
		result.append("Survey : "+surveyId).append(System.lineSeparator());
		result.append("___________________").append(System.lineSeparator());
		retrieveScore.forEach(score -> {
			result.append(String.format("Student ID : %1$s, %2$s point", score.getStudentId(), score.getScore()))
				.append(System.lineSeparator());
		});

		
		Linemessage linemessage = new Linemessage();
		linemessage.setMessages(new Message("text",result.toString()));
		
		ObjectMapper ow = new ObjectMapper();
		String jsonString = ow.writeValueAsString(linemessage);
		return jsonString;
	}
	
}
