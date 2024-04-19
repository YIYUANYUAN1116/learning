package com.xht.activiti.util;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.activiti.engine.impl.util.ProcessDefinitionUtil;
import org.springframework.util.Assert;

import java.util.List;

@AllArgsConstructor
@Slf4j
public class DynamicJumpCmd implements Command<Void> {

    /** 流程实例id */
    private String processInstanceId;

    /** 从哪个节点 */
    private String fromActivityId;

    /** 跳转到那个节点 */
    private String toActivityId;



    @Override
    public Void execute(CommandContext commandContext) {
        log.info("node jump: processInstanceId={}, fromActivityId={}, toActivityId={}", processInstanceId, fromActivityId, toActivityId);

        // 0.参数合法性检查
        ExecutionEntityManager executionEntityManager = commandContext.getExecutionEntityManager();
        ExecutionEntity executionEntity = executionEntityManager.findById(processInstanceId);
        Assert.isTrue(executionEntity != null, "processInstanceId is not exist");
        BpmnModel bpmnModel = ProcessDefinitionUtil.getBpmnModel(executionEntity.getProcessDefinitionId());
        FlowElement fromFlowElement = bpmnModel.getFlowElement(fromActivityId);
        Assert.isTrue(fromFlowElement != null, "fromActivityId is not exist");
        FlowElement toFlowElement = bpmnModel.getFlowElement(toActivityId);

        // 1.找出子执行实例fromActivityId，并删除该执行实例
        List<ExecutionEntity> childList = executionEntityManager.findChildExecutionsByProcessInstanceId(processInstanceId);
        ExecutionEntity currentChildExecutionEntity = null;
        for (ExecutionEntity child : childList) {
            if (child.getCurrentActivityId().equals(fromActivityId)) {
                currentChildExecutionEntity = child;
                break;
            }
        }
        executionEntityManager.deleteExecutionAndRelatedData(currentChildExecutionEntity, "任务动态跳转: 由："+fromFlowElement.getName()+" 跳转至 "+toFlowElement.getName());

        // 2.创建新的执行实例，并绑定到toActivityId
        ExecutionEntity childExecution = executionEntityManager.createChildExecution(executionEntity);
        childExecution.setCurrentFlowElement(toFlowElement);

        // 执行流程操作
        Context.getAgenda().planContinueProcessOperation(childExecution);
        return null;

    }
}
