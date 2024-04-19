package com.xht.activiti.service;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ActivitiSercive {


    void handleTask(String taskID, String action);

    void actOrSusProcessDefinition(String proDefKey, String type);

    void actOrSusProcessInstance(String proInsId, String activate);
}
