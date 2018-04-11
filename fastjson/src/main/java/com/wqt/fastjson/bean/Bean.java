package com.wqt.fastjson.bean;
/** 
 * @author Wqt 
 * @version 创建时间：2017年6月14日 下午12:10:25 
 * 
 */
public class Bean {
	private int id;
	private String beanName;
	private String param;
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
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	@Override
	public String toString() {
		return "Bean [id=" + id + ", beanName=" + beanName + ", param=" + param + "]";
	}
	
}
