package keyvalueobjects;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class KeyValue_Storage {
	
	public static String storage(File dir1, String gitD) throws Exception {
		return dfs(dir1, gitD);
	}

	public static String storage(String dirpath, String gitD) throws Exception {
		File dir=new File(dirpath);
		return dfs(dir, gitD);
	}
	
	public static String dfs(File dir, String gitD) throws Exception {
        File[] file = dir.listFiles();
        ArrayList<File> files=new ArrayList<File>(Arrays.asList(file));
        Tree tr=new Tree(dir);
        tr.copyFile(gitD);
        for(int i = 0; i < files.size(); i++) {
        	//判断如果是blob文件，则生成key并存储
        	if(files.get(i).isFile()) {
        		new Blob(files.get(i)).copyFile(gitD);
            }
        	
        	//如果是文件夹，则递归遍历
            if(files.get(i).isDirectory()&&!files.get(i).getName().equals(".git")) {
            	dfs(files.get(i), gitD);
            }
        }
        //返回当前tree的key
        return tr.getKey();
	}
	
	//根据key值查找文件，返回该文件
	public static File getValue(String repdir,String key) {
		String path=repdir+"/objects/"+key.charAt(0);
		File dir=new File(path);
		File[] fs = dir.listFiles();
		ArrayList<File> files=new ArrayList<File>(Arrays.asList(fs));
		for(int i = 0; i < files.size(); i++) {
		    String name=files.get(i).getName();
		    if(name.indexOf(key)!=-1)return files.get(i);
		}
		return null;
	}
	
}
