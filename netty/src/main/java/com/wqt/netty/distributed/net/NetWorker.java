package com.wqt.netty.distributed.net;

import com.wqt.netty.distributed.config.Config;
import com.wqt.netty.simple.initializer.EchoServerInitializer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/** 
 * @author Wqt 
 * @date 2017年12月12日 下午4:20:28 
 * @version v1.0
 */
public class NetWorker {
	
	private Config config;
	private EventLoopGroup boss;
	private EventLoopGroup work;
	
	public NetWorker(Config config) {
		this.config = config;
		boss = new NioEventLoopGroup();
		work = new NioEventLoopGroup();
	}
	
	public void start() {
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(boss, work);
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.localAddress(config.getLocalPort());
		bootstrap.childHandler(null);
		
		try {
			ChannelFuture future = bootstrap.bind().sync();
			future.channel().closeFuture().sync();
			
			boss.shutdownGracefully().sync();
			work.shutdownGracefully().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
