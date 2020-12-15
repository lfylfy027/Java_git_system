import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;

public class Tree extends KeyValueObject {

	//遍历Tree得到Value和对应的Key
    public Tree(File file) throws Exception {
    	//初始化Type和Value
        this.Type = "tree";
        this.Value = "";
        for(File f:file.listFiles()){
        	if(f.isFile()){
                Value = Value + "\n" + "100644 blob " + new Blob(f).getKey() + " " + f.getName();
            }
            else if(f.isDirectory()){
                Value = Value + "\n" + "040000 tree " + new Tree(f).getKey() + " " + f.getName();
            }
        }
        GenerateKey(Value);
    }

    //重写toString方法
    @Override
    public String toString(){
        return "040000 tree" + Key;
    }

    //重写复制方法
    @Override
    public void copyFile() throws IOException{
    	//输出
        PrintWriter p = new PrintWriter(this.Key);
        p.print(Value);
        p.close();//
    }

}
