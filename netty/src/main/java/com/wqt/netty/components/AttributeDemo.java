package com.wqt.netty.components;

import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.AttributeMap;
import io.netty.util.DefaultAttributeMap;

/** 
 * @author Wqt 
 * @version 创建时间：2017年10月26日 下午12:27:07 
 * 
 */
public class AttributeDemo {

	public static void attributeKey() {
		AttributeKey<Integer> attrKey = AttributeKey.newInstance("userId");
		int id = attrKey.id();
		String name = attrKey.name();
		
		System.out.println(id);
		System.out.println(name);
	}

	public static void attributeMap() {
		AttributeKey<Integer> attrKey = AttributeKey.newInstance("id");
		AttributeMap attrMap = new DefaultAttributeMap();
		Attribute<Integer> attr = attrMap.attr(attrKey);
		Integer id = attr.get();
		AttributeKey<Integer> key = attr.key();
		
		System.out.println(id);
		System.out.println(key.id());
		System.out.println(key.name());
	}
	
	public static void main(String[] args) {
		
//		attributeKey();
		
		attributeMap();
	}
}
