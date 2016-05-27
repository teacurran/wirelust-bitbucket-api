package com.wirelust.bitbucket.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

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

	public static final String BITBUCKET_AUTH_URL = "https://bitbucket.org/site/oauth2/authorize";

	public static final String BITBUCKET_TOKEN_URL = "https://bitbucket.org/site/oauth2/access_token";

	public static final String BITBUCKET_ENDPOINT_URL = "https://bitbucket.org/api";

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);

	private static final long serialVersionUID = 2378080314904271776L;

	private static final String ENV_FILE_NAME = "app.bitbucket.env";

	Properties configuredProperties = new Properties();

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

		if (!loadConfigFromFileSystem(configFileName)) {
			loadConfigFromResources(configFileName);
		}

		LOGGER.info("env properties loaded:{}", configuredProperties.toString());
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

	private void loadConfigFromResources(final String fileName) {
		try (InputStream configInputStream = this.getClass().getResourceAsStream("/environments/" + fileName)) {
			loadConfiguration(configInputStream);
		} catch (IOException e) {
			LOGGER.error("config resource file not found", e);
		}
	}

	private boolean loadConfigFromFileSystem(final String fileName) {
		File propertyFile = new File(fileName);

		try (InputStream configInputStream = new FileInputStream(propertyFile)) {
			loadConfiguration(configInputStream);
		} catch (IOException e) {
			LOGGER.error("config file not found", e);
		}
		return true;
	}

	private void loadConfiguration(final InputStream inputStream) throws IOException {
		configuredProperties.load(inputStream);

		try {
			for (Map.Entry<Object, Object> e : configuredProperties.entrySet()) {
				BeanUtils.setProperty(this, (String) e.getKey(), e.getValue());
			}
		} catch (InvocationTargetException | IllegalAccessException e) {
			LOGGER.error("error setting property value", e);
		}
	}
}
