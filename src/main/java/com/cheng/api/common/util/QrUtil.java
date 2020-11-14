package com.cheng.api.common.util;

import com.cheng.api.common.config.Global;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author fengcheng
 * @version 2020/4/13
 */
public class QrUtil {

	/**
	 * 获取网络图片流
	 */
	public static InputStream getImageStream(String url) {
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setReadTimeout(5000);
			connection.setConnectTimeout(5000);
			connection.setRequestMethod("GET");
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				return connection.getInputStream();
			}
		} catch (IOException e) {
			System.out.println("获取网络图片出现异常，图片路径为：" + url);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 识别二维码
	 */
	public static String qrReader(URL url) {
		try {
			MultiFormatReader formatReader = new MultiFormatReader();
			//读取指定的二维码文件
			BufferedImage bufferedImage = ImageIO.read(url);
			BinaryBitmap binaryBitmap = new BinaryBitmap(
					new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
			//定义二维码参数
			Map<DecodeHintType, Object> config = new HashMap<>(3);
			config.put(DecodeHintType.CHARACTER_SET, "UTF-8");
			Result result = formatReader.decode(binaryBitmap, config);
			bufferedImage.flush();
			return result.getText();
		} catch (IOException | NotFoundException e) {
			return null;
		}
	}

	/**
	 * 识别二维码
	 */
	public static String qrReader(File file) {
		try {
			MultiFormatReader formatReader = new MultiFormatReader();
			//读取指定的二维码文件
			BufferedImage bufferedImage = ImageIO.read(file);
			BinaryBitmap binaryBitmap = new BinaryBitmap(
					new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
			//定义二维码参数
			Map<DecodeHintType, Object> config = new HashMap<>(3);
			config.put(DecodeHintType.CHARACTER_SET, "UTF-8");
			Result result = formatReader.decode(binaryBitmap, config);
			bufferedImage.flush();
			return result.getText();
		} catch (IOException | NotFoundException e) {
			return null;
		}
	}

	private static BufferedImage creatRrCode(String content) {
		Map<EncodeHintType, Object> config = new HashMap<>(3);
		//设置容错级别最高
		config.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		//设置字符编码为utf-8
		config.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		//二维码空白区域,最小为0也有白边,只是很小,最小是6像素左右
		config.put(EncodeHintType.MARGIN, 0);
		try {
			// 读取文件转换为字节数组
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 200, 200, config);
			// 转为图片对象格式
			return toBufferedImage(bitMatrix);
			// 转换成png格式的IO流
		} catch (WriterException e) {
			return null;
		}
	}

	public static void getQrStream(String content, HttpServletResponse response) {
		// 转换成png格式的IO流
		try {
			BufferedImage bufferedImage = creatRrCode(content);
			if (bufferedImage != null) {
				ImageIO.write(bufferedImage, "png", response.getOutputStream());
			}
		} catch (IOException ignored) {
		}
	}

	public static String getQrImage(String content) {
		String realPath = "";
		String fileName = "";
		BufferedImage bufferedImage = creatRrCode(content);
		if (bufferedImage != null) {
			String path = Global.getFileUploadPath();
			realPath = "images/";
			fileName = UUID.randomUUID().toString().replace("-", "") + ".png";
			FileUtils.createDirectory(path + realPath);
			try {
				// 输出图片文件到指定位置
				ImageIO.write(bufferedImage, "png", new File(path + realPath + fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return realPath + fileName;
	}

	/**
	 * image流数据处理
	 */
	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
			}
		}
		return image;
	}

}
