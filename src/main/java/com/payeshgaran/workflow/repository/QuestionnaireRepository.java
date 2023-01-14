package com.payeshgaran.workflow.repository;

import com.payeshgaran.workflow.model.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {

    @Query("select q from Questionnaire q where q.taskId = ?1")
    Questionnaire getQuestionnaireByTaskId(String taskId);

    @Query("select a from Questionnaire a where a.executionId = ?1")
    Questionnaire getQuestionnaireByExecutionId(String executionId);

    @Query("select a from Questionnaire a where a.assignee = ?1")
    Iterable <Questionnaire> getUserQuestionnaires(String userId);

}