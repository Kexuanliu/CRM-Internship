
CREATE TABLE province (
  id            int,
  name           varchar(64),
  province_id      varchar(12),
  PRIMARY KEY (id)
);



CREATE TABLE city (
  id           int,
  name          varchar(64),
  city_id       varchar(12),
  province_id   varchar(12),
  PRIMARY KEY (id)
);

INSERT INTO province VALUES ('1', '北京市', '110000000000');
INSERT INTO province VALUES ('2', '天津市', '120000000000');
INSERT INTO province VALUES ('3', '河北省', '130000000000');
INSERT INTO province VALUES ('4', '山西省', '140000000000');
INSERT INTO province VALUES ('5', '内蒙古自治区', '150000000000');
INSERT INTO province VALUES ('6', '辽宁省', '210000000000');
INSERT INTO province VALUES ('7', '吉林省', '220000000000');
INSERT INTO province VALUES ('8', '黑龙江省', '230000000000');
INSERT INTO province VALUES ('9', '上海市', '310000000000');
INSERT INTO province VALUES ('10', '江苏省', '320000000000');
INSERT INTO province VALUES ('11', '浙江省', '330000000000');
INSERT INTO province VALUES ('12', '安徽省', '340000000000');
INSERT INTO province VALUES ('13', '福建省', '350000000000');
INSERT INTO province VALUES ('14', '江西省', '360000000000');
INSERT INTO province VALUES ('15', '山东省', '370000000000');
INSERT INTO province VALUES ('16', '河南省', '410000000000');
INSERT INTO province VALUES ('17', '湖北省', '420000000000');
INSERT INTO province VALUES ('18', '湖南省', '430000000000');
INSERT INTO province VALUES ('19', '广东省', '510000000000');
INSERT INTO province VALUES ('20', '广西壮族自治区', '520000000000');
INSERT INTO province VALUES ('21', '海南省', '530000000000');
INSERT INTO province VALUES ('22', '香港特别行政区', '540000000000');
INSERT INTO province VALUES ('23', '澳门特别行政区', '550000000000');
INSERT INTO province VALUES ('24', '重庆市', '610000000000');
INSERT INTO province VALUES ('25', '四川省', '620000000000');
INSERT INTO province VALUES ('26', '贵州省', '630000000000');
INSERT INTO province VALUES ('27', '云南省', '640000000000');
INSERT INTO province VALUES ('28', '西藏自治区', '650000000000');
INSERT INTO province VALUES ('29', '陕西省', '710000000000');
INSERT INTO province VALUES ('30', '甘肃省', '720000000000');
INSERT INTO province VALUES ('31', '青海省', '730000000000');
INSERT INTO province VALUES ('32', '宁夏回族自治区', '740000000000');
INSERT INTO province VALUES ('33', '新疆维吾尔自治区', '750000000000');


