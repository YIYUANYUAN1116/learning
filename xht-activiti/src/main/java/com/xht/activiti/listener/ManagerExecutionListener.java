package com.xht.activiti.listener;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * @author : YIYUANYUAN
 * @date: 2024/4/16  23:54
 */
@Slf4j
public class ManagerExecutionListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) {
        log.info("ManagerExecutionListener : " +delegateExecution.getEventName()
        +delegateExecution.getCurrentFlowElement()
        +delegateExecution.getProcessDefinitionId()
        +delegateExecution.getProcessInstanceId()
        +delegateExecution);
    }
}
