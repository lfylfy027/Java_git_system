package operationobjects;

import java.util.*;


public class CommandLine {
	public static void main(String[] args) throws Exception {
		while (true) {
			Scanner input = new Scanner(System.in);
	        String command = input.nextLine();
	        
	        String[] commandLine = command.split(" ");
	        
	    	if (commandLine[0].equals("init")){
	    		new Operation().newRep(commandLine[1]);
	    		System.out.println("新建仓库成功");

	    		
	         }
	    	
	    	else if (commandLine[0].equals("commit")) {
	    		new Operation().newCommit(commandLine[1]);
	    		
	            System.out.println("Commit成功");
	        }
	    	
	        else if (commandLine[0].equals("log")){
	        	new Operation().showBranches();
	        	
	        }
	    	
	        else if (commandLine[0].equals("reset")){
	        	if (commandLine[1].equals("soft")){
	            
	        		
	            }
	        	else if (commandLine[1].equals("hard")){
	            
	        		
	            }
	        	else {
	        		
	        		
	        	}
	        }
	    	
	        else if (commandLine[0].equals("branch")){
	        	new Operation().newBranch(commandLine[1]);
	        	
	        	System.out.println("新建分支成功");
	        }
	    	
	        else if (commandLine[0].equals("checkout")){
	        	new Operation().alterBranch(commandLine[1]);
	        	
	        	System.out.println("切换分支成功");
	        }
	    	
	        else{
	            System.out.println("输入指令错误");
	        }
	    }
	}
}
