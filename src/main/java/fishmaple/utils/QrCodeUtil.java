package fishmaple.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;


public class QrCodeUtil {

    // 二维码颜色
    private static final int BLACK = 0xFF000000;
    // 二维码颜色
    private static final int WHITE = 0xFFFFFFFF;

    public static void generateQRCode(String text,String targetImg,String downText,
                                      int width, int height, String format,
                                      HttpServletResponse response,String color)
            throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");// 指定编码格式
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);// 指定纠错等级
        hints.put(EncodeHintType.MARGIN, 1); // 白边大小，取值范围0~4
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        writeToStream(bitMatrix,targetImg,downText, format, response.getOutputStream(),color);
    }

    /**
     * 输出二维码图片流
     *
     * @param text 二维码内容
     * @param width 二维码宽
     * @param height 二维码高
     * @param format 图片格式eg: png, jpg, gif
     * @param response HttpServletResponse
     * @throws Exception
     */
    public static void generateQRCode(String text,String targetImg,String downText, int width, int height, String format, HttpServletResponse response)
            throws Exception {
        generateQRCode(text,targetImg,downText,width,height,format,response,null);
    }
    public static void generateQRCode(String text, int width, int height, String format, HttpServletResponse response)
            throws Exception {
        generateQRCode(text,null,null,width,height,format,response,null);
    }
    /**
     * 生成二维码图片流
     *
     * @param matrix
     * @param format
     * @param stream
     * @throws IOException
     */
    private static void writeToStream(BitMatrix matrix ,String targetImg,String downText,String format, OutputStream stream,String color) throws IOException {
        BufferedImage image=toBufferedImage(matrix,color);
        if(targetImg!=null) {
            File file = new File(targetImg);
            Image src = ImageIO.read(file);
            int widthLogo = image.getWidth()/6;
            int heightLogo = image.getWidth()/6; //保持二维码是正方形的
            int x = (image.getWidth() - widthLogo) / 2;
            int y = (image.getHeight() - heightLogo) / 2 ;
            Graphics2D g = image.createGraphics();
            g.drawImage(src, x, y, widthLogo, heightLogo, null);
        }
        if(downText!=null&&!downText.equals("")) {
            int width = matrix.getWidth();
            int height = matrix.getHeight();
            Graphics g = image.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0,height-10,height-10,10);
            g.setColor(Color.BLACK);
            g.setFont(new Font("宋体",0,12));


            g.drawString(downText, width/2-3*getFloatRight(downText), height-10);
            g.dispose();
        }
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }
    /**
     * @param matrix
     * @return
     */
    private static BufferedImage toBufferedImage(BitMatrix matrix,String colorSubject) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int color=0;
        if(colorSubject==null)
            color=BLACK;
        for (int x = 0; x < width; x++) {
            if(colorSubject!=null){
            color=0xFF160CBF+256*(x/2);}
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? color : WHITE);
            }
        }
        return image;
    }
    private static int getDandomHex(){
        StringBuffer result = new StringBuffer();
        for(int i=0;i<2;i++) {
            result.append(Integer.toHexString(new Random().nextInt(16)));
        }
        String s=result.toString();
        return Integer.parseInt(s,16);
    }
    private static int getFloatRight(String str){
        int i=0;
        for(char c:str.toCharArray()){
            if(String.valueOf(c).matches("[\u4e00-\u9fa5]")){
            i+=2;
            }else{
                i++;
            }
        }
        return i;
    }
}
