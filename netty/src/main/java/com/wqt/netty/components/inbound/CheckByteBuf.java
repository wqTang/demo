package com.wqt.netty.components.inbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/** 
 * @author Wqt 
 * @version 创建时间：2017年10月27日 上午11:33:11 
 * 
 */
public class CheckByteBuf extends ChannelInboundHandlerAdapter {
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		int readable = buf.readableBytes();
		System.out.println("[check] " + readable);
	}

}
