package com.supachok.exam.survey.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supachok.exam.survey.entity.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

}
