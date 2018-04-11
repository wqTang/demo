package com.wqt.fastjson.test;

import com.alibaba.fastjson.JSON;
import com.wqt.fastjson.annotation.AnnotationFieldBean;
import com.wqt.fastjson.annotation.AnnotationMethodBean;

/**
 * @author Wqt
 * @version 创建时间：2017年6月14日 下午3:59:15
 * 
 */
public class AnnotationTest {

	private static final AnnotationFieldBean bean = new AnnotationFieldBean();
	private static final AnnotationMethodBean bean2 = new AnnotationMethodBean();

	static {
		bean.setId(123);
		bean.setBeanName("uShu");
		bean.setAge(111);

		bean2.setId(3);
		bean2.setBeanName("uShu");
		bean2.setAge(3);
	}

	public static void annotationFieldJSON() {
		String jstr = JSON.toJSONString(bean);
		System.out.println(jstr);
	}

	public static String annotationMethodJSON() {
		String jstr = JSON.toJSONString(bean2);
		System.out.println(jstr);
		return jstr;
	}

	public static void antJSONToJavaObject() {
		String jstr = annotationMethodJSON();
		AnnotationMethodBean bean3 = JSON.parseObject(jstr, AnnotationMethodBean.class);
		System.out.println(bean3);
	}

	public static void main(String[] args) {

		annotationFieldJSON();

		// annotationMethodJSON();

		antJSONToJavaObject();
	}
}
