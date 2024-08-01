package com.spt.sp_template.controller;

import cn.hutool.core.io.FileUtil;
import com.spt.sp_template.annotation.AuthAccess;
import com.spt.sp_template.common.Result;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {
    @Value("${host:localhost}")
    String host;
    @Value("${server.port}")
    String port;

    private static final String ROOT_PATH = System.getProperty("user.dir") + File.separator + "files"; //图片存储的目录
    //单文件上传
    @AuthAccess
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();//文件的原始名称
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));//截取文件后缀名
        String objectName = UUID.randomUUID().toString()+extension;//UUID构造新文件名称
        String mainName = FileUtil.mainName(objectName);// aaa
        String extName = FileUtil.extName(objectName);// png
        //存储文件
        if (!FileUtil.exist(ROOT_PATH)){
            FileUtil.mkdir(ROOT_PATH);
        }
        File saveFile = new File(ROOT_PATH + File.separator + objectName);//父级目录拼接原始名称
        file.transferTo(saveFile);//存储文件到本地的磁盘里面去
        String url = "http://"+host+":"+port+"/file/download/"+ objectName;
        return Result.success(url);  //返回文件的链接,这个链接就是文件的下载地址,这个下载地址就是后台提供出来的
    }
    //下载
    //IO流
    @AuthAccess
    @GetMapping("/download/{fileName}")
    //@PathVariable表示路径参数
    public void download(@PathVariable String fileName , HttpServletResponse response) throws IOException {//fileName=下载文件名称
        String filePath = ROOT_PATH + File.separator + fileName;
        if (!FileUtil.exist(filePath)){
            return;
        }
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);//数组是一个字节数组,也就是文件的字节流数组
        outputStream.flush();
        outputStream.close();
    }
}
