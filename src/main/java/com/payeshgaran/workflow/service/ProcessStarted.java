package com.payeshgaran.workflow.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class ProcessStarted implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        System.err.println("\n\nProcess:\t"+ execution.getProcessDefinitionId() + "\tstarted...");

    }
}