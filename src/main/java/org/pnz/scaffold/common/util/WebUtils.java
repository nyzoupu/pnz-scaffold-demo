package com.shangshu.datacenter.util.web.protocol;

import com.alibaba.common.lang.StringUtil;
import com.alibaba.fastjson.JSONObject;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

/**
 * Created by guifeng.wgf on 2017-1-24.
 */
public class WebUtils {
    /**
     * http post map入参，不带文件
     * @param url
     * @param params
     * @param charset
     * @param connectTimeout
     * @param readTimeout
     * @return
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> params, String charset, int connectTimeout,
                                int readTimeout) throws IOException {
        String ctype = "application/x-www-form-urlencoded;charset=" + charset;
        String query = buildQuery(params, true, charset);
        byte[] content = {};
        if (query != null) {
            content = query.getBytes(charset);
        }
        return doPost(url, ctype, content, connectTimeout, readTimeout);
    }

    /**
     * http post map入参，不带文件，应用系统个性化定制
     * @param requestHeaderMap   acceptEncoding&contentType[]&
     * @param url
     * @param method   POST/GET/DELETE
     * @param params
     * @param charset
     * @param connectTimeout
     * @param readTimeout
     * @return
     * @throws IOException
     */
    public static String doRequest(Map<String, Object> requestHeaderMap, String url, String method,
                                   Map<String, Object> params, String charset, int connectTimeout,
                                   int readTimeout) throws  Exception{

        // 将contentTypeList转为String，this is reponse  setting
        List<String> contentTypes = (List<String>)requestHeaderMap.get("contentType");
        String contentString = null;
        if (null != contentTypes) {
            contentString = contentTypes.toString().replace("@", ";");
        }
        String bodyString = buildApiQuery(params, false, charset);
        if (StringUtil.equalsIgnoreCase(method, "GET")) {
            // GET方法，将查询参数挂在在URL中
            if (StringUtil.isNotBlank(bodyString)) {
                // url 只需提供访问域名即可
                url = url + "?" +  bodyString;
                bodyString = null;
            }
        }
        // POST , do nothing
        byte[] content = {};
        if (bodyString != null) {
            content = bodyString.getBytes(charset);
        }
        return _doRequest(url, contentString, content, connectTimeout, readTimeout, method, charset);
    }



    /**
     * http post 字节数据流，请求结果gzip压缩
     * @param url   请求url
     * @param ctype 请求头描述
     * @param content   请求内容
     * @param connectTimeout   连接超时时间(ms)
     * @param readTimeout   socket读超时时间(ms)
     * @return
     * @throws IOException
     */
    public static String _doRequest(String url, String ctype, byte[] content, int connectTimeout, int readTimeout, String method, String charset)
            throws IOException {
        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;
        try {
            try {
                conn = getRestConnection(new URL(url), method, ctype);
                conn.setConnectTimeout(connectTimeout);
                conn.setReadTimeout(readTimeout);
            } catch (IOException e) {
                //Map<String, String> map = getParamsFromUrl(url);
                throw e;
            }
            try {
                if (StringUtil.equalsIgnoreCase("GET", method)) {
                    InputStream inputStream = conn.getInputStream();
                    String inputData = convertStreamToString(inputStream, charset);
                    if (StringUtil.equalsIgnoreCase("gzip", conn.getContentEncoding())) {
                        // 没有编码，无需解码
                        rsp = uncompress(inputData);
                    } else {
                        // 没有编码，无需解码
                        rsp = inputData;
                    }
                } else {
                    out = conn.getOutputStream();
                    out.write(content);
                    rsp  = getResponseAsString(conn);

                }
                System.out.println(rsp);
            } catch (IOException e) {
                // Map<String, String> map = getParamsFromUrl(url);
                throw e;
            }
        } finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rsp;
    }

