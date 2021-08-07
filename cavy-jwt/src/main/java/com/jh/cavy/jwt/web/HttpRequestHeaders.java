package com.jh.cavy.jwt.web;

public enum HttpRequestHeaders {
    Accept("Accept", "Accept"),

    Upgrade("Upgrade", "Upgrade"),
    AcceptCharset("AcceptCharset", "Accept-Charset"),
    AcceptEncoding("AcceptEncoding", "Accept-Charset"),
    AcceptLanguage("AcceptLanguage", "Accept-Langauge"),
    Allow("Allow", "Allow"),
    Authorization("Authorization", "Authorization"),
    CacheControl("CacheControl", "Cache-Control"),
    Connection("Connection", "Connection"),
    ContentEncoding("ContentEncoding", "Content-Encoding"),
    ContentLanguage("ContentLanguage", "Content-Langauge"),
    ContentLength("ContentLength", "Content-Length"),
    ContentLocation("ContentLocation", "Content-Location"),
    ContentMd5("ContentMd5", "Content-MD5"),
    ContentRange("ContentRange", "Content-Range"),
    //https://docs.microsoft.com/zh-cn/dotnet/api/system.net.httprequestheader?view=net-5.0

    UserAgent("UserAgent", "User-Agent");

    private String head;

    private String name;

    private HttpRequestHeaders(String head, String name) {
        this.head = head;
        this.name = name;
    }
    public String getHead() {
        return head;
    }

    public String getName() {
        return name;
    }
}
