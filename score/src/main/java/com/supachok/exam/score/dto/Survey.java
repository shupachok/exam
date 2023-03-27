package com.supachok.exam.score.dto;

import java.util.ArrayList;
import java.util.List;


public class Survey {

	private Long id;
	
	private String title;
	
	private String description;
	
	private List<Question> questions = new ArrayList<>();

	public Survey() {
	}

	public Survey(String title, String description) {
		super();
		this.title = title;
		this.description = description;
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

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public void addQuestions(Question question) {
		this.questions.add(question);
	}

	@Override
	public String toString() {
		return "Survey [id=" + id + ", title=" + title + ", description=" + description + "]";
	}
	
	

}
