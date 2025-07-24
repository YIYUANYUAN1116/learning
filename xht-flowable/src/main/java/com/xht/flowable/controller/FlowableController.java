package com.xht.flowable.controller;

import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.*;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@RestController
public class FlowableController {
    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Resource
    private HistoryService historyService;

    @Resource
    IdentityService identityService;

    /**
     * 初始化流程
     *
     **/
    @GetMapping("initFlow")
    @Transactional(rollbackFor = Exception.class)
    public void initFlow() throws Exception {
        // 获取bpmn文件夹的所有.bpmn20.xml文件
        ClassPathResource bpmnFolder = new ClassPathResource("bpmn/");
        var files = bpmnFolder.getFile().listFiles((dir, name) -> name.endsWith(".bpmn20.xml"));

        if (files != null && files.length > 0) {
            // 创建部署对象
            var deploymentBuilder = repositoryService.createDeployment();

            for (var file : files) {
                // 添加BPMN文件到部署
                deploymentBuilder.addInputStream(file.getName(), file.toURI().toURL().openStream());
            }

            // 执行部署
            Deployment deployment = deploymentBuilder.deploy();
        }
    }
    /***
     * 查询所有的流程实例
     **/
    @GetMapping("/queryAllDeployedProcesses")
    public List<JSONObject> queryAllDeployedProcesses() {
        List<JSONObject> jsonObjects = new ArrayList<>();

        // 查询所有流程定义
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                .orderByProcessDefinitionKey().asc() // 按流程定义的 Key 排序
                .latestVersion() // 只查询每个流程定义的最新版本
                .list();

        // 打印所有已部署的流程的 key 和 name
        for (ProcessDefinition processDefinition : processDefinitions) {
            System.out.println("Process ID: " + processDefinition.getId());
            System.out.println("Process Key: " + processDefinition.getKey());
            System.out.println("Process Name: " + processDefinition.getName());
            System.out.println("Process Version: " + processDefinition.getVersion());
            JSONObject object = new JSONObject();
            object.put("id", processDefinition.getId());
            object.put("key", processDefinition.getKey());
            object.put("name", processDefinition.getName());
            object.put("version", processDefinition.getVersion());

            jsonObjects.add(object);
        }

        //分页查询
        // 创建查询对象
//        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery()
//                .latestVersion() // 只查询最新版本的流程定义
//                .orderByProcessDefinitionKey().asc(); // 按流程定义的 Key 升序排序
//
//        // 获取总条数
//        long totalCount = query.count();
//
//        // 分页查询流程定义
//        List<ProcessDefinition> processDefinitions = query.listPage((pageNum - 1) * pageSize, pageSize);

        return jsonObjects;
    }

    /**
     * 启动流程.
     *
     * @return 流程key
     */
    @PostMapping("/start")
    public ResponseEntity<Object> startProcessDef() {
        //1.声明流程id
        String processId = "test04:2:872efaa6-685f-11f0-aef7-94b48b57525e";
        //2.启动流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processId);
        //3.返回流程实例对象id
        return new ResponseEntity<>(processInstance.getId(), HttpStatus.OK);
    }

