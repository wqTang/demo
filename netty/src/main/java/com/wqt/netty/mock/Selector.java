package com.wqt.netty.mock;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.wqt.netty.mock.channel.Channel;

/** 
 * @author Wqt 
 * @version 创建时间：2017年10月17日 下午3:56:18 
 */
public class Selector{
	
	private Map<Channel, Object> registry = new HashMap<Channel, Object>();

	public void register(Channel channel) {
		if (Objects.isNull(channel))
			return;
		registry.put(channel, null);
	}
	
	public void select() {
		
	}
	
}
