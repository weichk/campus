# execute some ddl at dao inited


CREATE TABLE `campus_customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_no` varchar(64) DEFAULT NULL COMMENT '用户号',
  `user_name` varchar(64) DEFAULT NULL COMMENT '用户名',
  `real_name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `cert_no` varchar(32) DEFAULT NULL COMMENT '身份证号',
  `customer_status` varchar(32) DEFAULT NULL COMMENT '状态',
  `ali_auth_code` varchar(128) DEFAULT NULL COMMENT '阿里支付授权码',
  `mobile_no` varchar(11) DEFAULT NULL COMMENT '手机号',
  `ext_json` varchar(512) DEFAULT NULL COMMENT 'json扩展信息',
  `agreement_no` varchar(255) DEFAULT NULL COMMENT '用户签约号',
  `alipay_user_id` varchar(255) DEFAULT NULL COMMENT '阿里用户id',
  `auth_app_id` varchar(255) DEFAULT NULL COMMENT '阿里用户appId',
  `sign_time` datetime DEFAULT NULL COMMENT '用户签约时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员信息';

CREATE TABLE `campus_customer_card` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `card_no` varchar(32) DEFAULT NULL COMMENT '持卡编号(能拿到就用卡号，不能拿到就自己生成唯一编码)',
  `shool_code` varchar(32) DEFAULT NULL COMMENT '学校编码(campus_shool.shool_code)',
  `shool_name` varchar(32) DEFAULT NULL COMMENT '学校名称',
  `customer_user_no` varchar(64) DEFAULT NULL COMMENT '会员用户编号(campus_customer.user_no)',
  `real_name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `cert_no` varchar(32) DEFAULT NULL COMMENT '身份证号',
  `card_status` varchar(32) DEFAULT NULL COMMENT '状态{ENABLE:有效,DISABLE:无效}',
  `mobile_no` varchar(11) DEFAULT NULL COMMENT '手机号',
  `ext_json` varchar(512) DEFAULT NULL COMMENT 'json扩展信息',
  `alipay_user_id` varchar(255) DEFAULT NULL COMMENT '阿里用户id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员卡信息';


CREATE TABLE `campus_shool` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `shool_code` varchar(32) DEFAULT NULL COMMENT '学校编码',
  `shool_name` varchar(32) DEFAULT NULL COMMENT '学校名称',
  `contacts_name` varchar(32) DEFAULT NULL COMMENT '学校联系人姓名',
  `mobile_no` varchar(16) DEFAULT NULL COMMENT '学校联系电话',
  `ali_auth_code` varchar(64) DEFAULT NULL COMMENT '支付宝操作权码',
  `ali_pid` varchar(64) NOT NULL COMMENT '学校支付宝PID',
  `address` varchar(128) DEFAULT NULL COMMENT '学校地址',
  `province` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '省',
  `city` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '市',
  `district` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '县',
  `shool_status` varchar(16) DEFAULT NULL COMMENT '状态{ENABLE:有效,DISABLE:无效}',
  `ext_json` varchar(512) DEFAULT NULL COMMENT 'json扩展信息',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学校信息';


CREATE TABLE `campus_trade` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `trade_no` varchar(32) DEFAULT NULL COMMENT '交易订单号',
  `shool_no` varchar(32) DEFAULT NULL COMMENT '学校编号',
  `customer_user_no` varchar(64) DEFAULT NULL COMMENT '会员订单号',
  `card_no` varchar(32) DEFAULT NULL COMMENT '持卡编号',
  `trade_type` varchar(16) DEFAULT NULL COMMENT '交易类型{CARD:卡余额交易,ONLINE:在线交易}',
  `amount` bigint(20) DEFAULT NULL COMMENT '交易金额',
  `balance` bigint(20) DEFAULT NULL COMMENT '交易后余额',
  `trade_status` varchar(16) DEFAULT NULL COMMENT '交易状态{SUCCESS:成功,FAILURE:失败}',
  `gateway_stauts` varchar(16) DEFAULT NULL COMMENT '网关状态{PROCESSING:处理中,SUCCESS:成功,FAILURE:失败}',
  `comment` varchar(128) DEFAULT NULL COMMENT '外部备注',
  `memo` varchar(128) DEFAULT NULL COMMENT '内部备注',
  `ext_json` varchar(512) DEFAULT NULL COMMENT 'json扩展信息',
  `trade_time` datetime DEFAULT NULL COMMENT '交易完成时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易记录';


CREATE TABLE `campus_gateway_deduct` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `channel_type` varchar(16) DEFAULT NULL COMMENT '渠道类型{ALI:支付宝}',
  `channel_api` varchar(32) DEFAULT NULL COMMENT '渠道服务api',
  `batch_no` varchar(64) DEFAULT NULL COMMENT '批次号',
  `biz_order_no` varchar(64) DEFAULT NULL COMMENT '业务订单号',
  `bank_order_no` varchar(64) DEFAULT NULL COMMENT '银行订单号',
  `user_no` varchar(64) DEFAULT NULL COMMENT '交易主体用户号(本业务对应campus_customer.user_no)',
  `user_ip` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户ip',
  `ref_user_no` varchar(64) DEFAULT NULL COMMENT '交易对象用户号(本业务对应收单shool，这里看是shool授权码还是shool_no)',
  `status` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '订单状态{INIT:初始状态,BS:成功,BF:银行失败,BP:银行处理中}',
  `notify_status` varchar(16) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '通知状态{INIT:未通知,SUCCESS:通知成功,FAIL:通知失败}',
  `notify_send_count` int(11) DEFAULT NULL COMMENT '通知次数',
  `notify_date` datetime DEFAULT NULL COMMENT '通知时间',
  `amount` bigint(20) DEFAULT NULL COMMENT '交易申请金额',
  `suc_amount` bigint(20) DEFAULT NULL COMMENT '成功金额',
  `memo` varchar(128) DEFAULT NULL COMMENT '内部备注',
  `valid_date` datetime DEFAULT NULL COMMENT '有效时间',
  `ext_json` varchar(512) DEFAULT NULL COMMENT 'json扩展信息',
  `bank_resp_message` text DEFAULT NULL COMMENT '银行响应报文',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网关代扣记录';