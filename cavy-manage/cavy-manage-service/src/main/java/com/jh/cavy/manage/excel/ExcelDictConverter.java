package com.jh.cavy.manage.excel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

@SuppressWarnings("rawtypes")
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
    public String convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return CellDataTypeEnum.STRING.name();
    }

    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<String> context) throws Exception {
        String value = context.getValue();
        ExcelContentProperty excelContentProperty = context.getContentProperty();
        GlobalConfiguration globalConfiguration = context.getWriteContext().currentWriteHolder().globalConfiguration();
        if (value == null) {
            return new WriteCellData<>(CellDataTypeEnum.EMPTY);
        }
        // 类里需要转换的字段
        Field field = excelContentProperty.getField();
        String fieldName = field.getName();
        String excelValue = "";
        excelValue = switch (fieldName) {
            case "gender" -> switch ((String) value) {
                case "1" -> "男";
                case "2" -> "女";
                default -> excelValue;
            };
            case "defaultUser" -> switch ((String) value) {
                case "1" -> "是";
                case "0" -> "否";
                default -> excelValue;
            };
            case "status" ->
                // 0已新建;1已使用;2已禁用;3已冻结
                    switch ((String) value) {
                        case "0" -> "已新建";
                        case "1" -> "已使用";
                        case "2" -> "已禁用";
                        case "3" -> "已冻结";
                        default -> excelValue;
                    };
            default -> "";
        };
        return new WriteCellData<>(StringUtils.isBlank(excelValue) ? value : excelValue);
    }
}
