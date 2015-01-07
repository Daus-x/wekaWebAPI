package org.weka.web.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.weka.web.application.service.FileService;
import org.weka.web.application.service.WekaClassifierService;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMO;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by suad on 1/7/2015.
 */
public class RandomForestImpl implements WekaClassifierService {
    public static final String OPTION_STRING = "-I 10 -K 0 -S 1";

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

        Classifier smoClassifier = new RandomForest();
        String[] options=weka.core.Utils.splitOptions(OPTION_STRING);
        smoClassifier.setOptions(options);
        smoClassifier.buildClassifier(tranInstance);


        Evaluation evaluation = new Evaluation(tranInstance);
        evaluation.evaluateModel(smoClassifier, testInstance);

        List<String> results = getResults(evaluation);

        return results;
    }

    @Override
    public List<String> crossValidateFile(String fileName) throws Exception {
        Instances tranInstance = getInstances(fileName);

        Classifier smoClassifier = new RandomForest();
        String[] options=weka.core.Utils.splitOptions(OPTION_STRING);
        smoClassifier.setOptions(options);
        smoClassifier.buildClassifier(tranInstance);

        Evaluation evaluation = new Evaluation(tranInstance);
        evaluation.crossValidateModel(smoClassifier, tranInstance, 10, new Random(1));

        List<String> results = getResults(evaluation);

        return results;

    }

    private List<String> getResults(Evaluation evaluation) throws Exception {
        List<String> results=new ArrayList<String>(6);

        String recallRate = "Recall Rate  " +  evaluation.recall(0);
        results.add(recallRate);

        String precisionRate = "Precision Rate  " +  evaluation.precision(0);
        results.add(precisionRate);

        String f1 = "\nF1   " +  evaluation.fMeasure(0);
        results.add(f1);

        String summaryString= evaluation.toSummaryString();
        resolveResults(summaryString, results);


        String matrixString = evaluation.toMatrixString();
        resolveResults(matrixString, results);

        String classDetailsString = evaluation.toClassDetailsString();
        resolveResults(classDetailsString, results);
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

    private void resolveResults(String text, List<String> stringList){
        String [] strings = text.split("\n");
        for(int count= 0; count<strings.length;count++){
            stringList.add(strings[count]);
        }

    }
}
