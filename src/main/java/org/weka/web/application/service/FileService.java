package org.weka.web.application.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by suad on 1/3/2015.
 */
public interface FileService {
    public boolean saveFile(File file, String fileName);

    public List<String> getAllFileNames();

    public File getFile(String fileName) throws IOException;

    public boolean deleteFile(String fileName);
}
