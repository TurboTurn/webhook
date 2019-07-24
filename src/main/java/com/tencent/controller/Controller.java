package com.tencent.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author : ys
 * @date : 2019/7/23 22:25 星期二
 **/

@RestController
public class Controller {
	String url = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=731e518f-7ac5-4955-8a6f-7f970e70750e";

	String gitoa = "https://git.code.oa.com/stephenyi/msgService";
	@RequestMapping("/payload")
	public String fun1(@RequestBody HashMap<String, Object> map) {
//		String str = JSON.toJSONString(map);
//		System.out.println(str);

		ArrayList<String> list = new ArrayList<>();
		String author = "";
		ArrayList<HashMap<String, Object>> objs = (ArrayList<HashMap<String, Object>>) (map.get("commits"));
		if (objs == null) {
			return "ok";
		}
		int len = objs.size();
		for (int i = 0; i < len; i++) {
			HashMap<String, Object> commit = objs.get(i);
			list.add((String) commit.get("message"));
			HashMap<String, String> authinfo = (HashMap<String, String>) commit.get("author");
			author = authinfo.get("name");
		}
		String msg = String.format("%s推送了%d次提交，请查看%s", author, len, gitoa);
		System.out.println(msg);
		String log = list.toString();
		log = log.substring(1, log.length() - 1);

		String param = String.format("{\n" +
				"        \"msgtype\": \"text\",\n" +
				"        \"text\": {\n" +
				"            \"content\": \"%s\\ncommit log:%s\"\n" +
				"        }\n" +
				"   }", msg, log);
		String result = post(url, param);
		System.out.println(result);
		return "ok";
	}

	public String post(String strURL, String params) {
		System.out.println(params);
		BufferedReader reader = null;
		try {
			URL url = new URL(strURL);// 创建连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			// connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
			connection.connect();
			//一定要用BufferedReader 来接收响应， 使用字节来接收响应的方法是接收不到内容的
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
			out.append(params);
			out.flush();
			out.close();
			// 读取响应
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line;
			String res = "";
			while ((line = reader.readLine()) != null) {
				res += line;
			}
			reader.close();
			return res;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "error"; // 自定义错误信息
	}
}
