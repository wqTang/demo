package com.wqt.netty.components;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

/** 
 * @author Wqt 
 * @version 创建时间：2017年10月19日 上午11:54:43 
 * 
 */
public class DemoOutboundHandler extends ChannelOutboundHandlerAdapter {

	/**
	 * You're responsible for release resources by use read/write operation.
	 * Furthermore, the distinction of read operation is important not only to release resources 
	 * but also to notify the ChannelPromise in write operation, 
	 * otherwise a situation might arise where a ChannelFutureListener 
	 * has not been notified about a message that has been handled.
	 */
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		ReferenceCountUtil.release(msg);	// directly discard all writen data.
		promise.setSuccess();	// !!! notify the ChannelPromise
	}
}
