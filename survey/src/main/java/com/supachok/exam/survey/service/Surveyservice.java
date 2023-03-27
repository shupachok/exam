package com.supachok.exam.survey.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supachok.exam.survey.dto.OptionDto;
import com.supachok.exam.survey.dto.QuestionDto;
import com.supachok.exam.survey.dto.SurveyDto;
import com.supachok.exam.survey.entity.Option;
import com.supachok.exam.survey.entity.Question;
import com.supachok.exam.survey.entity.Survey;
import com.supachok.exam.survey.repository.OptionRepository;
import com.supachok.exam.survey.repository.QuestionRepository;
import com.supachok.exam.survey.repository.SurveyRepository;

@Service
@Transactional
public class Surveyservice {

	@Autowired
	SurveyRepository surveyRepository;

	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	OptionRepository optionRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<Survey> findAllSurvey() {
		return surveyRepository.findAll();
	}

	public Optional<Survey> findSurveyById(Long surveyId) {
		return surveyRepository.findById(surveyId);
	}

	public Survey saveSurvey(SurveyDto survey) {
		Survey surveyEntity = convertToEntity(survey);
		return surveyRepository.save(surveyEntity);
	}

	public void updateSurvey(Survey findSurvey, SurveyDto surveyDto) {
		findSurvey.setTitle(surveyDto.getTitle());
		findSurvey.setDescription(surveyDto.getDescription());
	}

	public void deleteSurveyById(Long surveyId) {
		surveyRepository.deleteById(surveyId);
	}

	private Survey convertToEntity(SurveyDto surveyDto) {
		Survey survey = modelMapper.map(surveyDto, Survey.class);
		return survey;
	}

	public List<Question> findQuestionsBySurveyId(Long surveyId) {
		List<Question> questions = questionRepository.findBySurveyId(surveyId);
		return questions;
	}

	public Optional<Question> findQuestionById(Long questionId) {
		return questionRepository.findById(questionId);
	}

	public void saveQuestions(Survey survey, List<Question> questions) {
		questions.forEach(q -> {
			q.setSurvey(survey);
			questionRepository.save(q);
			q.getOptions().forEach(option -> {
				option.setQuestion(q);
				optionRepository.save(option);
			});
		});

	}

	public Optional<Question> findQuestion(Long questionId, Long surveyId) {
		return questionRepository.findByIdAndSurveyId(questionId, surveyId);
	}

	public void updateQuestion(Question findQuestion, QuestionDto questionDto) {
		findQuestion.setDescription(questionDto.getDescription());
		findQuestion.setCorrectAnswer(questionDto.getCorrectAnswer());
	}

	public void deleteQuestionById(Long questionId) {
		questionRepository.deleteById(questionId);
	}

	public Optional<Option> findOption(Long optionId, Long questionId, Long surveyId) {
		Optional<Question> findQuestion = questionRepository.findByIdAndSurveyId(questionId, surveyId);
		if (findQuestion.isEmpty())
			return Optional.empty();
		List<Option> options = findQuestion.get().getOptions();
		Optional<Option> filterOptions = options.stream().filter(
							option -> option.getId().equals(optionId)).findFirst();
		return filterOptions;
	}

	public void saveOptions(Question question, List<Option> options) {
		options.forEach(option -> {
			option.setQuestion(question);
		});
		
		optionRepository.saveAll(options);
		
	}

	public void updateOption(Option option, OptionDto optionDto) {
		option.setAnswer(optionDto.getAnswer());
	}

	public void deleteOptionById(Long optionId) {
		optionRepository.deleteById(optionId);
		
	}

	public HashMap<Long, String> findcorrectAnswer(Survey survey) {
		HashMap<Long, String> correctAnswers = new HashMap<Long, String>();
		survey.getQuestions().forEach(question -> {
			correctAnswers.put(question.getId(), question.getCorrectAnswer());
		});
		return correctAnswers;
	}
	
	

}
