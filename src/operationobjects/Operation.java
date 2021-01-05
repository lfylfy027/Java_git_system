package operationobjects;
import keyvalueobjects.Commit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class Operation {
	protected Repository currRepository;
	protected Branch currBranch;
	protected Commit currCommit;
	
	//读取已有仓库
	public void readRep(String location) throws IOException {
		currRepository = new Repository(location);	
		currBranch = new Branch();
		currBranch.alterBranch("master", currRepository.getgitDir()); 
		File in = new File(currRepository.getgitDir() + "/refs/heads/master");
		if(in.length() != 0) {
			currCommit = new Commit();
			currCommit.loadcommit(currRepository.getgitDir(),currBranch.getCommitKey(currRepository.getgitDir()));				
		}
		System.out.println("读取仓库成功");
	}
	
	//创建仓库
	public void newRep(String p) throws Exception {
		currRepository = new Repository(p) ;
		currBranch = new Branch("master",currRepository.getgitDir(),"");
		currBranch.writeCommitKey("", currRepository.getgitDir());
		System.out.println("新建仓库成功");
	}
	
	//新建分支
	public void newBranch(String name) throws IOException {
		currBranch.copyLogs(name, currRepository.getgitDir());
		currBranch = new Branch(name,currRepository.getgitDir(), currCommit.getcommitID());
		System.out.println("新建分支成功");
	}
	
	//新建commit	
	public void newCommit(String file) throws Exception {
		File files = new File(file);
		File in = new File(currRepository.getgitDir() + "/refs/heads/" + currBranch.getBranchName() );
		if(in.length() != 0) {									
			Commit compare = new Commit(currCommit.getcommitID(),files, currRepository.getgitDir());
		
			if (!compare.gettree().equals(currCommit.gettree())) {
				currCommit=compare;
				currCommit.copyFile();
				currBranch.writeCommitKey(currCommit.getcommitID(),currCommit.getinfo(),currRepository.getgitDir());
				System.out.println("Commit成功");
			}
			else System.out.println("并没有改动文件");
		}
		else {
			currCommit = new Commit("0000000000000000000000000000000000000000",files, currRepository.getgitDir());
			currCommit.copyFile();
			currBranch.writeCommitKey(currCommit.getcommitID(),currCommit.getinfo(),currRepository.getgitDir());
			System.out.println("Commit成功");
		}
	}
	
	
	//展示所有commit记录
	public void showcommits() throws IOException {
		FileInputStream logf = new FileInputStream(currRepository.getgitDir() + "/logs/refs/heads/" + currBranch.getBranchName());
    	InputStreamReader isr = new InputStreamReader(logf);
    	BufferedReader br = new BufferedReader(isr);
    	String commits = null;
    	System.out.println("Commit记录：");
    	while((commits = br.readLine()) != null) {
    		System.out.println(commits);
    	}
    	br.close();
	}
	
	
	//展示所有分支
	public void showBranches() {
		File f = new File(currRepository.getgitDir() + "/refs/heads");
		File fa[] = f.listFiles();
		System.out.println("当前的分支有：");
		for (int i = 0; i < fa.length; i++) {
			File fs = fa[i];
			System.out.println(fs.getName());
		}
	}
			
	//切换分支
	public void alterBranch(String name) throws IOException {
		currBranch.alterBranch(name, currRepository.getgitDir());
		System.out.println("切换分支成功");
	}
	
	//切换分支指向的commit
	public void alterCommit(String commitid) throws IOException {
		currBranch.resetBranch(commitid, currRepository.getgitDir());				
	}
	
	//reset --hard操作回滚commit记录以及项目文件
	public void reset_hard(String commitkey) {
		Commit commit=new Commit();
		commit.loadcommit(currRepository.getgitDir(), commitkey);
		new Reset(currRepository,currBranch,commit).reset_hard(currRepository.getgitDir());
		try {
			currBranch.resetCommitHistory(commitkey, currRepository.getgitDir());
		} catch (IOException e) {
			e.printStackTrace();
		}
		currCommit=commit;
		System.out.println("回滚成功");
	}
	
	public static void main(String args[]) throws Exception {
	}
}
