package keyvalueobjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
public class Blob extends Commit {

    public Blob(File file) throws Exception {
        this.Type = "blob";
        GenerateKey(file);
    }
    
    public String toString() {
        return "100644 blob " + Key;
    }

    public void copyFile(String gitDir) throws IOException{
    	
    	//以hash值首字母创建路径
    	File dir = new File(gitDir+"/objects/"+this.Key.charAt(0));
    	if (!dir.exists()) {
    		dir.mkdirs();
    	}
    	
    	//获得原文件后缀
    	String fileName=File.getName();	
    	String fileType=fileName.substring(fileName.lastIndexOf("."),fileName.length());

        FileInputStream fileInputStream = new FileInputStream(this.File);
        FileOutputStream fileOutputStream = new FileOutputStream(gitDir+"/objects/"+this.Key.charAt(0)+"/"+this.Key+fileType);
        byte[] buffer = new byte[1024];
        while (fileInputStream.read(buffer) != -1) {
        		fileOutputStream.write(buffer);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
    

}
