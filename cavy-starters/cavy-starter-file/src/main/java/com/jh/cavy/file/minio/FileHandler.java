package com.jh.cavy.file.minio;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import com.jh.cavy.file.minio.domain.File;
import com.jh.cavy.file.minio.mapper.FileMapper;
import com.jh.cavy.file.minio.utils.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;

@Slf4j
@Service
@Transactional
public class FileHandler {
    @Autowired(required = false)
    private MinioUtil minioUtil;
    @Resource
    private FileMapper fileMapper;

    /**
     * 上传文件
     */
    public void uploadFile(MultipartFile file) {
        try {
            // 事务问题未解决
            File fileInfo = new File();
            String filePath = minioUtil.uploadFile(file);
            String webUrl = minioUtil.getMinioProperties().getWebUrl();
            if (!webUrl.endsWith("/")) {
                webUrl = webUrl + "/";
            }
            fileInfo.setWebUrl( webUrl + filePath);
            fileInfo.setFilePath(filePath);
            String ssha256 = SecureUtil.sha256(file.getInputStream());
            String md5 = MD5.create().digestHex(file.getBytes());
            fileInfo.setFileCode(ssha256);
            fileInfo.setFileName(file.getOriginalFilename());
            fileMapper.insert(fileInfo);
        } catch (Exception e) {
            log.error("uploadFile [{}]", e.getMessage());
        }
    }

    /**
     * 下载文件
     */
    public void downLoad(String id, HttpServletResponse response) {
        File file = fileMapper.selectById(id);
        if (file!=null&&StrUtil.isNotBlank(file.getFileName()) && StrUtil.isNotBlank(file.getFilePath())) {
            try {
                InputStream inputStream = minioUtil.downloadFile(file.getFilePath());
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + URLEncoder.encode(file.getFileName(), "UTF-8"));
                response.setCharacterEncoding("UTF-8");
                IoUtil.copy(inputStream, response.getOutputStream());
            } catch (Exception e) {
                log.error("downLoad [{}]", e.getMessage());
            }
        }
    }


    /**
     * 删除文件
     */
    public void deleteFile(String id) {
        try {
            File file = fileMapper.selectById(id);
            if (file != null) {
                fileMapper.deleteById(id);
                minioUtil.removeFile(file.getFilePath());
            }
        } catch (Exception e) {
            log.error("deleteFile [{}]", e.getMessage());
        }
    }
}
