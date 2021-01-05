package keyvalueobjects;

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

}
