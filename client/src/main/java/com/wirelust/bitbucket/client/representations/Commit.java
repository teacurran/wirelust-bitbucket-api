package com.wirelust.bitbucket.client.representations;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wirelust.bitbucket.client.Constants;

/**
 * Date: 08-Oct-2015
 *
 * @author T. Curran
 */
public class Commit implements Serializable {

	private static final long serialVersionUID = 6410285719035915746L;

	String hash;
	private Map<String, List<Link>> links;
	List<Commit> parents;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT_2)
	Date date;
	String message;
	CommitAuthor author;
	Repository repository;
	private List<Participant> participants;

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Map<String, List<Link>> getLinks() {
		return links;
	}

	public void setLinks(Map<String, List<Link>> links) {
		this.links = links;
	}

	public List<Commit> getParents() {
		return parents;
	}

	public void setParents(List<Commit> parents) {
		this.parents = parents;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public CommitAuthor getAuthor() {
		return author;
	}

	public void setAuthor(CommitAuthor author) {
		this.author = author;
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public List<Participant> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Participant> participants) {
		this.participants = participants;
	}
}
