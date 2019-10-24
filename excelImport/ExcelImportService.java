/**
 * @author ZXL
 * @date 2019/6/21 18:20
 */
package com.xuebei.crm.excelImport;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExcelImportService {

    void insertExcelBatchLog(ExcelImportBatchLog excelImportBatchLog);

    void insertExcelImportDetail(ExcelImportDetail excelImportDetail);

    List<ExcelImportBatchLog> getExcelImportBatchLogList(String userId, Integer type);

    ImportResult importAgentExcel(MultipartFile file, String fileName,String userId);

    ImportResult importOppExcel(MultipartFile file, String userId);

    ImportResult importSchoolExcel(MultipartFile file, String excelName, String userId);

    ImportResult importJournalExcel(MultipartFile file, String excelName, String userId);
}
