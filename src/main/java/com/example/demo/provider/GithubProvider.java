package com.example.demo.provider;

import com.alibaba.fastjson.JSON;
import com.example.demo.dto.AccessTokenDto;
import com.example.demo.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component//IOC!!!控制反转！！！把类加到spring上下文中！！！对象自动实例化
public class GithubProvider {
    public String getAccessToken(AccessTokenDto accessTokenDto) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");//格式
        OkHttpClient client = new OkHttpClient();//发送请求端
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDto));//accessTokenDTO类格式转化为JSON格式的body

        Request request = new Request.Builder()//这里曾经找不到类，应该是新版依赖和其他有冲突，JVM没有识别。换到旧版就好了
                .url("https://github.com/login/oauth/access_token")
                .post(body)//post
                .build();//通过这步，得到了accessToken
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            //System.out.println(string);//body里应该不只accessToken，看看都有什么//是一串
            string = string.split("&")[0].split("=")[1];

            return string;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;//不会被执行
    }

    public GithubUser getUser(String accsessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accsessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string,GithubUser.class);//把string（json格式） 转为GithubUser（类格式）
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
