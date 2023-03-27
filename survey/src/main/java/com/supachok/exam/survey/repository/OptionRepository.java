package com.supachok.exam.survey.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supachok.exam.survey.entity.Option;

public interface OptionRepository extends JpaRepository<Option, Long> {

}
