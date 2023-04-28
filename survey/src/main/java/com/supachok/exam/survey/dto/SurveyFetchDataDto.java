package com.supachok.exam.survey.dto;

import java.util.ArrayList;
import java.util.List;

public class SurveyFetchDataDto {
	private Long id;
	private String title;
	private String description;
	private List<QuestionFetchDataDto> questions = new ArrayList<>();

	public SurveyFetchDataDto() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<QuestionFetchDataDto> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionFetchDataDto> questions) {
		this.questions = questions;
	}

	@Override
	public String toString() {
		return "Survay [id=" + id + ", title=" + title + ", description=" + description + ", questions=" + questions
				+ "]";
	}

}
