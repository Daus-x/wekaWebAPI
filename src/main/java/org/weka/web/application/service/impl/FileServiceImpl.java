package org.weka.web.application.service.impl;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.weka.web.application.service.FileService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.gridfs.GridFsCriteria.whereFilename;

/**
 * Created by suad on 1/3/2015.
 */
public class FileServiceImpl implements FileService {

    @Autowired
    GridFsOperations gridFsOperations;


    @Override
    public boolean saveFile(File file, String fileName) {
        if(fileName.equals(null) || file.equals(null)){
            throw new IllegalArgumentException("can't save file");
        }
        InputStream inputStream= null;
        try {
            inputStream = new FileInputStream(file);
            gridFsOperations.store(inputStream,fileName);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<String> getAllFileNames() {
        List<String> fileNames=new ArrayList<String>();
        for(GridFsResource gridFsResource : gridFsOperations.getResources("*")){
            fileNames.add(gridFsResource.getFilename());
        }
        return fileNames;
    }

    @Override
    public File getFile(String fileName) throws IOException {
        if(fileName.equals(null)){
            throw new IllegalArgumentException("Can't find file without its name");
        }
        GridFsResource gridFsResource=gridFsOperations.getResource(fileName);
        if(gridFsResource==null) {
            throw new IllegalArgumentException("Input Stream is Null");
        }
        File tempFile=new File(gridFsResource.getFilename());
        try {
            InputStream inputStream=gridFsResource.getInputStream();

            OutputStream outputStream = new FileOutputStream(tempFile);
            IOUtils.copy(inputStream, outputStream);
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return tempFile;
    }

    @Override
    public boolean deleteFile(String fileName) {
        try {
            gridFsOperations.delete(query(whereFilename().is(fileName)));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
}
