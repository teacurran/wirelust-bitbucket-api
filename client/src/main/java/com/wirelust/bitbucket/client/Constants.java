package com.wirelust.bitbucket.client;

/**
 * Date: 07-Oct-2015
 *
 * @author T. Curran
 */
public class Constants {
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX";

	// This is curious, the examples from bitbucket have 2 different date formats
	// I don't yet know if this an error in their docs or their API.
	public static final String DATE_TIME_FORMAT_2 = "yyyy-MM-dd'T'HH:mm:ssXXX";

	/*
	v1 date format:
	2015-12-04 20:55:28+00:00
	 */
	public static final String DATE_TIME_FORMAT_V1 = "yyyy-MM-dd HH:mm:ssXXX";

	private Constants() {
		// class is used for static properties only
	}
}
