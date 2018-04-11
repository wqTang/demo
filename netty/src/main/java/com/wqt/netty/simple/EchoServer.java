package com.wqt.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import com.wqt.netty.simple.inbound.EchoServerHandler;
import com.wqt.netty.simple.initializer.EchoServerInitializer;

/**
 * Created by Wqt on 2017/10/16
 */
public class EchoServer {

	private final int port;

	public EchoServer(int port) {
		this.port = port;
	}

	public void start() throws InterruptedException {
		ServerBootstrap bootstrap = new ServerBootstrap();
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		ChannelHandler handler = new EchoServerHandler();
		ChannelInitializer<SocketChannel> initializer = new EchoServerInitializer(handler);

		/** Configuration */
		bootstrap.group(boss, worker);
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.localAddress(port);
		bootstrap.handler(null); // for boss
		bootstrap.childHandler(initializer); // for worker

		ChannelFuture future = bootstrap.bind().sync(); // DefaultPromise.sync();
		future.channel().closeFuture().sync();

		boss.shutdownGracefully().sync();
		worker.shutdownGracefully().sync();
	}

	public static void main(String[] args) throws Exception {
		EchoServer echoServer = new EchoServer(5889);
		echoServer.start();
	}
}
