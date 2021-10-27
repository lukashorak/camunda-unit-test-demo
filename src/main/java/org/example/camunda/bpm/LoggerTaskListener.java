package org.example.camunda.bpm;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component("loggerTaskListener")
public class LoggerTaskListener implements TaskListener {

    private final Logger LOGGER = Logger.getLogger(LoggerTaskListener.class.getName());
    @Override
    public void notify(DelegateTask delegateTask) {

        LOGGER.info("\n\n  ... LoggerDelegate invoked by "
                +"taskEventName='"+delegateTask.getEventName()+"'"
                +"taskName='"+delegateTask.getName()+"'"
                + "activityName='" + delegateTask.getExecution().getCurrentActivityName() + "'"
                + ", activityId=" + delegateTask.getExecution().getCurrentActivityId()
                + ", processDefinitionId=" + delegateTask.getProcessDefinitionId()
                + ", processInstanceId=" + delegateTask.getProcessInstanceId()
                + ", businessKey=" + delegateTask.getExecution().getProcessBusinessKey()
                + ", executionId=" + delegateTask.getId()
                + ", variables=" + delegateTask.getVariables()
                + " \n\n");

    }

}
