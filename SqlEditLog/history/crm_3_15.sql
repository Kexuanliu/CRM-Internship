ALTER TABLE `sales_opportunity`
  ADD COLUMN `agent_id`  varchar(36) DEFAULT '' COMMENT '代理商ID' AFTER `progress`,
  ADD COLUMN `decision_maker_id`  varchar(36) NOT NULL DEFAULT '' COMMENT '决策者ID' AFTER `agent_id`,
  ADD COLUMN `agent_link_id`  varchar(36)  DEFAULT '' COMMENT '代理商联系人ID' AFTER `decision_maker_id`,
  ADD COLUMN `charge_id`  varchar(36) NOT NULL DEFAULT '' COMMENT '负责人ID' AFTER `agent_link_id`,
  AUTO_INCREMENT=2;



ALTER TABLE `customer_dept`
  ADD COLUMN `create_id`  varchar(36) NOT NULL DEFAULT '' COMMENT '创建者ID' AFTER `profile`,
  ADD COLUMN `create_name`  varchar(50) NULL DEFAULT '' COMMENT '创建者名称' AFTER `create_id`;



ALTER TABLE `customer_dept`
  ADD COLUMN `create_ts`  date NULL COMMENT '创建时间' AFTER `create_name`;


CREATE TABLE `visit_agents` (
  `visit_agents_id` int(11) NOT NULL AUTO_INCREMENT,
  `visit_log_id` int(11) NOT NULL COMMENT '拜访记录ID',
  `agent_link_id` varchar(36) NOT NULL COMMENT '联系人ID',
  `agent_id` varchar(36) NOT NULL COMMENT '代理商ID',
  `create_ts` date NOT NULL COMMENT '创建时间',
  `create_id` varchar(36) NOT NULL,
  `create_name` varchar(20) NOT NULL,
  `show_info` varchar(20) DEFAULT NULL COMMENT '展示出来的缓存字段',
  PRIMARY KEY (`visit_agents_id`),
  KEY `Refvisit_log822` (`visit_log_id`),
  KEY `Refcontacts812` (`agent_link_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4;



#数据同步,将以前的联系人转换为决策人和负责人
update sales_opportunity a,opportunity_contacts b set a.decision_maker_id=b.contacts_id,a.charge_id=b.contacts_id where a.sales_opportunity_id=b.sales_opportunity_id



