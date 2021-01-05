package operationobjects;

import java.util.*;


public class CommandLine {
	public static void main(String[] args) throws Exception {
		Operation go = new Operation();
		while (true) {
			Scanner input = new Scanner(System.in);
			String command = input.nextLine();
			String[] commandLine = command.split(" ");

			if (commandLine[0].equals("gitto") && commandLine[1].equals("new")) {
				go.newRep(commandLine[2]);
				//System.out.println("新建仓库成功");
			}
			
			else if (commandLine[0].equals("gitto") && commandLine[1].equals("read")) {
				go.readRep(commandLine[2]);
				//System.out.println("读取仓库成功");
			}

			else if (commandLine[0].equals("gitto") && commandLine[1].equals("commit")) {
				go.newCommit();
				//System.out.println("Commit成功");
			}

			else if (commandLine[0].equals("gitto") && commandLine[1].equals("branch")) {
				go.newBranch(commandLine[2]);
				//System.out.println("新建分支成功");
			}

			else if (commandLine[0].equals("gitto") && commandLine[1].equals("showBranches")) {
				//System.out.println("当前的分支有：");
				go.showBranches();
			}
			
			else if (commandLine[0].equals("gitto") && commandLine[1].equals("checkout")) {
				go.alterBranch(commandLine[2]);
				//System.out.println("切换分支成功");
			}

			else if (commandLine[0].equals("gitto") && commandLine[1].equals("log")) {
				//System.out.println("Commit记录：");
				go.showcommits();

			}

			else if (commandLine[0].equals("gitto") && commandLine[1].equals("reset")) {
				go.reset_hard(commandLine[2]);
				//System.out.println("回滚成功");
			}

			else if (commandLine[0].equals("gitto") && commandLine[1].equals("merge")) {
				go.mergebranch(commandLine[2]);
				//System.out.println("合并成功");
			}
			
			else {
				System.out.println("输入指令错误");
			}
		}
	}
}