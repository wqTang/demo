package com.wqt.netty.mock;
/** 
 * @author Wqt 
 * @version 创建时间：2017年10月17日 下午4:00:07 
 * 
 */
public enum SelectState {
	
	NULL(0),
	
	OP_READ(1),
	
	OP_WRITE(2),
	
	OP_ACCEPT(3),
	
	OP_CONNECT(4),
	
	;
	
	private int code;
	
	private SelectState(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
}
