import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Branch extends KeyValueObject{
    String branchName = "master";
    
    //新建分支的构造方法
    public Branch(String branchName1) throws IOException {
    	this.branchName=branchName1;
    	writeCommitID();	
    }
    
    public Branch() {

	}

	//判断分支是否存在
    public boolean branchExist(String branchName1) {
    	String path = gitDir + "/refs/heads";
        File f = new File(path + "/" + branchName1);
        return f.exists();
    }    	    	
    
    //通过重写HEAD切换分支
    public void switchBranch(String branchName1) throws IOException {
        File HEAD = new File(gitDir + "/HEAD");
        PrintWriter p = new PrintWriter(HEAD);
        if (branchExist(branchName1)) {
        	p.print("ref: refs/heads/" + branchName1);
        	p.close();
        }
        else System.out.println("The branch doesn't exist");
    }
    
    //在分支文件中写入指向的commit
    public void writeCommitID() throws IOException {
        File f = new File(gitDir + "/refs/heads/" + branchName);
        PrintWriter p = new PrintWriter(f);
        p.print(CommitID);
        p.close();
    }

    //将分支重新指向一个commit
    public void resetBranch(String CommitID1) throws IOException {
    	CommitID = CommitID1;
    	writeCommitID();	
    }
}
