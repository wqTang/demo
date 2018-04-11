package com.wqt.netty.distributed.net.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;

/** 
 * @author Wqt 
 * @date 2017年12月12日 下午4:39:05 
 * @version v1.0
 */
public class DefaultChannelInitializer extends ChannelInitializer<NioSocketChannel>{

	private ChannelHandler[] handlers;
	
	public DefaultChannelInitializer(ChannelHandler... handlers) {
		this.handlers = handlers;
	}
	
	@Override
	protected void initChannel(NioSocketChannel ch) throws Exception {
		ch.pipeline().addLast(handlers);
	}

}
