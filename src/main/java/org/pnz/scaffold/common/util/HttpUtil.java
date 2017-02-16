/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.shangshu.datacenter.util.web.protocol;

import java.nio.charset.CodingErrorAction;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Consts;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.common.logging.Logger;
import com.alibaba.common.logging.LoggerFactory;
import com.shangshu.datacenter.util.exception.HttpRequestException;

/**
 * HttpUtil  适用于TLSv1，1.1，1.2
 * @author wb-zp195009
 * @version $Id: HttpUtil.java, v 0.1 2016年12月27日 上午9:57:32 wb-zp195009 Exp $
 */
public class HttpUtil {
    
    private final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private static Registry<ConnectionSocketFactory> socketFactoryRegistry = null;
    private static CloseableHttpClient httpclient = null;
    static {
        try {
            System.setProperty("jsse.enableSNIExtension", "false");
            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                }
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                }
            } };
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            socketFactoryRegistry = RegistryBuilder
                .<ConnectionSocketFactory> create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslContext,
                        new String[] { "TLSv1", "TLSv1.1", "TLSv1.2" }, null,
                        SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)).build();
            
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
                socketFactoryRegistry);
            // Create socket configuration,这里的超时如果有自定义超时时间，则会覆盖该超时设置
            SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).setSoTimeout(2500).build();
            connManager.setDefaultSocketConfig(socketConfig);
            // Create message constraints
            MessageConstraints messageConstraints = MessageConstraints.custom().setMaxHeaderCount(200)
                .setMaxLineLength(2000).build();
            // Create connection configuration
            ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8)
                .setMessageConstraints(messageConstraints).build();
            connManager.setDefaultConnectionConfig(connectionConfig);
            connManager.setMaxTotal(1000);
            connManager.setDefaultMaxPerRoute(200);
            httpclient = HttpClients.custom().setConnectionManager(connManager).build();
        } catch (KeyManagementException e) {
            logger.error("http client KeyManagementException", e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("http client NoSuchAlgorithmException", e);
        }
    }
    

    /**
     *<p>http(s) Map参数get请求 (自定义超时)
     *
     * @param url http(s)请求url
     * @param inputParams
     * @param connectTimeOut http请求服务器连接超时
     * @param readTimeOut http请求服务器socket超时
     * @return 请求返回的String
     * @throws HttpRequestException http请求异常
     * @throws ClientProtocolException http客户端协议异常
     * @throws IOException 网络IO异常
     */
    public static String get(String url, Map<String, String> inputParams, int connectTimeOut, int readTimeOut)
                                                                                                              throws HttpRequestException,
                                                                                                              ClientProtocolException,
                                                                                                              IOException {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        Set<String> set = inputParams.keySet();
        //遍历inputParams得到BasicNameValuePair
        for(String s:set){
            params.add(new BasicNameValuePair(s,inputParams.get(s)));
        }
        //格式化并对url进行url编码
        url = url + "?" + URLEncodedUtils.format(params,HTTP.UTF_8);
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(20000)
            .setConnectTimeout(connectTimeOut).setSocketTimeout(readTimeOut).build();
        httpGet.setConfig(requestConfig);
        //执行get请求
        HttpResponse httpResponse = httpclient.execute(httpGet);
        return responseConvert(httpResponse);
    }

    /**
     * 待超时时间设置的http(s) post请求
     * @param url
     * @param parms
     * @param timeOut
     * @return
     * @throws HttpRequestException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String post(String url, Map<String, String> parms, int connectTimeOut, int readTimeOut)
                                                                                                         throws HttpRequestException,
                                                                                                         ClientProtocolException,
                                                                                                         IOException {
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(20000)
                .setConnectTimeout(connectTimeOut).setSocketTimeout(readTimeOut).build();
        httpPost.setConfig(requestConfig);

        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        for (String k : parms.keySet()) {
            formparams.add(new BasicNameValuePair(k, parms.get(k)));
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        httpPost.setEntity(entity);
        HttpResponse httpResponse = httpclient.execute(httpPost);
        return responseConvert(httpResponse);
    }
    
    /**
     * 带超时时间设置的http(s) postWithHeader请求
     * @param url
     * @param parms
     * @param header
     * @param connectTimeOut
     * @param readTimeOut
     * @return
     * @throws HttpRequestException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String postWithHeader(String url, Map<String, String> parms, Map<String, String> header,
                                        int connectTimeOut, int readTimeOut) throws HttpRequestException,
                                                                            ClientProtocolException, IOException {
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(20000)
            .setConnectTimeout(connectTimeOut).setSocketTimeout(readTimeOut).build();
        httpPost.setConfig(requestConfig);

        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        for (String k : parms.keySet()) {
            formparams.add(new BasicNameValuePair(k, parms.get(k)));
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        httpPost.setEntity(entity);
        for (Map.Entry<String, String> entry : header.entrySet()) {
            httpPost.addHeader(entry.getKey(), entry.getValue());
        }
        HttpResponse httpResponse = httpclient.execute(httpPost);
        return responseConvert(httpResponse);
    }

    /**
     * <p>http请求结果转化
     * 
     * @param httpResponse  httpClient执行体
     * @return  请求后返回的结果String
     * @throws HttpRequestException http请求异常
     * @throws ClientProtocolException http客户端协议异常
     * @throws IOException 网络IO异常
     */
    private static String responseConvert(HttpResponse httpResponse) throws HttpRequestException,
                                                                    ClientProtocolException, IOException {
        //响应状态
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) //非正常状态
        {
            throw new HttpRequestException(statusCode);
        } else {
            //获取响应消息实体  
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                byte[] content = EntityUtils.toByteArray(entity);
                ContentType contentType = ContentType.getOrDefault(entity);
                Charset charset = contentType.getCharset();
                if (charset == null) {
                    charset = HTTP.DEF_CONTENT_CHARSET;
                }
                String xml = new String(content, charset.name());
                return xml;
            }
            if (logger.isInfoEnabled()) {
                logger.info("httpResponse get entity is empty. statusCode=" + statusCode);
            }
        }
        return null;
    }
    public static void main(String[] args) throws ClientProtocolException, HttpRequestException, IOException {
        String url = "https://112.124.107.12/appname/index.htm?keyword=万家";
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("key1", "value1");
        System.out.println(WebUtils.doPost(url, paramsMap, "utf-8", 2500, 2500));
//        System.out.println(post(url, paramsMap, 2500, 2500));
    }
}