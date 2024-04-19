package com.xht.activiti.service.impl;

import com.xht.activiti.cons.WFOperationConst;
import com.xht.activiti.service.ActivitiSercive;
import com.xht.common.vo.Result;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.zip.ZipInputStream;

@Service
public class ActivitiSerciveImpl implements ActivitiSercive {
    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    HistoryService historyService;

    @Autowired
    ProcessEngine processEngine;

    @Override
    public void handleTask(String taskID, String action) {

        if (action.equals(WFOperationConst.APPROVE)){
            taskService.complete(taskID);
        }else if (action.equals(WFOperationConst.REJECT)){
            rejectTask(taskID);
        }else if (action.equals(WFOperationConst.ROLLBACK)){
            rollBackTask(taskID);
        }
    }

    @Override
    public void actOrSusProcessDefinition(String proDefKey, String type) {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(proDefKey).list();
        for (ProcessDefinition processDefinition : list) {
            if (processDefinition.isSuspended()&&type.equals(WFOperationConst.ACTIVATE)){
                repositoryService.activateProcessDefinitionById(processDefinition.getId());
            } else if (!processDefinition.isSuspended()&&type.equals(WFOperationConst.SUSPEND)) {
                repositoryService.suspendProcessDefinitionById(processDefinition.getId());
            }
        }
    }

    @Override
    public void actOrSusProcessInstance(String proInsId, String activate) {
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().processInstanceId(proInsId).list();
        for (ProcessInstance processInstance : list) {
            if (processInstance.isSuspended() && activate.equals(WFOperationConst.ACTIVATE)){
                runtimeService.activateProcessInstanceById(processInstance.getId());
            }else if (processInstance.isSuspended() && activate.equals(WFOperationConst.SUSPEND)){
                runtimeService.suspendProcessInstanceById(processInstance.getId());
            }
        }
    }

    private void rollBackTask(String taskID) {
        //取得当前任务
        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskID).singleResult();
        if (historicTaskInstance == null)return;

        //取得流程实例
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(historicTaskInstance.getProcessInstanceId()).singleResult();
        if (historicProcessInstance == null) return;

        //取得流程定义
        ProcessDefinitionEntity definition = (ProcessDefinitionEntity) (processEngine.getRepositoryService().getProcessDefinition(historicTaskInstance
                .getProcessDefinitionId()));
        if (definition == null)return;



    }

    private void rejectTask(String taskID){

    }
}
