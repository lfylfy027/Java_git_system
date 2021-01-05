package operationobjects;

import keyvalueobjects.Commit;

public abstract class GitOperation {
	protected Repository rep;
	protected Commit commit;
	protected Branch branch;
	protected String gitDir;
	protected String dir;
	
	
	public  GitOperation(Repository rep,Branch branch,Commit commit) {
		this.rep=rep;
		this.commit=commit;
		this.branch=branch;
		gitDir=rep.getgitDir();
		dir=rep.getlocation();
	}
}
