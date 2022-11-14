package com.scientific.audit.service;

import cn.hutool.core.util.IdUtil;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.scientific.audit.common.model.base.ResultCode;
import com.scientific.audit.config.QiNiuProperties;
import com.scientific.audit.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class QiNiuStorageService {

    private final QiNiuProperties qiNiuProperties;

    public QiNiuStorageService(QiNiuProperties qiNiuProperties) {
        this.qiNiuProperties = qiNiuProperties;
    }


    /**
     * 文件上传
     * @param path 文件在七牛云云中的子路径 不能以 “/” 开头
     */
    public String uploadFile(String path, MultipartFile file) {
        try {

            //1、获取文件上传的流
            byte[] fileBytes = file.getBytes();
            //2、创建日期目录分隔
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String datePath = dateFormat.format(new Date());

            //3、获取文件名
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String flag = IdUtil.fastSimpleUUID();

            //        String flag = IdUtil.fastSimpleUUID();
            String filename = path + "/" + flag + "_" + originalFilename;  // 获取上传的路径

            //4.构造一个带指定 Region 对象的配置类
            Configuration cfg = new Configuration(Region.huanan());
            UploadManager uploadManager = new UploadManager(cfg);

            //5.获取七牛云提供的 token
            Auth auth = Auth.create(qiNiuProperties.getAccessKey(), qiNiuProperties.getAccessSecretKey());
            String upToken = auth.uploadToken(qiNiuProperties.getBucket());
            uploadManager.put(fileBytes, filename, upToken);

            return filename;

        } catch (IOException exception) {
            throw new CustomException(ResultCode.UPLOAD_ERROR);
        }
    }


}
