package com.xht.activiti.controller;


import com.xht.activiti.cons.WFOperationConst;
import com.xht.activiti.service.ActivitiSercive;
import com.xht.activiti.service.DynamicJumpCmdService;
import com.xht.common.vo.Result;
import com.xht.common.vo.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.activiti.engine.repository.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipInputStream;

@RestController
@Tag(name = "工作流接口")
@RequestMapping("/activiti")
@Slf4j
public class ActivitiController {

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    HistoryService historyService;

    @Autowired
    ActivitiSercive activitiSercive;

    @Autowired
    ProcessEngine processEngine;

    @Autowired
    DynamicJumpCmdService dynamicJumpCmdService;



    @PostMapping("/deploy")
    @Operation(summary = "部署")
    public Result deploy(@RequestPart("file") MultipartFile file) throws Exception{
        if (file == null) return Result.build(null,401,"文件不能为空");

        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        ZipInputStream zipInputStream = null;
        //压缩流
        zipInputStream = new ZipInputStream(file.getInputStream());
        deploymentBuilder.addZipInputStream(zipInputStream);
        deploymentBuilder.name("请假审批");
        Deployment deploy = deploymentBuilder.deploy();
        return Result.buildSuccess(deploy);
    }

    @GetMapping("/deploy/query/all")
    @Operation(summary = "查询所有部署")
    public Result queryAll(){
        List<Deployment> list = repositoryService.createDeploymentQuery().list();
        return Result.buildSuccess(list.toString());
    }


