package com.payeshgaran.workflow.service;

import org.camunda.bpm.engine.rest.dto.task.TaskDto;

import java.util.Comparator;

public class DateCompare implements Comparator<TaskDto> {

    @Override
    public int compare(TaskDto o1, TaskDto o2) {
        return o1.getCreated().compareTo(o2.getCreated());
    }
}
