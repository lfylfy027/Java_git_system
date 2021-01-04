package operationobjects;
import keyvalueobjects.Commit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Operation {
	protected Repository currRepository;
	protected Branch currBranch;
	protected Commit currCommit;
	
	//创建仓库
	public void newRep(String p) throws IOException {
		currRepository = new Repository(p) ;
		currBranch = new Branch("master",currRepository.getgitDir(),"");
	}
	
	//新建分支
	public void newBranch(String name) throws IOException {
		currBranch = new Branch(name,currRepository.getgitDir(), currCommit.getcommitID());		
	}
	
	//新建commit	
	public void newCommit(String file) throws Exception {
		File files = new File(file);
		currCommit = new Commit(files, currRepository.getgitDir());
	}
	
	//提交commit
	public void docommit() throws IOException {
		currCommit.copyFile();
		currBranch.writeCommitID(currCommit.getcommitID(),currCommit.getinfo());
	}
	
	//展示所有分支
	public void showBranches() {
		File f = new File(currRepository.getgitDir() + "/refs/heads");
		File fa[] = f.listFiles();
		for (int i = 0; i < fa.length; i++) {
			File fs = fa[i];
			System.out.println(fs.getName());
		}
	}
			
	//切换分支
	public void alterBranch(String name) throws IOException {
		File f = new File(currRepository.getgitDir() + "/refs/heads/" + name);	
        if (f.exists()) {
        	
        	//重写HEAD
        	File HEAD = new File(currRepository.getgitDir() + "/HEAD");
            FileWriter p = new FileWriter(HEAD);
        	p.write("ref: refs/heads/" + name);
        	p.close();
        	
        	//在logs/HEAD中追加切换分支记录
        	File logHEAD = new File(currRepository.getgitDir() + "/logs/HEAD");
        	FileWriter p1 = new FileWriter(logHEAD , true);
        	p1.write("ref: refs/heads/" + name + "\n");
        	p1.close();  
        	
        	currBranch = new Branch(name, currRepository.getgitDir());
        }
        else System.out.println("The branch doesn't exist");		
	}
	
	//切换分支指向的commit
	public void alterCommit(String commitid) throws IOException {
		currBranch.resetBranch(commitid);				
	}
	
	public static void main(String args[]) throws Exception {
		Operation go = new Operation();
		go.newRep("TestRep1");
		go.newCommit("test");
		go.docommit();
		go.newBranch("newBranch");
		go.showBranches();
		go.alterBranch("newBranch");			
	}
}
