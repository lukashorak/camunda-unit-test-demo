package org.example.camunda.bpm;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component("loggerExecutionListener")
public class LoggerExecutionListener implements ExecutionListener {

    private final Logger LOGGER = Logger.getLogger(LoggerExecutionListener.class.getName());

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        LOGGER.info("\n\n  ... LoggerDelegate invoked by "
                +"eventName='"+delegateExecution.getEventName()+"'"
                + "activityName='" + delegateExecution.getCurrentActivityName() + "'"
                + ", activityId=" + delegateExecution.getCurrentActivityId()
                + ", processDefinitionId=" + delegateExecution.getProcessDefinitionId()
                + ", processInstanceId=" + delegateExecution.getProcessInstanceId()
                + ", businessKey=" + delegateExecution.getProcessBusinessKey()
                + ", executionId=" + delegateExecution.getId()
                + ", variables=" + delegateExecution.getVariables()
                + " \n\n");
    }
}
