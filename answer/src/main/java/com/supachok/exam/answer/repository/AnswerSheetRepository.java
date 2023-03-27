package com.supachok.exam.answer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supachok.exam.answer.entity.AnswerSheet;

public interface AnswerSheetRepository extends JpaRepository<AnswerSheet, Long>{

	List<AnswerSheet> findBySurveyIdAndSchoolYear(Long surveyId, String schoolYear);

}
