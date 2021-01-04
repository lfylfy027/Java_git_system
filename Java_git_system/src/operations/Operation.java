package operations;
import keyvalueobjects.Commit;
import java.io.File;
import java.io.IOException;

public class Operation {
	
	//创建仓库
	public static void createrpo(String p) throws IOException {
		new Repository().createRepository(p) ;
	}
	
	//commit
	public static void commit(String files) throws Exception {
		File dir = new File(files);
		new Commit(dir).copyFile();
		new Branch().writeCommitID();
	}
	
	//新建分支
	public static void newBranch(String name) throws IOException {
		new Branch(name);		
	}
	
	//切换分支
	public static void alterBranch(String name) throws IOException {
		new Branch().switchBranch(name);		
	}
	
	//切换分支指向的commit
	public void alterCommit(String commitid) throws IOException {
		new Branch().resetBranch(commitid);				
	}
	
	public static void main(String args[]) throws Exception {
		createrpo("TestRep1");
		commit("test");
		newBranch("newBranch");
		alterBranch("newBranch");				
	}
}
