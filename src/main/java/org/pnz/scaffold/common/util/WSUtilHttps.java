package org.pnz.scaffold.common.util;

import java.net.URL;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.codehaus.xfire.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangGB
 *
 */
public class WSUtilHttps {/*
	private static final Logger LOGGER = LoggerFactory.getLogger(WSUtilHttps.class);

	@SuppressWarnings("static-access")
	public static String process(String method, String xmlStr, String urlStr, String urlWithOutWSDL, String tisVerson)
			throws Exception {

		Client client = null;
		HttpsURLConnection httpsConnection = null;
		URL inUrl = new URL(urlStr);
		SSLContext cxt = SSLContext.getInstance(tisVerson, "SunJSSE");
		cxt.init(new KeyManager[0], null, new SecureRandom());
		SSLContext.setDefault(cxt);
		SSLSocketFactory sslsocketfactory = cxt.getSocketFactory();
		httpsConnection = (HttpsURLConnection) inUrl.openConnection();
		httpsConnection.setDefaultSSLSocketFactory(sslsocketfactory);
		httpsConnection.connect();
		client = new Client(httpsConnection.getInputStream(), null);
		client.setUrl(urlWithOutWSDL);
		LoggerUtil.info(LOGGER, "SSLContext %s HttpsURLConnection %s Client %s", cxt, httpsConnection, client);
		Object[] objects = client.invoke(method, new Object[] { xmlStr });
		client.close();
		return (String) objects[0];
	}
*/}
