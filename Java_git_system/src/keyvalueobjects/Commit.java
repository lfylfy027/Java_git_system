package keyvalueobjects;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Commit extends KeyValueObject {
	//parent等信息的写入之后完善
	protected static String parent;
	protected String author;
	protected static String committer;
	protected static String commitID;
	
	
	
	public Commit(File dir) throws Exception {
		this.File=dir;
		this.Type = "commit";
		this.Value="";
		Value+= "tree "+KeyValue_Storage.storage(dir)+"\n";
		Value+= "parent "+"\n";
		Value+= "author "+"\n";
		Value+= "commiter "+"\n";
		GenerateKey(Value);
	}
	
	@Override
    public void copyFile() throws IOException {
    	//输出
		
		//以hash值首字母创建路径
		File dir = new File(gitDir+"/objects/"+this.Key.charAt(0));
		if (!dir.exists()) {
			dir.mkdirs();
		}
    	
        PrintWriter p = new PrintWriter(gitDir+"/objects/"+this.Key.charAt(0)+"/"+this.Key);
        p.print(Value);
        p.close();
        
        commitID = this.Key;
    }
	
	public static String getcommitID() {
		return commitID;
	}
	
	public static String getinfo() {
		return parent + commitID + committer;
	}
	

}
