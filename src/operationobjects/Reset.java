package operationobjects;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Reset {
	protected Repository rep;
	protected Commit commit;
	protected String gitDir=set_gitDir();
	protected String dir=set_dir();
	
	private String set_gitDir() {
		return rep.gitDir;
	}
	
	private String set_dir() {
		return rep.dir;
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
	
	private void cleardir() {
		File dirfile=new File(dir);
		File[] fs = dirfile.listFiles();
		ArrayList<File> files=new ArrayList<File>(Arrays.asList(fs));
		for(int i = 0; i < files.size(); i++) {
			if(files.get(i).getName()!=".git")files.get(i).delete();
		}
	}
	
	public void reset_hard() {
		cleardir();
		reset(dir,commit.gettree());
	}
}