INSERT INTO city VALUES ('1', '北京城区', '110100000000', '110000000000');
INSERT INTO city VALUES ('2', '天津城区', '120100000000', '120000000000');
INSERT INTO city VALUES ('3', '石家庄市', '130100000000', '130000000000');
INSERT INTO city VALUES ('4', '唐山市', '130200000000', '130000000000');
INSERT INTO city VALUES ('5', '秦皇岛市', '130300000000', '130000000000');
INSERT INTO city VALUES ('6', '邯郸市', '130400000000', '130000000000');
INSERT INTO city VALUES ('7', '邢台市', '130500000000', '130000000000');
INSERT INTO city VALUES ('8', '保定市', '130600000000', '130000000000');
INSERT INTO city VALUES ('9', '张家口市', '130700000000', '130000000000');
INSERT INTO city VALUES ('10', '承德市', '130800000000', '130000000000');
INSERT INTO city VALUES ('11', '沧州市', '130900000000', '130000000000');
INSERT INTO city VALUES ('12', '廊坊市', '131000000000', '130000000000');
INSERT INTO city VALUES ('13', '衡水市', '131100000000', '130000000000');
INSERT INTO city VALUES ('14', '太原市', '140100000000', '140000000000');
INSERT INTO city VALUES ('15', '大同市', '140200000000', '140000000000');
INSERT INTO city VALUES ('16', '阳泉市', '140300000000', '140000000000');
INSERT INTO city VALUES ('17', '长治市', '140400000000', '140000000000');
INSERT INTO city VALUES ('18', '晋城市', '140500000000', '140000000000');
INSERT INTO city VALUES ('19', '朔州市', '140600000000', '140000000000');
INSERT INTO city VALUES ('20', '晋中市', '140700000000', '140000000000');
INSERT INTO city VALUES ('21', '运城市', '140800000000', '140000000000');
INSERT INTO city VALUES ('22', '忻州市', '140900000000', '140000000000');
INSERT INTO city VALUES ('23', '临汾市', '141000000000', '140000000000');
INSERT INTO city VALUES ('24', '吕梁市', '141100000000', '140000000000');
INSERT INTO city VALUES ('25', '呼和浩特市', '150100000000', '150000000000');
INSERT INTO city VALUES ('26', '包头市', '150200000000', '150000000000');
INSERT INTO city VALUES ('27', '乌海市', '150300000000', '150000000000');
INSERT INTO city VALUES ('28', '赤峰市', '150400000000', '150000000000');
INSERT INTO city VALUES ('29', '通辽市', '150500000000', '150000000000');
INSERT INTO city VALUES ('30', '鄂尔多斯市', '150600000000', '150000000000');
INSERT INTO city VALUES ('31', '呼伦贝尔市', '150700000000', '150000000000');
INSERT INTO city VALUES ('32', '巴彦淖尔市', '150800000000', '150000000000');
INSERT INTO city VALUES ('33', '乌兰察布市', '150900000000', '150000000000');
INSERT INTO city VALUES ('34', '兴安盟', '152200000000', '150000000000');
INSERT INTO city VALUES ('35', '锡林郭勒盟', '152500000000', '150000000000');
INSERT INTO city VALUES ('36', '阿拉善盟', '152900000000', '150000000000');
INSERT INTO city VALUES ('37', '沈阳市', '210100000000', '210000000000');
INSERT INTO city VALUES ('38', '大连市', '210200000000', '210000000000');
INSERT INTO city VALUES ('39', '鞍山市', '210300000000', '210000000000');
INSERT INTO city VALUES ('40', '抚顺市', '210400000000', '210000000000');
INSERT INTO city VALUES ('41', '本溪市', '210500000000', '210000000000');
INSERT INTO city VALUES ('42', '丹东市', '210600000000', '210000000000');
INSERT INTO city VALUES ('43', '锦州市', '210700000000', '210000000000');
INSERT INTO city VALUES ('44', '营口市', '210800000000', '210000000000');
INSERT INTO city VALUES ('45', '阜新市', '210900000000', '210000000000');
INSERT INTO city VALUES ('46', '辽阳市', '211000000000', '210000000000');
INSERT INTO city VALUES ('47', '盘锦市', '211100000000', '210000000000');
INSERT INTO city VALUES ('48', '铁岭市', '211200000000', '210000000000');
INSERT INTO city VALUES ('49', '朝阳市', '211300000000', '210000000000');
INSERT INTO city VALUES ('50', '葫芦岛市', '211400000000', '210000000000');
INSERT INTO city VALUES ('51', '长春市', '220100000000', '220000000000');
INSERT INTO city VALUES ('52', '吉林市', '220200000000', '220000000000');
INSERT INTO city VALUES ('53', '四平市', '220300000000', '220000000000');
INSERT INTO city VALUES ('54', '辽源市', '220400000000', '220000000000');
INSERT INTO city VALUES ('55', '通化市', '220500000000', '220000000000');
INSERT INTO city VALUES ('56', '白山市', '220600000000', '220000000000');
INSERT INTO city VALUES ('57', '松原市', '220700000000', '220000000000');
INSERT INTO city VALUES ('58', '白城市', '220800000000', '220000000000');
INSERT INTO city VALUES ('59', '延边朝鲜族自治州', '222400000000', '220000000000');
INSERT INTO city VALUES ('60', '哈尔滨市', '230100000000', '230000000000');
INSERT INTO city VALUES ('61', '齐齐哈尔市', '230200000000', '230000000000');
INSERT INTO city VALUES ('62', '鸡西市', '230300000000', '230000000000');
INSERT INTO city VALUES ('63', '鹤岗市', '230400000000', '230000000000');
INSERT INTO city VALUES ('64', '双鸭山市', '230500000000', '230000000000');
INSERT INTO city VALUES ('65', '大庆市', '230600000000', '230000000000');
INSERT INTO city VALUES ('66', '伊春市', '230700000000', '230000000000');
INSERT INTO city VALUES ('67', '佳木斯市', '230800000000', '230000000000');
INSERT INTO city VALUES ('68', '七台河市', '230900000000', '230000000000');
INSERT INTO city VALUES ('69', '牡丹江市', '231000000000', '230000000000');
INSERT INTO city VALUES ('70', '黑河市', '231100000000', '230000000000');
INSERT INTO city VALUES ('71', '绥化市', '231200000000', '230000000000');
INSERT INTO city VALUES ('72', '大兴安岭地区', '232700000000', '230000000000');
INSERT INTO city VALUES ('73', '上海城区', '310100000000', '310000000000');
INSERT INTO city VALUES ('74', '南京市', '320100000000', '320000000000');
INSERT INTO city VALUES ('75', '无锡市', '320200000000', '320000000000');
INSERT INTO city VALUES ('76', '徐州市', '320300000000', '320000000000');
INSERT INTO city VALUES ('77', '常州市', '320400000000', '320000000000');
INSERT INTO city VALUES ('78', '苏州市', '320500000000', '320000000000');
INSERT INTO city VALUES ('79', '南通市', '320600000000', '320000000000');
INSERT INTO city VALUES ('80', '连云港市', '320700000000', '320000000000');
INSERT INTO city VALUES ('81', '淮安市', '320800000000', '320000000000');
INSERT INTO city VALUES ('82', '盐城市', '320900000000', '320000000000');
INSERT INTO city VALUES ('83', '扬州市', '321000000000', '320000000000');
INSERT INTO city VALUES ('84', '镇江市', '321100000000', '320000000000');
INSERT INTO city VALUES ('85', '泰州市', '321200000000', '320000000000');
INSERT INTO city VALUES ('86', '宿迁市', '321300000000', '320000000000');
INSERT INTO city VALUES ('87', '杭州市', '330100000000', '330000000000');
INSERT INTO city VALUES ('88', '宁波市', '330200000000', '330000000000');
INSERT INTO city VALUES ('89', '温州市', '330300000000', '330000000000');
INSERT INTO city VALUES ('90', '嘉兴市', '330400000000', '330000000000');
INSERT INTO city VALUES ('91', '湖州市', '330500000000', '330000000000');
INSERT INTO city VALUES ('92', '绍兴市', '330600000000', '330000000000');
INSERT INTO city VALUES ('93', '金华市', '330700000000', '330000000000');
INSERT INTO city VALUES ('94', '衢州市', '330800000000', '330000000000');
INSERT INTO city VALUES ('95', '舟山市', '330900000000', '330000000000');
INSERT INTO city VALUES ('96', '台州市', '331000000000', '330000000000');
INSERT INTO city VALUES ('97', '丽水市', '331100000000', '330000000000');
INSERT INTO city VALUES ('98', '合肥市', '340100000000', '340000000000');
INSERT INTO city VALUES ('99', '芜湖市', '340200000000', '340000000000');
INSERT INTO city VALUES ('100', '蚌埠市', '340300000000', '340000000000');
INSERT INTO city VALUES ('101', '淮南市', '340400000000', '340000000000');
INSERT INTO city VALUES ('102', '马鞍山市', '340500000000', '340000000000');
INSERT INTO city VALUES ('103', '淮北市', '340600000000', '340000000000');
INSERT INTO city VALUES ('104', '铜陵市', '340700000000', '340000000000');
INSERT INTO city VALUES ('105', '安庆市', '340800000000', '340000000000');
INSERT INTO city VALUES ('106', '黄山市', '341000000000', '340000000000');
INSERT INTO city VALUES ('107', '滁州市', '341100000000', '340000000000');
INSERT INTO city VALUES ('108', '阜阳市', '341200000000', '340000000000');
INSERT INTO city VALUES ('109', '宿州市', '341300000000', '340000000000');
INSERT INTO city VALUES ('110', '六安市', '341500000000', '340000000000');
INSERT INTO city VALUES ('111', '亳州市', '341600000000', '340000000000');
INSERT INTO city VALUES ('112', '池州市', '341700000000', '340000000000');
INSERT INTO city VALUES ('113', '宣城市', '341800000000', '340000000000');
INSERT INTO city VALUES ('114', '福州市', '350100000000', '350000000000');
INSERT INTO city VALUES ('115', '厦门市', '350200000000', '350000000000');
INSERT INTO city VALUES ('116', '莆田市', '350300000000', '350000000000');
INSERT INTO city VALUES ('117', '三明市', '350400000000', '350000000000');
INSERT INTO city VALUES ('118', '泉州市', '350500000000', '350000000000');
INSERT INTO city VALUES ('119', '漳州市', '350600000000', '350000000000');
INSERT INTO city VALUES ('120', '南平市', '350700000000', '350000000000');
INSERT INTO city VALUES ('121', '龙岩市', '350800000000', '350000000000');
INSERT INTO city VALUES ('122', '宁德市', '350900000000', '350000000000');
INSERT INTO city VALUES ('123', '南昌市', '360100000000', '360000000000');
INSERT INTO city VALUES ('124', '景德镇市', '360200000000', '360000000000');
INSERT INTO city VALUES ('125', '萍乡市', '360300000000', '360000000000');
INSERT INTO city VALUES ('126', '九江市', '360400000000', '360000000000');
INSERT INTO city VALUES ('127', '新余市', '360500000000', '360000000000');
INSERT INTO city VALUES ('128', '鹰潭市', '360600000000', '360000000000');
INSERT INTO city VALUES ('129', '赣州市', '360700000000', '360000000000');
INSERT INTO city VALUES ('130', '吉安市', '360800000000', '360000000000');
INSERT INTO city VALUES ('131', '宜春市', '360900000000', '360000000000');
INSERT INTO city VALUES ('132', '抚州市', '361000000000', '360000000000');
INSERT INTO city VALUES ('133', '上饶市', '361100000000', '360000000000');
INSERT INTO city VALUES ('134', '济南市', '370100000000', '370000000000');
INSERT INTO city VALUES ('135', '青岛市', '370200000000', '370000000000');
INSERT INTO city VALUES ('136', '淄博市', '370300000000', '370000000000');
INSERT INTO city VALUES ('137', '枣庄市', '370400000000', '370000000000');
INSERT INTO city VALUES ('138', '东营市', '370500000000', '370000000000');
INSERT INTO city VALUES ('139', '烟台市', '370600000000', '370000000000');
INSERT INTO city VALUES ('140', '潍坊市', '370700000000', '370000000000');
INSERT INTO city VALUES ('141', '济宁市', '370800000000', '370000000000');
INSERT INTO city VALUES ('142', '泰安市', '370900000000', '370000000000');
INSERT INTO city VALUES ('143', '威海市', '371000000000', '370000000000');
INSERT INTO city VALUES ('144', '日照市', '371100000000', '370000000000');
INSERT INTO city VALUES ('145', '莱芜市', '371200000000', '370000000000');
INSERT INTO city VALUES ('146', '临沂市', '371300000000', '370000000000');
INSERT INTO city VALUES ('147', '德州市', '371400000000', '370000000000');
INSERT INTO city VALUES ('148', '聊城市', '371500000000', '370000000000');
INSERT INTO city VALUES ('149', '滨州市', '371600000000', '370000000000');
INSERT INTO city VALUES ('150', '菏泽市', '371700000000', '370000000000');
INSERT INTO city VALUES ('151', '郑州市', '410100000000', '410000000000');
INSERT INTO city VALUES ('152', '开封市', '410200000000', '410000000000');
INSERT INTO city VALUES ('153', '洛阳市', '410300000000', '410000000000');
INSERT INTO city VALUES ('154', '平顶山市', '410400000000', '410000000000');
INSERT INTO city VALUES ('155', '安阳市', '410500000000', '410000000000');
INSERT INTO city VALUES ('156', '鹤壁市', '410600000000', '410000000000');
INSERT INTO city VALUES ('157', '新乡市', '410700000000', '410000000000');
INSERT INTO city VALUES ('158', '焦作市', '410800000000', '410000000000');
INSERT INTO city VALUES ('159', '濮阳市', '410900000000', '410000000000');
INSERT INTO city VALUES ('160', '许昌市', '411000000000', '410000000000');
INSERT INTO city VALUES ('161', '漯河市', '411100000000', '410000000000');
INSERT INTO city VALUES ('162', '三门峡市', '411200000000', '410000000000');
INSERT INTO city VALUES ('163', '南阳市', '411300000000', '410000000000');
INSERT INTO city VALUES ('164', '商丘市', '411400000000', '410000000000');
INSERT INTO city VALUES ('165', '信阳市', '411500000000', '410000000000');
INSERT INTO city VALUES ('166', '周口市', '411600000000', '410000000000');
INSERT INTO city VALUES ('167', '驻马店市', '411700000000', '410000000000');
INSERT INTO city VALUES ('169', '武汉市', '420100000000', '420000000000');
INSERT INTO city VALUES ('170', '黄石市', '420200000000', '420000000000');
INSERT INTO city VALUES ('171', '十堰市', '420300000000', '420000000000');
INSERT INTO city VALUES ('172', '宜昌市', '420500000000', '420000000000');
INSERT INTO city VALUES ('173', '襄阳市', '420600000000', '420000000000');
INSERT INTO city VALUES ('174', '鄂州市', '420700000000', '420000000000');
INSERT INTO city VALUES ('175', '荆门市', '420800000000', '420000000000');
INSERT INTO city VALUES ('176', '孝感市', '420900000000', '420000000000');
INSERT INTO city VALUES ('177', '荆州市', '421000000000', '420000000000');
INSERT INTO city VALUES ('178', '黄冈市', '421100000000', '420000000000');
INSERT INTO city VALUES ('179', '咸宁市', '421200000000', '420000000000');
INSERT INTO city VALUES ('180', '随州市', '421300000000', '420000000000');
INSERT INTO city VALUES ('181', '恩施土家族苗族自治州', '422800000000', '420000000000');
INSERT INTO city VALUES ('182', '省直辖县级行政区划', '429000000000', '420000000000');
INSERT INTO city VALUES ('183', '长沙市', '430100000000', '430000000000');
INSERT INTO city VALUES ('184', '株洲市', '430200000000', '430000000000');
INSERT INTO city VALUES ('185', '湘潭市', '430300000000', '430000000000');
INSERT INTO city VALUES ('186', '衡阳市', '430400000000', '430000000000');
INSERT INTO city VALUES ('187', '邵阳市', '430500000000', '430000000000');
INSERT INTO city VALUES ('188', '岳阳市', '430600000000', '430000000000');
INSERT INTO city VALUES ('189', '常德市', '430700000000', '430000000000');
INSERT INTO city VALUES ('190', '张家界市', '430800000000', '430000000000');
INSERT INTO city VALUES ('191', '益阳市', '430900000000', '430000000000');
INSERT INTO city VALUES ('192', '郴州市', '431000000000', '430000000000');
INSERT INTO city VALUES ('193', '永州市', '431100000000', '430000000000');
INSERT INTO city VALUES ('194', '怀化市', '431200000000', '430000000000');
INSERT INTO city VALUES ('195', '娄底市', '431300000000', '430000000000');
INSERT INTO city VALUES ('196', '湘西土家族苗族自治州', '433100000000', '430000000000');
INSERT INTO city VALUES ('197', '广州市', '440100000000', '510000000000');
INSERT INTO city VALUES ('198', '韶关市', '440200000000', '510000000000');
INSERT INTO city VALUES ('199', '深圳市', '440300000000', '510000000000');
INSERT INTO city VALUES ('200', '珠海市', '440400000000', '510000000000');
INSERT INTO city VALUES ('201', '汕头市', '440500000000', '510000000000');
INSERT INTO city VALUES ('202', '佛山市', '440600000000', '510000000000');
INSERT INTO city VALUES ('203', '江门市', '440700000000', '510000000000');
INSERT INTO city VALUES ('204', '湛江市', '440800000000', '510000000000');
INSERT INTO city VALUES ('205', '茂名市', '440900000000', '510000000000');
INSERT INTO city VALUES ('206', '肇庆市', '441200000000', '510000000000');
INSERT INTO city VALUES ('207', '惠州市', '441300000000', '510000000000');
INSERT INTO city VALUES ('208', '梅州市', '441400000000', '510000000000');
INSERT INTO city VALUES ('209', '汕尾市', '441500000000', '510000000000');
INSERT INTO city VALUES ('210', '河源市', '441600000000', '510000000000');
INSERT INTO city VALUES ('211', '阳江市', '441700000000', '510000000000');
INSERT INTO city VALUES ('212', '清远市', '441800000000', '510000000000');
INSERT INTO city VALUES ('213', '东莞市', '441900000000', '510000000000');
INSERT INTO city VALUES ('214', '中山市', '442000000000', '510000000000');
INSERT INTO city VALUES ('215', '潮州市', '445100000000', '510000000000');
INSERT INTO city VALUES ('216', '揭阳市', '445200000000', '510000000000');
INSERT INTO city VALUES ('217', '云浮市', '445300000000', '510000000000');
INSERT INTO city VALUES ('218', '南宁市', '450100000000', '520000000000');
INSERT INTO city VALUES ('219', '柳州市', '450200000000', '520000000000');
INSERT INTO city VALUES ('220', '桂林市', '450300000000', '520000000000');
INSERT INTO city VALUES ('221', '梧州市', '450400000000', '520000000000');
INSERT INTO city VALUES ('222', '北海市', '450500000000', '520000000000');
INSERT INTO city VALUES ('223', '防城港市', '450600000000', '520000000000');
INSERT INTO city VALUES ('224', '钦州市', '450700000000', '520000000000');
INSERT INTO city VALUES ('225', '贵港市', '450800000000', '520000000000');
INSERT INTO city VALUES ('226', '玉林市', '450900000000', '520000000000');
INSERT INTO city VALUES ('227', '百色市', '451000000000', '520000000000');
INSERT INTO city VALUES ('228', '贺州市', '451100000000', '520000000000');
INSERT INTO city VALUES ('229', '河池市', '451200000000', '520000000000');
INSERT INTO city VALUES ('230', '来宾市', '451300000000', '520000000000');
INSERT INTO city VALUES ('231', '崇左市', '451400000000', '520000000000');
INSERT INTO city VALUES ('232', '海口市', '460100000000', '530000000000');
INSERT INTO city VALUES ('233', '三亚市', '460200000000', '530000000000');
INSERT INTO city VALUES ('234', '三沙市', '460300000000', '530000000000');
INSERT INTO city VALUES ('235', '儋州市', '460400000000', '530000000000');
INSERT INTO city VALUES ('236', '五指山市', '460500000000', '530000000000');
INSERT INTO city VALUES ('237', '琼海市', '460600000000', '530000000000');
INSERT INTO city VALUES ('238', '文昌市', '460700000000', '530000000000');
INSERT INTO city VALUES ('239', '万宁市', '460800000000', '530000000000');
INSERT INTO city VALUES ('240', '东万市', '460900000000', '530000000000');
INSERT INTO city VALUES ('241', '重庆城区', '610100000000', '610000000000');


