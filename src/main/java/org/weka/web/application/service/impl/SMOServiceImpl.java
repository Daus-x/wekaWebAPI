package org.weka.web.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.weka.web.application.service.FileService;
import org.weka.web.application.service.SMOService;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMO;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by suad on 1/3/2015.
 */
public class SMOServiceImpl implements SMOService {
    public static final String OPTION_STRING = "-C 1.0 -L 0.0010 -P 1.0E-12 -N 0 -V -1 -W 1 " +
            "-K \"weka.classifiers.functions.supportVector.PolyKernel -C 250007 -E 1.0\"";

    @Autowired
    FileService fileService;

    @Override
    public List<String> GetAttributes(String fileName) throws Exception {
        Instances tranInstance = getInstances(fileName);

        List<String> attributes=new ArrayList<String>();

        for(int i=0;i<tranInstance.numAttributes();i++){
            attributes.add(tranInstance.attribute(i).name());
        }

        return attributes;
    }

    @Override
    public List<String> trainFile(String fileName, String testFileName) throws Exception {
        Instances tranInstance = getInstances(fileName);
        Instances testInstance = getInstances(fileName);

        Classifier smoClassifier = new SMO();
        String[] options=weka.core.Utils.splitOptions(OPTION_STRING);
        smoClassifier.setOptions(options);
        smoClassifier.buildClassifier(tranInstance);


        Evaluation evaluation = new Evaluation(tranInstance);
        evaluation.evaluateModel(smoClassifier, testInstance);

        List<String> results=new ArrayList<String>(6);

        String recallRate = "Recall Rate  " +  evaluation.recall(0);
        results.add(recallRate);

        String precisionRate = "Precision Rate  " +  evaluation.precision(0);
        results.add(precisionRate);

        String f1 = "\nF1   " +  evaluation.fMeasure(0);
        results.add(f1);

        String summaryString= evaluation.toSummaryString();
        results.add(summaryString);

        String matrixString = evaluation.toMatrixString();
        results.add(matrixString);

        String classDetailsString = evaluation.toClassDetailsString();
        results.add(classDetailsString);

        return results;
    }

    @Override
    public List<String> crossValidateFile(String fileName) throws Exception {
        Instances tranInstance = getInstances(fileName);

        Classifier smoClassifier = new SMO();
        String[] options=weka.core.Utils.splitOptions(OPTION_STRING);
        smoClassifier.setOptions(options);
        smoClassifier.buildClassifier(tranInstance);

        Evaluation evaluation = new Evaluation(tranInstance);
        evaluation.crossValidateModel(smoClassifier, tranInstance, 10, new Random(1));

        List<String> results=new ArrayList<String>(6);

        String recallRate = "Recall Rate  " +  evaluation.recall(0);
        results.add(recallRate);

        String precisionRate = "Precision Rate  " +  evaluation.precision(0);
        results.add(precisionRate);

        String f1 = "\nF1   " +  evaluation.fMeasure(0);
        results.add(f1);

        String summaryString= evaluation.toSummaryString();
        results.add(summaryString);

        String matrixString = evaluation.toMatrixString();
        results.add(matrixString);

        String classDetailsString = evaluation.toClassDetailsString();
        results.add(classDetailsString);

        return results;

    }

    @Override
    public Instances getInstances(String fileName) throws Exception {
        File tempFile=fileService.getFile(fileName);

        ConverterUtils.DataSource dataSource=new ConverterUtils.DataSource(new FileInputStream(tempFile));

        Instances tranInstance = dataSource.getDataSet();

        if (tranInstance.classIndex() == -1)
            tranInstance.setClassIndex(tranInstance.numAttributes() - 1);
        return tranInstance;
    }

    public SMOServiceImpl() {
    }

    public SMOServiceImpl(FileServiceImpl fileService) {
        this.fileService = fileService;
    }
}