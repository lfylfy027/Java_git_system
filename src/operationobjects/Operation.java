package operationobjects;
import keyvalueobjects.Commit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Operation {
	protected Repository currRepository;
	protected Branch currBranch;
	protected Commit currCommit;
	
	//读取已有仓库
	public void readRep(String location) throws IOException {
		currRepository = new Repository(location);
		currBranch = new Branch();
		alterBranch("master");		
		currCommit = new Commit();
		currCommit.loadcommit(currRepository.getgitDir(),currBranch.getCommitID());	
	}
	
	//创建仓库
	public void newRep(String p) throws Exception {
		currRepository = new Repository(p) ;
		currBranch = new Branch("master",currRepository.getgitDir(),"");
		File files = new File(p);
		currCommit = new Commit("0000000000000000000000000000000000000000",files,currRepository.getgitDir());
		currCommit.copyFile();
		currBranch.writeCommitID(currCommit.getcommitID(),currCommit.getinfo());	
	}
	
	//新建分支
	public void newBranch(String name) throws IOException {
		currBranch = new Branch(name,currRepository.getgitDir(), currCommit.getcommitID());		
	}
	
	//新建commit	
	public void newCommit(String file) throws Exception {
		File files = new File(file);
		Commit compare = new Commit(currCommit.getcommitID(),files, currRepository.getgitDir());

		if (!compare.gettree().equals(currCommit.gettree())) {
			currCommit=compare;
			currCommit.copyFile();
			currBranch.writeCommitID(currCommit.getcommitID(),currCommit.getinfo());			
		}
		else System.out.println("并没有改动文件");
	}
	
	
	//展示所有commit记录
	public void showcommits() throws IOException {
		FileInputStream logf = new FileInputStream(currRepository.getgitDir() + "/logs/refs/heads/" + currBranch.getBranchName());
    	InputStreamReader isr = new InputStreamReader(logf);
    	BufferedReader br = new BufferedReader(isr);
    	String commits = null;
    	while((commits = br.readLine()) != null) {
    		System.out.println(commits);
    	}
    	
	}
	
	//删除某commit记录以后的所有记录
	public void resetCommitHistory(String commitID) throws IOException {
		File logf = new File(currRepository.getgitDir() + "/logs/refs/heads/" + currBranch.getBranchName());
		BufferedReader br = new BufferedReader(new FileReader(logf));
		String commit = null;
		String usefulCommits = "";		
		while((commit = br.readLine()) != null) {
			if(!commit.contains(" commitID " + commitID)) {
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
		File newlogf = new File(currRepository.getgitDir() + "/logs/refs/heads/" + currBranch.getBranchName());
		FileWriter logp = new FileWriter(newlogf , true);
		for(int i = 0; i<newCommits.length; i++) {
			logp.write(newCommits[i]+"\n");
			System.out.println(newCommits[i]);
		}
    	logp.close(); 
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
	
	//reset --hard操作回滚commit记录以及项目文件
	public void reset_hard(String commitkey) {
		Commit commit=new Commit();
		commit.loadcommit(currRepository.getgitDir(), commitkey);
		new Reset(currRepository,currBranch,commit).reset_hard();
		currCommit=commit;
	}
	
	public static void main(String args[]) throws Exception {
		Operation go = new Operation();
		//go.newRep("test");
		go.readRep("test");
		//go.currCommit=new Commit();
		//go.currCommit.loadcommit(go.currRepository.getgitDir(), "e2c23c5ceb9f7e0a2bd2a891e644724539292290");
		//go.newRep("test");
		go.newCommit("test");
		//new Reset(go.currRepository,go.currBranch, go.currCommit).reset_hard();
		//go.newBranch("newBranch");
		//go.showBranches();
		//go.alterBranch("newBranch");	
		go.showcommits();
		//go.resetCommitHistory("e005b584f0c79eeb7303da9f14eef8aa9b380a7a");
	}
}
