package keyvalueobjects;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class SHA1Checksum {

    public static String Hash(File file) throws Exception{
    	FileInputStream is = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("SHA1");
        int numRead = 0;        
        do {
        	numRead = is.read(buffer);
        	if(numRead>0){
        		complete.update(buffer, 0 , numRead);
        	}
        }while(numRead!=-1);
        is.close();
        return getKEY(complete.digest());
    }

    public static String Hash(String a) throws Exception{
        MessageDigest complete = MessageDigest.getInstance("SHA1");
        complete.update(a.getBytes());
        String result = getKEY(complete.digest());
        return result;
    }

    //将byte[]转换为16进制字符串的hash值
    public static String getKEY(byte[] sha1){
    	String result = "";
        int n = sha1.length;
        for(int i = 0;i< n;i++) {
            String append = Integer.toString(sha1[i] & 0xFF, 16);
            if (append.length()<2) {
                result = result + "0" + append; // 如果不满2位字符，则将其前面补一个"0"
            }
            else {
                result += append;
            }
        }
        return result;
    }
    
}

