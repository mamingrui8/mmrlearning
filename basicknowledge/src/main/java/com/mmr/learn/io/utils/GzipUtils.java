package com.mmr.learn.io.utils;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Description: Gzip压缩&解压缩  由jdk提供
 * 一般用于传递大数据，如图片信息
 * User: MaMingRui
 * Email: mamr@broada.com
 * Date: 2019年02月08日 21:32
 * ModificationHistory: Who         When         What
 * ---------  --------     ---------------------------
 */
public class GzipUtils {
    public static void main(String[] args){
        try {
            FileInputStream fis = new FileInputStream(new File("D://1.jpg"));
            byte[] temp = new byte[fis.available()];
            int length = fis.read(temp);
            System.out.println("长度: " + length);

            byte[] zipArray = GzipUtils.zip(temp);
            System.out.println("压缩后的长度: " + zipArray.length);

            byte[] unzipArray = GzipUtils.unzip(zipArray);
            System.out.println("解压后的长度: " + unzipArray.length);

            FileOutputStream fos = new FileOutputStream("D://1/101.jpg");
            fos.write(unzipArray);

            fos.flush();
            fos.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解压缩
     * @param source 源数据。 需要解压的数据
     * @return 解压后的数据  即 恢复后的数据
     * @throws Exception
     */
    public static byte[] unzip(byte[] source) throws Exception{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(source);
        //GZIPInputStream 由JDK提供，专门用于压缩和解压缩使用的流对象
        GZIPInputStream zipIn = new GZIPInputStream(in);
        byte[] temp = new byte[256];
        int length = 0;
        while((length = zipIn.read(temp, 0, temp.length)) != -1){
            out.write(temp, 0, length);
        }

        //将字节数组输出流中的数据转换成解析后的字节数组
        byte[] target = out.toByteArray();

        //关闭流  (如果是装饰流，只需关闭最外层即可)
        zipIn.close();
        out.close();

        return target;
    }

    /**
     * 压缩
     * 把源数据压缩成 压缩数据
     */
    public static byte[] zip(byte[] source) throws Exception{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream zipOut = new GZIPOutputStream(out);

        //将压缩信息写入至内存中，写入的过程会实现压缩。
        zipOut.write(source);
        zipOut.finish();
        byte[] target = out.toByteArray();

        zipOut.close();

        return target;
    }

}
