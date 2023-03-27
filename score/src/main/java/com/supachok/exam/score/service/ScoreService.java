package com.supachok.exam.score.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supachok.exam.score.dto.Answer;
import com.supachok.exam.score.dto.AnswerSheet;
import com.supachok.exam.score.entity.Score;
import com.supachok.exam.score.proxy.AnswerProxy;
import com.supachok.exam.score.proxy.SurveyProxy;
import com.supachok.exam.score.repository.ScoreRepository;

@Service
@Transactional
public class ScoreService {
	
	@Autowired
	private SurveyProxy surveyProxy;
	
	@Autowired
	private AnswerProxy answerProxy;

	@Autowired
	ScoreRepository scoreRepository;

	public List<Score> findAllScore() {
		return scoreRepository.findAll();
	}

	public void calculate(String schoolYear, Long surveyId) {
		scoreRepository.deleteBySchoolYear(schoolYear);
		
		HashMap<Long, String> correctAnswerMap = surveyProxy.retrieveCorrectAnswerBySurveyId(surveyId);
		
		List<AnswerSheet> answerSheets = answerProxy.retrieveAnswerSheets(surveyId, schoolYear);
		for (AnswerSheet answerSheet : answerSheets) {
			long score = 0;
			List<Answer> answers = answerSheet.getAnswers();
			for (Answer answer : answers) {
				String correctAnswer = correctAnswerMap.get(answer.getQuestionId());
				if(correctAnswer.equals(answer.getAnswerValue())){
					score++;
				}
			}
			
			Score scoreStudent = new Score();
			scoreStudent.setScore(score);
			scoreStudent.setSurveyId(surveyId);
			scoreStudent.setSchoolYear(schoolYear);
			scoreStudent.setStudentId(answerSheet.getStudentId());
			scoreRepository.save(scoreStudent);
		}
		
	}

	public List<Score> findScoreWithParam(String schoolYear, Long surveyId) {
		return scoreRepository.findBySchoolYearAndSurveyId(schoolYear, surveyId);
	}
}
