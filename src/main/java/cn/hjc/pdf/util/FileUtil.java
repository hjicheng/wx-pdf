package cn.hjc.pdf.util;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class FileUtil {

    /**
     * 获取文件的md5
     * @param filePath 文件地址
     * @return
     */
    public static String getFileMd5Value(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return "";
        }
        byte[] buffer = new byte[2048];
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            FileInputStream in = new FileInputStream(file);
            while (true) {
                int len = in.read(buffer, 0, 2048);
                if (len != -1) {
                    digest.update(buffer, 0, len);
                } else {
                    break;
                }
            }
            in.close();

            byte[] md5Bytes = digest.digest();
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }
}

