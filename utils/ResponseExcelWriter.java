/**
 * @author ZXL
 * @date 2019/7/23 10:38
 */
package com.xuebei.crm.utils;

import org.apache.http.entity.ContentType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author Rong Weicheng
 */
public class ResponseExcelWriter<T> extends ExcelWriter<T> {

    private static final String ATTACHMENT_AND_FILENAME = "attachment;filename=";

    private static final String EXCELTYPE = ".xls";

    private static final String ISO = "iso-8859-1";

    public ResponseExcelWriter(List<ColumnExtractor<T>> extractors) {
        super(extractors);
    }

    public void write(HttpServletResponse response, String fileName, List<T> data) throws IOException {
        String sheetName = buildHttpHeader(response, fileName);
        try (OutputStream outputStream = response.getOutputStream()) {
            super.write(outputStream, sheetName, data);
        }
    }

    public void write(HttpServletResponse response, String fileName, List<T> data, List<String> finalLine) throws IOException {
        String sheetName = buildHttpHeader(response, fileName);
        try (OutputStream outputStream = response.getOutputStream()) {
            super.write(outputStream, sheetName, data, finalLine);
        }
    }

    private String buildHttpHeader(HttpServletResponse response, String fileName) throws IOException {
        String sheetName = fileName;
        if (!fileName.endsWith(EXCELTYPE)) {
            fileName += EXCELTYPE;
        }
        response.setContentType(ContentType.APPLICATION_OCTET_STREAM.getMimeType());
        String returnName = response.encodeURL(new String(fileName.getBytes(HttpConstants.ENCODING_UTF_8), ISO));
        response.addHeader(HttpConstants.HEADER_CONTENT_DISPOSITION, ATTACHMENT_AND_FILENAME + returnName);
        return sheetName;
    }
}

