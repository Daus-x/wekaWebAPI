package org.weka.web.application.service;

import weka.core.Instances;
import java.util.List;

/**
 * Created by suad on 1/3/2015.
 */
public interface WekaClassifierService {


    public List<String> GetAttributes(String fileName) throws Exception;

    public List<String> trainFile(String fileName, String testFileName) throws Exception;

    public List<String> crossValidateFile(String fileName) throws Exception;

    public Instances getInstances(String fileName) throws Exception;
}
