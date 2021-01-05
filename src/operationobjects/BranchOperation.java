package operationobjects;

import keyvalueobjects.Commit;

public class BranchOperation extends GitOperation {

	public BranchOperation(Repository rep, Branch branch, Commit commit) {
		super(rep, branch, commit);
	}
	public void createBranch(String name) {
		
	}
}
