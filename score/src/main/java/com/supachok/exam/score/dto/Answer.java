package com.supachok.exam.score.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Answer {
	@Id
	@GeneratedValue
	private Long id;

	private Long questionId;

	private String answerValue;
	
	
	public Answer() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}


	public String getAnswerValue() {
		return answerValue;
	}

	public void setAnswerValue(String answerValue) {
		this.answerValue = answerValue;
	}

	@Override
	public String toString() {
		return "Answer [id=" + id + ", questionId=" + questionId + ", answerValue=" + answerValue + "]";
	}

}
