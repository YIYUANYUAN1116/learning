package com.xht.activiti.service.impl;

import com.xht.activiti.service.DynamicJumpCmdService;
import com.xht.activiti.util.DynamicJumpCmd;
import org.activiti.engine.ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DynamicJumpCmdServiceImpl implements DynamicJumpCmdService {

    @Autowired
    protected ManagementService managementService;

    @Override
    public void jumpTask(String processInstanceId, String fromActivityId, String toActivityId) {
        DynamicJumpCmd dynamicJumpCmd = new DynamicJumpCmd(processInstanceId, fromActivityId, toActivityId);
        managementService.executeCommand(dynamicJumpCmd);
    }

}
