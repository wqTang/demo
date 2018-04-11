package com.wqt.fastjson.test;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.wqt.fastjson.bean.Bean;

public class Test {
	
	private static Bean bean = new Bean();
	
	static {
		bean.setId(999);
		bean.setBeanName("iuShu");
		bean.setParam("param01");
	}
	
	// object --> json
	public static void toJSONString() {
		String jstr = JSON.toJSONString(bean);
    	System.out.println(jstr);
	}
	
	// json --> object
	public static void toJavaObject() {
		String jstr = "{\"beanName\":\"iuShu\",\"id\":999,\"param\":\"param01\"}";
		Bean b = JSON.parseObject(jstr, Bean.class);
		System.out.println(b);
	}
	
	// list --> json
	public static String listToJSONString() {
		List<Bean> list = new ArrayList<Bean>();
	
		Bean b = new Bean();
		b.setId(222);
		b.setBeanName("uShu");
		b.setParam("param66");
	
		list.add(bean);
		list.add(b);
		
		String jstr = JSONObject.toJSONString(list);
		System.out.println(jstr);
		return jstr;
	}

	// json --> list
	public static void toJavaList() {
		String jstr = listToJSONString();
		List<Bean> list = JSON.parseArray(jstr, Bean.class);
		System.out.println(list);
	}
	
    public static void main(String[] args) {
//    	toJSONString();
    	
//    	toJavaObject();
    	
//    	listToJSONString();
    	
    	toJavaList();
    }
}
