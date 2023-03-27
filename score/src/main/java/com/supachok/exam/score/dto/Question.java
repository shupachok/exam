package com.supachok.exam.score.dto;

public class Question {

	Long id;
	
	String description;
	
	String correctAnswer;

	public Question() {

	}

	public Question(String description, String correctAnswer) {
		super();
		this.description = description;
		this.correctAnswer = correctAnswer;
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
	
	@Override
	public String toString() {
		return "Question [id=" + id + ", description=" + description + ", correctAnswer=" + correctAnswer + "]";
	}
}
