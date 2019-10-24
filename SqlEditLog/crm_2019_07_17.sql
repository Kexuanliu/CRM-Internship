#更新顺序, 首先使用部门创建时间, 如果没有寻找日志里的时间, 同时补全部门时间为最早日志时间, 否则为默认时间
#/dataRepair/repairContacts
ALTER TABLE `contacts`
  ADD COLUMN `create_ts` datetime NULL COMMENT '创建时间' AFTER `is_excel_import`,
  ADD COLUMN `create_id` varchar(50) NULL COMMENT '创建人员' AFTER `create_ts`,
  ADD COLUMN `create_name` varchar(50) NULL COMMENT '创建人员姓名' AFTER `create_id`;

#/dataRepair/repairSaleOppAgent
