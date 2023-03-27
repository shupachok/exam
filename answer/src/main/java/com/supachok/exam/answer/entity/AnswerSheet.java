package com.supachok.exam.answer.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class AnswerSheet {

	@Id
	@GeneratedValue
	private long id;
	private long studentId;
	private long surveyId;
	private String schoolYear;

	@OneToMany(mappedBy = "answerSheet")
	@OnDelete(action = OnDeleteAction.CASCADE)
	List<Answer> answers = new ArrayList<>();

	public AnswerSheet() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(long surveyId) {
		this.surveyId = surveyId;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswer(Answer answer) {
		this.answers.add(answer);
	}

	public String getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}

	@Override
	public String toString() {
		return "AnswerSheet [id=" + id + ", StudentId=" + studentId + ", SurveyId=" + surveyId + ", schoolYear="
				+ schoolYear + "]";
	}

}
