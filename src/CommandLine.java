

import java.util.*;
import java.io.*;


public class CommandLine extends KeyValueObject {
	private void commandLine(String[] args) throws Exception {
    	if (args[0].equals("init")){
    		new Repository().createRepository(args[1]);
    		System.out.println("新建成功");
         }
    	
    	else if (args[0].equals("commit")) {
    		File dir=new File(args[1]);
    		new Commit(dir).copyFile();
            System.out.println("Commit成功");
        }
    	
        else if (args[0].equals("log")){

        	
        	
        }
    	
        else if (args[0].equals("reset")){
        	new Reset().reset_hard();
        	System.out.println("回滚成功");
        }
    	
        else if (args[0].equals("branch")){
        	new Branch(args[1]);
        	System.out.println("新建分支成功");
        }
    	
        else if (args[0].equals("checkout")){
        	new Branch().switchBranch(args[1]);
        	System.out.println("切换分支成功");
        }
    	
        else{
            System.out.println("输入指令错误");
        }
    }
}
