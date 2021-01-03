import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Repository extends KeyValueObject{
    public void createRepository(String path) throws IOException {
    	gitDir = path + "/.git";
    	//创建文件夹
        File dotgit = new File(gitDir);
        dotgit.mkdirs();
        //创建objects文件夹
        File objects = new File(gitDir + "/objects");
        objects.mkdir();
        //创建heads文件夹
        File heads = new File(gitDir + "/refs/heads");
        heads.mkdirs();
        //创建tags文件夹
        File tags = new File(gitDir + "/refs/tags");
        tags.mkdirs();
        //创建HEAD文件
        File HEAD = new File(gitDir + "/HEAD");
        PrintWriter f = new PrintWriter(HEAD);      
        f.print("ref: refs/heads/master\n");
        f.close();
        //创建master分支文件
        File master = new File(gitDir + "/refs/heads/master");
        PrintWriter f1 = new PrintWriter(master);
        f1.close();      
    }        
}

