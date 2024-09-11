package com.jh.cavy.file.minio;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import com.jh.cavy.file.minio.domain.CavyFile;
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
            CavyFile cavyFileInfo = new CavyFile();
            String filePath = minioUtil.uploadFile(file);
            String webUrl = minioUtil.getMinioProperties().getWebUrl();
            if (!webUrl.endsWith("/")) {
                webUrl = webUrl + "/";
            }
            cavyFileInfo.setWebUrl( webUrl + filePath);
            cavyFileInfo.setFilePath(filePath);
            String ssha256 = SecureUtil.sha256(file.getInputStream());
            String md5 = MD5.create().digestHex(file.getBytes());
            cavyFileInfo.setFileCode(ssha256);
            cavyFileInfo.setFileName(file.getOriginalFilename());
            fileMapper.insert(cavyFileInfo);
        } catch (Exception e) {
            log.error("uploadFile [{}]", e.getMessage());
        }
    }

    /**
     * 下载文件
     */
    public void downLoad(String id, HttpServletResponse response) {
        CavyFile cavyFile = fileMapper.selectById(id);
        if (cavyFile !=null&&StrUtil.isNotBlank(cavyFile.getFileName()) && StrUtil.isNotBlank(cavyFile.getFilePath())) {
            try {
                InputStream inputStream = minioUtil.downloadFile(cavyFile.getFilePath());
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + URLEncoder.encode(cavyFile.getFileName(), "UTF-8"));
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
            CavyFile cavyFile = fileMapper.selectById(id);
            if (cavyFile != null) {
                fileMapper.deleteById(id);
                minioUtil.removeFile(cavyFile.getFilePath());
            }
        } catch (Exception e) {
            log.error("deleteFile [{}]", e.getMessage());
        }
    }
}
