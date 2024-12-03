package cn.anlucky.system.utils;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


@Component
public class AvatarUtils {

    private static final int WIDTH = 200;
    private static final int HEIGHT = 200;
    private static final int BORDER_WIDTH = 5;

    /**
     * 生成头像
     *
     * @param name
     * @param isRound
     * @return
     * @throws IOException
     */
    public byte[] generateAvatar(String name, boolean isRound) throws IOException {
        // 根据名字首字母确定背景色
        char firstChar = name.charAt(0);
        Color backgroundColor;
        if (isChinese(firstChar)) {
            backgroundColor = getColorForChineseChar(firstChar);
        } else {
            backgroundColor = getColorForEnglishChar(firstChar);
        }

        BufferedImage image;
        Graphics2D g2d;
        if (isRound) {
            image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
            g2d = image.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 绘制圆形背景
            RoundRectangle2D.Float roundRect = new RoundRectangle2D.Float(0, 0, WIDTH, HEIGHT, WIDTH, HEIGHT);
            g2d.setColor(backgroundColor);
            g2d.fill(roundRect);

            // 绘制白色边框
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(BORDER_WIDTH));
            g2d.draw(roundRect);
        } else {
            image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
            g2d = image.createGraphics();
            g2d.setColor(backgroundColor);
            g2d.fillRect(0, 0, WIDTH, HEIGHT);

            // 绘制白色边框
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(BORDER_WIDTH));
            g2d.drawRect(0, 0, WIDTH - 1, HEIGHT - 1);
        }

        // 设置字体
        Font font;
        if (isChinese(firstChar)) {
            font = new Font("SimSun", Font.BOLD, WIDTH - 40);
        } else {
            font = new Font("Arial", Font.BOLD, WIDTH - 40);
        }
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);

        // 计算文字位置
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(getFirstLetter(name));
        int stringHeight = fontMetrics.getHeight();
        int x = (WIDTH - stringWidth) / 2;
        int y = (HEIGHT + stringHeight) / 2 - fontMetrics.getDescent() - 10;
        g2d.drawString(getFirstLetter(name), x, y);

        g2d.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);

        return baos.toByteArray();
    }

    /**
     * 判断是否是中文
     *
     * @param c
     * @return
     */
    private boolean isChinese(char c) {
        return c >= '\u4e00' && c <= '\u9fa5';
    }

    /**
     * 获取首字母
     *
     * @param name
     * @return
     */
    private String getFirstLetter(String name) {
        char firstChar = name.charAt(0);
        if (isChinese(firstChar)) {
            return String.valueOf(firstChar);
        } else {
            return String.valueOf(firstChar).toUpperCase();
        }
    }

    /**
     * 根据中文首字母确定背景色
     *
     * @param chineseChar
     * @return
     */
    private Color getColorForChineseChar(char chineseChar) {
        int index = chineseChar % 8;
        switch (index) {
            case 0:
                return new Color(255, 0, 0);
            case 1:
                return new Color(0, 255, 0);
            case 2:
                return new Color(0, 0, 255);
            case 3:
                return new Color(255, 255, 0);
            case 4:
                return new Color(0, 255, 255);
            case 5:
                return new Color(255, 0, 255);
            case 6:
                return new Color(128, 128, 128);
            default:
                return new Color(192, 192, 192);
        }
    }

    /**
     * 根据英文首字母确定背景色
     *
     * @param englishChar
     * @return
     */
    private Color getColorForEnglishChar(char englishChar) {
        int index = englishChar % 8;
        switch (index) {
            case 0:
                return new Color(128, 0, 128);
            case 1:
                return new Color(255, 165, 0);
            case 2:
                return new Color(128, 128, 0);
            case 3:
                return new Color(0, 128, 128);
            case 4:
                return new Color(128, 0, 0);
            case 5:
                return new Color(0, 128, 0);
            case 6:
                return new Color(0, 0, 128);
            default:
                return new Color(255, 255, 255);
        }
    }

    /**
     * 测试例子
     */
    //
    // @GetMapping(value = "/{name}/{shape}", produces = MediaType.IMAGE_PNG_VALUE)
    // public void getNameAvatar(@PathVariable String name, @PathVariable String shape, OutputStream outputStream) throws IOException {
    //     boolean isRound = "round".equals(shape);
    //     byte[] avatarBytes = avatarUtils.generateAvatar(name, isRound);
    //     outputStream.write(avatarBytes);
    //     outputStream.flush();
    // }
    //


}
