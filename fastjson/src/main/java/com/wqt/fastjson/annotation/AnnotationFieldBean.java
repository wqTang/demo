package com.wqt.fastjson.annotation;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author Wqt
 * @version 创建时间：2017年6月14日 下午3:57:24
 * 
 */
public class AnnotationFieldBean {
	
	@JSONField(name="ab_id")
	private int id;
	
	@JSONField(name="ab_beanName")
	private String beanName;
	
	@JSONField(name="ab_age")
	private int age;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "AnnotationBean [id=" + id + ", beanName=" + beanName + ", age=" + age + "]";
	}

}
