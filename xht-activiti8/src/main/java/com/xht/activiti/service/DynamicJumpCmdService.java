package com.xht.activiti.service;

import com.xht.activiti.util.DynamicJumpCmd;

public interface DynamicJumpCmdService {

    public void jumpTask(String processInstanceId, String fromActivityId, String toActivityId);
}
