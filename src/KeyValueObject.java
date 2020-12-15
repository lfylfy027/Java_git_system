
import java.io.*;

public abstract class KeyValueObject {
    protected String Type;
    protected String Key;
    protected File File;
    protected String Value;

    protected  KeyValueObject(){

    }

    //输入文件生成key
    protected void GenerateKey(File file) throws Exception{
        SHA1Checksum s = new SHA1Checksum(file); //新建一个SHA1Checksum类实例s
        this.Key = s.getSHA1(); //得到SHA1Checksum类中封装的数据域key
        this.File = file;
    }

    protected void GenerateKey(String a) throws Exception{
        SHA1Checksum s = new SHA1Checksum(a);
        this.Key = s.getSHA1();
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
        FileInputStream fileInputStream = new FileInputStream(this.File);
        FileOutputStream fileOutputStream = new FileOutputStream("objects/"+this.Key.charAt(0)+"/"+this.Key);
        byte[] buffer = new byte[1024];
        while (fileInputStream.read(buffer) != -1) {
        		fileOutputStream.write(buffer);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
