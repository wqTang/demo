package com.wqt.netty.mock.channel;
/** 
 * @author Wqt 
 * @version 创建时间：2017年10月17日 下午3:57:21 
 * 
 */
public interface Channel {

	void read();
	
	void write();

	void close();
}
