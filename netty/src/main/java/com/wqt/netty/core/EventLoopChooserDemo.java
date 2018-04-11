package com.wqt.netty.core;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * There are two kinds of EventExecutorChooser in EventLoopGroup. <br/>
 * They both in the class:
 * io.netty.util.concurrent.DefaultEventExecutorChooserFactory <br/>
 * 
 * @see PowerOfTwoEventExecutorChooser
 * @see GenericEventExecutorChooser
 * 
 * @author iuShu
 * @date Apr 2, 2018 8:14:37 PM
 */
public class EventLoopChooserDemo {

	AtomicInteger idx = new AtomicInteger();
	public static int executorsLength = 16;

	public int powerOfTwo() {
		System.out.println("[power] idx: " + idx.get());
		return idx.getAndIncrement() & executorsLength - 1;
	}

	public int generic() {
		System.out.println("[generic] idx: " + idx.get());
		return Math.abs(idx.getAndIncrement() % executorsLength);
	}

	public void reset() {
		idx = new AtomicInteger();
	}

	public static void main(String[] args) {
		EventLoopChooserDemo demo = new EventLoopChooserDemo();
		int access = 20;

		int i = 0;
		for (;;) {
			if (++i > access)
				break;
			int pot = demo.powerOfTwo();
			System.out.println("[power] " + pot);
		}

		System.out.println();

		i = 0;
		demo.reset();
		for (;;) {
			if (++i > access)
				break;
			int gen = demo.generic();
			System.out.println("[generic] " + gen);
		}
	}

}
