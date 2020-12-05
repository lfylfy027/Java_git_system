

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class Dir_Storage {
	public static String dfs(File dir) throws Exception {
        File[] file = dir.listFiles();
        File_Storage fs=new File_Storage();
        ArrayList<File> files=new ArrayList<File>(Arrays.asList(file));
        
        //创建一个临时txt记录文件夹信息
        OutputStreamWriter pw = null;
        pw = new OutputStreamWriter(new FileOutputStream(dir.getName()+".txt"),"GBK");
        
        
        for(int i = 0; i < files.size(); i++) {
            
        	//判断如果是blob文件，就计算并更新哈希值,记录在临时txt中
        	if(files.get(i).isFile()) {
        		pw.write(blobcreate(files.get(i))+"\n");
            }
        	
        	//如果是文件夹，则递归遍历其下的文件
            if(files.get(i).isDirectory()) {
            	String hash=dfs(files.get(i));
            	pw.write("tree    "+hash+"    "+files.get(i).getName()+"\n");
            
            }
        }
        pw.close();
        
        //将文件夹信息存入objects，并删去临时文件
        File tmp=new File(dir.getName()+".txt");
        String hash=fs.Keystorage(tmp);
        tmp.delete();
        
        return hash;
	}
	
	//存储blob文件，并且返回用于记录在tree中的字符串数据
	public static String blobcreate(File file) throws Exception {
		File_Storage fs=new File_Storage();
		String hash=fs.Keystorage(file);
		String content="blob"+"    "+hash+"    "+file.getName();
		return content;
	}
	
	public static void main(String args[]) {
			try {
				System.out.println(dfs(new File("test")));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
