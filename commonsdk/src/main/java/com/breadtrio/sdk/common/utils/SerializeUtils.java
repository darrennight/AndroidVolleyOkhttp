package com.breadtrio.sdk.common.utils;

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Serialize Utils
 * 
 */
public class SerializeUtils {

	/**
	 * deserialization from file
	 * 
	 * @param filePath
	 * @return
	 * @throws RuntimeException if an error occurs
	 */
	public static Object deserialization(String filePath) {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(filePath));
			Object o = in.readObject();
			in.close();
			return o;
		} catch (FileNotFoundException e) {
			throw new RuntimeException("FileNotFoundException occurred. ", e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("ClassNotFoundException occurred. ", e);
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	/**
	 * serialize to file
	 * 
	 * @param filePath
	 * @param obj
	 * @return
	 * @throws RuntimeException if an error occurs
	 */
	public static void serialization(String filePath, Object obj) {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(filePath));
			out.writeObject(obj);
			out.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("FileNotFoundException occurred. ", e);
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
	}

	/**
	 * 序列化一个对象
	 * 
	 * @param object
	 * @return
	 */
	public static String getSeriString(Object object) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		String productBase64 = "";
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			productBase64 = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
		} catch (IOException e) {
			throw new RuntimeException("IOException occurred. ", e);
		} catch (OutOfMemoryError e) {
			throw new RuntimeException("OutOfMemoryError occurred. ", e);
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					throw new RuntimeException("IOException occurred. ", e);
				}
			}
		}
		return productBase64;
	}

	/**
	 * 反序列化一个对象
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static Object getObjectFromSerString(String data) throws Exception {
		byte[] objBytes = Base64.decode(data.getBytes(), Base64.DEFAULT);
		if (objBytes == null || objBytes.length == 0) {
			return null;
		}
		ByteArrayInputStream bi = null;
		ObjectInputStream oi = null;
		Object object = null;
		try {
			bi = new ByteArrayInputStream(objBytes);
			oi = new ObjectInputStream(bi);
			object = oi.readObject();
		} finally {
			if (oi != null) {
				oi.close();
			}
			if (bi != null) {
				bi.close();
			}
		}
		return object;
	}

}
