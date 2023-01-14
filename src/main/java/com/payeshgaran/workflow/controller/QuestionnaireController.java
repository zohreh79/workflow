package com.payeshgaran.workflow.controller;

import com.payeshgaran.workflow.model.QuestionnaireDto;
import com.payeshgaran.workflow.service.QuestionnaireService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.rest.dto.task.TaskDto;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
public class QuestionnaireController {

    @Autowired
    private QuestionnaireService questionnaireService;

    @PostMapping("/start")
    public String StartQuestionnaireProcess(@RequestBody QuestionnaireDto questionnaireDto) throws InterruptedException {

        return questionnaireService.StartProcess(questionnaireDto);

    }

    @GetMapping("/user-tasks/{userId}")
    @ResponseBody
    public List<TaskDto> userTaskList(@PathVariable String userId){

        return questionnaireService.getUserTaskList(userId);

    }

    @GetMapping("/user-questionnaire/{userId}")

    public Iterable<QuestionnaireDto> showUserQuestionnaires(@PathVariable  String userId) {

        return questionnaireService.showUserQuestionnaires(userId);

    }

    @PostMapping("/answer/{taskId}/{answer}")
    public String answer(@PathVariable String taskId, @PathVariable String answer){

        return questionnaireService.answerToQuestionnaire(taskId,answer);

    }

    @PostMapping("/correction-questionnaire/{taskId}")
    public String correctionQuestionnaire(@PathVariable String taskId,@RequestBody QuestionnaireDto questionnaireDto){

        return questionnaireService.correctionQuestionnaire(taskId,questionnaireDto);

    }



}