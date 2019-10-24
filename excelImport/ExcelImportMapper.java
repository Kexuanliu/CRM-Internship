/**
 * @author ZXL
 * @date 2019/6/21 17:26
 */
package com.xuebei.crm.excelImport;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExcelImportMapper {

    void insertExcelBatchLog(ExcelImportBatchLog excelImportBatchLog);

    void insertExcelImportDetail(ExcelImportDetail excelImportDetail);

    List<ExcelImportBatchLog> getExcelImportBatchLogList(@Param("userId") String userId,@Param("type")Integer type);

}
