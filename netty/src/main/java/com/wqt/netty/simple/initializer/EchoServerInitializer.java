package com.wqt.netty.simple.initializer;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.ChannelPipeline;

/** 
 * ChannelInitializer
 * 
 * <pre>
 * When ChannelInitializer.initChannel() is called, the ChannelInitializer installs
 * a custom set of ChannelHandlers in the {@link ChannelPipeline}.
 * After that, ChannelInitializer removes itself from the ChannelPipeline.
 * </pre>
 * 
 * @author Wqt 
 * @version 创建时间：2017年10月16日 下午5:20:45 
 * 
 */
public class EchoServerInitializer extends ChannelInitializer<SocketChannel>{
	
	private ChannelHandler[] handlers;
	
	public EchoServerInitializer(ChannelHandler... handlers) {
		this.handlers = handlers;
	}
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline().addLast(handlers);
	}

}