INSERT INTO city VALUES ('242', '成都市', '510100000000', '620000000000');
INSERT INTO city VALUES ('243', '自贡市', '510300000000', '620000000000');
INSERT INTO city VALUES ('244', '攀枝花市', '510400000000', '620000000000');
INSERT INTO city VALUES ('245', '泸州市', '510500000000', '620000000000');
INSERT INTO city VALUES ('246', '德阳市', '510600000000', '620000000000');
INSERT INTO city VALUES ('247', '绵阳市', '510700000000', '620000000000');
INSERT INTO city VALUES ('248', '广元市', '510800000000', '620000000000');
INSERT INTO city VALUES ('249', '遂宁市', '510900000000', '620000000000');
INSERT INTO city VALUES ('250', '内江市', '511000000000', '620000000000');
INSERT INTO city VALUES ('251', '乐山市', '511100000000', '620000000000');
INSERT INTO city VALUES ('252', '南充市', '511300000000', '620000000000');
INSERT INTO city VALUES ('253', '眉山市', '511400000000', '620000000000');
INSERT INTO city VALUES ('254', '宜宾市', '511500000000', '620000000000');
INSERT INTO city VALUES ('255', '广安市', '511600000000', '620000000000');
INSERT INTO city VALUES ('256', '达州市', '511700000000', '620000000000');
INSERT INTO city VALUES ('257', '雅安市', '511800000000', '620000000000');
INSERT INTO city VALUES ('258', '巴中市', '511900000000', '620000000000');
INSERT INTO city VALUES ('259', '资阳市', '512000000000', '620000000000');

