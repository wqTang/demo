package com.wqt.netty.mock.channel;

import com.wqt.netty.mock.SelectState;
import com.wqt.netty.mock.Selector;

/** 
 * @author Wqt 
 * @version 创建时间：2017年10月17日 下午4:07:20 
 * 
 */
public abstract class AbstractServer implements ServerSocketChannel{

	private Selector selector;
	private SelectState[] interest;
	private SelectState state = SelectState.NULL;
	
	public AbstractServer(Selector selector) {
		this.selector = selector;
		this.selector.register(this);
	}
	
	public void read() {
		
	}

	public void write() {
		
	}

	public void close() {
		
	}

	public void accept() {
		selector.register(this);
	}

	public void interest(SelectState... states) {
		
	}
	
	private void stateChange(SelectState state) {
	}
	
	protected abstract void channelRead();
	
	protected abstract void channelAccept();
}
