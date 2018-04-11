package com.wqt.netty.mock;
/** 
 * @author Wqt 
 * @version 创建时间：2017年10月17日 下午4:19:18 
 * 
 */
public class SelectorRunner implements Runnable {

	private Selector selector;
	
	public SelectorRunner(Selector selector) {
		this.selector = selector;
	}
	
	public void run() {
		for (;;)
			if (interest())
				selector.select();
	}
	
	private boolean interest() {
		return false;
	}
	
}
