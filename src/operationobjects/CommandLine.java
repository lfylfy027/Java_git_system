package operationobjects;

import java.util.*;
import java.io.*;
import keyvalueobjects.Blob;
import keyvalueobjects.Commit;
import keyvalueobjects.KeyValue_Storage;
import keyvalueobjects.KeyValueObject;
import keyvalueobjects.SHA1Checksum;
import keyvalueobjects.Tree;


public class CommandLine extends KeyValueObject {
	private void commandLine(String[] args) throws Exception {
    	if (args[0].equals("init")){
    		new Operation().newRep(args[1]);
    		
    		System.out.println("新建仓库成功");
         }
    	
    	else if (args[0].equals("commit")) {
    		new Operation().newCommit(args[1]);
    		new Operation().docommit();
    		
            System.out.println("Commit成功");
        }
    	
        else if (args[0].equals("log")){
        	new Operation().showBranches();
        	
        }
    	
        else if (args[0].equals("reset")){
        	if (args[1].equals("soft")){
            
        		
            }
        	else if (args[1].equals("hard")){
            
        		
            }
        	else {
        		
        		
        	}
        }
    	
        else if (args[0].equals("branch")){
        	
        	
        	System.out.println("新建分支成功");
        }
    	
        else if (args[0].equals("checkout")){
        	new Operation().alterBranch(args[1]);
        	
        	System.out.println("切换分支成功");
        }
    	
        else{
            System.out.println("输入指令错误");
        }
    }
	
	public static void main(String[] args) throws Exception {

		
	}
}
