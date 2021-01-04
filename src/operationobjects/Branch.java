package operationobjects;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Branch {
    String branchName = "master";
    String Path;
    String CommitID;
       
    //新建分支的构造方法
    public Branch(String name1, String gitD, String commitid) throws IOException {
    	this.branchName = name1;
    	this.Path = gitD;
    	writeCommitID(commitid);    	
    }
    
    //切换分支时的构造方法
    public Branch(String name, String gitD) throws IOException {
    	this.branchName = name;
    	this.Path = gitD;
    	@SuppressWarnings("resource")
		BufferedReader in = new BufferedReader(new FileReader(Path + "/refs/heads/" + name));
    	writeCommitID(in.readLine());     	    	
    }
        
    public String getBranchName() {
    	return branchName;
    }
    
    public void writeCommitID(String commitid, String commitinfo) throws IOException {    	
    	//在分支文件中写入指向的commit
    	CommitID = commitid;
        File f = new File(Path + "/refs/heads/" + branchName);
        FileWriter p = new FileWriter(f);
        p.write(CommitID);
        p.close();
        
        //在logs中追加commit记录
        File logf = new File(Path + "/logs/refs/heads/" + branchName);
    	FileWriter logp = new FileWriter(logf , true);
    	logp.write(commitinfo+"\n");
    	logp.close();        
    }
    
	private void writeCommitID(String commitID2) throws IOException {
    	//在分支文件中写入指向的commit
    	CommitID = commitID2;
        File f = new File(Path + "/refs/heads/" + branchName);
        FileWriter p = new FileWriter(f);
        p.write(CommitID);
        p.close();
	}

    //将分支重新指向一个commit
    public void resetBranch(String CommitID1) throws IOException {
    	CommitID = CommitID1;
    	writeCommitID(CommitID);	
    }

}
