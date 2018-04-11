package com.wqt.netty.components;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ResourceLeakDetector;

/** 
 * Sharable: <br>
 * This annotation indicating that this ChannelHandler can be added to multiple ChannelPipeline.
 * 
 *  @see ChannelHandlerAdapter#isSharable()
 *  
 *  It should be note that, use @Sharable only if you're certain that your ChannelHandler is 
 *  thread-safe.
 */
@Sharable
public class DemoInboundHandler extends ChannelInboundHandlerAdapter{

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("[active] " + ctx.name());
	}
	
	/**
	 * ###  You need to ensure that the read/write operation does no resource leak  ###
	 * 
	 * Note the release operation of ByteBuf which contained by ChannelHandlerContext.
	 * 
	 * Netty provides class ResourceLeakDetector, which will sample about 1% of your application's
	 * buffer allocations to check for memory leak.
	 * 
	 * # If the leak is detected, a log message similar to the following will be produced:
	 * --> LEAK: ByteBuf.release() was not called before it's garbage-collected.
	 * --> Enable advanced leak reporting to find out where the leak occurred. To enable
	 * --> advanced leak reporting, specify the JVM option :
	 * ==> '-Dio.netty.leakDetectionLevel=ADVANCED' or call method:
	 * @see ResourceLeakDetector#setLevel(io.netty.util.ResourceLeakDetector.Level)
	 * 
	 * Don't release resources if the resource need to be reference in the next ChannelHandler.
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf in = (ByteBuf) msg;
		System.out.println("[read] " + in.toString(CharsetUtil.UTF_8));
		ctx.write(in);
		
//		ReferenceCountUtil.release(in);	// release resource
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}
	
	/**
	 * An application should supply at least one ChannelHandler that implementeds this method.
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
