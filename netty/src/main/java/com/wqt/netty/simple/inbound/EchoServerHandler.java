package com.wqt.netty.simple.inbound;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.nio.NioEventLoop;
import io.netty.util.CharsetUtil;

/**
 * @author Wqt
 * @version 创建时间：2017年10月16日 下午4:58:37
 * 
 */
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("[active] " + ctx.name());

		NioEventLoop eventLoop = (NioEventLoop) ctx.channel().eventLoop();
		System.out.println("eventloop: " + eventLoop.getClass().getName());
		System.out.println("pendingTasks: " + eventLoop.pendingTasks());
		System.out.println("parent: " + eventLoop.parent().getClass().getName());

	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf in = (ByteBuf) msg;
		System.out.println("[read] " + in.toString(CharsetUtil.UTF_8));
		ctx.write(in);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}

	/**
	 * An application should supply at least one ChannelHandler that implemented
	 * this method.
	 * 
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
