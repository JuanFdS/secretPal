package com.tenPines.auth;


import com.tenPines.utils.Payload;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class GoogleAuth {

    private Properties gmailAPIInformation;
    private HttpClient httpClient;

    public GoogleAuth(Properties gmailAPIInformation, HttpClient httpClient){
        this.gmailAPIInformation = gmailAPIInformation;
        this.httpClient = httpClient;
    }

    public String authUserWithPayload(Payload payload) throws IOException {
        HttpPost post = new HttpPost(gmailAPIInformation.getProperty("oauthAPI"));
        post.setEntity(new UrlEncodedFormEntity(buildOAuthRequest(payload)));
        JSONObject jsonResponse = makeRequest(post);
        return (String) jsonResponse.get("access_token");
    }


    public String getAuthUserEmail(String accessToken) throws IOException {
        HttpGet get = new HttpGet(gmailAPIInformation.getProperty("gmailContactsAPI"));
        get.addHeader("Authorization", String.format("Bearer %s", accessToken));
        JSONObject userInfoResponse = makeRequest(get);
        return userInfoResponse.get("email").toString();
    }


    private List<BasicNameValuePair> buildOAuthRequest(Payload payload) {
        return Arrays.asList(
                new BasicNameValuePair("code", payload.getCode()),
                new BasicNameValuePair("client_id", payload.getClientId()),
                new BasicNameValuePair("client_secret", gmailAPIInformation.getProperty("secretKey")),
                new BasicNameValuePair("redirect_uri", payload.getRedirectUri()),
                new BasicNameValuePair("grant_type", "authorization_code"));
    }

    private JSONObject makeRequest(HttpRequestBase request) throws IOException {
        HttpResponse userResponse = httpClient.execute(request);
        return new JSONObject(EntityUtils.toString(userResponse.getEntity()));
    }
}
