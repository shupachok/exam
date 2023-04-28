package com.supachok.exam.survey.dto;

import java.util.ArrayList;
import java.util.List;

public class QuestionFetchDataDto {
	Long id;
	String description;
	
	String correctAnswer;
	List<OptionFetchDataDto> options = new ArrayList<>();
	public QuestionFetchDataDto() {
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getCorrectAnswer() {
		return correctAnswer;
	}


	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public List<OptionFetchDataDto> getOptions() {
		return options;
	}

	public void setOptions(List<OptionFetchDataDto> options) {
		this.options = options;
	}


	@Override
	public String toString() {
		return "QuestionFetchDataDto [id=" + id + ", description=" + description + ", correctAnswer=" + correctAnswer + "]";
	}

}
