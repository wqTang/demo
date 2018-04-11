package com.wqt.netty.components.inbound;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/** 
 * @author Wqt 
 * @version 创建时间：2017年10月26日 下午5:12:13 
 * 
 */
public class FixLengthFrameDecoder extends ByteToMessageDecoder { 
	
	private int frameLength;
	
	public FixLengthFrameDecoder(int frameLength) {
		this.frameLength = frameLength;
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		while(in.readableBytes() >= frameLength) {
			ByteBuf fixLengthFrame = in.readBytes(frameLength);
			out.add(fixLengthFrame);
		}
	}

}
