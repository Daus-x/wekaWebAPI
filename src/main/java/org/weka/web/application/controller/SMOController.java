package org.weka.web.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.weka.web.application.service.WekaClassifierService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suad on 1/3/2015.
 */
@RestController
public class SMOController {

    @Autowired
    WekaClassifierService wekaClassifierService;

    @RequestMapping(value = "/train/{fileName}/{testName}",method = RequestMethod.GET)
    public ResponseEntity<List<String>> trainClassifier(@PathVariable("fileName") String fileName,
                                                        @PathVariable("testName") String testName){
        try {
            List<String> tempList= wekaClassifierService.trainFile(fileName,testName);
            return new ResponseEntity<List<String>>(tempList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<String>>(new ArrayList<String>(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/cross-validate/{fileName}",method = RequestMethod.GET)
    public ResponseEntity<List<String>> trainCrossValidate(@PathVariable("fileName") String fileName){

        try {
            List<String> tempList= wekaClassifierService.crossValidateFile(fileName);
            return new ResponseEntity<List<String>>(tempList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<String>>(new ArrayList<String>(), HttpStatus.BAD_REQUEST);
        }
    }

}
