
import java.io.*;

public abstract class KeyValueObject {
    protected String Type;
    protected String Key;
    protected File File;
    protected String Value;
    protected static String gitDir;
    protected static String CommitID;
    protected String dir;
    protected  KeyValueObject(){

    }

    //输入文件生成key
    protected void GenerateKey(File file) throws Exception{
        this.Key = SHA1Checksum.Hash(file); //得到SHA1Checksum类中封装的数据域key
        this.File = file;
    }
    
    protected void GenerateKey1(File file) throws Exception{
        this.Key = SHA1Checksum.Hash(file); //得到SHA1Checksum类中封装的数据域key
    }

    protected void GenerateKey(String a) throws Exception{
        this.Key = SHA1Checksum.Hash(a);
    }

    //封装数据域
    public String getKey(){
        return this.Key;
    }

    public String getType(){
        return this.Type;
    }

    public String getValue(){
        return this.Value;
    }

    //拷贝文件，文件名为key值，存放到objects中
    public void copyFile() throws IOException{
    	
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
