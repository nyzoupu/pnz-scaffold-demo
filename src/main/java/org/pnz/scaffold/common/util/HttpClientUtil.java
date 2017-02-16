package com.shangshu.datacenter.util.web.protocol;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.common.logging.Logger;
import com.alibaba.common.logging.LoggerFactory;
import com.shangshu.datacenter.util.exception.HttpRequestException;

/**
 * http client 工具类 
 * 支持https
 * @author wb-chenxin.g
 * @version $Id: HttpClientUtil.java, v 0.1 2015年12月4日 下午4:06:33 wb-chenxin.g Exp $
 */
public class HttpClientUtil {
    private final static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    private static CloseableHttpClient httpclient = null;
    static {
        try {
            SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(null, new TrustStrategy() {
                    //信任所有
                    public boolean isTrusted(X509Certificate[] chain,
                                             String authType) throws CertificateException {
                        return true;
                    }
                }).build();

            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                .<ConnectionSocketFactory> create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslContext)).build();

            PoolingHttpClientConnectionManager connManager = null;
            connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

            // Create socket configuration
            //soclet的超时时延设置为2.5s.
            SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).setSoTimeout(5000)
                .build();            
            connManager.setDefaultSocketConfig(socketConfig);
            // Create message constraints
            MessageConstraints messageConstraints = MessageConstraints.custom()
                .setMaxHeaderCount(200).setMaxLineLength(2000).build();
            // Create connection configuration
            ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8)
                .setMessageConstraints(messageConstraints).build();
            connManager.setDefaultConnectionConfig(connectionConfig);
            connManager.setMaxTotal(200);
            connManager.setDefaultMaxPerRoute(20);
            httpclient = HttpClients.custom().setConnectionManager(connManager).build();
        } catch (KeyManagementException e) {
            logger.error("http client KeyManagementException", e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("http client NoSuchAlgorithmException", e);
        } catch (KeyStoreException e) {
            logger.error("http client  KeyStoreException", e);
        }
    }

    /**
     * @param url
     * @return
     * @throws HttpRequestException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String get(String url) throws HttpRequestException, ClientProtocolException,
                                         IOException {
        HttpGet httpGet = new HttpGet(url);
        //执行get请求
        HttpResponse httpResponse = httpclient.execute(httpGet);
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
        return "";
    }

    /**
     * @param url
     * @param inputParams
     * @return
     * @throws HttpRequestException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String get(String url,Map<String, String> inputParams) throws HttpRequestException, ClientProtocolException,
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
        //执行get请求
        HttpResponse httpResponse = httpclient.execute(httpGet);
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
        return "";
    }

    /**
     * @param url
     * @param parms
     * @return
     * @throws HttpRequestException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String post(String url, Map<String, String> parms) throws HttpRequestException,
                                                                     ClientProtocolException,
                                                                     IOException {
        HttpPost httpPost = new HttpPost(url);
        // 创建参数队列 
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        Set<String> key = parms.keySet();
        for (String k : key) {
            formparams.add(new BasicNameValuePair(k, parms.get(k)));
        }
        UrlEncodedFormEntity entity;
        entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        httpPost.setEntity(entity);
        //post请求
        HttpResponse httpResponse = httpclient.execute(httpPost);
        //响应状态
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) //非正常状态
        {
            throw new HttpRequestException(statusCode);
        } else {
            //获取响应消息实体
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                byte[] content = EntityUtils.toByteArray(httpEntity);
                ContentType contentType = ContentType.getOrDefault(httpEntity);
                Charset charset = contentType.getCharset();
                if (charset == null) {
                    charset = HTTP.DEF_CONTENT_CHARSET;
                }
                String response = new String(content, charset.name());
                return response;
            }
            if (logger.isInfoEnabled()) {
                logger.info("httpResponse get entity is empty. statusCode=" + statusCode);
            }
        }
        return "";
    }

    /**
     * @param url
     * @param stringEntity
     * @return
     * @throws HttpRequestException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String post(String url, String stringEntity) throws HttpRequestException,
                                                               ClientProtocolException,
                                                               IOException {

        HttpPost httpPost = new HttpPost(url);
        HttpEntity entity;
        entity = new StringEntity(stringEntity, Consts.UTF_8);
        httpPost.setEntity(entity);
        //post请求
        HttpResponse httpResponse = httpclient.execute(httpPost);
        //响应状态
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) //非正常状态
        {
            throw new HttpRequestException(statusCode);
        } else {
            //获取响应消息实体
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                byte[] content = EntityUtils.toByteArray(httpEntity);
                ContentType contentType = ContentType.getOrDefault(httpEntity);
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
        return "";
    }
}