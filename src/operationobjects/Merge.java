package operationobjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


import keyvalueobjects.Commit;
import keyvalueobjects.KeyValue_Storage;

public class Merge extends GitOperation {
	protected Branch newbranch;
	
	public Merge(Repository rep, Branch branch, Commit commit,Branch newbranch) {
		super(rep, branch, commit);
		this.newbranch=newbranch;
	}
	/*
		判断需要merge的两分支的最近共同祖先，如果是共同祖先是base分支，则base分支直接添加new分支的
	最新commit；如果共同祖先是new分支，则说明base分支更靠前，不需变动；如果共同祖先是第三个commit，则
	进行三路合并，保留相对共同祖先的变动。如果同一文件发生了不同的变动，则返回错误信息。
	*/
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
	
	//三路合并
	public void three_merge(String commoncommit) {
		Commit newcommit=new Commit();
		Commit basecommit=new Commit();
		Commit thirdcommit=new Commit();
		try {
			newcommit.loadcommit(gitDir, newbranch.getCommitKey(gitDir));
			basecommit.loadcommit(gitDir, branch.getCommitKey(gitDir));
			thirdcommit.loadcommit(gitDir, commoncommit);
		} catch (IOException e) {
			e.printStackTrace();
		}
		File basetree=KeyValue_Storage.getValue(gitDir, basecommit.gettree());
		File newtree=KeyValue_Storage.getValue(gitDir, newcommit.gettree());
		File thirdtree=KeyValue_Storage.getValue(gitDir, thirdcommit.gettree());
		String [][] base=new String[10][];
		String [][] newf=new String[10][];
		String [][] third=new String[10][];
		Scanner in0;
		try {
			in0 = new Scanner(basetree);
		int i=0;
		while(in0.hasNext()) {
			base[i]=in0.nextLine().split(" ");
			i++;
		}
		in0.close();
		Scanner in1=new Scanner(newtree);
		i=0;
		while(in1.hasNext()) {
			newf[i]=in1.nextLine().split(" ");
			i++;
		}
		in1.close();
		Scanner in2=new Scanner(thirdtree);
		i=0;
		while(in2.hasNext()) {
			third[i]=in2.nextLine().split(" ");
			i++;
		}
		in2.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//如果new分支的文件与base分支和共同祖先都不同，则保留。
		for (String[] strings : newf) {
			if(strings==null)break;
			if(!findfile(base, strings[3], strings[2])&&!findfile(third, strings[3], strings[2])) {
				try {
					copyfile(KeyValue_Storage.getValue(gitDir, strings[2]), dir+"/"+strings[3]);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//如果base分支中的文件与祖先相同，与new分支不同，则删除，保留new分支的相应文件
		for (String[] strings : base) {
			if(strings==null)break;
			if(findfile(third, strings[3], strings[2])&&!findfile(newf, strings[3], strings[2])) {
				File file=new File(dir+"/"+strings[3]);
				file.delete();
			}
		}
	}
	private boolean findfile(String[][] lists,String target,String key) {
		for (String[] list : lists) {
			if(list==null)return false;
			if(list[3].equals(target)&&list[2].equals(key))return true;
		}
		return false;
	}
	
	private ArrayList<String> getCommitlist(Branch branch){
		ArrayList<String> list=new ArrayList<String>();
		String path=gitDir+"/logs/refs/heads/" + branch.getBranchName();
		//String path= "test/.git/logs/refs/heads/master";
		File log=new File(path);
		try {
			Scanner in=new Scanner(log);
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
	
	//如果merge的当前分支与
	public int merge() {
		String tmp=checkcommit();
		if(tmp.equals("uptodate"))return 1;
		if(tmp.equals("fast")) {
			fast_merge();
			return 0;
		}
		three_merge(tmp);
		return 2;
	}
	
	//从objects中复制文件到项目文件夹
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
	public static void main(String[] args) {
	}
}
