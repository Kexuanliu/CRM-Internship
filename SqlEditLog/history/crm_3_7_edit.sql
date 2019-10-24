# 日志中添加其他字段
ALTER TABLE `journal`
  ADD COLUMN `other`  text NULL AFTER `plan`;


#院校中添加客户来源字段
ALTER TABLE `crm_customer`
  ADD COLUMN `customer_from`  varchar(20) NOT NULL DEFAULT '' COMMENT '客户来源' AFTER `website`;

#院校添加是否删除字段
ALTER TABLE `crm_customer`
  ADD COLUMN `is_del`  tinyint NOT NULL DEFAULT 0 COMMENT '是否删除 0未删除1 已删除' AFTER `update_ts`;

#将之前今日总结数据保留到与客户无关字段上
update journal set other=summary;

#将中文枚举转换为英文枚举
update crm_customer set type_cd='COLLEAGE' where type_cd='高校';

update crm_customer set type_cd='VOCATIONSCHOOL' where type_cd='高职';

update crm_customer set type_cd='SECONDARYSCHOOL' where type_cd='中职';

update crm_customer set type_cd='AGENT' where type_cd='代理商';

#默认客户来源为公司
update crm_customer set customer_from='COMPANY'