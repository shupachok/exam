package com.supachok.exam.score.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supachok.exam.score.entity.Score;

public interface ScoreRepository extends JpaRepository<Score, Long> {
	public void deleteBySchoolYear(String schoolYear);
	
	public List<Score> findBySchoolYearAndSurveyId(String schoolYear, Long surveyId);

}
