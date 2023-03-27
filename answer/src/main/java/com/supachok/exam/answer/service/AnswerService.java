package com.supachok.exam.answer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supachok.exam.answer.dto.AnswerDto;
import com.supachok.exam.answer.dto.AnswerSheetDto;
import com.supachok.exam.answer.entity.Answer;
import com.supachok.exam.answer.entity.AnswerSheet;
import com.supachok.exam.answer.repository.AnswerRepository;
import com.supachok.exam.answer.repository.AnswerSheetRepository;

@Service
@Transactional
public class AnswerService {

	@Autowired
	AnswerSheetRepository answerSheetRepository;

	@Autowired
	AnswerRepository answerRepository;

	public List<AnswerSheet> findAllAnswerSheet() {
		return answerSheetRepository.findAll();
	}

	public Optional<AnswerSheet> findAnswerSheetById(Long answerSheetId) {
		return answerSheetRepository.findById(answerSheetId);
	}

	public void saveAnswerSheet(AnswerSheet answerSheet) {
		answerSheetRepository.save(answerSheet);
		answerSheet.getAnswers().forEach(answer -> {
			answer.setAnswerSheet(answerSheet);
			answerRepository.save(answer);
		});

	}

	public void updateAnswerSheet(AnswerSheet answerSheet, AnswerSheetDto answerSheetDto) {
		answerSheet.setStudentId(answerSheetDto.getStudentId());
		answerSheet.setSurveyId(answerSheetDto.getSurveyId());
	}

	public void deleteAnswerSheet(Long answerSheetId) {
		answerSheetRepository.deleteById(answerSheetId);
	}

	public List<Answer> findAllAnswer() {
		return answerRepository.findAll();
	}

	public Optional<Answer> findAnswer(Long answerId, Long answerSheetId) {
		return answerRepository.findByIdAndAnswerSheetId(answerId, answerSheetId);
	}

	public void saveAnswers(List<Answer> answers, AnswerSheet answerSheet) {
		answers.forEach(answer -> {
			answer.setAnswerSheet(answerSheet);
			answerRepository.save(answer);
		});
	}

	public void saveAnswer(Answer answer, AnswerDto answerDto) {
		answer.setAnswerValue(answerDto.getAnswerValue());
		answer.setQuestionId(answerDto.getQuestionId());
	}

	public void deleteAnswerById(Long answerId) {
		answerRepository.deleteById(answerId);
	}

	public List<AnswerSheet> findAnswerSheetWithParam(Long surveyId, String schoolYear) {
		return answerSheetRepository.findBySurveyIdAndSchoolYear(surveyId, schoolYear);
	}

}
