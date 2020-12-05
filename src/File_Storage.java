

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;


public class File_Storage {
		public MessageDigest complete;
		public File_Storage() throws Exception {
			complete=MessageDigest.getInstance("SHA1");
		}
		public static void SHA1Checksum(MessageDigest complete,InputStream is) throws IOException {
			byte[] buffer = new byte[1024];
	        int numRead;
	        do {
	            numRead = is.read(buffer);
	            if (numRead > 0) {
	                complete.update(buffer, 0, numRead);
	            }
	        } while (numRead != -1);
	        is.close();
		}
		
		//复制文件
		public static void copyFile(File oldfile ,String newpath) throws IOException {
			File file =new File(newpath);
			FileInputStream in=new FileInputStream(oldfile);
			FileOutputStream out=new FileOutputStream(file);
			byte[] buffer=new byte[1024];
			while(in.read(buffer)!=-1) {
				out.write(buffer);
			}
			in.close();
			out.close();
		}
		
		//获得一个文件的key，即sha1值
		public static byte[] getKey(MessageDigest complete,File file) throws Exception  {
			//MessageDigest complete=MessageDigest.getInstance("SHA1");
			FileInputStream is=new FileInputStream(file);
			SHA1Checksum(complete,is);
			MessageDigest md=(MessageDigest) complete.clone();
			return md.digest();
		}
		
		//将文件存入指定文件夹objects，并按哈希值命名
		public String Keystorage(File file) throws Exception {
			byte[] key=getKey(complete,file);
			StringBuilder newfileName=new StringBuilder();
			for (int i = 0; i < key.length; i++) {
				int a=key[i];
				if(a<0) a+=256;  
	            if(a<16) newfileName.append("0"); 
                newfileName.append(Integer.toHexString(a));
            }
			//获得原文件后缀
			String fileName=file.getName();	
			String fileType=fileName.substring(fileName.lastIndexOf("."),fileName.length());
			
			String hash=newfileName.toString();
			newfileName.append(fileType);
			String newname=newfileName.toString();
			String newpath="objects/"+newname.charAt(0)+"/"+newname;
			copyFile(file, newpath);
			return hash;
			
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
}
