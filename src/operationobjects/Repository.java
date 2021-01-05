package operationobjects;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Repository{
	private String location;//仓库路径
			
    public Repository(String path) throws IOException {    	
    	location = path;
    	path = path + "/.git";   	
    	File git = new File(path);
    	if(!git.exists()) {
    		//创建文件夹
            File dotgit = new File(path);
            dotgit.mkdirs();
            
            //创建objects文件夹
            File objects = new File(path + "/objects");
            objects.mkdir();
            
            //创建heads文件夹
            File heads = new File(path + "/refs/heads");
            heads.mkdirs();
            File logheads = new File(path + "/logs/refs/heads");
            logheads.mkdirs();
            
            //创建tags文件夹
            File tags = new File(path + "/refs/tags");
            tags.mkdirs();

            //创建.git和logs里的HEAD文件
            File HEAD = new File(path + "/HEAD");
            PrintWriter f = new PrintWriter(HEAD);      
            f.print("ref: refs/heads/master\n");
            f.close();
            File logHEAD = new File(path + "/logs/HEAD");
            PrintWriter f1 = new PrintWriter(logHEAD);      
            f1.print("ref: refs/heads/master\n");
            f1.close();
            
            //创建master分支文件
            File master = new File(path + "/refs/heads/master");
            PrintWriter f11 = new PrintWriter(master);
            f11.close(); 
            File logmaster = new File(path + "/logs/refs/heads/master");
            PrintWriter f111 = new PrintWriter(logmaster);
            f111.close();     		
    	}    	       
    }
    
	public String getgitDir() {
		return location + "/.git";
	}
	public String getlocation() {
		return location;
	}
}

