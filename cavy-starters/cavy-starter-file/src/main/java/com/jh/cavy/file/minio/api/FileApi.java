package com.jh.cavy.file.minio.api;

import com.jh.cavy.file.minio.service.FileService;
import com.jh.cavy.file.minio.vo.FileVO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class FileApi {
    @Resource
    private FileService fileService;
    //实现接收的方法
    @CrossOrigin
    @PostMapping(value = "/api/uploadVidoe3")
    @ResponseBody
    public Map<String,String> savaVideotest(@RequestParam("file") MultipartFile file, @RequestParam String SavePath)
            throws IllegalStateException {
        Map<String,String> resultMap = new HashMap<>();
        try{
            //获取文件后缀，因此此后端代码可接收一切文件，上传格式前端限定
            String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1)
                                     .toLowerCase();
            // 重构文件名称
            System.out.println("前端传递的保存路径："+SavePath);
            String pikId = UUID.randomUUID().toString().replaceAll("-", "");
            String newVidoeName = pikId + "." + fileExt;
            System.out.println("重构文件名防止上传同名文件："+newVidoeName);
            //保存视频
            File fileSave = new File(SavePath, newVidoeName);
            file.transferTo(fileSave);
            //构造Map将视频信息返回给前端
            //视频名称重构后的名称
            resultMap.put("newVidoeName",newVidoeName);
            //正确保存视频则设置返回码为200
            resultMap.put("resCode","200");
            //返回视频保存路径
            resultMap.put("VideoUrl",SavePath + "/" + newVidoeName);
            return  resultMap;

        }catch (Exception e){
            e.printStackTrace();
            e.getMessage();
            //保存视频错误则设置返回码为400
            resultMap.put("resCode","400");
            return  resultMap ;

        }
    }


    @PostMapping("batchUpload")
    public List<FileVO> batchUpload() {
        return fileService.batchUpload();
    }


    @PostMapping("ocr")
    public void ocr(@RequestParam(name = "file") MultipartFile file) throws Exception {
        fileService.ocr(file);
    }
}
