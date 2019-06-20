/*
 * www.prosysoft.com Inc.
 * Copyright (c) 2017 All Rights Reserved
 */

/*
 * 修订记录:
 * shuijing 2018-10-08 16:05 创建
 */
package com.qiudot.edu.util;


import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;

/**
 * @author shuijing
 */
public class QRCodeUtil {

    /**
     * 生成二维码
     *
     * @param stream
     * @param width   宽
     * @param height  高
     * @param format  格式
     * @param content 内容
     * @throws IOException
     */
    public static void createQRCode(OutputStream stream, int width, int height, int margin, String level
            , String format, String content) throws IOException {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            Hashtable hints = new Hashtable();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.valueOf(level));// 纠错等级L,M,Q,H
            hints.put(EncodeHintType.MARGIN, margin); // 边距
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, height, width, hints);
            MatrixToImageWriter.writeToStream(bitMatrix, format, stream);
        } catch (WriterException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }

    /**
     * 读取二维码
     *
     * @throws IOException
     * @throws NotFoundException
     */
    public static String readQRCode(String qrcodeUrl) throws IOException, NotFoundException {
        MultiFormatReader read = new MultiFormatReader();
        URL url = new URL(qrcodeUrl);

        HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
        httpUrl.connect();
        BufferedImage image = ImageIO.read(httpUrl.getInputStream());
        Binarizer binarizer = new HybridBinarizer(new BufferedImageLuminanceSource(image));
        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
        Result res = read.decode(binaryBitmap);
        return res.toString();
    }

    /*public static void main(String[] args) throws IOException, WriterException {
        int margin = 0;
        String level = "L";
        String format = "jpg";
        String content = "http://tool.yoodb.com/qrcode/generate";
        QRCodeUtil.createQRCode(new FileOutputStream(new File("/var/log/webapps/nfs/authImg/ivsA.jpg")), 200, 200, margin, level, format, content);
    }*/
}
