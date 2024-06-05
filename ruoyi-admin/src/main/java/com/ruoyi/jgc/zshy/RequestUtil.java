package com.ruoyi.jgc.zshy;

import cn.hutool.core.map.MapUtil;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

/**
 * @program: hnbd-atmospheric-inversion-project
 * @description:
 * @author:
 * @create: 2024-05-30 11:48
 */
public class RequestUtil {

    private static final Logger log = LoggerFactory.getLogger(RequestUtil.class);

    public static void sendGet(String url, Map<String, String> headerMap){
        sendGet(url, headerMap, false, false, false);
    }

    public static void sendGet(String url, Map<String, String> headerMap, boolean proxy, boolean ssl, boolean withCert){
        sendReq(url, "get", null, headerMap, proxy, ssl, withCert);
    }

    public static void sendPost(String url, Map<String, String> bodyMap, Map<String, String> headerMap){
        sendPost(url, bodyMap, headerMap, false, false, false);
    }

    public static void sendPost(String url, Map<String, String> bodyMap, Map<String, String> headerMap, boolean proxy, boolean ssl, boolean withCert){
        sendReq(url, "post", bodyMap, headerMap, proxy,ssl, withCert);
    }

    public static void sendReq(String url, String method, Map<String, String> bodyMap, Map<String, String> headerMap , boolean proxy, boolean ssl, boolean withCert) {
            Request request = createReq(url, method, createReqBody(bodyMap), headerMap);
            resp(createClient(proxy, ssl,  withCert), request);
    }


