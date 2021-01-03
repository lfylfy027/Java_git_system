
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class KeyValue_Storage {

	public static String storage(String dirpath) throws Exception {
		File dir=new File(dirpath);
		return dfs(dir);
	}
	
	public static String storage(File dir) throws Exception {
		return dfs(dir);
	}
	public static String dfs(File dir) throws Exception {
        File[] file = dir.listFiles();
        ArrayList<File> files=new ArrayList<File>(Arrays.asList(file));
        Tree tr=new Tree(dir);
        tr.copyFile();
        for(int i = 0; i < files.size(); i++) {
            
        	//判断如果是blob文件，则生成key并存储
        	if(files.get(i).isFile()) {
        		new Blob(files.get(i)).copyFile();
            }
        	
        	//如果是文件夹，则递归遍历
            if(files.get(i).isDirectory()) {
            	dfs(files.get(i));
            }
        }
        //返回当前tree的key
        return tr.getKey();
	}
	
	//根据key值查找文件，返回该文件
	public static File getValue(String key) {
		String path="objects/"+key.charAt(0);
		File dir=new File(path);
		File[] fs = dir.listFiles();
		ArrayList<File> files=new ArrayList<File>(Arrays.asList(fs));
		for(int i = 0; i < files.size(); i++) {
		    String name=files.get(i).getName();
		    if(name.indexOf(key)!=-1)return files.get(i);
		}
		return null;
	}
	
	public static void main(String args[]) {
			try {
				//新建仓库
				new Repository().createRepository("TestRep1");
				File dir=new File("test");
				new Commit(dir).copyFile();
				//将commit信息写入当前分支
				new Branch().writeCommitID();
				//创建新分支
				new Branch("new");
				//切换分支
				new Branch().switchBranch("new");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
