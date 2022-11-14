package com.scientific.audit.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.scientific.audit.common.model.base.Result;
import com.scientific.audit.config.QiNiuProperties;
import com.scientific.audit.service.QiNiuStorageService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.scientific.audit.common.model.base.ResultCode.UPLOAD_ERROR;

@RestController
@RequestMapping("/files")
@Api(tags = "文件操作")
@Slf4j
public class FileController {

    private final QiNiuStorageService qiNiuStorageService;

    public FileController(QiNiuStorageService qiNiuStorageService) {
        this.qiNiuStorageService = qiNiuStorageService;
    }


    /**
     * 文件上传
     */
    @PostMapping("/upload")
    @Operation(summary = "文件上传接口")
    public Result upload(MultipartFile file) {
        qiNiuStorageService.uploadFile("file", file);
        return Result.ok("/" + qiNiuStorageService.uploadFile("file", file));
    }


    /**
     * 富文本文件上传接口
     */
    @Operation(summary = "富文本文件上传接口")
    @PostMapping("/editor/upload")
    public JSON editorUpload(MultipartFile file) throws IOException {

        String url = qiNiuStorageService.uploadFile("img", file);

        JSONObject json = new JSONObject();
        json.set("errno", 0);
        JSONArray arr = new JSONArray();
        JSONObject data = new JSONObject();
        arr.add(data);
        data.set("url", "/" + url);
        json.set("data", arr);

        return json;  // 返回结果 url
    }


}
