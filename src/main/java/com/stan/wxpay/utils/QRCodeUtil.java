package com.stan.wxpay.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * 功能描述:
 *
 * @author zz
 * @version 1.0
 * @date 2019/5/30 18:59
 */
public class QRCodeUtil {

    private static final int BLACK = 0xff000000;
    private static final int WHITE = 0xFFFFFFFF;

    // 默认宽为300
    private static Integer WIDTH = 300;
    // 默认高为300
    private static Integer HEIGHT = 300;
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    // 默认二维码图片格式
    private String IMAGEFORMAT = "png";
    private static final String IMAGE_FORMAT= "JPG";

    // 默认二维码字符编码
    private static String CHARTYPE = "utf-8";
    // 默认二维码的容错级别
    private static ErrorCorrectionLevel corretionLevel = ErrorCorrectionLevel.H;
    // 二维码与图片的边缘
    private static Integer MARGIN = 1;
    // 二维码参数
    private static Map<EncodeHintType, Object> encodeHits = new HashMap<EncodeHintType,Object>();


    /**
     * 功能描述: 生成二维码 BufferedImage.
     *
     * @param content
     * @param qrWidth
     * @param qrHeight
     * @return java.awt.image.BufferedImage
     * @author zz
     * @date 2019/5/31 9:13
     */
    public static BufferedImage getBufferImage(String content, int qrWidth, int qrHeight) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrWidth, qrHeight, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(qrWidth, qrHeight, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }

    /**
     * 功能描述:  生成base64格式二维码.
     *
     * @param content  content
     * @param qrWidth  qrWidth
     * @param qrHeight qrHeight
     * @return string
     * @author zz
     * @date 2019/5/31 9:18
     */
    public static String getBase64(String content, int qrWidth, int qrHeight) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            BufferedImage image = getBufferImage(content, qrWidth, qrHeight);
            //转换成png格式的IO流
            ImageIO.write(image, "png", byteArrayOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] bytes = byteArrayOutputStream.toByteArray();
        BASE64Encoder encoder = new BASE64Encoder();
        String base64 = encoder.encodeBuffer(bytes).trim();
        base64 = "data:image/png;base64," + base64;
        return base64;
    }

    public static void writeToFile(BitMatrix matrix, String format, File file)
            throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }

     //将二维码保存到输出流中
     public static void writeToStream(BitMatrix matrix, String format, OutputStream stream)
             throws IOException {
         BufferedImage image = toBufferedImage(matrix);
         if (!ImageIO.write(image, format, stream)) {
             throw new IOException("Could not write an image of format " + format);
         }
     }

    /**
     * 将路径生成二维码图片
     * @author chenp
     * @param content
     * @param response
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void encodeQrcode(String content, HttpServletResponse response){

        if(StringUtils.isBlank(content))
            return;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        Map hints = new HashMap();
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 250, 250,hints);
            BufferedImage image = toBufferedImage(bitMatrix);
            //输出二维码图片流
            try {
                ImageIO.write(image, "png", response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (WriterException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 类型转换
     * @author chenp
     * @param matrix
     * @return
     */
    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) == true ? BLACK : WHITE);
            }
        }
        return image;
    }

    // 初始化二维码的参数
    private static void initialParamers() {
        // 字符编码
        encodeHits.put(EncodeHintType.CHARACTER_SET, CHARTYPE);
        // 容错等级 L、M、Q、H 其中 L 为最低, H 为最高
        encodeHits.put(EncodeHintType.ERROR_CORRECTION, corretionLevel);
        // 二维码与图片边距
        encodeHits.put(EncodeHintType.MARGIN, MARGIN);
    }

    // 特殊字符处理
    public static String UrlEncode(String src)  throws UnsupportedEncodingException {
        return URLEncoder.encode(src, "UTF-8").replace("+", "%20");
    }

    /**
     * 功能描述:  test.
     *
     * @param
     * @return
     * @author zz
     * @date 2019/5/30 19:27
     */
    public static void main(String[] args) throws Exception {
        ImageIO.write(getBufferImage("4545454545", 500, 500), "jpg", new File("D:\\qrCode.jpg"));
        //System.out.println("--base64--" + getBase64("4545454545", 500, 500));
    }
}

