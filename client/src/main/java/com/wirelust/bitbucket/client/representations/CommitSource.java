package com.wirelust.bitbucket.client.representations;

/**
 * Date: 08-Oct-2015
 *
 * @author T. Curran
 */
public class CommitSource {

	Commit commit;
	Repository repository;
	Branch branch;

	public Commit getCommit() {
		return commit;
	}

	public void setCommit(Commit commit) {
		this.commit = commit;
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}
}
