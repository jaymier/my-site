package com.jaymie.utils;

import com.jaymie.config.spring.SpringBeanFactoryUtils;
import com.jaymie.service.file.impl.TPubFileServiceImpl;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.env.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Properties;

/**
 * 描述: fastDFS分布式文件上传下载工具类
 * 作者: 李君其
 * 日期: 2018/12/10 15:37
 */
public class FastDFSUtil {


    private TrackerClient trackerClient = null;// 声明跟踪器客户端对象
    private TrackerServer trackerServer = null;// 声明跟踪器服务对象
    private StorageServer storageServer = null;// 声明存储器服务对象
    private StorageClient1 storageClient = null;// 声明存储器客户端对象

    private static Environment env = SpringBeanFactoryUtils.getApplicationContext().getBean(Environment.class);

    public FastDFSUtil(String conf) {
        try {
            if (conf.contains("classpath:")) {
                String path = URLDecoder.decode(getClass().getProtectionDomain().getCodeSource().getLocation().toString(), "UTF-8");
                path = path.substring(6);
                conf = conf.replace("classpath:", URLDecoder.decode(path, "UTF-8"));
            }
            ClientGlobal.init(conf);
            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            storageServer = null;
            storageClient = new StorageClient1(trackerServer, storageServer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static FastDFSUtil getFastProperties() {
        FastDFSUtil cilent = null;
        try {
            String fileName = env.getProperty("fastdfs.file.name");
            cilent = new FastDFSUtil(File.separator + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cilent;
    }

    /**
     * 功能描述:fastDFS上传文件
     * 参数: fileName 文件的磁盘路径名称 如：D:/image/aaa.jpg
     * 参数：extName 文件扩展名
     * 参数：metas 文件扩展信息描述
     * 作者: 李君其
     * 日期: 2018/12/10 15:50
     * 返回值: java.lang.String
     */
    public String uploadFile(String fileName, String extName, NameValuePair[] metas) {
        String result = null;
        try {
            result = storageClient.upload_file1(fileName, extName, metas);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 功能描述:fastDFS上传文件
     * 参数: fileName 文件的磁盘路径名称 如：D:/image/aaa.jpg
     * 作者: 李君其
     * 日期: 2018/12/10 15:50
     * 返回值: java.lang.String
     */
    public String uploadFile(String fileName) {
        return uploadFile(fileName, null, null);
    }

    /**
     * 功能描述:fastDFS上传文件
     * 参数: fileName 文件的磁盘路径名称 如：D:/image/aaa.jpg
     * 参数：extName 文件扩展名
     * 作者: 李君其
     * 日期: 2018/12/10 15:50
     * 返回值: java.lang.String
     */
    public String uploadFile(String fileName, String extName) {
        return uploadFile(fileName, extName, null);
    }

    /**
     * 功能描述:fastDFS上传文件
     * 参数: fileContent 文件的内容，字节数组
     * 参数：extName 文件扩展名
     * 参数 metas 文件扩展信息描述
     * 作者: 李君其
     * 日期: 2018/12/10 15:50
     * 返回值: java.lang.String
     */
    public String uploadFile(byte[] fileContent, String extName, NameValuePair[] metas) {
        String result = null;
        try {
            result = storageClient.upload_file1(fileContent, extName, metas);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 功能描述:fastDFS上传文件
     * 参数: fileContent 文件的内容，字节数组
     * 作者: 李君其
     * 日期: 2018/12/10 15:50
     * 返回值: java.lang.String
     */
    public String uploadFile(byte[] fileContent) throws Exception {
        return uploadFile(fileContent, null, null);
    }

    /**
     * 功能描述:fastDFS上传文件
     * 参数: fileContent 文件的内容，字节数组
     * 参数：extName 文件扩展名
     * 作者: 李君其
     * 日期: 2018/12/10 15:50
     * 返回值: java.lang.String
     */
    public String uploadFile(byte[] fileContent, String extName) {
        return uploadFile(fileContent, extName, null);
    }

    /**
     * 功能描述:文件下载到磁盘
     * 参数: path 文件路径
     * 参数：output 输出流 包含要输出到磁盘的路径
     * 作者: 李君其
     * 日期: 2018/12/10 15:50
     * 返回值: java.lang.String
     */
    public void download_file(String path, BufferedOutputStream output) {
        int result = -1;
        try {
            byte[] b = storageClient.download_file1(path);
            try {
                if (b != null) {
                    output.write(b);
                }
            } catch (Exception e) {
            } //用户可能取消了下载
            finally {
                if (output != null) try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述:获取文件数组
     * 参数: path 文件路径 如group1/M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg
     * 作者: 李君其
     * 日期: 2018/12/10 15:50
     * 返回值: java.lang.Byte
     */
    public byte[] download_bytes(String path) {
        byte[] b = null;
        try {
            b = storageClient.download_file1(path);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 功能描述:删除文件
     * 参数: group 组名 如：group1
     * 参数：storagePath 不带组名的路径名称 如：M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg
     * 作者: 李君其
     * 日期: 2018/12/10 15:50
     * 返回值: java.lang.Integer -1失败,0成功
     */
    public Integer delete_file(String group, String storagePath) {
        int result = -1;
        try {
            result = storageClient.delete_file(group, storagePath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 功能描述:删除文件
     * 参数：storagePath  文件的全部路径 如：group1/M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg
     * 作者: 李君其
     * 日期: 2018/12/10 15:50
     * 返回值: java.lang.Integer -1失败,0成功
     */
    public Integer delete_file(String storagePath) {
        int result = -1;
        try {
            result = storageClient.delete_file1(storagePath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getIpPort(){
        String ipPort = "";
        try {
            String fileName = env.getProperty("fastdfs.file.name");
            Properties pro = new Properties();
            InputStream in = null;
            in = TPubFileServiceImpl.class.getClassLoader().getResourceAsStream(File.separator + fileName);
            pro.load(in);
            ipPort = pro.getProperty("file_path");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ipPort;
    }
}
