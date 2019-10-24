#为商机添加两个字段, 一个是是否包含学呗, 1是 2否 0 未填写 一个是项目资金来源
ALTER TABLE `sales_opportunity`
  ADD COLUMN `contain_xuebei` tinyint(4) NULL COMMENT '是否包含学呗' AFTER `charge_id`,
  ADD COLUMN `money_from` varchar(50) NULL COMMENT '资金来源' AFTER `contain_xuebei`;

#0否 1是
ALTER TABLE `sales_opportunity`
  ADD COLUMN `is_excel_import` tinyint(4) NULL DEFAULT 0 COMMENT '是否通过excel导入' AFTER `money_from`;
