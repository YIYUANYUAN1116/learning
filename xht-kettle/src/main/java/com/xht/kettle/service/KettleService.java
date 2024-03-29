package com.xht.kettle.service;

import java.util.Map;

public interface KettleService {
    boolean runTaskKtr(String filename, Map<String,String> params);

    boolean runTaskKjb(String filename, Object o);
}
