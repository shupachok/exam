package com.supachok.exam.survey.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.supachok.exam.survey.entity.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    @Query("SELECT s, q, o FROM Survey s LEFT JOIN s.questions q LEFT JOIN q.options o")
    List<Object[]> findAllSurveysWithQuestionsAndOptions();
}