    /**
     * 完成节点任务.
     *
     * @return 流程key
     */
    @PostMapping("/complete/{taskId}")
    public ResponseEntity<Object> completeTask(@PathVariable String taskId) {
//        //1.查询指定用户的待办任务
//        Task task = taskService.createTaskQuery().taskAssignee(agentUser).singleResult();
        //2.完成指定任务的审批
        taskService.complete(taskId);
        //3.返回响应
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/rollback/targetTaskId/{targetTaskId}/userId/{userId}")
    public ResponseEntity<Object> rollBackTask(@PathVariable String targetTaskId) {
        //1.查询目标任务数据
        HistoricTaskInstance targetTask = historyService
                .createHistoricTaskInstanceQuery()
                .taskId(targetTaskId)
                .singleResult();
        //2.判空
        if (Objects.isNull(targetTask)) {
            return new ResponseEntity<>("驳回目的任务不存在！",
                    HttpStatus.BAD_REQUEST);
        }

        //3.获取流程实例id
        String processInstanceId = targetTask.getProcessInstanceId();
        //4.获取当前任务节点
        Task sourceTask = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .active()
                .singleResult();
        //5.判空
        if (Objects.isNull(sourceTask)) {
            return new ResponseEntity<>("驳回当前任务不存在！",
                    HttpStatus.BAD_REQUEST);
        }
        //6.不能驳回到自己
        if (Objects.equals(targetTask, sourceTask)) {
            return new ResponseEntity<>("不能驳回到当前节点！",
                    HttpStatus.BAD_REQUEST);
        }
        //7.获取流程定义id
        String processDefinitionId = targetTask.getProcessDefinitionId();
        //8.使用流程定义id查询流程定义，获取流程定义的所有节点信息
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        Process process = bpmnModel.getProcesses().get(0);
        Collection<FlowElement> flowElements = process.getFlowElements();
        //9.遍历流程定义的所有节点，找到目标节点的定义信息
        FlowElement targetElement = null;
        for (FlowElement flowElement : flowElements) {
            if (flowElement.getId().equals(targetTask.getTaskDefinitionKey())) {
                targetElement = flowElement;
                break;
            }
        }
        //10.判空
        if (Objects.isNull(targetElement)) {
            return new ResponseEntity<>("驳回目的任务定义不存在！",
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

    /**
     * 回退节点并清理数据.
     *
     * @param sourceTask 当前任务
     * @param targetTask 目标任务
     * @return 驳回后的任务
     */
    public Task rollbackTask(Task sourceTask, HistoricTaskInstance targetTask) {
        //1.获取流程实例id
        String processInstanceId = sourceTask.getProcessInstanceId();
        //2.回退节点
        runtimeService.createChangeActivityStateBuilder()
                //指定流程实例ID
                .processInstanceId(processInstanceId)
                //指定源节点和目标节点
                .moveActivityIdTo(sourceTask.getTaskDefinitionKey(), targetTask.getTaskDefinitionKey())
                .changeState();
        //3.清理两个节点之间的脏数据
        clearDirtyData(processInstanceId, sourceTask, targetTask);
        //4.返回新目标节点
        return taskService.createTaskQuery()
                //指定流程实例ID
                .processInstanceId(processInstanceId)
                //指定目标节点定义
                .taskDefinitionKey(targetTask.getTaskDefinitionKey())
                .singleResult();
    }

    /**
     * 清除节点脏数据.
     *
     * @param processInstanceId 流程实例id
     * @param sourceTask      当前任务id
     * @param targetTask      目标任务id
     */
    private void clearDirtyData(String processInstanceId, Task sourceTask,
                                HistoricTaskInstance targetTask) {
        //1.获取历史任务列表
        List<HistoricTaskInstance> historyTasks = historyService
                .createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                //按照任务开始时间降序排序
                .orderByHistoricTaskInstanceStartTime().desc()
                .list();
        //2.获取sourceTaskId和targetTaskId之间的所有任务节点，包括 targetTask 和 sourceTask，回滚之后删除。
        List<HistoricTaskInstance> betweenNodes =
                getBetweenNodes(historyTasks, sourceTask.getId(), targetTask.getId());
        //3.获取节点id列表
        List<String> taskIds = betweenNodes.stream()
                .map(HistoricTaskInstance::getId)
                .toList();
        //4.删除流程变量
        List<String> variableKeys = new ArrayList<>();
        for (String taskId : taskIds) {
            //分数key
            String scoreKey = "score";
            //所属人key
            String ownerKey = "ownerId";
            variableKeys.add(scoreKey);
            variableKeys.add(ownerKey);
            //删除历史节点
            historyService.deleteHistoricTaskInstance(taskId);
        }
        //5.删除流程实例中的指定变量
        runtimeService.removeVariables(processInstanceId, variableKeys);
    }

    /**
     * 获取sourceTaskId和targetTaskId之间的所有任务节点,包括 targetTask 和 sourceTask.
     *
     * @param sourceTaskId 当前任务id
     * @param targetTaskId 目标任务id
     * @return 任务节点列表
     */
    List<HistoricTaskInstance> getBetweenNodes(List<HistoricTaskInstance> historicTasks,
                                               String sourceTaskId,
                                               String targetTaskId) {
        //1.初始化一个列表，用于存储源任务和目标任务之间的所有任务节点
        List<HistoricTaskInstance> betweenNodes = new ArrayList<>();
        //2.定义一个标志，用于判断是否开始记录任务节点
        boolean startRecord = false;
        for (HistoricTaskInstance historicTask : historicTasks) {
            //3.如果历史任务的ID等于源任务的ID，那么开始记录任务节点
            if (historicTask.getId().equals(sourceTaskId)) {
                startRecord = true;
            }

            //4.如果开始记录任务节点，那么将当前历史任务添加到任务节点列表中
            if (startRecord) {
                betweenNodes.add(historicTask);
            }
            //5.如果历史任务的ID等于目标任务的ID，那么停止记录任务节点，并结束循环
            if (historicTask.getId().equals(targetTaskId)){
                break;
            }
        }
        //6.返回任务节点列表
        return betweenNodes;
    }
}