INSERT INTO city VALUES ('260', '贵阳市', '520100000000', '630000000000');
INSERT INTO city VALUES ('261', '六盘水市', '520200000000', '630000000000');
INSERT INTO city VALUES ('262', '遵义市', '520300000000', '630000000000');
INSERT INTO city VALUES ('263', '安顺市', '520400000000', '630000000000');
INSERT INTO city VALUES ('264', '毕节市', '520500000000', '630000000000');
INSERT INTO city VALUES ('265', '铜仁市', '520600000000', '630000000000');
INSERT INTO city VALUES ('266', '黔西南布依族苗族自治州', '522300000000', '630000000000');
INSERT INTO city VALUES ('267', '黔东南苗族侗族自治州', '522600000000', '630000000000');
INSERT INTO city VALUES ('268', '黔南布依族苗族自治州', '522700000000', '630000000000');

INSERT INTO city VALUES ('269', '昆明市', '530100000000', '640000000000');
INSERT INTO city VALUES ('270', '曲靖市', '530300000000', '640000000000');
INSERT INTO city VALUES ('271', '玉溪市', '530400000000', '640000000000');
INSERT INTO city VALUES ('272', '保山市', '530500000000', '640000000000');
INSERT INTO city VALUES ('273', '昭通市', '530600000000', '640000000000');
INSERT INTO city VALUES ('274', '丽江市', '530700000000', '640000000000');
INSERT INTO city VALUES ('275', '普洱市', '530800000000', '640000000000');
INSERT INTO city VALUES ('276', '临沧市', '530900000000', '640000000000');

