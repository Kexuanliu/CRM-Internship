/**
 * @author ZXL
 * @date 2019/7/23 10:38
 */
package com.xuebei.crm.utils;

import java.util.function.Function;

/**
 * @author Rong Weicheng
 */
public class ColumnExtractor<T> {

    private String columnName;
    private Function<T, String> extractor;

    public ColumnExtractor(String columnName, Function<T, String> extractor) {
        this.columnName = columnName;
        this.extractor = extractor;
    }

    public String extractValue(T t) {
        return extractor.apply(t);
    }

    public String getColumnName() {
        return columnName;
    }
}
