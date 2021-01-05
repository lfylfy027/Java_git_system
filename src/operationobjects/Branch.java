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
import java.util.Scanner;

import keyvalueobjects.Commit;
import keyvalueobjects.KeyValue_Storage;


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
    public Branch() {}
    public Branch(String name) {
    	this.branchName=name;
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
	public void alterBranch(String name, String gitDir,String dir) throws IOException {
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
        	
        	//切换项目文件内容
        	Scanner in=new Scanner(f);
        	String commitkey=in.nextLine();
        	in.close();
        	Commit commit=new Commit();
        	commit.loadcommit(gitDir,commitkey);
        	cleardir(new File(dir));
        	reset(dir, commit.gettree(),gitDir);
        	
        	branchName = name;
        	
        	System.out.println("√");
        }
        else System.out.println("× 不存在这个分支喵！");		
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
	
	//切换分支时同时切换文件夹内容
	private void reset(String dirpath,String treekey,String gitDir) {
		File tree=KeyValue_Storage.getValue(gitDir, treekey);
		try {
			Scanner in=new Scanner(tree);
			while(in.hasNext()) {
				String[] line=in.nextLine().split(" ");
				String type=line[1];
				String key=line[2];
				String filename=line[3];
				if(type.equals("tree")) {
					File dir = new File(dirpath+"/"+filename);
		            if (!dir.exists()) {
		            	// 判断目录是否存在     
		                dir.mkdir();   
		            }
					reset(dirpath+"/"+filename, key,gitDir);
				}
				if(type.equals("blob")) {
					String newpath=dirpath+"/"+filename;
						try {
							copyfile(KeyValue_Storage.getValue(gitDir, key), newpath);
						} catch (IOException e) {
							e.printStackTrace();
						}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static void cleardir(File dirfile) {
		for(File f:dirfile.listFiles()){
        	if(f.isFile()){
               f.delete();
            }
            else if(f.isDirectory()&&!f.getName().equals(".git")){
               cleardir(f);
               f.delete();
            }
        }
	}
	//将文件恢复至项目文件夹
	private static void copyfile(File oldfile,String newpath) throws IOException  {
		FileInputStream fileInputStream = new FileInputStream(oldfile);
        FileOutputStream fileOutputStream = new FileOutputStream(newpath);
        byte[] buffer = new byte[1024];
        while (fileInputStream.read(buffer) != -1) {
        		fileOutputStream.write(buffer);
        }
        fileInputStream.close();
        fileOutputStream.close();
	}
}