    private static String convertStreamToString(InputStream is, String encoding)
            throws IOException {
            /*
             * To convert the InputStream to String we use the
             * Reader.read(char[] buffer) method. We iterate until the
             * Reader return -1 which means there's no more data to
             * read. We use the StringWriter class to produce the string.
             */
        if (is != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try
            {
                Reader reader = new BufferedReader(
                        new InputStreamReader(is, encoding));
                int n;
                while ((n = reader.read(buffer)) != -1)
                {
                    writer.write(buffer, 0, n);
                }
            }
            finally
            {
                is.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }


    // gzip解压缩
    public static String uncompress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(str
                .getBytes("ISO-8859-1"));
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer))>= 0) {
            out.write(buffer, 0, n);
        }
        // toString()使用平台默认编码，也可以显式的指定如toString(&quot;GBK&quot;)
        return out.toString();
    }


    /**
     * http post 字节数据流
     * @param url   请求url
     * @param ctype 请求头描述
     * @param content   请求内容
     * @param connectTimeout   连接超时时间(ms)
     * @param readTimeout   socket读超时时间(ms)
     * @return
     * @throws IOException
     */
    public static String doPost(String url, String ctype, byte[] content, int connectTimeout, int readTimeout)
            throws IOException {
        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;
        try {
            try {
                conn = getConnection(new URL(url), "POST", ctype);
                conn.setConnectTimeout(connectTimeout);
                conn.setReadTimeout(readTimeout);
            } catch (IOException e) {
                //Map<String, String> map = getParamsFromUrl(url);
                throw e;
            }
            try {
                out = conn.getOutputStream();
                out.write(content);
                rsp = getResponseAsString(conn);
            } catch (IOException e) {
                // Map<String, String> map = getParamsFromUrl(url);
                throw e;
            }

        } finally {
            if (out != null) {
                out.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rsp;
    }

    private static String buildQuery(Map<String, String> params, boolean needEncode, String charset)
            throws IOException {
        if (params == null || params.isEmpty()) {
            return null;
        }

        StringBuilder query = new StringBuilder();
        Set<Map.Entry<String, String>> entries = params.entrySet();
        boolean hasParam = false;

        for (Map.Entry<String, String> entry : entries) {
            String name = entry.getKey();
            String value = entry.getValue();
            if (areNotEmpty(name, value)) {
                if (hasParam) {
                    query.append("&");
                } else {
                    hasParam = true;
                }
                if (needEncode) {
                    value = URLEncoder.encode(value, charset);
                }
                query.append(name).append("=").append(value);
            }
        }
        return query.toString();
    }

    public static boolean areNotEmpty(String... values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (String value : values) {
                result &= !isEmpty(value);
            }
        }
        return result;
    }

    private static String buildApiQuery(Map<String, Object> params, boolean needEncode, String charset)
            throws IOException {
        if (params == null || params.isEmpty()) {
            return null;
        }
        StringBuilder query = new StringBuilder();
        Set<Map.Entry<String, Object>> entries = params.entrySet();
        boolean hasParam = false;

        for (Map.Entry<String, Object> entry : entries) {
            String name = entry.getKey();
            Object value = entry.getValue();
            if (!isEmpty(name)) {
                if (hasParam) {
                    query.append("&");
                } else {
                    hasParam = true;
                }
                if (value instanceof String) {
                    // String 类型直接处理
                    // 强制URL编码，所有传输必须URL编码
                    value = URLEncoder.encode((String)value, charset);
                    query.append(name).append("=").append(value);
                } else {
                    // 非String, JSON String
                    String otherString = JSONObject.toJSONString(value);
                    value = URLEncoder.encode((String)otherString, charset);
                    query.append(name).append("=").append(value);
                }
            }
        }
        return query.toString();
    }


    @SuppressWarnings("Duplicates")
    private static HttpURLConnection getRestConnection(URL url, String method, String ctype) throws IOException {
        HttpURLConnection conn = getHttpURLConnection(url);
        //conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        // 返回支持gzip
        conn.setRequestProperty("Accept-Encoding", "gzip");

        // 支持所有的requese返回
        conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html,*/*");
        conn.setRequestProperty("User-Agent", "shangshu-api-rest");
        //conn.setRequestProperty("Content-Type", ctype);
        return conn;
    }

    @SuppressWarnings("Duplicates")
    private static HttpURLConnection getConnection(URL url, String method, String ctype) throws IOException {
        HttpURLConnection conn = getHttpURLConnection(url);
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html");
        conn.setRequestProperty("User-Agent", "aop-sdk-java");
        conn.setRequestProperty("Content-Type", ctype);
        return conn;
    }

    private static HttpURLConnection getHttpURLConnection(URL url) throws IOException {
        HttpURLConnection conn = null;
        if ("https".equals(url.getProtocol())) {
            SSLContext ctx = null;
            try {
                ctx = SSLContext.getInstance("TLSv1.2");
                ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() },
                        new SecureRandom());
            } catch (Exception e) {
                throw new IOException(e);
            }
            HttpsURLConnection connHttps = (HttpsURLConnection) url.openConnection();
            connHttps.setSSLSocketFactory(ctx.getSocketFactory());
            connHttps.setHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;//默认认证不通过，进行证书校验。
                }
            });
            conn = connHttps;
        } else {
            conn = (HttpURLConnection) url.openConnection();
        }
        return conn;
    }

    /**
     * 从URL中提取所有的参数。
     *
     * @param query URL地址
     * @return 参数映射
     */
    public static Map<String, String> splitUrlQuery(String query) {
        Map<String, String> result = new HashMap<String, String>();

        String[] pairs = query.split("&");
        if (pairs != null && pairs.length > 0) {
            for (String pair : pairs) {
                String[] param = pair.split("=", 2);
                if (param != null && param.length == 2) {
                    result.put(param[0], param[1]);
                }
            }
        }
        return result;
    }

    private static byte[] getTextEntry(String fieldName, String fieldValue, String charset)
            throws IOException {
        StringBuilder entry = new StringBuilder();
        entry.append("Content-Disposition:form-data;name=\"");
        entry.append(fieldName);
        entry.append("\"\r\nContent-Type:text/plain\r\n\r\n");
        entry.append(fieldValue);
        return entry.toString().getBytes(charset);
    }

    private static byte[] getFileEntry(String fieldName, String fileName, String mimeType, String charset)
            throws IOException {
        StringBuilder entry = new StringBuilder();
        entry.append("Content-Disposition:form-data;name=\"");
        entry.append(fieldName);
        entry.append("\";filename=\"");
        entry.append(fileName);
        entry.append("\"\r\nContent-Type:");
        entry.append(mimeType);
        entry.append("\r\n\r\n");
        return entry.toString().getBytes(charset);
    }

    protected static String getResponseAsString(HttpURLConnection conn) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        InputStream es = conn.getErrorStream();
        if (es == null) {
            return getStreamAsString(conn.getInputStream(), charset);
        } else {
            String msg = getStreamAsString(es, charset);
            if (isEmpty(msg)) {
                throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
            } else {
                throw new IOException(msg);
            }
        }
    }

    private static String getResponseCharset(String ctype) {
        String charset = "UTF-8";
        if (!isEmpty(ctype)) {
            String[] params = ctype.split(";");
            for (String param : params) {
                param = param.trim();
                if (param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if (pair.length == 2) {
                        if (!isEmpty(pair[1])) {
                            charset = pair[1].trim();
                        }
                    }
                    break;
                }
            }
        }
        return charset;
    }

    /**
     * 检查指定的字符串是否为空。
     * <ul>
     * <li> isEmpty(null) = true</li>
     * <li> isEmpty("") = true</li>
     * <li> isEmpty("   ") = true</li>
     * <li> isEmpty("abc") = false</li>
     * </ul>
     *
     * @param value 待检查的字符串
     * @return true/false
     */
    private static boolean isEmpty(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(value.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    private static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
            StringWriter writer = new StringWriter();

            char[] chars = new char[256];
            int count = 0;
            while ((count = reader.read(chars)) > 0) {
                writer.write(chars, 0, count);
            }
            return writer.toString();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    private static class DefaultTrustManager implements X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }

}
