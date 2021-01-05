package operationobjects;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import keyvalueobjects.*;

public class Reset {
	protected Repository rep;
	protected Commit commit;
	protected Branch branch;
	protected String gitDir;
	protected String dir;
	
	
	public Reset(Repository rep,Branch branch,Commit commit) {
		this.rep=rep;
		this.commit=commit;
		this.branch=branch;
		gitDir=rep.getgitDir();
		dir=rep.getlocation();
	}

	
	//递归实现回滚文件的方法
	private void reset(String dirpath,String treekey) {
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
					reset(dirpath+"/"+filename, key);
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
	
	//回滚时从objects中复制文件到项目文件夹
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
	
	//清空已有的项目文件
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
	
	public void reset_hard() {
		File dirFile=new File(dir);
		cleardir(dirFile);
		reset(dir,commit.gettree());
		try {
			branch.resetBranch(commit.getcommitID());
		} catch (IOException e) {e.printStackTrace();}
	}
}
