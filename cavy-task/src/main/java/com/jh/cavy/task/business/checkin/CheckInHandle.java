package com.jh.cavy.task.business.checkin;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CheckInHandle {

    public static void invoker(List<Plant> plants, boolean isProxy) {
        if (plants == null) {
            return;
        }
        for (Plant plant : plants) {
            // 获取文件 拼接
            String loginUrl = plant.getLoginUrl();
            String checkInUrl = plant.getCheckInUrl();
            try {
                try {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("email", plant.getEmail());
                    params.put("passwd", plant.getPasswd());
                    params.put("remember_me", plant.getRemember_me());
                    params.put("code", plant.getCode());
                    BasicCookieStore basicCookieStore = new BasicCookieStore();
                    JSONObject jsonObject = postForm(loginUrl, params, null, basicCookieStore,isProxy);
                    log.info(StrUtil.toString(jsonObject));
                    if (jsonObject != null && StrUtil.toString(jsonObject.get("msg")).contains("成功")) {
                        log.info("登录成功");
                        List<Cookie> cookies = basicCookieStore.getCookies();
                        BasicCookieStore checkInCookies = new BasicCookieStore();
                        cookies.forEach(checkInCookies::addCookie);
                        JSONObject checkInResult = post(checkInUrl, null, checkInCookies,isProxy);
                        log.info(StrUtil.toString(checkInResult));
                        if (checkInResult != null && StrUtil.toString(jsonObject.get("msg")).startsWith("获得")) {
                            log.info("签到成功");
                        }
                    } else {
                        log.info("登录失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (
                      ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static JSONObject postForm(String url, Map<String, String> params, Map<String, String> heads, CookieStore httpCookieStore,boolean isProxy) throws IOException {
        HttpPost post = new HttpPost(url);
        RequestConfig.Builder builder = RequestConfig.custom()
                                                .setConnectTimeout(10000)
                                                .setSocketTimeout(10000)
                                                .setConnectionRequestTimeout(3000);
        if (isProxy){
            HttpHost proxy = new HttpHost("127.0.0.1", 7890, "http");
            builder.setProxy(proxy);
        }
        RequestConfig defaultRequestConfig = builder.build();
        if (httpCookieStore == null) {
            httpCookieStore = new BasicCookieStore();
        }
        HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(defaultRequestConfig).setDefaultCookieStore(httpCookieStore).build();
        List<org.apache.http.NameValuePair> parameters = new ArrayList<>();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        if (heads != null) {
            for (Map.Entry<String, String> entry : heads.entrySet()) {
                post.setHeader(entry.getKey(), entry.getValue());
            }
        }
        post.setHeader("Content-type", "application/x-www-form-urlencoded");
        post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36");
        HttpEntity entity = new UrlEncodedFormEntity(parameters, "UTF-8");
        post.setEntity(entity);
        HttpResponse response = client.execute(post);
        log.info(response.getStatusLine().getStatusCode() + "");
        if (response.getStatusLine().getStatusCode() == 200) {
            String string = EntityUtils.toString(response.getEntity(), "UTF-8");
            return JSONUtil.parseObj(string);
        }
        return null;
    }

    public static JSONObject post(String url, Map<String, String> heads, CookieStore httpCookieStore,boolean isProxy) throws IOException {
        HttpPost post = new HttpPost(url);
        RequestConfig.Builder builder = RequestConfig.custom()
                                                .setConnectTimeout(10000)
                                                .setSocketTimeout(10000)
                                                .setConnectionRequestTimeout(3000);
        if (isProxy){
            HttpHost proxy = new HttpHost("127.0.0.1", 7890, "http");
            builder.setProxy(proxy);
        }
        RequestConfig defaultRequestConfig = builder.build();
        if (httpCookieStore == null) {
            httpCookieStore = new BasicCookieStore();
        }
        HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(defaultRequestConfig).setDefaultCookieStore(httpCookieStore).build();
        if (heads != null) {
            for (Map.Entry<String, String> entry : heads.entrySet()) {
                post.setHeader(entry.getKey(), entry.getValue());
            }
        }
        post.setHeader("Content-type", "application/x-www-form-urlencoded");
        post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36");
        HttpResponse response = client.execute(post);
        log.info(response.getStatusLine().getStatusCode() + "");
        if (response.getStatusLine().getStatusCode() == 200) {
            String string = EntityUtils.toString(response.getEntity(), "UTF-8");
            return JSONUtil.parseObj(string);
        }
        return null;
    }

    ///**
    // * httpclient post方法使用json字符串形式传参
    // *
    // * @param pk_corp    测试参数
    // * @param dataSource 测试参数
    // * @throws IOException
    // */
    //public static void postJson(String pk_corp, String dataSource) throws IOException {
    //    String url = "http://10.10.6.161:8201/organisation/v1/getCorpInfoByPkTwo";
    //    JSONObject jsonObject = new JSONObject();
    //    jsonObject.put("pk_corp", pk_corp);
    //    jsonObject.put("dataSource", dataSource);
    //
    //    CloseableHttpClient client = HttpClients.createDefault();
    //    HttpPost post = new HttpPost(url);
    //    post.addHeader("Content-Type", "application/json");
    //
    //    post.setEntity(new StringEntity(jsonObject.toString()));
    //    HttpResponse response = client.execute(post);
    //    System.out.println(response.getStatusLine().getStatusCode());
    //    //	System.out.println(EntityUtils.toString(response.getEntity()));
    //    String string = EntityUtils.toString(response.getEntity());
    //    JSONObject fromObject = JSONObject.parseObject(string);
    //    System.out.println(fromObject.get("status"));
    //
    //}
}
