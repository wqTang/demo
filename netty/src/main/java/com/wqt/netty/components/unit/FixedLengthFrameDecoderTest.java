package com.wqt.netty.components.unit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.embedded.EmbeddedChannel;

import org.junit.Test;

import com.wqt.netty.components.inbound.CheckByteBuf;
import com.wqt.netty.components.inbound.FixLengthFrameDecoder;

/**
 * Unit test of FixedLengthFrameDecoder
 * 
 * # check the number of ByteBuf which in FixLengthFrameDecoder#decode()
 * 
 * @author Wqt
 * @version 创建时间：2017年10月26日 下午5:25:35
 * 
 */
public class FixedLengthFrameDecoderTest {

	public static final int FIX = 3;

	@Test
	public void testFramesDecoded() {
		ByteBuf buf = Unpooled.buffer();
		for (int i = 0; i < 10; i++)
			buf.writeInt(i);

		ChannelHandler fixedLength = new FixLengthFrameDecoder(FIX);
		CheckByteBuf check = new CheckByteBuf();
		EmbeddedChannel embedded = new EmbeddedChannel(fixedLength, check);

		// write message
		ByteBuf in = buf.duplicate();
		boolean r = embedded.writeInbound(in.retain());
		boolean r2 = embedded.finish();

		// read message
		ByteBuf out = embedded.readInbound();
		boolean r3 = buf.readSlice(FIX).equals(out);
		out.release();

		printf(r, r2, r3);

	}

	public void testByteBuf() {
		ByteBuf buf = Unpooled.buffer();
		for (int i = 0; i < 10; i++)
			buf.writeInt(i + 1);

		System.out.println(buf.readableBytes()); // 4 * 10 = 40

		ByteBuf slice = buf.readSlice(FIX); // set readableBytes to FIX
		int i = 0;
		for (byte b : slice.array()) {
			if (i > slice.readableBytes())
				break;
			System.out.println(b);
			i++;
		}
	}

	public static void main(String[] args) {

		FixedLengthFrameDecoderTest test = new FixedLengthFrameDecoderTest();

		test.testFramesDecoded();

		// test.testByteBuf();

	}

	public void printf(boolean... args) {
		StringBuilder sb = new StringBuilder();
		for (boolean r : args)
			sb.append(r).append("\t");
		System.out.println(sb);
	}
}
