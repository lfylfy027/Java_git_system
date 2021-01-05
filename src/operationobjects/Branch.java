package operationobjects;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import keyvalueobjects.Commit;

public class Branch {
    String branchName = "master";
       
    //新建分支的构造方法
    public Branch(String name1, String gitDir, String commitKey) throws IOException {
    	this.branchName = name1;
    	writeCommitKey(commitKey,gitDir);    	
    }
    

    //切换分支时的构造方法
    public Branch(String name, String gitDir) throws IOException {
    	this.branchName = name;
    	File in = new File(gitDir + "/refs/heads/master");
		if(in.length() != 0) {
			@SuppressWarnings("resource")
			BufferedReader input = new BufferedReader(new FileReader(gitDir + "/refs/heads/" + name));   	
	    	writeCommitKey(input.readLine(), gitDir);					
		}
    	     	    	
    }
        
    public Branch() {
		
	}

	public String getBranchName() {
    	return branchName;
    }
    
	//有commit的情况
    public void writeCommitKey(String commitKey, String commitinfo, String gitDir) throws IOException {    	
    	//在分支文件中写入指向的commit
        File f = new File(gitDir + "/refs/heads/" + branchName);
        FileWriter p = new FileWriter(f);
        p.write(commitKey);
        p.close();
        
        //在logs中追加commit记录
        File logf = new File(gitDir + "/logs/refs/heads/" + branchName);
    	FileWriter logp = new FileWriter(logf , true);
    	logp.write(commitinfo+"\n");
    	logp.close();        
    }
    
    //没有commit的情况
	public void writeCommitKey(String commitKey, String gitDir) throws IOException {
    	//在分支文件中写入指向的commit
        File f = new File(gitDir + "/refs/heads/" + branchName);
        FileWriter p = new FileWriter(f);
        p.write(commitKey);
        p.close();
	}

    //将分支重新指向一个commit
    public void resetBranch(String commitKey, String gitDir) throws IOException {
    	writeCommitKey(commitKey,gitDir);	
    }
    
    //获取CommitKey
    public String getCommitKey(String gitDir) throws IOException {
    	BufferedReader in = new BufferedReader(new FileReader(gitDir + "/refs/heads/" + branchName));
    	String CommitKey = in.readLine();
    	in.close();
    	return CommitKey;
    }
    
	//删除某commit记录以后的所有记录
	public void resetCommitHistory(String commitkey, String gitDir) throws IOException {
		String pathdir=gitDir + "/logs/refs/heads/" + branchName;
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
		File newlogf = new File(gitDir + "/logs/refs/heads/" +branchName);
		FileWriter logp = new FileWriter(newlogf , true);
		for(int i = 0; i<newCommits.length; i++) {
			logp.write(newCommits[i]+"\n");
			System.out.println(newCommits[i]);
		}
    	logp.close(); 
	}
	
	//切换分支
	public void alterBranch(String name, String gitDir) throws IOException {
		File f = new File(gitDir + "/refs/heads/" + name);	
        if (f.exists()) {
        	
        	//重写HEAD
        	File HEAD = new File(gitDir + "/HEAD");
            FileWriter p = new FileWriter(HEAD);
        	p.write("ref: refs/heads/" + name);
        	p.close();
        	
        	//在logs/HEAD中追加切换分支记录
        	File logHEAD = new File(gitDir + "/logs/HEAD");
        	FileWriter p1 = new FileWriter(logHEAD , true);
        	p1.write("ref: refs/heads/" + name + "\n");
        	p1.close();  
        	
        	branchName = name;
        	      	
        }
        else System.out.println("The branch doesn't exist");		
	}
	
	//logs中的分支内容复制
	public void copyLogs(String newBranch, String gitDir) throws IOException {
		File prelog = new File(gitDir + "/logs/refs/heads/" + branchName);
		InputStream input = new FileInputStream(prelog);
		File newlog = new File(gitDir + "/logs/refs/heads/" + newBranch);
		OutputStream output = new FileOutputStream(newlog);
		byte[] buf = new byte[1024];
		int bytesRead;
		while ((bytesRead = input.read(buf)) != -1) {
			output.write(buf, 0 ,bytesRead);
		}
		input.close();
		output.close();
	}
}
