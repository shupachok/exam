package com.supachok.exam.score.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Score {

	@Id
	@GeneratedValue
	private Long id;

	private Long surveyId;

	private Long score;
	
	private Long studentId;

	private String schoolYear;

	public Score() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	public Long getScore() {
		return score;
	}

	public void setScore(Long score) {
		this.score = score;
	}
	
	

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	@Override
	public String toString() {
		return "Score [id=" + id + ", surveyId=" + surveyId + ", score=" + score + ", studentId=" + studentId
				+ ", schoolYear=" + schoolYear + "]";
	}

	

}
