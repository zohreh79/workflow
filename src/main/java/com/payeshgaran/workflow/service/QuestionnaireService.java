package com.payeshgaran.workflow.service;

import com.payeshgaran.workflow.model.Questionnaire;
import com.payeshgaran.workflow.model.QuestionnaireDto;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import java.util.List;

public interface QuestionnaireService {

    String StartProcess(QuestionnaireDto questionnaireDto) throws InterruptedException;

    List<TaskDto> getUserTaskList(String userId);

    TaskDto getLatestTaskByUserId(String userId);

    String answerToQuestionnaire(String taskId, String answer);

    Iterable <QuestionnaireDto> showUserQuestionnaires(String userId);

    String correctionQuestionnaire(String TaskId, QuestionnaireDto questionnaireDto);

    String compareQuestionnaires(Questionnaire questionnaire1, QuestionnaireDto questionnaireDto);

}