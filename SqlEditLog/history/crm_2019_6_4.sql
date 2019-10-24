drop table role_user;

drop table role_department;

drop table crm_role;

drop table checkpoint;

drop table department;

drop table department_user;

drop table user_job;
drop table job_invite;
drop table job;

drop table nm_remark;


drop table notice;
drop table refund_attachment;

drop table start_apply_rec;

drop table task;


drop table holiday;



ALTER TABLE `visit_log`
  ADD COLUMN `contact_type`  int(11) NULL DEFAULT -1 COMMENT '-1 未知, 0 无联系, 1 1-10分钟,2 10分钟以上' AFTER `sales_opportunity_id`;


ALTER TABLE `company_user`
  ADD COLUMN `rels_fullpath`  varchar(255) NULL DEFAULT '' COMMENT '用户ID拼接成' AFTER `is_delete`;


ALTER TABLE `journal`
  DROP INDEX `Refcompany_user922` ,
  ADD INDEX `Refcompany_user922` (`user_id`) USING HASH ;

ALTER TABLE `journal`
  ADD COLUMN `create_time`  int(11) NULL DEFAULT NULL COMMENT '创建时间 yyyyMMdd ' AFTER `update_ts`;

#修正历史数据
update journal set create_time=CONCAT(DATE_FORMAT(create_ts,'%Y%m%d') ,user_id,type_cd)

# 执行修复接口:
# /dataRepair/repairCompanyUserLeadPath