package com.wqt.netty.oio;

import com.wqt.netty.DefaultChannelInitializer;
import com.wqt.netty.oio.inbound.OioServerChannelHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;

/** 
 * @author Wqt 
 * @version 创建时间：2017年10月17日 下午2:46:50 
 * 
 */
public class OioServer {
	
	private final int port;
	
	public OioServer(int port) {
		this.port = port;
	}
	
	public void start() throws Exception {
		ServerBootstrap bootstrap = new ServerBootstrap();
		EventLoopGroup group = new OioEventLoopGroup(); // oio transport
		ChannelHandler handler = new OioServerChannelHandler();
		ChannelInitializer<SocketChannel> initializer = new DefaultChannelInitializer(handler);
		
		bootstrap.group(group);
		bootstrap.channel(OioServerSocketChannel.class); // oio server channel
		bootstrap.localAddress(port);
		bootstrap.childHandler(initializer);
		
		ChannelFuture future = bootstrap.bind().sync();
		future.channel().closeFuture().sync();
		
		group.shutdownGracefully().sync();
	}
	
	public static void main(String[] args) throws Exception {
		OioServer server = new OioServer(5889);
		server.start();
	}
}
