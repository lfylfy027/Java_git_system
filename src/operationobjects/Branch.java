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
    public Branch(String name1, String gitDir, String commitid) throws IOException {
    	this.branchName = name1;
    	this.Path = gitDir;
    	writeCommitID(commitid);    	
    }
    
    //切换分支时的构造方法
    public Branch(String name, String gitDir) throws IOException {
    	this.branchName = name;
    	this.Path = gitDir;
    	@SuppressWarnings("resource")
		BufferedReader in = new BufferedReader(new FileReader(Path + "/refs/heads/" + name));
    	writeCommitID(in.readLine());     	    	
    }
        
    public Branch() {
		
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
    
    //获取CommitID
    public String getCommitID() {
    	return CommitID;
    }
    
	//删除某commit记录以后的所有记录
	public void resetCommitHistory(String commitkey) throws IOException {
		String pathdir=Path + "/logs/refs/heads/" + branchName;
		System.out.print(pathdir);
		File logf = new File(pathdir);
		BufferedReader br = new BufferedReader(new FileReader(logf));
		String commit = null;
		String usefulCommits = "";		
		while((commit = br.readLine()) != null) {
			if(!commit.contains(" commitID " + commitkey)) {
				usefulCommits+=(commit + "|");
			}
			else {
				usefulCommits+=(commit + "|");
				break;
			}
		}
		br.close();
		logf.delete();
		String[] newCommits = usefulCommits.split("\\|");				
		File newlogf = new File(Path + "/logs/refs/heads/" +branchName);
		FileWriter logp = new FileWriter(newlogf , true);
		for(int i = 0; i<newCommits.length; i++) {
			logp.write(newCommits[i]+"\n");
			System.out.println(newCommits[i]);
		}
    	logp.close(); 
	}
}
