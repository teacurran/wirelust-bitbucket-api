package com.wirelust.bitbucket.example;

import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Date: 30-Sep-2015
 *
 * @author T. Curran
 */
@ApplicationScoped
public class ApplicationConfig implements Serializable {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);

	private static final long serialVersionUID = 1L;

	private static final String ENV_FILE_NAME = "app.bitbucket.env";

	private String bitbucketOauthKey;
	private String bitbucketSecret;
	private String bitbucketOauthRedirectUrl;

	@PostConstruct
	public void init() {
		String configFileName = System.getProperty(ENV_FILE_NAME);
		LOGGER.info("{}={}", ENV_FILE_NAME, configFileName);

		if (configFileName == null) {
			LOGGER.warn("{} was not specified. using 'dev'", ENV_FILE_NAME);
			configFileName = "dev.properties";
		}

		InputStream configInputStream = null;
		File propertyFile = new File(configFileName);

		// Attempt to load the config from a file
		if (propertyFile.exists() && propertyFile.canRead()) {
			try {
				configInputStream = new FileInputStream(propertyFile);
			} catch (FileNotFoundException e) {
				// impossible to get here
				LOGGER.error("config file not found", e);
			}
		} else {
			configInputStream = this.getClass().getResourceAsStream("/environments/" + configFileName);
		}

		if (configInputStream == null) {
			throw new RuntimeException("Error initializing config, unable to load property file:" + configFileName);
		}

		Properties props = new Properties();
		try {
			props.load(configInputStream);
			try {
				for (Map.Entry<Object, Object> e : props.entrySet()) {
					BeanUtils.setProperty(this, (String) e.getKey(), e.getValue());
				}
			} catch (Exception e) {
				throw new RuntimeException("Error initializing from properties: " + props);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				configInputStream.close();
			} catch (IOException ioe) {
				// nothing we can really do here
				LOGGER.error("error closing input stream", ioe);
			}
		}
		LOGGER.info("env properties loaded:{}", props.toString());
	}

	public String getBitbucketOauthKey() {
		return bitbucketOauthKey;
	}

	public void setBitbucketOauthKey(String bitbucketOauthKey) {
		this.bitbucketOauthKey = bitbucketOauthKey;
	}

	public String getBitbucketSecret() {
		return bitbucketSecret;
	}

	public void setBitbucketSecret(String bitbucketSecret) {
		this.bitbucketSecret = bitbucketSecret;
	}

	public String getBitbucketOauthRedirectUrl() {
		return bitbucketOauthRedirectUrl;
	}

	public void setBitbucketOauthRedirectUrl(String bitbucketOauthRedirectUrl) {
		this.bitbucketOauthRedirectUrl = bitbucketOauthRedirectUrl;
	}
}
