package org.fleetopgroup.web.util;

import java.util.Base64;

import javax.xml.bind.DatatypeConverter;



public class ByteConvertor {

	public static byte[] base64toByteArray(String imageStr) throws Exception {

		Base64.Decoder	decoder		= null;
		byte[]			imageByte	= null;

		try {
			decoder			= Base64.getDecoder();
			imageByte		= decoder.decode(imageStr);
			return imageByte;
		} catch (NullPointerException _e) {
			return null;
		} catch (Exception _e) {
			return null;
		} finally {
			decoder			= null;
			imageByte		= null;
		}
	}

	public static String byteArraytoBase64(byte[] imageByte) throws Exception {
		try {
			return DatatypeConverter.printBase64Binary(imageByte);
		} catch (NullPointerException e) {
			return null;
		} catch (Exception e) {
			return null;
		} finally {

		}
	}
	
	public static byte[] base64ToByte(String data) throws IllegalArgumentException {
	        try {
	            return Base64.getDecoder().decode(data);
	        } catch (IllegalArgumentException e) {
	            throw new IllegalArgumentException("Invalid Base64 string: " + e.getMessage(), e);
	        }
	    }
}
