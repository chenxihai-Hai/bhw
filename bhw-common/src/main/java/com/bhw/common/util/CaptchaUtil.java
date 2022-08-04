package com.bhw.common.util;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 验证码工具类
 */
public class CaptchaUtil {
    /**
     * 验证码长度
     */
    public static final int CAPTCHA_LENGTH = 4;

    /**
     * 获取随机颜色
     * @param fc
     * @param bc
     * @return
     */
    public static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 输出JPEG验证码图片流
     * @param response
     * @return 返回验证码文本
     * @throws Exception
     */
    public static String captchaOutput(HttpServletResponse response) throws Exception{
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);
        int width=68, height=30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Random random = new Random();
        g.setColor(getRandColor(200,250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman",Font.PLAIN,18));
        g.setColor(getRandColor(160,200));
        for (int i=0;i<155;i++){
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x,y,x+xl,y+yl);
        }
        String codes = "0123456789abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CAPTCHA_LENGTH; i++){
            String rand = String.valueOf(codes.charAt(random.nextInt(codes.length())));
            sb.append(rand);
            g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
            g.drawString(rand,13*i+6,16);
        }
        g.dispose();
        ImageIO.write(image, "JPEG", response.getOutputStream());

        return sb.toString();
    }
}
