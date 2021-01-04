package keyvalueobjects;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Commit extends KeyValueObject {
	//parent等信息的写入之后完善
	protected String parent;
	protected String author;
	protected String committer;
	protected String tree;
	protected String commitID;
	protected String gitDir;
	
	protected Commit() {
		
	}
	
	public void loadcommit(File commit) {
		try {
			Scanner in=new Scanner(commit);
			
			String[] line=in.nextLine().split(" ");
			this.tree=line[1];
			
			line=in.nextLine().split(" ");
			if(line.length==2) {this.parent=line[1];}
			
			line=in.nextLine().split(" ");
			if(line.length==2) {this.author=line[1];}
			
			line=in.nextLine().split(" ");
			if(line.length==2) {this.committer=line[1];}
			
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	//根据仓库路径新生成一个commit
	public Commit(File dir, String gitD) throws Exception {
		gitDir = gitD;
		this.File=dir;
		this.Type = "commit";
		this.Value="";
		this.tree=KeyValue_Storage.storage(dir, gitD);
		Value+= "tree "+tree+"\n";
		Value+= "parent "+"\n";
		Value+= "author "+"\n";
		Value+= "committer "+"\n";
		GenerateKey(Value);
	}
	
	public String gettree() {
		return tree;
	}
		
    public void copyFile() throws FileNotFoundException, IOException {
		
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
    
	public String getcommitID() {
		return commitID;
	}
	
	public String getinfo() {
		return parent + commitID + committer;
	}
	
}
