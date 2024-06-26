/**
 * 
 */
package org.fleetopgroup.web.controller;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author Satheesh kumar
 *
 */
public class ByteImageConvert {
	
	//convert image size code
		public byte[] scale(byte[] fileData, int width, int height) throws Exception {
			ByteArrayInputStream in = new ByteArrayInputStream(fileData);
			try {
				BufferedImage img = ImageIO.read(in);
				if (height == 0) {
					height = (width * img.getHeight()) / img.getWidth();
				}
				if (width == 0) {
					width = (height * img.getWidth()) / img.getHeight();
				}
				Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
				BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0, 0, 0), null);

				ByteArrayOutputStream buffer = new ByteArrayOutputStream();

				ImageIO.write(imageBuff, "jpg", buffer);

				return buffer.toByteArray();
			} catch (IOException e) {
				throw new Exception("IOException in scale");
			}
		}

}
