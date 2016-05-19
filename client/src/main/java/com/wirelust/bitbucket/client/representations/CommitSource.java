package com.wirelust.bitbucket.client.representations;

import java.io.Serializable;

/**
 * Date: 08-Oct-2015
 *
 * @author T. Curran
 */
public class CommitSource implements Serializable {

	private static final long serialVersionUID = 6410285719035915746L;

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
