package com.payeshgaran.workflow.service;

import com.payeshgaran.workflow.model.Person;
import com.payeshgaran.workflow.model.Questionnaire;
import com.payeshgaran.workflow.repository.PersonRepository;
import com.payeshgaran.workflow.repository.QuestionnaireRepository;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskListenerAssignedTask implements TaskListener {

    @Autowired
    private QuestionnaireRepository questionnaireRepository;
    @Autowired
    private PersonRepository personRepository;

    @Override
    public void notify(DelegateTask delegateTask) {

        Person person = personRepository.getByPriority(1L);

        if (!delegateTask.getAssignee().equals(person.getUserId())){

            Questionnaire questionnaire = questionnaireRepository.getQuestionnaireByExecutionId(delegateTask.getExecutionId());
            questionnaire.setTaskId(delegateTask.getId());
            questionnaire.setExecutionId(delegateTask.getExecutionId());
            questionnaire.setAssignee(delegateTask.getAssignee());

            questionnaireRepository.save(questionnaire);

        }

        String messageTaskListenerAssignedTask;
        messageTaskListenerAssignedTask = "\n\nNew Task assigned to: "+delegateTask.getAssignee();
        System.err.println(messageTaskListenerAssignedTask);
    }

}