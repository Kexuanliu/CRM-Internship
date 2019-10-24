CREATE TABLE `crm_agent` (
  `agent_id` varchar(36) NOT NULL DEFAULT '' COMMENT '代理ID',
  `company_name` varchar(50) NOT NULL DEFAULT '' COMMENT '代理公司名称',
  `profile` text COMMENT '简介',
  `website` varchar(50) DEFAULT '' COMMENT '网站',
  `customer_from` varchar(20) NOT NULL DEFAULT '' COMMENT '客户来源',
  `customer_level` varchar(20) NOT NULL COMMENT '客户等级',
  `cooperation` varchar(20) NOT NULL COMMENT '合作意向',
  `cooperation_type` varchar(20) NOT NULL DEFAULT '' COMMENT '合作方式',
  `create_ts` datetime NOT NULL,
  `create_name` varchar(50) NOT NULL DEFAULT '' COMMENT '创建者名称',
  `create_id` varchar(36) NOT NULL DEFAULT '',
  `updater_id` varchar(36) NOT NULL DEFAULT '',
  `update_ts` datetime NOT NULL,
  `is_del` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除 0未删除1 已删除',
  PRIMARY KEY (`agent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE `crm_agent_link` (
  `link_user_id` varchar(36) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '联系人ID',
  `agent_id` varchar(36) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `link_name` varchar(20) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '联系人名称',
  `link_position` varchar(20) CHARACTER SET utf8mb4 NOT NULL COMMENT '职位',
  `link_general` varchar(10) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '性别',
  `link_mobile` varchar(20) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '手机号',
  `link_phone` varchar(20) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '座机号',
  `link_weixin` varchar(50) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '微信号',
  `link_qq` varchar(20) CHARACTER SET utf8mb4 DEFAULT '' COMMENT 'qq',
  `link_mail` varchar(50) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '邮箱',
  `link_bg` text CHARACTER SET utf8mb4 COMMENT '联系人背景',
  `create_ts` datetime NOT NULL,
  `create_id` varchar(36) CHARACTER SET utf8mb4 NOT NULL DEFAULT '',
  `updater_id` varchar(36) CHARACTER SET utf8mb4 NOT NULL DEFAULT '',
  `update_ts` datetime NOT NULL,
  `is_del` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除 0未删除1 已删除',
  PRIMARY KEY (`link_user_id`),
  KEY `AGENTID_INDEX` (`agent_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

