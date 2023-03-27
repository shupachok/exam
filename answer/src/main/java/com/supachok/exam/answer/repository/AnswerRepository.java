package com.supachok.exam.answer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supachok.exam.answer.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long>{
	Optional<Answer> findByIdAndAnswerSheetId(Long id,Long answerSheetId);
}
