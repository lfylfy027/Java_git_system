package operations;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Branch {
    String branchName = "master";
    static String Path;
    String CommitID;
    
    
    //新建分支的构造方法
    public Branch(String branchName1) throws IOException {
    	this.branchName=branchName1;
    	writeCommitID();	
    }
    
    public Branch() {

	}

    //判断分支是否存在
    public boolean branchExist(String branchName1) {
        File f = new File(Path + "/refs/heads/" + branchName1);
        return f.exists();
    }    	    	
    
    //切换分支
    public void switchBranch(String branchName1) throws IOException {
        if (branchExist(branchName1)) {
        	//重写HEAD
        	File HEAD = new File(Path + "/HEAD");
            FileWriter p = new FileWriter(HEAD);
        	p.write("ref: refs/heads/" + branchName1);
        	p.close();
        	//在logs/HEAD中追加切换分支记录
        	File logHEAD = new File(Path + "/logs/HEAD");
        	FileWriter p1 = new FileWriter(logHEAD , true);
        	p1.write(branchName+" "+branchName1);
        	p1.close();    
        	branchName = branchName1;
        }
        else System.out.println("The branch doesn't exist");
    }
        
    public void writeCommitID() throws IOException {
    	
    	//在分支文件中写入指向的commit
    	CommitID = keyvalueobjects.Commit.getcommitID();
        File f = new File(Path + "/refs/heads/" + branchName);
        FileWriter p = new FileWriter(f);
        p.write(CommitID);
        p.close();
        
        //在logs中追加commit记录
        File logf = new File(Path + "/logs/refs/heads/" + branchName);
    	FileWriter logp = new FileWriter(logf , true);
    	logp.write(keyvalueobjects.Commit.getinfo());
    	logp.close();        
    }

    //将分支重新指向一个commit
    public void resetBranch(String CommitID1) throws IOException {
    	CommitID = CommitID1;
    	writeCommitID();	
    }

	public static void setPath(String path) {
		// TODO Auto-generated method stub
		Path = path;		
	}
}
