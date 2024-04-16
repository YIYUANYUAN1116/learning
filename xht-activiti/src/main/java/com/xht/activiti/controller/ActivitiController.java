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

    @GetMapping("/startByAssignName")
    @Operation(summary = "通过流程定义ID启动一个流程实例")
    public Result start(@RequestParam("id") String id,@RequestParam("agName") String agName){
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().processDefinitionId(id).list();
        if (list.size()<1){
            return Result.build("list == null");
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("agName",agName);
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(id,hashMap);

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

    @Operation(summary = "根据代办人查询任务")
    @GetMapping("queryByAssigneeTask")
    public Result queryByAssigneeTask(@RequestParam("assignee")String assignee) {

        List<Task> list = taskService.createTaskQuery().taskAssignee(assignee)
                .active()
                .list();
        log.info("查看历史活动流程实例 {}", list.toString());
        return Result.build(list.toString());
    }

    @Operation(summary ="按任务id更新代办人")
    @GetMapping("updateAssigneeByTaskId")
    public Result updateAssigneeByTaskId(
         @RequestParam(value = "任务id", required = true) String taskId,
            @RequestParam(value = "新代办人", required = true) String assignee
    ) {

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            return Result.build("任务不存在");
        }
        //更新当前任务的代办人
        taskService.setAssignee(taskId, assignee);
        return Result.build("更新成功");
    }


    @Operation(summary = "添加审批人意见")
    @GetMapping("addComment")
    public Result addComment(
            @RequestParam("taskId") String taskId,
            @RequestParam("processInstanceId") String processInstanceId,
            @RequestParam("message") String message
    ) {
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .processInstanceId(processInstanceId)
                .singleResult();
        if (task == null) {
            return Result.build("任务不存在");
        }
        Comment comment = taskService.addComment(taskId, processInstanceId, message);
        return Result.build(comment);
    }

    @Operation(summary = "查询个人审批意见")
    @GetMapping("queryComment")
    public Result queryComment(@RequestParam("taskId") String taskId){
        //注意，这里也可以使用type做搜索，通过添加意见的第三个参数，指定用户id
        //taskService.addComment("任务id", "流程实例id", "自定义变量type，可以用作用户id", "意见");
        List<Comment> taskComments = taskService.getTaskComments(taskId);
        //taskService.getTaskComments(taskId,"自定义变量type，可以用作用户id");
        log.info("查询个人审批意见 {}", taskComments);
        return Result.build(taskComments.toString());
    }


    @Operation(summary ="根据候选人查询任务")
    @GetMapping("queryTaskByCandidateUser")
    public Result queryTaskByCandidateUser(
            @RequestParam("userName") String userName
    ){
        List<Task> taskList = taskService.createTaskQuery()
                //候选人名称
                .taskCandidateUser(userName)
                .list();
        return Result.build(taskList);
    }


    @Operation(summary ="候选人拾取任务，拾取后的任务，候选人才可以完成")
    @GetMapping("claimTask")
    public Result claimTask(
            @RequestParam("taskId") String taskId,
            @RequestParam("userName") String userName) {
        Task task = taskService.createTaskQuery()
                //任务id
                .taskId(taskId)
                //候选人名称
                .taskCandidateUser(userName)
                .singleResult();
        if (task == null) {
            return Result.build("任务不存在");
        }
        //拾取任务
        taskService.claim(taskId, userName);
        return Result.build("拾取任务成功");
    }


    @Operation(summary ="发起流程")
    @GetMapping("startProcess")
    public Result startProcess(
            @RequestParam("processDefinitionId") String processDefinitionId
    ) {
        log.info("发起流程，processDefinitionId：{}", processDefinitionId);
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).list();
        if (list.size() != 1) {
            return Result.build("流程定义不存在");
        }
        //流程节点中变量，替换占位符
        Map<String, Object> variablesMap = new HashMap<>();
        //流程变量day
        variablesMap.put("day", "8");
        //通过流程定义ID启动一个流程实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId, variablesMap);
        log.info("流程实例：{}", processInstance);
        return Result.build("发起成功 " + processInstance);
    }


    @Operation(summary ="任务委派")
    @GetMapping("delegateTask")
    public Result delegateTask(
             @RequestParam("taskId") String taskId,
             @RequestParam("userName") String userName
    ) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            return Result.build("任务不存在");
        }
        taskService.delegateTask(taskId, userName);
        return Result.build();
    }

    @Operation(summary ="任务转办")
    @GetMapping("setAssignee")
    public Result setAssignee(
            @RequestParam("taskId") String taskId,
            @RequestParam("userName") String userName
    ) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            return Result.build("任务不存在");
        }
        taskService.setAssignee(taskId, userName);
        return Result.build();
    }
}