INSERT INTO city VALUES ('277', '拉萨市', '540100000000', '650000000000');
INSERT INTO city VALUES ('278', '日喀则市', '540200000000', '650000000000');
INSERT INTO city VALUES ('279', '昌都市', '540300000000', '650000000000');
INSERT INTO city VALUES ('280', '林芝市', '540400000000', '650000000000');
INSERT INTO city VALUES ('281', '山南市', '540500000000', '650000000000');
INSERT INTO city VALUES ('282', '那曲市', '540600000000', '650000000000');

INSERT INTO city VALUES ('283', '西安市', '610100000000', '710000000000');
INSERT INTO city VALUES ('284', '铜川市', '610200000000', '710000000000');
INSERT INTO city VALUES ('285', '宝鸡市', '610300000000', '710000000000');
INSERT INTO city VALUES ('286', '咸阳市', '610400000000', '710000000000');
INSERT INTO city VALUES ('287', '渭南市', '610500000000', '710000000000');
INSERT INTO city VALUES ('288', '延安市', '610600000000', '710000000000');
INSERT INTO city VALUES ('289', '汉中市', '610700000000', '710000000000');
INSERT INTO city VALUES ('290', '榆林市', '610800000000', '710000000000');
INSERT INTO city VALUES ('291', '安康市', '610900000000', '710000000000');
INSERT INTO city VALUES ('292', '商洛市', '611000000000', '710000000000');

