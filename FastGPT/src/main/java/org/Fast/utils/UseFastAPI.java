package org.Fast.utils;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import okhttp3.*;
import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author suze
 * @date 2024/2/25
 * @time 21:05
 **/
@Component
public class UseFastAPI {
    @Resource
    // 创建RestTemplate对象
    private RestTemplate restTemplate;

    private JSONArray AppidArray =new JSONArray();
    //每周三凌晨四点执行这段代码替换token
    @Scheduled(cron = "0 0 4 * * WED")
    public void LoginByPassWord(){

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        // 设置请求体
        String requestBody = "{\"username\":\"root\",\"password\":\"13c039419e4ff7c73c953ecbb2248fda61afead1bd9a02289f35b31c907c79f7\"}";

        // 创建请求实体对象
        RequestEntity<String> requestEntity = new RequestEntity<>(requestBody, headers, HttpMethod.POST, URI.create("http://47.236.235.78:3000/api/support/user/account/loginByPassword"));

        // 发送请求
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);


        String responseBody = responseEntity.getBody();
        // 解析响应体为 JSON 对象
        JSONObject responseJson = new JSONObject(responseBody);

        // 获取 token 值
        String token = responseJson.getJSONObject("data").getStr("token");

        System.out.println("token " + token);

        // 读取现有的 YAML 文件
        Yaml yaml = new Yaml();
        Map<String, Object> data = new HashMap<>();
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/application.yaml");
            data = yaml.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

// 更新 YAML 数据中的 token 值
        data.put("token", token);

// 将更新后的数据写入 YAML 文件
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml newYaml = new Yaml(options);
        try {
            FileWriter writer = new FileWriter("src/main/resources/application.yaml");
            newYaml.dump(data, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public RequestBody createFormRequestBody(String key, String value) {
        RequestBody requestBody = new FormBody.Builder()
                .add(key, value)
                .build();
        return requestBody;
    }

    public String getToken() {
        // 读取现有的 YAML 文件
        Yaml yaml = new Yaml();
        Map<String, Object> data = new HashMap<>();
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/application.yaml");
            data = yaml.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Object token = data.get("token");
        return token.toString();
    }
    public JSONArray getAppid() {
        String token = getToken();
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("token", (String) token); // 添加 token 到请求头

        // 创建请求实体对象
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // 发送请求
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://47.236.235.78:3000/api/core/app/list",
                HttpMethod.GET,
                requestEntity,
                String.class
        );

        // 处理响应
        String responseBody = responseEntity.getBody();
        JSONObject json = JSONUtil.parseObj(responseBody);

        // 解析响应
        JSONArray dataArray = json.getJSONArray("data");
        for (int i = 0; i < dataArray.size(); i++) {
            JSONObject jsonObject=new JSONObject();
            JSONObject dataObject = dataArray.getJSONObject(i);
            String name = dataObject.getStr("name");
            String id = dataObject.getStr("_id");
            jsonObject.set("name",name);
            jsonObject.set("id",id);
            AppidArray.add(jsonObject);
            // 在这里进行进一步处理
        }
        System.out.println(AppidArray.toString());
        return AppidArray;
    }

    public String createApiKey(String username, String appid) {
        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("token",getToken());

        // 设置请求体
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", username);
        JSONObject limitObject = new JSONObject();
        limitObject.put("credit", -1);
        requestBody.put("limit", limitObject);
        requestBody.put("appId", appid);

        // 创建请求实体对象
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.toString(), headers);

        // 发送请求
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://47.236.235.78:3000/api/support/openapi/create",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // 处理响应
        HttpStatus statusCode = responseEntity.getStatusCode();
        String responseBody = responseEntity.getBody();
        // 在这里处理响应数据
        JSONObject jsonObject = JSONUtil.parseObj(responseBody);
        String  ApiKey = (String) jsonObject.get("data");
        System.out.println(ApiKey);
        return  ApiKey;
    }
    public JSONObject chat(String apiKey,String message) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("User-Agent", "Apifox/1.0.0 (https://apifox.com)");
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\n  \"chatId\": \"fastgpt-yV6P4uVraw2Wl0v68pvTXUhc3NrQHUmE\",\n  \"stream\": false,\n  \"detail\": false,\n  \"messages\": [\n    {\n      \"content\": \"阿里巴巴倒过来念\",\n      \"role\": \"user\"\n    }\n  ]\n}";
        HttpEntity<String> requestEntity = new HttpEntity<>(message, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://47.236.235.78:3000/api/v1/chat/completions",
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        String response = responseEntity.getBody();
        return JSONUtil.parseObj(response);
    }
    public JSONArray getAppidArray() {
        return AppidArray;
    }
}
