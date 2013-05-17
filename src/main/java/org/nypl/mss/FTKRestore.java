package org.nypl.mss;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class FTKRestore {
    
    private File dir;
    private File fileList;
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        FTKRestore f = new FTKRestore(args[0]);
    }
    
    
    FTKRestore(String dir) throws FileNotFoundException, IOException{
        this.dir  = new File(dir);
        if(!validateDir()){System.exit(1);}
        System.out.println("Directory valid and FileList.txtFound");
        parseFileList();
    }

    private Boolean validateDir() {

        if(dir.exists() && dir.isDirectory() && dir.canRead()){
            File f = new File(dir.getAbsolutePath() + "/" + "FileList.txt");
                if(f.exists() && f.canRead()){
                    fileList = f;
                    return true;
                } else {
                    System.err.println("FileList.txt not found");
                    return false;
                }
        }else{
            System.err.println("Directory is not valid");
            return false;
        }
    }

    private void parseFileList() throws FileNotFoundException, IOException {
        String line;
        BufferedReader br = new BufferedReader(new FileReader(fileList));
        while((line = br.readLine()) != null){
            StringBuilder currentDir = new StringBuilder("/");
            String[] tokens = line.split("/");
            for(int x = 1; x < tokens.length; x++){
                if(x < tokens.length - 1){
                    currentDir.append(tokens[x]).append("/");
                    createDir(currentDir.toString());
                } else {
                    moveFile(currentDir.toString(), tokens[x]);
                } 
            }
        }
    }

    private void createDir(String dirIn) {
        File currentDir = new File(dir.getAbsolutePath() + dirIn);
        if(currentDir.exists()){
            
        } else {
            currentDir.mkdir();
        }
    }

    private void moveFile(String dirIn, String filename) throws IOException {
        File file = new File(dir.getAbsolutePath() + "/" + filename);
        File currentDir = new File(dir.getAbsolutePath() + dirIn);
        if(file.exists()){
            FileUtils.moveFileToDirectory(file, currentDir, false);
        } else {
            String alt = getDuplicate(file);
            if(alt != null){
                
                File altFile = new File(alt);
                String altName = altFile.getName();
                FileUtils.moveFileToDirectory(altFile, currentDir, false);
                altFile = new File(currentDir + "/" + altName);
                File finalFile = new File(currentDir + "/" + filename);
                FileUtils.moveFile(altFile, finalFile);
                System.out.println("Alternate filename identified: " + finalFile.getAbsolutePath());
            }

        }
    }

    private String getDuplicate(File file) {
        
        String name = FilenameUtils.getBaseName(file.getName());
        String ext = FilenameUtils.getExtension(file.getName());
        Pattern p = Pattern.compile(name + "\\[\\d*\\]." + ext);
        File[] files = dir.listFiles();
        
        for(File f: files){
            Matcher m = p.matcher(f.getName());
            if(m.find()){
                return f.getAbsolutePath();
            }
        }
        return null;
    }
}
