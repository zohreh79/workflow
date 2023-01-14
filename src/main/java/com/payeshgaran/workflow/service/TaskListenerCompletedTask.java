package com.payeshgaran.workflow.service;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

@Service
public class TaskListenerCompletedTask implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {

        String messageTaskListenerCompletedTask;

        messageTaskListenerCompletedTask = delegateTask.getAssignee()+ " did complete the task\n\n";

        System.err.println(messageTaskListenerCompletedTask);

    }
}