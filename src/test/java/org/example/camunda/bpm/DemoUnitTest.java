package org.example.camunda.bpm;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
import static org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions.processEngine;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;


/**
 * Test case starting an in-memory database-backed Process Engine.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoUnitTest {

  @Autowired
  private ProcessEngine processEngine;

  static {
    LogFactory.useSlf4jLogging(); // MyBatis
  }

  @ClassRule
  @Rule
  public static ProcessEngineRule rule;

  @PostConstruct
  void initRule() {
    rule = TestCoverageProcessEngineRuleBuilder.create(processEngine).build();
  }

  @Before
  public void setup() {
    init(processEngine);
  }

  @Test
  @Deployment(resources = "processTest.bpmn") // only required for process test coverage
  public void testHappyPath() {
    // Drive the process by API and assert correct behavior by camunda-bpm-assert

    ProcessInstance processInstance = processEngine().getRuntimeService()
        .startProcessInstanceByKey("camunda-junit-demo");

    //Task task = task();
    //Task task = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

//    assertThat(processInstance).isActive();
//
//    assertThat(task()).hasName("task1");
//
//    Map<String, Object> variables1 = new HashMap<>();
//    variables1.put("var1", false);
//
//    complete(task(), variables1);

    firstPartOfTest(processInstance);


    assertThat(processInstance).isEnded();

  }

  private void firstPartOfTest(ProcessInstance processInstance){
    assertThat(processInstance).isActive();

    assertThat(task()).hasName("task1");

    Map<String, Object> variables1 = new HashMap<>();
    variables1.put("var1", false);

    complete(task(), variables1);
  }

  @Test
  @Deployment(resources = "processTest.bpmn") // only required for process test coverage
  public void testMessageCorrelation() {
    // Drive the process by API and assert correct behavior by camunda-bpm-assert

    ProcessInstance processInstance = processEngine().getRuntimeService()
            .startProcessInstanceByKey("camunda-junit-demo");


    assertThat(processInstance).isActive();

    assertThat(task()).hasName("task1");

    processEngine().getRuntimeService().createMessageCorrelation("Message_352c8d1").processInstanceId(processInstance.getId()).correlate();


    assertThat(processInstance).isActive();

    assertThat(task()).hasName("task3");

  }



  @Test
  @Deployment(resources = "processTest.bpmn") // only required for process test coverage
  public void testAlternatePath() {
    // Drive the process by API and assert correct behavior by camunda-bpm-assert

    ProcessInstance processInstance = processEngine().getRuntimeService()
            .startProcessInstanceByKey("camunda-junit-demo");

    //Task task = task();
    //Task task = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

    assertThat(processInstance).isActive();

    assertThat(task()).hasName("task1");
    Map<String, Object> variables1 = new HashMap<>();
    variables1.put("var1", true);

    complete(task(), variables1);

    assertThat(processInstance).isNotEnded();
    //
    assertThat(task()).hasName("task2");
    Map<String, Object> variables2 = new HashMap<>();
    variables1.put("var2", "hello");

    complete(task(), variables2);

    assertThat(processInstance).isEnded();
  }


  @Test
  @Deployment(resources = "processTest.bpmn") // only required for process test coverage
  public void testHappyPathWithJson() throws IOException {
    // Drive the process by API and assert correct behavior by camunda-bpm-assert

    ProcessInstance processInstance = processEngine().getRuntimeService()
            .startProcessInstanceByKey("camunda-junit-demo");

    //Task task = task();
    //Task task = processEngine.getTaskService().createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

    assertThat(processInstance).isActive();

    assertThat(task()).hasName("task1");

    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> variables1 = objectMapper.readValue(new ClassPathResource("./task1.json").getFile(), Map.class);

    complete(task(), variables1);

    assertThat(processInstance).isEnded();

  }
}
