package operationobjects;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import keyvalueobjects.Commit;

public class Merge extends GitOperation {
	protected Branch newbranch;
	
	public Merge(Repository rep, Branch branch, Commit commit,Branch newbranch) {
		super(rep, branch, commit);
		this.newbranch=newbranch;
	}
	public String checkcommit() {
		ArrayList<String> listbase=new ArrayList<String>();
		ArrayList<String> listnew=new ArrayList<String>();
		listbase=getCommitlist(branch);
		listnew=getCommitlist(newbranch);
		String commonCommit="";
		outer:for(int i=listbase.size()-1;i>=0;i--) {
			for(int j=listnew.size()-1;j>=0;j--) {
				if(listbase.get(i).equals(listnew.get(j))) {
					commonCommit=listbase.get(i);
					break outer;
				}
			}
		}
		if(commonCommit.equals(listnew.get(listnew.size()-1))) {
			return "uptodate";
		}
		if(commonCommit.equals(listbase.get(listbase.size()-1))) {
			return "fast";
		}
		return commonCommit;
	}
	
	private ArrayList<String> getCommitlist(Branch branch){
		ArrayList<String> list=new ArrayList<String>();
		String path=gitDir+"/logs/refs/heads/" + branch.getBranchName();
		//String path= "test/.git/logs/refs/heads/master";
		File log=new File(path);
		try {
			Scanner in=new Scanner(log);
			in.nextLine();
			while(in.hasNext()) {
				String[] line=in.nextLine().split(" ");
				list.add(line[10]);
			}
			in.close();
		} catch (Exception e) {

		}
		return list;
	}
	//如果merge的当前分支是目标分支的父节点，则将当前分支直接指向目标分支的最新commit
	private void fast_merge() {
		try {
			Commit newcommit=new Commit();
			newcommit.loadcommit(gitDir, newbranch.getCommitKey(gitDir));
			new Reset(rep, newbranch, newcommit).reset_hard();
			File logf = new File(gitDir + "/logs/refs/heads/" + branch.getBranchName());
	    	FileWriter logp = new FileWriter(logf , true);
	    	logp.write(newcommit.getinfo()+"\n");
	    	logp.close();        
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String merge() {
		String tmp=checkcommit();
		if(tmp.equals("uptodate"))return "当前分支领先于目标分支。";
		if(tmp.equals("fast")) {
			fast_merge();
			return "分支合并成功";
		}
		return "";
	}
	public static void main(String[] args) {
	}
}
