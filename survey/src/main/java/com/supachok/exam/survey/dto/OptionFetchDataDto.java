package com.supachok.exam.survey.dto;

public class OptionFetchDataDto {
	Long id;
	String answer;
	public OptionFetchDataDto() {
		super();
	}
	public OptionFetchDataDto(Long id, String answer) {
		super();
		this.id = id;
		this.answer = answer;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	@Override
	public String toString() {
		return "OptionFetchDataDto [id=" + id + ", answer=" + answer + "]";
	}
}
