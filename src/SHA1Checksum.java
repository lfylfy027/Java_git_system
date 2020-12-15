
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

public class SHA1Checksum {
    protected String key; //用字符串记录hash值
    
    //两种情况下的Hash值构造
    public SHA1Checksum(File file) throws Exception{
        FileInputStream input = new FileInputStream(file);
        this.key = Hash(input);
    }

    public SHA1Checksum(String a)throws Exception{
        this.key = Hash(a);
    }

    public static String Hash(InputStream is) throws Exception{
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
        String ans = getKEY(complete.digest());
        return ans;
    }

    public static String Hash(String a) throws Exception{
        MessageDigest complete = MessageDigest.getInstance("SHA1");
        complete.update(a.getBytes());
        String ans = getKEY(complete.digest());
        return ans;
    }

    //将byte[]转换为16进制字符串的hash值
    public static String getKEY(byte[] sha1){
    	String ans = "";
        int n = sha1.length;
        for(int i = 0;i< n;i++) {
            String append = Integer.toString(sha1[i] & 0xFF, 16);
            if (append.length()<2) {
                ans = ans + "0" + append; // 如果不满2位字符，则将其前面补一个"0"
            }
            else {
                ans += append;
            }
        }
        return ans;
    }
    
    //封装数据域
    public String getSHA1(){
        return this.key;
    }
}

