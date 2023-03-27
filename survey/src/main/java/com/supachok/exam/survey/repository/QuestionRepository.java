package com.supachok.exam.survey.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supachok.exam.survey.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
	List<Question> findBySurveyId(Long surveyId);
	
	Optional<Question> findByIdAndSurveyId(Long id,Long surveyId);
}
