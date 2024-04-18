package com.xht.activiti.controller;


import com.xht.activiti.service.ActivitiSercive;
import com.xht.common.vo.Result;
import com.xht.common.vo.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

        activitiSercive.handleTask(taskID,action);

        return Result.buildSuccess();
    }


}
