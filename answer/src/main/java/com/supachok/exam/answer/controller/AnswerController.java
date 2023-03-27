package com.supachok.exam.answer.controller;

import java.net.URI;
import java.util.Arrays;
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

import com.supachok.exam.answer.dto.AnswerDto;
import com.supachok.exam.answer.dto.AnswerSheetDto;
import com.supachok.exam.answer.entity.Answer;
import com.supachok.exam.answer.entity.AnswerSheet;
import com.supachok.exam.answer.service.AnswerService;

@RestController
public class AnswerController {
	
	@Autowired
	AnswerService answerService;
	
	@GetMapping("/answer-sheets")
	public List<AnswerSheet> retrieveAnswerSheets(Long surveyId,String schoolYear) {
		if(surveyId != null && schoolYear != null)
			return answerService.findAnswerSheetWithParam(surveyId,schoolYear);
		
		return answerService.findAllAnswerSheet();
	}
	
	@GetMapping("/answer-sheets/{answerSheetId}")
	public AnswerSheet retrieveAnswerSheetById(@PathVariable Long answerSheetId) {
		Optional<AnswerSheet> findAnswerSheet = answerService.findAnswerSheetById(answerSheetId);
		if(findAnswerSheet.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return findAnswerSheet.get();
	}
	
	@PostMapping("/answer-sheets")
	public ResponseEntity<Object> insertAnswerSheet(@RequestBody AnswerSheet answerSheet) {
		answerService.saveAnswerSheet(answerSheet);		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{answerSheetId}")
				.buildAndExpand(answerSheet.getId()).toUri();

		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/answer-sheets/{answerSheetId}")
	public ResponseEntity<Object> updateAnswerSheet(@PathVariable Long answerSheetId,@RequestBody AnswerSheetDto answerSheet) {
		Optional<AnswerSheet> findAnswerSheet = answerService.findAnswerSheetById(answerSheetId);
		if(findAnswerSheet.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		answerService.updateAnswerSheet(findAnswerSheet.get(),answerSheet);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/answer-sheets/{answerSheetId}")
	public ResponseEntity<Object> deleteAnswerSheet(@PathVariable Long answerSheetId) {

		answerService.deleteAnswerSheet(answerSheetId);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/answer-sheets/{answerSheetId}/answers")
	public List<Answer> retrieveAnswers(@PathVariable Long answerSheetId) {
		Optional<AnswerSheet> findAnswerSheet = answerService.findAnswerSheetById(answerSheetId);
		if(findAnswerSheet.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return findAnswerSheet.get().getAnswers();
	}
	
	@GetMapping("/answer-sheets/{answerSheetId}/answers/{answerId}")
	public Answer retrieveAnswers(@PathVariable Long answerSheetId,@PathVariable Long answerId) {
		Optional<Answer> findAnswer = answerService.findAnswer(answerId,answerSheetId);
		if(findAnswer.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return findAnswer.get();
	}
	
	@PostMapping("/answer-sheets/{answerSheetId}/answers")
	public ResponseEntity<Object> insertAnswers(@PathVariable Long answerSheetId,
			@RequestBody List<Answer> answers){
		Optional<AnswerSheet> findAnswerSheet = answerService.findAnswerSheetById(answerSheetId);
		if(findAnswerSheet.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		answerService.saveAnswers(answers,findAnswerSheet.get());
		
		Stream<String> locations = answers.stream().map(q -> ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{answerId}").buildAndExpand(q.getId()).toUriString());
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", Arrays.toString(locations.toArray()));

		return new ResponseEntity<>("", headers, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/answer-sheets/{answerSheetId}/answers/{answerId}")
	public ResponseEntity<Object> updateAnswer(@PathVariable Long answerSheetId
			,@PathVariable Long answerId,@RequestBody AnswerDto answerDto) {
		Optional<Answer> findAnswer = answerService.findAnswer(answerId,answerSheetId);
		if(findAnswer.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		answerService.saveAnswer(findAnswer.get(),answerDto);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/answer-sheets/{answerSheetId}/answers/{answerId}")
	public ResponseEntity<Object> deleteAnswer(@PathVariable Long answerSheetId
			,@PathVariable Long answerId,@RequestBody AnswerDto answerDto) {
		
		answerService.deleteAnswerById(answerId);
		
		return ResponseEntity.noContent().build();
	}
	
}
