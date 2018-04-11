package com.wqt.fastjson.annotation;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author Wqt
 * @version 创建时间：2017年6月14日 下午3:57:24
 */
public class AnnotationMethodBean {

	private int id;

	private String beanName;

	private int age;

	@JSONField(name = "get_id")
	public int getId() {
		return id;
	}

	@JSONField(name = "get_id")
	public void setId(int id) {
		this.id = id;
	}

	@JSONField(name = "get_beanName")
	public String getBeanName() {
		return beanName;
	}

	@JSONField(name = "set_beanName")
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	@JSONField(name = "get_age")
	public int getAge() {
		return age;
	}

	@JSONField(name = "set_age")
	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "AnnotationBean [id=" + id + ", beanName=" + beanName + ", age=" + age + "]";
	}

}