INSERT INTO city VALUES ('293', '兰州市', '620100000000', '720000000000');
INSERT INTO city VALUES ('294', '嘉峪关市', '620200000000', '720000000000');
INSERT INTO city VALUES ('295', '金昌市', '620300000000', '720000000000');
INSERT INTO city VALUES ('296', '白银市', '620400000000', '720000000000');
INSERT INTO city VALUES ('297', '天水市', '620500000000', '720000000000');
INSERT INTO city VALUES ('298', '武威市', '620600000000', '720000000000');
INSERT INTO city VALUES ('299', '张掖市', '620700000000', '720000000000');
INSERT INTO city VALUES ('300', '平凉市', '620800000000', '720000000000');
INSERT INTO city VALUES ('301', '酒泉市', '620900000000', '720000000000');
INSERT INTO city VALUES ('311', '庆阳市', '621000000000', '720000000000');
INSERT INTO city VALUES ('312', '定西市', '621100000000', '720000000000');
INSERT INTO city VALUES ('313', '陇南市', '621200000000', '720000000000');
INSERT INTO city VALUES ('314', '临夏回族自治州', '622900000000', '720000000000');
INSERT INTO city VALUES ('315', '甘南藏族自治州', '623000000000', '720000000000');

INSERT INTO city VALUES ('316', '西宁市', '630100000000', '730000000000');
INSERT INTO city VALUES ('317', '海东市', '630200000000', '730000000000');
INSERT INTO city VALUES ('318', '海北藏族自治州', '632200000000', '730000000000');
INSERT INTO city VALUES ('319', '黄南藏族自治州', '632300000000', '730000000000');
INSERT INTO city VALUES ('320', '海南藏族自治州', '632500000000', '730000000000');
INSERT INTO city VALUES ('321', '果洛藏族自治州', '632600000000', '730000000000');
INSERT INTO city VALUES ('322', '玉树藏族自治州', '632700000000', '730000000000');
INSERT INTO city VALUES ('323', '海西蒙古族藏族自治州', '632800000000', '730000000000');

