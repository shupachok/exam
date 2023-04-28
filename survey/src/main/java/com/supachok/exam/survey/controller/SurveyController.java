package com.supachok.exam.survey.controller;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.supachok.exam.survey.dto.OptionDto;
import com.supachok.exam.survey.dto.QuestionDto;
import com.supachok.exam.survey.dto.SurveyDto;
import com.supachok.exam.survey.dto.SurveyFetchDataDto;
import com.supachok.exam.survey.entity.Option;
import com.supachok.exam.survey.entity.Question;
import com.supachok.exam.survey.entity.Survey;
import com.supachok.exam.survey.service.Surveyservice;

@RestController
public class SurveyController {

	@Autowired
	Surveyservice surveyService;

	@GetMapping("/surveys")
	public List<SurveyFetchDataDto> retieveSurvey() {
		return surveyService.findAllSurvey();
	}

	@GetMapping("/surveys/{surveyId}")
	public Survey retrieveSurveyById(@PathVariable Long surveyId) {
		Optional<Survey> survey = surveyService.findSurveyById(surveyId);
		if (survey.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return survey.get();
	}
	
	@GetMapping("/surveys/{surveyId}/correctAnswers")
	public HashMap<Long, String> retrieveCorrectAnswerBySurveyId(@PathVariable Long surveyId) {
		Optional<Survey> survey = surveyService.findSurveyById(surveyId);
		if (survey.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		HashMap<Long, String> correctAnswers = surveyService.findcorrectAnswer(survey.get());
	     
		return correctAnswers;
	}

	@PostMapping("/surveys")
	public ResponseEntity<Object> insertSurvey(@RequestBody SurveyDto survey) {
		Survey saveSurvey = surveyService.saveSurvey(survey);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{surveyId}")
				.buildAndExpand(saveSurvey.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping("/surveys/{surveyId}")
	public ResponseEntity<Object> updateSurvey(@PathVariable Long surveyId, @RequestBody SurveyDto survey) {

		Optional<Survey> findSurvey = surveyService.findSurveyById(surveyId);
		if (findSurvey.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		surveyService.updateSurvey(findSurvey.get(), survey);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/surveys/{surveyId}")
	public ResponseEntity<Object> deleteSurvey(@PathVariable Long surveyId) {
		surveyService.deleteSurveyById(surveyId);

		return ResponseEntity.noContent().build();

	}

	@GetMapping("/surveys/{surveyId}/questions")
	public List<Question> retrieveQuestionsBySurveyId(@PathVariable Long surveyId) {
		Optional<Survey> findSurvey = surveyService.findSurveyById(surveyId);
		if (findSurvey.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		Survey survey = findSurvey.get();
		return survey.getQuestions();
	}

	@GetMapping("/surveys/{surveyId}/questions/{questionId}")
	public Question retrieveQuestionById(@PathVariable Long surveyId, @PathVariable Long questionId) {

		Optional<Question> question = surveyService.findQuestion(questionId, surveyId);
		if (question.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return question.get();
	}

	@PostMapping("/surveys/{surveyId}/questions")
	public ResponseEntity<Object> insertQuestions(@PathVariable Long surveyId, @RequestBody List<Question> questions) {
		Optional<Survey> survey = surveyService.findSurveyById(surveyId);
		if (survey.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		surveyService.saveQuestions(survey.get(), questions);

		Stream<String> locations = questions.stream().map(q -> ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{surveyId}").buildAndExpand(q.getId()).toUriString());

		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", Arrays.toString(locations.toArray()));

		return new ResponseEntity<>("", headers, HttpStatus.CREATED);

	}

	@PutMapping("/surveys/{surveyId}/questions/{questionId}")
	public ResponseEntity<Object> updateQuestion(@PathVariable Long surveyId, @PathVariable Long questionId,
			@RequestBody QuestionDto question) {
		Optional<Question> findQuestion = surveyService.findQuestion(questionId, surveyId);
		if (findQuestion.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		surveyService.updateQuestion(findQuestion.get(), question);

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/surveys/{surveyId}/questions/{questionId}")
	public ResponseEntity<Object> deleteQuestionById(@PathVariable Long surveyId, @PathVariable Long questionId) {

		surveyService.deleteQuestionById(questionId);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/surveys/{surveyId}/questions/{questionId}/options")
	public List<Option> retrieveOptionsBySurveyId(@PathVariable Long surveyId, @PathVariable Long questionId) {
		Optional<Question> findQuestion = surveyService.findQuestion(questionId, surveyId);
		if (findQuestion.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		Question question = findQuestion.get();
		return question.getOptions();
	}

	@GetMapping("/surveys/{surveyId}/questions/{questionId}/options/{optionId}")
	public Option retrieveOptionById(@PathVariable Long surveyId, @PathVariable Long questionId,
			@PathVariable Long optionId) {

		Optional<Option> option = surveyService.findOption(optionId, questionId, surveyId);
		if (option.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		return option.get();
	}

	@PostMapping("/surveys/{surveyId}/questions/{questionId}/options")
	public ResponseEntity<Object> insertOptions(@PathVariable Long surveyId, @PathVariable Long questionId,
			@RequestBody List<Option> options) {

		Optional<Question> findQuestion = surveyService.findQuestion(questionId, surveyId);
		if (findQuestion.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		surveyService.saveOptions(findQuestion.get(), options);

		Stream<String> locations = options.stream().map(option -> ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{surveyId}").buildAndExpand(option.getId()).toUriString());

		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", Arrays.toString(locations.toArray()));
		return new ResponseEntity<>("", headers, HttpStatus.CREATED);
	}

	@PutMapping("/surveys/{surveyId}/questions/{questionId}/options/{optionId}")
	public ResponseEntity<Object> updateOption(@PathVariable Long surveyId, @PathVariable Long questionId,
			@PathVariable Long optionId, @RequestBody OptionDto option) {

		Optional<Option> findOption = surveyService.findOption(optionId, questionId, surveyId);
		if (findOption.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		surveyService.updateOption(findOption.get(), option);

		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/surveys/{surveyId}/questions/{questionId}/options/{optionId}")
	public ResponseEntity<Object> deleteOption(@PathVariable Long surveyId, @PathVariable Long questionId,
			@PathVariable Long optionId) {

		surveyService.deleteOptionById(optionId);

		return ResponseEntity.noContent().build();
	}

}
