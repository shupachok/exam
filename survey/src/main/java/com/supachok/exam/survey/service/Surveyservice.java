package com.supachok.exam.survey.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supachok.exam.survey.dto.OptionDto;
import com.supachok.exam.survey.dto.OptionFetchDataDto;
import com.supachok.exam.survey.dto.QuestionDto;
import com.supachok.exam.survey.dto.QuestionFetchDataDto;
import com.supachok.exam.survey.dto.SurveyDto;
import com.supachok.exam.survey.dto.SurveyFetchDataDto;
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
		Optional<Option> filterOptions = options.stream().filter(option -> option.getId().equals(optionId)).findFirst();
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

	public List<SurveyFetchDataDto> findAllSurvey() {
		List<Object[]> results = surveyRepository.findAllSurveysWithQuestionsAndOptions();
		Map<Long, SurveyFetchDataDto> surveys = new HashMap<>();
		for (Object[] row : results) {
			Survey survey = (Survey) row[0];
			Question question = (Question) row[1];
			Option option = (Option) row[2];
			
			SurveyFetchDataDto surveyDto = new SurveyFetchDataDto();
			
			if (!surveys.containsKey(survey.getId())) {
				BeanUtils.copyProperties(survey, surveyDto);
				surveys.put(survey.getId(), surveyDto);
			} 
			else {
				surveyDto = surveys.get(survey.getId());
			}

			if (question != null) {
				QuestionFetchDataDto questionDto = new QuestionFetchDataDto();
				BeanUtils.copyProperties(question, questionDto);
				
				surveyDto.getQuestions().add(questionDto);
				
				if (option != null) {
					OptionFetchDataDto optionDto = new OptionFetchDataDto();
					BeanUtils.copyProperties(option, optionDto);
					
					questionDto.getOptions().add(optionDto);
				}
			}
		}
		return new ArrayList<>(surveys.values());
	}
}
