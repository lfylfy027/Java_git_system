import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Commit extends KeyValueObject {
	//parent等信息的写入之后完善
	protected String parent;
	protected String author;
	protected String commiter;
	
	
	
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
    public void copyFile() throws FileNotFoundException {
    	//输出
		
		//以hash值首字母创建路径
		File dir = new File(gitDir+"/objects/"+this.Key.charAt(0));
		if (!dir.exists()) {
			dir.mkdirs();
		}
    	
        PrintWriter p = new PrintWriter(gitDir+"/objects/"+this.Key.charAt(0)+"/"+this.Key);
        p.print(Value);
        p.close();
        
        CommitID = this.Key;
    }
}
