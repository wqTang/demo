package com.wqt.netty.components;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;

/**
 * + ByteBuf 
 * 		+ HeapByteBuf 
 * 			- as array internally 
 * 		+ DirectByteBuf 
 * 			- allocate in direct buffer 
 * 		+ CompositeByteBuf 
 * 			- use to heap or direct, or both two. 
 * 			- such as HTTP, you can combine header and body by CompositeByteBuf.
 */
public class ByteBufDemo {

	public static void bufferType() {
		String str = "Hello ByteBuf"; // str.length() = 13

		/**
		 * Here was create a heap bytebuf
		 * 
		 * Use buf.hasArray() judge the type is heap buffer or direct buffer.
		 * buf.hasArray() can't judge the type of compositeByteBuf.
		 */
		ByteBuf buf = Unpooled.buffer(5);
		int r1 = buf.readerIndex(); // 0
		int w1 = buf.writerIndex(); // 0

		buf.writeBytes(str.getBytes()); // copy bytes into
										// UnpooledHeapByteBuf.array byte[]
		int r2 = buf.readerIndex(); // 0
		int w2 = buf.writerIndex(); // 13

		int length = buf.readableBytes(); // readableBytes = writerIndex -
											// readerIndex

		// This read operation process by CharBuffer (java.nio.CharBuffer)
		String read = (String) buf.readCharSequence(length, CharsetUtil.UTF_8);
		int r4 = buf.readerIndex();
		int w4 = buf.writerIndex();

		System.out.println("readableBytes: " + length);
		System.out.println("readByte: " + read);

		System.out.println(r1 + "  " + w1);
		System.out.println(r2 + "  " + w2);
		System.out.println(r4 + "  " + w4);
	}

	public static void readBuf() {
		String hello = "hello";
		String hi = "hi";
		ByteBuf one = Unpooled.wrappedBuffer(hello.getBytes());
		ByteBuf two = Unpooled.buffer(7).writeBytes(hi.getBytes());
		
		int ro1 = one.readerIndex(); // 0
		int wo1 = one.writerIndex(); // 5
		
		int rt1 = two.readerIndex(); // 0
		int wt1 = two.writerIndex(); // 2

		one.readBytes(two);
		
		int ro2 = one.readerIndex(); // 5  readerIndex increase by the above read operation.
		int wo2 = one.writerIndex(); // 5

		int rt2 = two.readerIndex(); // 0 
		int wt2 = two.writerIndex(); // 7  writerIndex increase by the above read operation.

		System.out.println(ro1 + " " + wo1);
		System.out.println(rt1 + " " + wt1);
		System.out.println(ro2 + " " + wo2);
		System.out.println(rt2 + " " + wt2);
	}
	
	public static void writeBuf() {
		String hello = "hello";
		String hi = "hi";
		ByteBuf one = Unpooled.wrappedBuffer(hello.getBytes());
		ByteBuf two = Unpooled.buffer(7).writeBytes(hi.getBytes());
		
		int ro1 = one.readerIndex(); // 0
		int wo1 = one.writerIndex(); // 5
		
		int rt1 = two.readerIndex(); // 0
		int wt1 = two.writerIndex(); // 2
		
		/**
		 * + ByteBuf_1.write*(ByteBuf_2)
		 * 		- The first bytebuf's capacity required: capacity_1 = capacity_1 + capacity_2
		 * 	 	- Otherwise, the write operation will throw index out of bounds exception.
		 */
		ByteBuf buf = two.writeBytes(one);
		
		int ro2 = one.readerIndex(); // 5  readerIndex increase by the above write operation.
		int wo2 = one.writerIndex(); // 5
		
		int rt2 = two.readerIndex(); // 0  readerIndex not change
		int wt2 = two.writerIndex(); // 7  writerIndex increate by the above write operation.
		
		int rb = buf.readerIndex(); // 0 
		int wb = buf.writerIndex(); // 7
		
		System.out.println(ro1 + " " + wo1);
		System.out.println(rt1 + " " + wt1);
		System.out.println(ro2 + " " + wo2);
		System.out.println(rt2 + " " + wt2);
		System.out.println(rb + " " + wb);
		
		System.out.println(two == buf);		 // true
		System.out.println(two.equals(buf)); // true

	}
	
	public static void simpleSearch() {
		String s = "hello";
		ByteBuf buf = Unpooled.wrappedBuffer(s.getBytes());
		
		byte key = 101; // 'e' = 101   'l' = 108
		int index = buf.indexOf(0, buf.readableBytes(), key);
		System.out.println("index: " + index);
	}
	
	public static void complexSearch() {
		/**
		 * complex process by {@link ByteProcessor}
		 */
		String s = "hello to ByteBuf";
		ByteBuf buf = Unpooled.wrappedBuffer(s.getBytes());
		
		byte key = 111;
		int index = buf.forEachByte(new DemoByteProcessor(key));
		System.out.println("index: " + index);	// return index or -1.
	}
	
	public static void derivedBuffer() {
		String s = "hello";
		ByteBuf buf = Unpooled.wrappedBuffer(s.getBytes());
	
		/**
		 * About the derived buffer
		 * The following operation return the reference of 'derivedBuf'
		 * If you modify its contents you are modifing the source instance as well,
		 * so beware.
		 */
		ByteBuf temp0 = Unpooled.unmodifiableBuffer(buf);
//		ByteBuf temp0 = buf.asReadOnly(); // new method
		ByteBuf temp1 = buf.duplicate();
		ByteBuf temp2 = buf.slice();
		ByteBuf temp3 = buf.slice(0, 3); // temp3 ==> 'hel'
//		ByteBuf temp4 = buf.order(ByteOrder);
		ByteBuf temp5 = buf.readSlice(4);
		
	}
	
	public static void main(String[] args) {
		
//		bufferType();
		
//		readBuf();
		
//		writeBuf();
		
//		simpleSearch();
		
//		complexSearch();
		
	}

}

/**
 * ByteBufProcessor is Deprecated
 * Instead, use ByteProcessor
 */
class DemoByteProcessor implements ByteProcessor {
	
	private int count = 0;
	private byte key;
	
	public DemoByteProcessor(byte key) {
		this.key = key;
	}
	
	public boolean process(byte value) throws Exception {
		System.out.println(count++ + " byte processor process...");

		if (key == value)
			return false;	// stop process
		return true; // keep process
	}
}