    @GetMapping("/start")
    @Operation(summary = "通过流程定义id发起流程")
    public Result start(@RequestParam("processDefinitionId") String processDefinitionId){
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("ceo","我是ceo");
        stringObjectHashMap.put("hr","我是hr");
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId, stringObjectHashMap);
        return Result.buildSuccess(processInstance.toString());
    }


    @GetMapping("/process/step")
    @Operation(summary = "查看流程进度")
    public Result processStep(@RequestParam("processInstanceId") String processInstanceId){
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (historicProcessInstance.getEndTime() !=null){
            return Result.buildSuccess("审批已完成,完成时间： "+historicProcessInstance.getEndTime());
        }

        List<Task> list = taskService.createTaskQuery().processInstanceId(processInstanceId)
                .list();
        return Result.buildSuccess(list.toString());
    }

    @Operation(summary ="根据用户名查询待处理任务")
    @GetMapping("queryTaskByUser")
    public Result queryTaskByUser(
            @RequestParam("userName") String userName
    ){
        List<Task> taskList = taskService.createTaskQuery()
                //候选人名称
                .taskCandidateOrAssigned(userName)
                .list();
        return Result.buildSuccess(taskList.toString());
    }

    @Operation(summary ="根据用户名查询历史任务")
    @GetMapping("queryHisTaskByUser")
    public Result queryHisTaskByUser(
            @RequestParam("userName") String userName
    ){
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(userName)
                .list();
        return Result.buildSuccess(list);
    }


    @Operation(summary ="正常审批通过")
    @GetMapping("completeTask")
    public Result completeTask(
            @RequestParam("taskID") String taskID
    ){
        taskService.complete(taskID);
        return Result.buildSuccess();
    }

    @Operation(summary ="拒绝申请")
    @GetMapping("rejectTask")
    public Result handleTask(
            @RequestParam("taskID") String taskID,@RequestParam("action") String action
    ){
        //todo
        activitiSercive.handleTask(taskID,action);

        return Result.buildSuccess();
    }


    @Operation(summary ="挂起该流程的所有流程实例")
    @GetMapping("suspendProcessDefinition")
    public Result suspendProcessDefinition(@RequestParam("ProDefKey") String ProDefKey){
        activitiSercive.actOrSusProcessDefinition(ProDefKey, WFOperationConst.SUSPEND);
        return Result.buildSuccess();
    }

    @Operation(summary ="激活该流程的所有流程实例")
    @GetMapping("activateProcessDefinition")
    public Result activateProcessDefinition(@RequestParam("ProDefKey") String ProDefKey){
        activitiSercive.actOrSusProcessDefinition(ProDefKey, WFOperationConst.ACTIVATE);
        return Result.buildSuccess();
    }

    @Operation(summary ="激活某个流程实例")
    @GetMapping("activateProcessInstance")
    public Result activateSingleProcessInstance(@RequestParam("proInsId") String proInsId){
        activitiSercive.actOrSusProcessInstance(proInsId,WFOperationConst.ACTIVATE);
        return Result.buildSuccess();
    }

    @Operation(summary ="挂起某个流程实例")
    @GetMapping("suspendProcessInstance")
    public Result suspendSingleProcessInstance(@RequestParam("proInsId") String proInsId){
        activitiSercive.actOrSusProcessInstance(proInsId,WFOperationConst.SUSPEND);
        return Result.buildSuccess();
    }


    @Operation(summary ="查询流程所有节点")
    @GetMapping("getProcessInstancePoint")
    public Result getProcessInstancePoint(@RequestParam("ProProInsId") String proInsId){
        //流程定义id
        String processDefinitionId = runtimeService.createProcessInstanceQuery()
                .processInstanceId(proInsId).singleResult().getProcessDefinitionId();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        Process process = bpmnModel.getProcesses().get(0);
        //获取所有节点
        Collection<FlowElement> flowElements = process.getFlowElements();
        List<String> collect = flowElements.stream().filter(item->item.getName()!=null).map(FlowElement::getId).toList();
        return Result.buildSuccess(collect);
    }

    @Operation(summary ="查询流程所处节点")
    @GetMapping("getProcessInstanceCurPoint")
    public Result getProcessInstanceCurPoint(@RequestParam("ProProInsId") String proInsId){
        List<Task> list = taskService.createTaskQuery().processInstanceId(proInsId).list();
        return Result.buildSuccess(list.toString());
    }

    @Operation(summary ="跳转节点")
    @GetMapping("jumpTask")
    public Result jumpTask(@RequestParam("ProProInsId") String proInsId
            ,@RequestParam("from") String from,@RequestParam("to") String to){
        dynamicJumpCmdService.jumpTask(proInsId,from,to);
        return Result.buildSuccess();
    }

    @Operation(summary = "查看流程图")
    @GetMapping("viewPng")
    public ResponseEntity<InputStreamResource> viewPng(@RequestParam("procDefId") String procDefId){
        InputStream diagramStream = null;
        InputStreamResource inputStreamResource = null;
        try {
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
            String diagramResourceName = processDefinition.getDiagramResourceName();
            if (diagramResourceName != null && diagramResourceName.endsWith(".png")){
                diagramStream = repositoryService.getResourceAsStream(
                        processDefinition.getDeploymentId(),
                        diagramResourceName);


                inputStreamResource = new InputStreamResource(diagramStream);
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .body(inputStreamResource);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (diagramStream!=null){
                try {
                    diagramStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return ResponseEntity.notFound().build();
    }

    @Operation(summary ="删除部署")
    @GetMapping("/deploy/delete")
    public Result deleteDeploy(@RequestParam("depId") String depId){
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(depId).list();
        boolean b = list.stream().anyMatch(item -> taskService.createTaskQuery().processInstanceId(item.getId()) != null);
        if (b){
            return Result.buildSuccess("该部署存在 正在进行的任务实例");
        }else {
            repositoryService.deleteDeployment(depId,true);
        }
        return Result.buildSuccess();
    }



    @Operation(summary ="查询任务")
    @GetMapping("/getTask")
    public Result getTask(@RequestParam("username") String username){
        List<Task> list = taskService.createTaskQuery().taskCandidateUser(username).list();
        return Result.buildSuccess(list.toString());
    }

    @Operation(summary ="拾取任务")
    @GetMapping("/claimTask")
    public Result getTask(@RequestParam("username") String username,@RequestParam("taskId") String taskId){
        taskService.claim(taskId,username);
        return Result.buildSuccess();
    }

    @Operation(summary ="转交任务")
    @GetMapping("/trans")
    public Result transTask(@RequestParam("username") String username,@RequestParam("taskId") String taskId){
        taskService.setAssignee(taskId,username);
        return Result.buildSuccess();
    }

    @Operation(summary ="删除候选人")
    @GetMapping("/deleteCandidateUser")
    public Result deleteCandidateUser(@RequestParam("username") String username,@RequestParam("taskId") String taskId){
        taskService.deleteCandidateUser(taskId,username);
        return Result.buildSuccess();
    }

    @Operation(summary ="添加候选人")
    @GetMapping("/addCandidateUser")
    public Result addCandidateUser(@RequestParam("username") String username,@RequestParam("taskId") String taskId){
        taskService.addCandidateUser(taskId,username);
        return Result.buildSuccess();
    }
}
