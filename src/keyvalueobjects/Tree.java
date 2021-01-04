package keyvalueobjects;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;

public class Tree extends Commit {

	//遍历Tree得到Value和对应的Key
    public Tree(File file) throws Exception {
    	//初始化Type和Value
        this.Type = "tree";
        this.Value = "";
        for(File f:file.listFiles()){
        	if(f.isFile()){
                Value += "100644 blob " + new Blob(f).getKey() + " " + f.getName()+"\n";
            }
            else if(f.isDirectory()){
                Value += "040000 tree " + new Tree(f).getKey() + " " + f.getName()+ "\n";
            }
        }
        GenerateKey(Value);
    }

    //重写toString方法
    @Override
    public String toString(){
        return "040000 tree" + Key;
    }

    public void copyFile(String gitDir) throws IOException{
    	//输出到objects文件夹中
    	
    	//以hash值首字母创建路径
    	File dir = new File(gitDir+"/objects/"+this.Key.charAt(0));
    	if (!dir.exists()) {
    		dir.mkdirs();
    	}
    	
        PrintWriter p = new PrintWriter(gitDir+"/objects/"+this.Key.charAt(0)+"/"+this.Key);
        p.print(Value);
        p.close();//
    }

}
