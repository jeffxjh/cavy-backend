package com.jh.cavy.file.minio.handle;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class ExcelHandle {

    public <T,M> void readExcel(InputStream inputStream, ReadListener<T> t, Class<M> module) {
        ExcelReader excelReader = EasyExcel.read(inputStream, module, t).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        excelReader.read(readSheet);
        excelReader.finish();
    }
}
