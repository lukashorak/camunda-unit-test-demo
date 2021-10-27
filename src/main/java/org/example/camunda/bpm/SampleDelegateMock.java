package org.example.camunda.bpm;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.inject.Named;
import java.util.logging.Logger;

@Component
@Named("sampleDelegate")
@Profile("!prod")
public class SampleDelegateMock implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(SampleDelegateMock.class.getName());
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        LOGGER.info("SampleDelegate -- Mock !!");
        // Here is some integration logic
    }
}