INSERT INTO city VALUES ('324', '银川市', '640100000000', '740000000000');
INSERT INTO city VALUES ('325', '石嘴山市', '640200000000', '740000000000');
INSERT INTO city VALUES ('326', '吴忠市', '640300000000', '740000000000');
INSERT INTO city VALUES ('327', '固原市', '640400000000', '740000000000');
INSERT INTO city VALUES ('328', '中卫市', '640500000000', '740000000000');
INSERT INTO city VALUES ('329', '乌鲁木齐市', '650100000000', '750000000000');
INSERT INTO city VALUES ('330', '克拉玛依市', '650200000000', '750000000000');
INSERT INTO city VALUES ('331', '吐鲁番市', '650400000000', '750000000000');
INSERT INTO city VALUES ('332', '哈密市', '650500000000', '750000000000');
INSERT INTO city VALUES ('333', '昌吉回族自治州', '652300000000', '750000000000');
INSERT INTO city VALUES ('334', '博尔塔拉蒙古自治州', '652700000000', '750000000000');
INSERT INTO city VALUES ('335', '巴音郭楞蒙古自治州', '652800000000', '750000000000');
INSERT INTO city VALUES ('336', '阿克苏地区', '652900000000', '750000000000');
INSERT INTO city VALUES ('337', '克孜勒苏柯尔克孜自治州', '653000000000', '750000000000');
INSERT INTO city VALUES ('338', '喀什地区', '653100000000', '750000000000');
INSERT INTO city VALUES ('339', '和田地区', '653200000000', '750000000000');
INSERT INTO city VALUES ('340', '伊犁哈萨克自治州', '654000000000', '750000000000');
INSERT INTO city VALUES ('341', '塔城地区', '654200000000', '750000000000');
INSERT INTO city VALUES ('342', '阿勒泰地区', '654300000000', '750000000000');
INSERT INTO city VALUES ('343', '自治区直辖县级行政区划', '659000000000', '750000000000');


