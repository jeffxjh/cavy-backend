package com.jh.cavy.file.minio.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.benjaminwan.ocrlibrary.OcrResult;
import com.jh.cavy.file.minio.domain.CavyFile;
import com.jh.cavy.file.minio.mapper.FileMapper;
import com.jh.cavy.file.minio.service.FileService;
import com.jh.cavy.file.minio.vo.FileVO;
import io.github.mymonstercat.Model;
import io.github.mymonstercat.ocr.InferenceEngine;
import io.github.mymonstercat.ocr.config.ParamConfig;
import jakarta.annotation.Resource;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.rendering.ImageType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, CavyFile> implements FileService {
    @Resource
    private FileMapper fileMapper;

    @Override
    public List<FileVO> batchUpload() {
        ArrayList<CavyFile> objects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CavyFile cavyFile = new CavyFile();
            cavyFile.setWebUrl("" + i);
            cavyFile.setFileCode(i + "");
            cavyFile.setFileName(i + "");
            cavyFile.setFilePath(i + "");
            objects.add(cavyFile);
        }
        this.saveBatch(objects);
        CavyFile byId = this.getById(16);
        byId.setFilePath("1");
        this.saveOrUpdate(byId);
        return BeanUtil.copyToList(objects, FileVO.class);
    }

    @Override
    public void ocr(MultipartFile file) throws Exception {
        ParamConfig paramConfig = ParamConfig.getDefaultConfig();
        paramConfig.setDoAngle(true);
        paramConfig.setMostAngle(true);
        InferenceEngine engine = InferenceEngine.getInstance(Model.ONNX_PPOCR_V3);
        String filePath = "/tmp/" + file.getOriginalFilename();
        File tmpFile = new File(filePath);
        FileUtil.writeFromStream(file.getInputStream(), tmpFile, true);
        List<String> list = List.of(filePath);
        if ("pdf".equalsIgnoreCase(FileNameUtil.extName(filePath))) {
            list = pdf2png("/tmp/", file.getOriginalFilename(), "PNG");
            //convertToImage(filePath, "/tmp/"+FileNameUtil.getPrefix(file.getOriginalFilename())+".png",file.getOriginalFilename()+"png",144);
        }
        for (String f : list) {
            // 开始识别
            OcrResult ocrResult = engine.runOcr(f, paramConfig);
            System.out.println(ocrResult.getStrRes().trim());
        }

    }

    /**
     * 将pdf转换为多张图片，返回多张图片地址，将原文件名末尾添加序号
     *
     * @param fileAddress
     * @param filename
     * @param type
     * @return
     */
    public static List<String> pdf2png(String fileAddress, String filename, String type) {
        List<String> list = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        // 将文件地址和文件名拼接成路径 注意：线上环境不能使用\\拼接
        File file = new File(fileAddress + "\\" + filename);
        try {
            // 写入文件
            PDDocument doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            for (int i = 0; i < pageCount; i++) {
                // dpi为144，越高越清晰，转换越慢
                BufferedImage image = renderer.renderImageWithDPI(i, 144); // Windows native DPI
                // 将图片写出到该路径下
                ImageIO.write(image, type, new File(fileAddress + "\\" + filename + "_" + (i + 1) + "." + type));
                list.add(fileAddress + "\\" + filename + "_" + (i + 1) + "." + type);
            }
            long endTime = System.currentTimeMillis();
            System.out.println("共耗时：" + ((endTime - startTime) / 1000.0) + "秒");  //转化用时
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * PDF文件转换为长图
     *
     * @param pdfFile    pdf 路径
     * @param outputFile 保存文件
     * @param formatName 格式 png|jpg 等其它 ImageIO  支持的格式
     * @param dpi        DPI
     * @throws IOException 异常
     */
    public static void convertToImage(String pdfFile, String outputFile, String formatName, int dpi) throws IOException {
        File file = new File(pdfFile);
        PDDocument document = PDDocument.load(file);
        PDFRenderer renderer = new PDFRenderer(document);
        long startTime = System.currentTimeMillis();
        try {
            PDPageTree pages = document.getPages();
            float width = 0; // 合并后图片的宽度
            float height = 0; // 合并后图片的高度
            float spaceHeight = 5; //页面间距
            // 获取每张原始图片的尺寸信息
            double scale = dpi / 72.0F;

            //算一下整张的大小
            for (int i = 0; i < pages.getCount(); i++) {
                PDPage page = pages.get(i);
                PDRectangle bBox = page.getCropBox();
                float heightPt = bBox.getHeight();
                float widthPt = bBox.getHeight();
                int widthPx = (int) Math.max(Math.floor(widthPt * scale), 1.0);
                int heightPx = (int) Math.max(Math.floor(heightPt * scale), 1.0);
                width = Math.max(widthPx, width);
                height += heightPx;
                if (i > 0) {
                    height += spaceHeight;
                }
            }
            BufferedImage mergedImg = new BufferedImage(Math.round(width), Math.round(height),
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = mergedImg.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, Math.round(width), Math.round(height));

            int yOffset = 0; // 当前位置的x偏移值
            for (int i = 0; i < pages.getCount(); i++) {
                // 渲染页面为 BufferedImage
                BufferedImage image = renderer.renderImageWithDPI(i, dpi, ImageType.RGB);
                int xOffset = (mergedImg.getWidth() - image.getWidth()) / 2; // 居中显示
                g.drawImage(image, xOffset, yOffset, null);
                yOffset += (int) (image.getHeight() + spaceHeight); //上加页面间距
            }
            g.dispose();
            //写文件
            ImgUtil.write(mergedImg, new File(outputFile));
        } finally {
            System.out.println("用时:" + (System.currentTimeMillis() - startTime));
            document.close();
        }

    }
}
