package operationobjects;

import keyvalueobjects.Commit;

public class Merge extends GitOperation {
	protected Branch newbranch;
	public Merge(Repository rep, Branch branch, Commit commit,Branch newbranch) {
		super(rep, branch, commit);
		this.newbranch=newbranch;
	}
	private String checkcommit() {
		
		
		return "";
	}
}
