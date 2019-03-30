package cn.hjc.pdf.util;

import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.*;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * FastDFS工具类【实现文件上传、下载、删除、查询】
 * @author Zhangyongliang
 */
public class FastDFSClient {

    // 本地文件位置
    String local = "E:\\桌面\\Java 9 新特性介绍.pdf";
    // 声明跟踪器客户端对象
    TrackerClient trackerClient = null;
    // 声明存储器客户端对象
    StorageClient1 storageClient1 = null;
    // 声明跟踪器服务对象
    TrackerServer trackerServer = null;
    // 声明存储器服务对象
    StorageServer storageServer = null;

    // 文件上传
    public String testUpload(String path) {
        try {
            // 初始化配置文件
            ClientGlobal.init("fdfs_client.conf");
            // 创建跟踪器客户端对象
            trackerClient = new TrackerClient();
            // 获取跟踪器连接
            trackerServer = trackerClient.getConnection();
            // 获取存储器客户端对象
            storageClient1 = new StorageClient1(trackerServer, storageServer);
            // 上传文件，返回文件标识
            String index = storageClient1.upload_file1(path, null, null);
            // 查看标识
            return index;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 文件下载
    public void testDownload() {
        try {
            ClientGlobal.init("fdfs_client.conf");
            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            storageClient1 = new StorageClient1(trackerServer, storageServer);
            // 根据文件标识下载文件
            byte[] by = storageClient1.download_file1("group1/M00/00/00/rBADhFycOl-AZ1IIABJPE3U5sSU364.pdf");
            // 将数据写入输出流
            IOUtils.write(by, new FileOutputStream("E:\\桌面\\111.pdf"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    // 文件删除
    public void testDelete() {
        try {
            ClientGlobal.init("fdfs_client.conf");
            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            storageClient1 = new StorageClient1(trackerServer, storageServer);
            // 根据文件标识删除文件，返回0则删除成功
            int i = storageClient1.delete_file1("group1/M00/00/00/wKhphVry2QmAXgH2AANfM1yHJic724.txt");
            if (i == 0) {
                System.out.println("删除成功");
            } else {
                System.out.println("删除失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    // 文件信息
    public void testGetFileInfo() {
        try {
            ClientGlobal.init("fdfs_client.conf");
            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            storageClient1 = new StorageClient1(trackerServer, storageServer);
            // 根据文件标识获取文件信息
            FileInfo fileInfo = storageClient1.get_file_info1("group1/M00/00/00/wKhphVry2QmAXgH2AANfM1yHJic724.txt");
            // 文件IP地址
            System.out.println(fileInfo.getSourceIpAddr());
            // 文件大小
            System.out.println(fileInfo.getFileSize());
            // 创建时间
            System.out.println(fileInfo.getCreateTimestamp());
            // 错误校验码
            System.out.println(fileInfo.getCrc32());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
}