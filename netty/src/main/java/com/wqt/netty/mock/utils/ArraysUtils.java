package com.wqt.netty.mock.utils;

import java.util.Arrays;
import java.util.Objects;

/** 
 * @author Wqt 
 * @version 创建时间：2017年10月17日 下午4:39:12 
 * 
 */
public class ArraysUtils {
	
	public static void add(Object[] arr, Object var) {
		for (int i=arr.length - 1; i >= 0; i--) {
			if (!Objects.isNull(arr[arr.length - 1]))	// full
				break;
			if (!Objects.isNull(arr[i])) {
				arr[i + 1] = var;
				return;
			}
		}
		
		// full
		arr = Arrays.copyOf(arr, arr.length + 1);
		arr[arr.length - 1] = var;
	}

	public static void remove(Object[] arr, Object var) {
		for (Object obj : arr)
			if (obj.equals(var))
				obj = null;
	}
}
