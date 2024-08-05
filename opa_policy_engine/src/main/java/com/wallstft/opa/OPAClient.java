package com.wallstft.opa;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class OPAClient {

    private static final String OPA_URL = "http://localhost:8181/v1/data/example/allow";

    public boolean checkAccess(String userRole, String resource, String permission) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(OPA_URL);
            JSONObject json = new JSONObject();
            json.put("input", new JSONObject()
                    .put("user_role", userRole)
                    .put("resource", resource)
                    .put("permission", permission));

            post.setEntity(new org.apache.http.entity.StringEntity(json.toString()));
            post.setHeader("Content-Type", "application/json");

            try (CloseableHttpResponse response = httpClient.execute(post)) {
                HttpEntity entity = response.getEntity();
                String responseString = EntityUtils.toString(entity);
                JSONObject responseJson = new JSONObject(responseString);
                return responseJson.optBoolean("result", false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}

