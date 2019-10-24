ALTER TABLE `crm_agent`
  ADD COLUMN `is_excel_import` tinyint(4) NULL DEFAULT 0 COMMENT '是否通过excel导入' AFTER `is_del`;

#具体插入的数据
CREATE TABLE `excel_import_deail` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `batch_log_id` bigint(20) DEFAULT NULL COMMENT '批量记录日志ID',
  `result` tinyint(4) DEFAULT '0' COMMENT '插入结果 1 成功 0失败',
  `show_info` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '展示结果',
  `create_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者ID',
  `execute_code` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '执行码',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

#批次
CREATE TABLE `excel_import_batch_log` (
  `batch_log_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_id` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `import_type` tinyint(4) DEFAULT NULL COMMENT '1代理商 2院校',
  PRIMARY KEY (`batch_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


ALTER TABLE `crm_agent`
  ADD COLUMN `province` varchar(50) NULL COMMENT '省' AFTER `is_excel_import`,
  ADD COLUMN `city` varchar(50) NULL COMMENT '市' AFTER `province`,
  ADD COLUMN `region` varchar(50) NULL COMMENT '销售区域(华中华南之类)' AFTER `city`;