package com.xht.kettle.service;

public interface KettleService {
    boolean runTaskKtr(String filename, Object o);

    boolean runTaskKjb(String filename, Object o);
}
