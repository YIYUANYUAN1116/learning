package com.xht.kettle.service.impl;

import com.xht.kettle.service.KettleService;
import com.xht.kettle.vo.ResultCodeEnum;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.Result;
import org.pentaho.di.core.ResultFile;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.plugins.PluginFolder;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KettleServiceImpl implements KettleService {
    @Value("${kettleConfig.filepath}")
    private String kettleFilePath;

    @Value("${kettleConfig.pluginpath}")
    private String kettlePluginPath;
    @Override
    public boolean runTaskKtr(String filename, Map<String,String> params) {
        Result result = null;
        try {

            StepPluginType.getInstance().getPluginFolders().add(new PluginFolder(kettlePluginPath, false, true));

            KettleEnvironment.init();

            TransMeta transMeta = new TransMeta(filename);

            List<String> usedVariables = transMeta.getUsedVariables();
            Map<String, String> map = new HashMap<>();

            for (String usedVariable : usedVariables) {
                String value = transMeta.getParameterDefault(usedVariable);
                map.put(usedVariable,value);
            }

            Trans trans = new Trans(transMeta);


            if (params != null){
                for (Map.Entry<String, String> stringEntry : params.entrySet()) {
                    trans.setParameterValue(stringEntry.getKey(),stringEntry.getValue());
                }
            }

            trans.execute(null);

            trans.waitUntilFinished();

            Result result1 = trans.getResult();
            List<RowMetaAndData> resultRows = trans.getResultRows();
            List<ResultFile> resultFiles = trans.getResultFiles();
            result = trans.getResult();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  result.getResult();

    }

    @Override
    public boolean runTaskKjb(String filename, Object o) {
        Result result = null;
        try {
            StepPluginType.getInstance().getPluginFolders().add(new PluginFolder(kettlePluginPath, false, true));

            KettleEnvironment.init();

            JobMeta jobMeta = new JobMeta(filename,null);

            Job job = new Job(null,jobMeta);

            job.start();

            job.waitUntilFinished();

            result = job.getResult();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result.getResult();
    }
}