#0否 1是
ALTER TABLE `crm_customer`
  ADD COLUMN `is_excel_import` tinyint(4) NULL DEFAULT 0 COMMENT '是否通过excel导入' AFTER `is_del`;



#0否 1是
ALTER TABLE `customer_dept`
  ADD COLUMN `is_excel_import` tinyint(4) NULL DEFAULT 0 COMMENT '是否通过excel导入' AFTER `create_ts`;



#0否 1是
ALTER TABLE `contacts`
  ADD COLUMN `is_excel_import` tinyint(4) NULL DEFAULT 0 COMMENT '是否通过excel导入' AFTER `contacts_type_id`;



ALTER TABLE `crm_customer`
  ADD COLUMN `province` varchar(50) NULL COMMENT '省' AFTER `is_excel_import`,
  ADD COLUMN `city` varchar(50) NULL COMMENT '市' AFTER `province`,
  ADD COLUMN `region` varchar(50) NULL COMMENT '销售区域(华中华南之类)' AFTER `city`;


  alter table contacts drop foreign key contacts_ibfk_1;

  alter table contacts_type drop  foreign key contacts_type_ibfk_1;

  ALTER TABLE `contacts_type`
  DROP COLUMN `customer_id`;


#通过 /dataRepair/repairPosition 进行历史数据修复, 修复前进行数据备份