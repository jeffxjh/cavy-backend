package com.jh.cavy.manage.excel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

public class ExcelDictConverter implements Converter<String> {
    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return null;
    }

    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return CellDataTypeEnum.STRING.name();
    }

    @Override
    public CellData convertToExcelData(String o, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if (o == null) {
            return new CellData(CellDataTypeEnum.EMPTY);
        }
        // 类里需要转换的字段
        Field field = excelContentProperty.getField();
        String fieldName = field.getName();
        String excelValue="";
        switch (fieldName) {
            case "gender":
                switch ((String) o) {
                    case "1":
                        excelValue = "男";
                        break;
                    case "2":
                        excelValue = "女";
                        break;
                }

                break;
            case "defaultUser":
                switch ((String) o) {
                    case "1":
                        excelValue = "是";
                        break;
                    case "0":
                        excelValue = "否";
                        break;
                }

                break;
            case "status":
                //0已新建;1已使用;2已禁用;3已冻结
                switch ((String) o) {
                    case "0":
                        excelValue = "已新建";
                        break;
                    case "1":
                        excelValue = "已使用";
                        break;
                    case "2":
                        excelValue = "已禁用";
                        break;
                    case "3":
                        excelValue = "已冻结";
                        break;
                }
                break;
        }

        return new CellData<>(StringUtils.isBlank(excelValue)?o:excelValue);

    }
}