    private static OkHttpClient createClient(boolean proxyFlag, boolean sslFlag, boolean withCert) {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8999));
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (proxyFlag) {
            builder.proxy(proxy);
        }
        if (sslFlag) {
            builder.sslSocketFactory(getSocketFactory(withCert), (X509TrustManager) getTrustManager(withCert)[0]);
        }
        OkHttpClient client = builder
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                // 忽略SSL证书验证（不推荐在生产环境使用）
//                .sslSocketFactory(getUnSafeSslSocketFactory(), (X509TrustManager) getUnSafeTrustManager()[0])
                .hostnameVerifier((hostname, session) -> true) // 忽略主机名验证
                .build();

        return client;
    }

    private static RequestBody createReqBody(Map<String, String> map) {
        // 构造请求体
//        RequestBody requestBody = new FormBody.Builder()
//                .add("learnPlanId", "d30ab951-f0ae-4ca8-928c-8fe375db71a9")
//                .add("parentId", "")
//                .add("learnState", "2")
//                .add("top", "0")
//                .add("courseType", "0")
//                .build();

        FormBody.Builder builder = new FormBody.Builder();
        if (MapUtil.isNotEmpty(map)) {
            map.forEach((k,v)-> builder.add(k,v));
        }

        return builder.build();
    }

    private static Request createReq(String url, String method, RequestBody requestBody, Map<String, String> headerMap) {
        // 构造请求
//        Request request = new Request.Builder()
//                .url("https://sdnew.91huayi.com/health/HealthCourse/GetLearnCourseList")
//                .post(requestBody)
//                .addHeader("Host", "sdnew.91huayi.com")
//                .addHeader("Connection", "keep-alive")
//                .addHeader("Pragma", "no-cache")
//                .addHeader("Cache-Control", "no-cache")
//                .addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
//                .addHeader("X-Requested-With", "XMLHttpRequest")
//                .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 7.1.2; SM-G973N Build/PPR1.190810.011; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/92.0.4515.131 Mobile Safari/537.36;Android_HuaYi")
//                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
//                .addHeader("Origin", "https://sdnew.91huayi.com")
//                .addHeader("Sec-Fetch-Site", "same-origin")
//                .addHeader("Sec-Fetch-Mode", "cors")
//                .addHeader("Sec-Fetch-Dest", "empty")
//                .addHeader("Referer", "https://sdnew.91huayi.com/health/healthCourse/courseIndex?learnPlanId=d30ab951-f0ae-4ca8-928c-8fe375db71a9")
//                .addHeader("Accept-Encoding", "gzip, deflate")
//                .addHeader("Accept-Language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7")
//                .addHeader("Cookie", "ASP.NET_SessionId=hfp0xqqwm1hw4kxscof5sqy3; HWWAFSESID=1019173863cc8d2bf7; HWWAFSESTIME=1715241863443; e160f6fc84974abb989fde0f59e08685=WyIxNDU2NzMxMzg2Il0")
//                .build();

        Request.Builder builder = new Request.Builder();
        builder.url(url);
        switch (method) {
            case "post":
                builder.post(requestBody);
                break;
            case "get":
                builder.get();
        }

        if (MapUtil.isNotEmpty(headerMap)) {
            headerMap.forEach((k,v)-> builder.addHeader(k,v));
        }
        return builder.build();
    }

    private static void resp(OkHttpClient client, Request request) {
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            Headers headers = response.headers();
            boolean gzipFlag = false;
            for (String name : headers.names()) {
                String s = headers.get(name);
                if (s.contains("gzip")) {
                    gzipFlag = true;
                }
                log.info("header {} : {}", name, s);
            }
            log.info("header 显示完");
            if (gzipFlag) {
                GZIPInputStream inputStream = new GZIPInputStream(response.body().byteStream());
                byte[] bs = new byte[1024];
                int read = inputStream.read(bs);
                ByteArrayOutputStream buf = new ByteArrayOutputStream();
                int count = 0;
                while (read > 0) {
                    count = count + read;
                    buf.write(bs, 0, read);
                    read = inputStream.read(bs);
                }
                log.info("body {}", buf.toString());
            } else {
                log.info("body {}", response.body().string());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static SSLSocketFactory getSocketFactory(boolean withCert) {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, getTrustManager(withCert), new java.security.SecureRandom());

            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static TrustManager[] getTrustManager(boolean withCert) {
        if (withCert) {
            return get91TrastMag();
        }
        return getUnSafeTrustManager();
    }

    private static TrustManager[] get91TrastMag() {
        try {
            // 加载证书
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            FileInputStream is = new FileInputStream("D:/_.91huayi.com");
            Certificate cert = certFactory.generateCertificate(is);

            // 加载证书到KeyStore
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("certificateAlias", cert);

            TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
            tmf.init(keyStore);
            return tmf.getTrustManagers();
        } catch (Exception e) {
            log.error("加载证书获取trustManager异常", e);
        }
        return null;
    }

    private static TrustManager[] getUnSafeTrustManager() {
        return new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
        };
    }
    
    public static Map<String, String> zshyHeader() {
        return MapUtil.<String, String>builder()
            .put("Host", "sdnew.91huayi.com")
                .put("Connection", "keep-alive")
                .put("Pragma", "no-cache")
                .put("Cache-Control", "no-cache")
                .put("Accept", "application/json, text/javascript, */*; q=0.01")
                .put("X-Requested-With", "XMLHttpRequest")
                .put("User-Agent", "Mozilla/5.0 (Linux; Android 7.1.2; SM-G973N Build/PPR1.190810.011; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/92.0.4515.131 Mobile Safari/537.36;Android_HuaYi")
                .put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .put("Origin", "https://sdnew.91huayi.com")
                .put("Sec-Fetch-Site", "same-origin")
                .put("Sec-Fetch-Mode", "cors")
                .put("Sec-Fetch-Dest", "empty")
                .put("Referer", "https://sdnew.91huayi.com/health/healthCourse/courseIndex?learnPlanId=d30ab951-f0ae-4ca8-928c-8fe375db71a9")
                .put("Accept-Encoding", "gzip, deflate")
                .put("Accept-Language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7")
                .put("Cookie", "ASP.NET_SessionId=hfp0xqqwm1hw4kxscof5sqy3; HWWAFSESID=1019173863cc8d2bf7; HWWAFSESTIME=1715241863443; e160f6fc84974abb989fde0f59e08685=WyIxNDU2NzMxMzg2Il0")
                .build();
    }
}
