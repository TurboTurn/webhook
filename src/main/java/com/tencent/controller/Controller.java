package com.tencent.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author : ys
 * @date : 2019/7/23 22:25 星期二
 **/

@RestController
public class Controller {
	@RequestMapping("/payload")
	public String fun1(@RequestBody HashMap<String, Object> map) {
		String str = JSON.toJSONString(map);
		System.out.println(str);

		ArrayList<String> list = new ArrayList<>();
		String author = "";
		ArrayList<HashMap<String, Object>> objs = (ArrayList<HashMap<String, Object>>) (map.get("commits"));
		int len = objs.size();
		for (int i = 0; i < len; i++) {
			HashMap<String, Object> commit = objs.get(i);
			list.add((String) commit.get("message"));
//			String message = (String) commit.get("message");
			HashMap<String, String> authinfo = (HashMap<String, String>) commit.get("author");
			author = authinfo.get("name");
		}
		String msg = String.format("%s推送了%d次提交，请查看\ncommit log:%s", author, len, list);
		System.out.println(msg);
		return "ok";
	}
}
