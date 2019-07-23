package com.tencent.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : ys
 * @date : 2019/7/23 22:25 星期二
 **/

@RestController
public class Controller {
	@RequestMapping("/payload")
	public Map fun1(@RequestBody HashMap map) {
		String str = JSON.toJSONString(map);
		System.out.println(str);
		return map;
	}
}
