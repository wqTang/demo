package com.wqt.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/** 
 * Default implementation of ChannelInitializer for project demonstrations.
 * 
 * @author Wqt 
 * @version 创建时间：2017年10月17日 下午3:01:20 
 */
public class DefaultChannelInitializer extends ChannelInitializer<SocketChannel>{
	
	private ChannelHandler[] handlers;
	
	public DefaultChannelInitializer(ChannelHandler... handlers) {
		this.handlers = handlers;
	}
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast(handlers);
	}
}
