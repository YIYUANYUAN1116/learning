package com.xht.activiti.controller;

import com.xht.activiti.model.Result;
import com.xht.activiti.model.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
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
        return Result.build(deploy);
    }

    @GetMapping("/query")
    @Operation(summary = "查询部署")
    public Result query(){
        List<Deployment> list = repositoryService.createDeploymentQuery().list();
        return Result.build(list.toString());
    }

    @DeleteMapping()
    @Operation(summary = "根据id删除流程")
    public Result deleteById(@RequestParam("id") String id){
        List<Deployment> list = repositoryService.createDeploymentQuery().deploymentId(id).list();
        if (list.size()<1){
            return Result.build("list == null");
        }

        repositoryService.deleteDeployment(id);

        return Result.build(ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/start")
    @Operation(summary = "开启一个流程")
    public Result start(@RequestParam("id") String id){
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().processDefinitionId(id).list();
        if (list.size()<1){
            return Result.build("list == null");
        }
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(id);

        return Result.build(processInstance.toString(),ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/querydefinition")
    @Operation(summary = "获取定义信息")
    public Result querydefinition(){
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
        return Result.build(list.toString());
    }


    @Operation(summary = "完成任务")
    @GetMapping("completeTask")
    public Result completeTask(@RequestParam("processInstanceId") String processInstanceId) {
        //根据流程实例id，查询任务
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        if (taskList.size() != 1) {
            return Result.build("当前没有任务");
        }

        //根据任务id，完成任务
        taskService.complete(taskList.get(0).getId());
        return Result.build("完成任务");
    }


    @Operation(summary = "查询历史流程实例")
    @GetMapping("queryHistoryProcessInstance")
    public Result queryHistoryProcessInstance() {
        //也可以设置查询条件，自行查询API
        List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().list();
        log.info("查询历史流程实例 {}", list);
        return Result.build(list.toString());
    }


    @Operation(summary = "查询历史任务")
    @GetMapping("queryHistoryTask")
    public Result queryHistoryTask() {
        //也可以设置查询条件，自行查询API
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().list();
        log.info("查询历史任务 {}", list);
        return Result.build(list.toString());
    }


    @Operation(summary = "查看历史活动流程实例")
    @GetMapping("queryActivityInstance")
    public Result queryActivityInstance() {
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().list();
        log.info("查看历史活动流程实例 {}", list);
        return Result.build(list.toString());
    }
}
