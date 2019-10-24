ALTER TABLE `crm_prd`.`visit_log`
  ADD COLUMN `out_type` int(255) NULL COMMENT '1市内 2室外' AFTER `contact_type`,
  ADD COLUMN `out_reason` varchar(255) NULL COMMENT '外出缘由' AFTER `out_type`,
  ADD COLUMN `is_excel_import` int(255) ZEROFILL NULL DEFAULT 0 COMMENT '1 是 0 否 是否excel导入' AFTER `out_reason`;

# 设置上传文件大小
#spring.servlet.multipart.maxFileSize=100MB
#spring.servlet.multipart.maxRequestSize=100MB
#