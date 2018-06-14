/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : ams-v2

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-02-23 16:01:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ams_attdetall
-- ----------------------------
DROP TABLE IF EXISTS `ams_attdetall`;
CREATE TABLE `ams_attdetall` (
  `attdetall_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '考勤详情ID',
  `attlist_id` bigint(20) NOT NULL COMMENT '考勤单ID',
  `user_name` varchar(255) DEFAULT NULL COMMENT '学生姓名',
  `login_num` varchar(255) NOT NULL COMMENT '学号',
  `status` int(255) DEFAULT NULL COMMENT '出席状态 1-出席 2-未出席 3-请假',
  PRIMARY KEY (`attdetall_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ams_attdetall
-- ----------------------------
INSERT INTO `ams_attdetall` VALUES ('33', '15', '陈强', '14477218', '1');
INSERT INTO `ams_attdetall` VALUES ('34', '15', '戴韩健', '14477219', '1');
INSERT INTO `ams_attdetall` VALUES ('35', '15', '刘朝', '15416120', '3');
INSERT INTO `ams_attdetall` VALUES ('36', '15', '刘蒙', '15416121', '2');
INSERT INTO `ams_attdetall` VALUES ('37', '15', '刘文豪', '15416122', '3');
INSERT INTO `ams_attdetall` VALUES ('38', '16', '陈强', '14477218', '2');
INSERT INTO `ams_attdetall` VALUES ('39', '16', '戴韩健', '14477219', '2');
INSERT INTO `ams_attdetall` VALUES ('40', '16', '花兴荣', '14477220', '2');
INSERT INTO `ams_attdetall` VALUES ('41', '17', '陈强', '14477218', '1');
INSERT INTO `ams_attdetall` VALUES ('42', '17', '戴韩健', '14477219', '2');
INSERT INTO `ams_attdetall` VALUES ('43', '17', '刘朝', '15416120', '2');
INSERT INTO `ams_attdetall` VALUES ('44', '17', '刘蒙', '15416121', '2');
INSERT INTO `ams_attdetall` VALUES ('45', '17', '刘文豪', '15416122', '2');
INSERT INTO `ams_attdetall` VALUES ('46', '17', '杭登极', '15416115', '1');
INSERT INTO `ams_attdetall` VALUES ('47', '17', '孔祥月', '15416118', '1');

-- ----------------------------
-- Table structure for ams_attlist
-- ----------------------------
DROP TABLE IF EXISTS `ams_attlist`;
CREATE TABLE `ams_attlist` (
  `att_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '考勤单id',
  `att_code` varchar(50) NOT NULL COMMENT '考勤单编号',
  `course_id` bigint(20) NOT NULL COMMENT '课程ID',
  `course_name` varchar(255) NOT NULL COMMENT '课程名',
  `term_num` varchar(20) DEFAULT NULL COMMENT '学期',
  `create_id` bigint(20) NOT NULL COMMENT '创建人ID',
  `update_id` bigint(20) DEFAULT NULL COMMENT '更新人ID',
  `create_name` varchar(255) DEFAULT NULL COMMENT '创建人姓名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` int(255) DEFAULT NULL COMMENT '识别状态，1-上传成功，2正在识别，3，识别完成，4失败',
  PRIMARY KEY (`att_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='考勤单';

-- ----------------------------
-- Records of ams_attlist
-- ----------------------------
INSERT INTO `ams_attlist` VALUES ('15', 'ATT2018221410089', '1', '计算机科学与技术', '18-19-1', '7', null, '张老师', '2018-01-22 22:14:10', '2018-02-23 15:59:40', null);
INSERT INTO `ams_attlist` VALUES ('16', 'ATT2018222712591', '2', '软件工程', '18-19-1', '1', null, 'admin', '2018-01-22 22:27:13', '2018-01-22 22:27:13', null);
INSERT INTO `ams_attlist` VALUES ('17', 'ATT2018132809816', '1', '计算机科学与技术', '18-19-1', '7', null, '张老师', '2018-02-23 13:28:10', '2018-02-23 15:59:47', '3');

-- ----------------------------
-- Table structure for ams_course
-- ----------------------------
DROP TABLE IF EXISTS `ams_course`;
CREATE TABLE `ams_course` (
  `course_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '课程ID',
  `course_name` varchar(50) DEFAULT NULL COMMENT '课程名称',
  `course_code` varchar(50) DEFAULT NULL COMMENT '课程编号',
  `teacher_name` varchar(50) DEFAULT NULL,
  `teacher_id` bigint(20) DEFAULT NULL COMMENT '任课教师Id',
  PRIMARY KEY (`course_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ams_course
-- ----------------------------
INSERT INTO `ams_course` VALUES ('1', '计算机科学与技术', '085462462', '张老师', '7');
INSERT INTO `ams_course` VALUES ('2', '软件工程', '085462468', '王老师', '8');

-- ----------------------------
-- Table structure for ams_course_student
-- ----------------------------
DROP TABLE IF EXISTS `ams_course_student`;
CREATE TABLE `ams_course_student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `course_id` bigint(20) NOT NULL COMMENT '课程ID',
  `student_id` bigint(20) NOT NULL COMMENT '学生ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ams_course_student
-- ----------------------------
INSERT INTO `ams_course_student` VALUES ('5', '1', '4');
INSERT INTO `ams_course_student` VALUES ('6', '1', '5');
INSERT INTO `ams_course_student` VALUES ('7', '2', '4');
INSERT INTO `ams_course_student` VALUES ('8', '2', '5');
INSERT INTO `ams_course_student` VALUES ('9', '2', '6');
INSERT INTO `ams_course_student` VALUES ('10', '1', '14');
INSERT INTO `ams_course_student` VALUES ('11', '1', '15');
INSERT INTO `ams_course_student` VALUES ('12', '1', '16');
INSERT INTO `ams_course_student` VALUES ('13', '1', '12');
INSERT INTO `ams_course_student` VALUES ('14', '1', '13');

-- ----------------------------
-- Table structure for ams_faceset
-- ----------------------------
DROP TABLE IF EXISTS `ams_faceset`;
CREATE TABLE `ams_faceset` (
  `faceset_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户提供的FaceSet标识outer_id',
  `faceset_token` varchar(255) NOT NULL COMMENT '主键',
  `display_name` varchar(255) DEFAULT NULL COMMENT 'faceSet名称',
  `tags` varchar(255) DEFAULT NULL COMMENT '标签',
  `dept_name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`faceset_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=484 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ams_faceset
-- ----------------------------
INSERT INTO `ams_faceset` VALUES ('478', 'dea56254ea27ff285e1243b3803f6a50', 'testName', 'testTags', null, null);
INSERT INTO `ams_faceset` VALUES ('479', '1828c12755cb1cd5b59c6eae6b7494b4', 'testSat Feb 03 21:56:04 CST 2018', 'tag', '软件141', '30');
INSERT INTO `ams_faceset` VALUES ('480', '167597ab69cb1549fd58198ba1327faf', 'RJ142', 'RJ142', '软件142', '28');
INSERT INTO `ams_faceset` VALUES ('481', 'd2cf4c5436cb9dc99b43be18eaa7c880', 'dasdas', '', '计算机151', '37');
INSERT INTO `ams_faceset` VALUES ('482', 'd71ac8b2805e62155ed883730688fe84', 'aaaa', '', '制药141', '32');
INSERT INTO `ams_faceset` VALUES ('483', '79fdcea48dc0db2945672f26a59a21bf', 'qqq', '', '石油141', '35');

-- ----------------------------
-- Table structure for sys_class
-- ----------------------------
DROP TABLE IF EXISTS `sys_class`;
CREATE TABLE `sys_class` (
  `class_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '班级Id',
  `dept_id` bigint(20) NOT NULL COMMENT '上属学院ID',
  `name` varchar(20) NOT NULL COMMENT '班级名称',
  `code` varchar(50) NOT NULL COMMENT '班级代码',
  PRIMARY KEY (`class_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_class
-- ----------------------------

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `key` varchar(50) DEFAULT NULL COMMENT 'key',
  `value` varchar(2000) DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `key` (`key`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统配置信息表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  `dept_type` int(1) DEFAULT '0' COMMENT '部门类型，1班级，0其他',
  `group_name` varchar(255) DEFAULT NULL COMMENT '分组名称，随机数',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='部门管理';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', '0', '常州大学', '0', '0', '0', null);
INSERT INTO `sys_dept` VALUES ('2', '1', '信息数理学院', '1', '0', '0', null);
INSERT INTO `sys_dept` VALUES ('3', '1', '制药与生命科学学院', '2', '0', '0', null);
INSERT INTO `sys_dept` VALUES ('5', '3', '销售部', '1', '0', '0', null);
INSERT INTO `sys_dept` VALUES ('6', '5', '销售一部', '0', '0', '0', null);
INSERT INTO `sys_dept` VALUES ('7', '5', '销售二部', '1', '0', '0', null);
INSERT INTO `sys_dept` VALUES ('8', '5', '销售二部', '1', '0', '0', null);
INSERT INTO `sys_dept` VALUES ('9', '4', '技术一部', '0', '1', '0', null);
INSERT INTO `sys_dept` VALUES ('11', '1', '石油工程学院', '1', '0', '0', null);
INSERT INTO `sys_dept` VALUES ('12', '1', '环境与安全工程学院', '1', '0', '0', null);
INSERT INTO `sys_dept` VALUES ('13', '1', '经济管理学院', '1', '0', '0', null);
INSERT INTO `sys_dept` VALUES ('14', '1', '国际教育交流学院', '1', '0', '0', null);
INSERT INTO `sys_dept` VALUES ('15', '1', '数理学院', '1', '0', '0', null);
INSERT INTO `sys_dept` VALUES ('16', '1', '华罗庚学院', '1', '0', '0', null);
INSERT INTO `sys_dept` VALUES ('17', '1', '继续教育学院', '1', '0', '0', null);
INSERT INTO `sys_dept` VALUES ('18', '1', '石油化工学院', '1', '0', '0', null);
INSERT INTO `sys_dept` VALUES ('19', '1', '机械工程学院', '1', '0', '0', null);
INSERT INTO `sys_dept` VALUES ('20', '1', '材料科学与工程学院', '1', '0', '0', null);
INSERT INTO `sys_dept` VALUES ('21', '1', '史良法学院', '1', '0', '0', null);
INSERT INTO `sys_dept` VALUES ('22', '1', '外国语学院', '1', '0', '0', null);
INSERT INTO `sys_dept` VALUES ('23', '1', '体育学院', '1', '0', '0', null);
INSERT INTO `sys_dept` VALUES ('24', '1', '怀德学院', '1', '0', '0', null);
INSERT INTO `sys_dept` VALUES ('25', '1', '护理学院', '1', '0', '0', null);
INSERT INTO `sys_dept` VALUES ('26', '1', '艺术学院', '1', '0', '0', null);
INSERT INTO `sys_dept` VALUES ('27', '1', '城市轨道交通学院', '1', '0', '0', null);
INSERT INTO `sys_dept` VALUES ('28', '2', '软件142', '1', '0', '1', 'w4u1nh');
INSERT INTO `sys_dept` VALUES ('30', '2', '软件141', '1', '0', '1', '');
INSERT INTO `sys_dept` VALUES ('32', '3', '制药141', '0', '0', '1', null);
INSERT INTO `sys_dept` VALUES ('34', '3', '制药142', '0', '0', '1', null);
INSERT INTO `sys_dept` VALUES ('35', '11', '石油141', '1', '0', '1', null);
INSERT INTO `sys_dept` VALUES ('36', '26', '美术142', '1', '0', '1', null);
INSERT INTO `sys_dept` VALUES ('37', '2', '计算机151', '1', '0', '1', 'gt9ias');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', null, null, '0', 'fa fa-cog', '1');
INSERT INTO `sys_menu` VALUES ('2', '1', '教职工管理', 'modules/sys/user.html', null, '1', 'fa fa-user', '1');
INSERT INTO `sys_menu` VALUES ('3', '1', '角色管理', 'modules/sys/role.html', null, '1', 'fa fa-user-secret', '2');
INSERT INTO `sys_menu` VALUES ('4', '1', '菜单管理', 'modules/sys/menu.html', null, '1', 'fa fa-th-list', '3');
INSERT INTO `sys_menu` VALUES ('5', '1', 'SQL监控', 'druid/sql.html', null, '1', 'fa fa-bug', '4');
INSERT INTO `sys_menu` VALUES ('15', '2', '查看', null, 'sys:user:list,sys:user:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('16', '2', '新增', null, 'sys:user:save,sys:role:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('17', '2', '修改', null, 'sys:user:update,sys:role:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('18', '2', '删除', null, 'sys:user:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('19', '3', '查看', null, 'sys:role:list,sys:role:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('20', '3', '新增', null, 'sys:role:save,sys:menu:perms', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('21', '3', '修改', null, 'sys:role:update,sys:menu:perms', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('22', '3', '删除', null, 'sys:role:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('23', '4', '查看', null, 'sys:menu:list,sys:menu:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('24', '4', '新增', null, 'sys:menu:save,sys:menu:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('25', '4', '修改', null, 'sys:menu:update,sys:menu:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('26', '4', '删除', null, 'sys:menu:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('30', '1', '文件上传', 'modules/oss/oss.html', 'sys:oss:all', '1', 'fa fa-file-image-o', '6');
INSERT INTO `sys_menu` VALUES ('31', '1', '部门管理', 'modules/sys/dept.html', null, '1', 'fa fa-file-code-o', '1');
INSERT INTO `sys_menu` VALUES ('32', '31', '查看', null, 'sys:dept:list,sys:dept:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('33', '31', '新增', null, 'sys:dept:save,sys:dept:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('34', '31', '修改', null, 'sys:dept:update,sys:dept:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('35', '31', '删除', null, 'sys:dept:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('36', '0', '我的考勤', null, null, '0', 'fa fa-file-image-o', '0');
INSERT INTO `sys_menu` VALUES ('37', '36', '考勤记录', 'modules/ams/myAtt.html', null, '1', 'fa fa-file-text', '0');
INSERT INTO `sys_menu` VALUES ('39', '0', '教学管理', null, null, '0', 'fa fa-align-justify', '0');
INSERT INTO `sys_menu` VALUES ('40', '39', '学生管理', 'modules/ams/student.html', null, '1', 'fa fa-users', '0');
INSERT INTO `sys_menu` VALUES ('41', '39', '班级管理', 'modules/ams/class.html', null, '1', 'fa fa-window-restore', '1');
INSERT INTO `sys_menu` VALUES ('42', '39', '课程管理', 'modules/ams/course.html', null, '1', 'fa fa-files-o', '2');
INSERT INTO `sys_menu` VALUES ('43', '39', '考勤管理', 'modules/ams/attlist.html', null, '1', 'fa fa-clipboard', '3');
INSERT INTO `sys_menu` VALUES ('44', '41', '查看', null, 'sys:class:list,sys:class:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('45', '41', '保存', null, 'sys:class:save,sys:class:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('46', '41', '删除', null, 'sys:class:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('47', '41', '修改', null, 'sys:class:update,sys:class:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('48', '40', '保存', null, 'sys:student:save,sys:student:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('49', '40', '查看', null, 'sys:student:list,sys:student:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('50', '40', '删除', null, 'sys:student:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('51', '40', '修改', null, 'sys:student:update,sys:student:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('52', '42', '添加课程', null, 'sys:course:save,sys:course:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('53', '42', '查看课程', null, 'sys:course:list,sys:course:info', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('54', '42', '删除课程', null, 'sys:course:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('55', '42', '修改课程', null, 'sys:course:update,sys:course:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('56', '39', '选课管理', 'modules/ams/chose.html', null, '1', 'fa fa-check-square', '3');
INSERT INTO `sys_menu` VALUES ('57', '56', '已选学生', null, 'sys:chose:binded,sys:chose:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('58', '56', '绑定', null, 'sys:chose:bind,sys:chose:select', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('59', '43', '新增考勤单', null, 'sys:attlist:save', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('60', '43', '修改考勤单', null, 'sys:attlist:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('61', '43', '删除考勤单', null, 'sys:attlist:delete', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('62', '43', '详情', null, 'sys:attlist:detall', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('63', '43', '查看', null, 'sys:attlist:list,sys:attlist:info', null, null, null);
INSERT INTO `sys_menu` VALUES ('64', '0', '人脸管理', null, null, '0', 'fa fa-list-ul', '3');
INSERT INTO `sys_menu` VALUES ('71', '64', '脸集管理', 'modules/ams/faceGroup.html', null, '1', 'fa fa-users', '0');
INSERT INTO `sys_menu` VALUES ('72', '64', '人脸操作', 'modules/ams/faceManager.html', null, '1', null, '0');
INSERT INTO `sys_menu` VALUES ('73', '0', '查看', null, 'sys:facegroup:list', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('74', '72', '注册', null, 'sys:facetoken:upload', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('75', '72', '更新人脸', null, 'sys:facetoken:update', '2', null, '0');
INSERT INTO `sys_menu` VALUES ('76', '72', '删除人脸', null, 'sys:facetoken:delete', '2', null, '0');

-- ----------------------------
-- Table structure for sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='文件上传';

-- ----------------------------
-- Records of sys_oss
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) NOT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_userid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('2', '制药与生命科学学院管理员', '制药与生命科学学院管理员', '3', '2018-01-14 18:18:22', '1');
INSERT INTO `sys_role` VALUES ('3', '学生', '学生', '1', '2018-01-14 19:29:50', '1');
INSERT INTO `sys_role` VALUES ('4', '普通任课教室权限', null, '1', '2018-01-19 14:23:07', '1');

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色与部门对应关系';

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
INSERT INTO `sys_role_dept` VALUES ('4', '2', '3');
INSERT INTO `sys_role_dept` VALUES ('5', '2', '4');
INSERT INTO `sys_role_dept` VALUES ('6', '3', '1');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('18', '2', '1');
INSERT INTO `sys_role_menu` VALUES ('19', '2', '2');
INSERT INTO `sys_role_menu` VALUES ('20', '2', '15');
INSERT INTO `sys_role_menu` VALUES ('21', '2', '16');
INSERT INTO `sys_role_menu` VALUES ('22', '2', '17');
INSERT INTO `sys_role_menu` VALUES ('23', '2', '18');
INSERT INTO `sys_role_menu` VALUES ('24', '2', '3');
INSERT INTO `sys_role_menu` VALUES ('25', '2', '19');
INSERT INTO `sys_role_menu` VALUES ('26', '2', '20');
INSERT INTO `sys_role_menu` VALUES ('27', '2', '21');
INSERT INTO `sys_role_menu` VALUES ('28', '2', '22');
INSERT INTO `sys_role_menu` VALUES ('29', '3', '36');
INSERT INTO `sys_role_menu` VALUES ('30', '3', '37');
INSERT INTO `sys_role_menu` VALUES ('37', '4', '39');
INSERT INTO `sys_role_menu` VALUES ('38', '4', '42');
INSERT INTO `sys_role_menu` VALUES ('39', '4', '52');
INSERT INTO `sys_role_menu` VALUES ('40', '4', '53');
INSERT INTO `sys_role_menu` VALUES ('41', '4', '54');
INSERT INTO `sys_role_menu` VALUES ('42', '4', '55');
INSERT INTO `sys_role_menu` VALUES ('43', '4', '43');
INSERT INTO `sys_role_menu` VALUES ('44', '4', '59');
INSERT INTO `sys_role_menu` VALUES ('45', '4', '60');
INSERT INTO `sys_role_menu` VALUES ('46', '4', '61');
INSERT INTO `sys_role_menu` VALUES ('47', '4', '62');
INSERT INTO `sys_role_menu` VALUES ('48', '4', '63');
INSERT INTO `sys_role_menu` VALUES ('49', '4', '56');
INSERT INTO `sys_role_menu` VALUES ('50', '4', '57');
INSERT INTO `sys_role_menu` VALUES ('51', '4', '58');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login_num` varchar(20) NOT NULL COMMENT '工号',
  `user_name` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `salt` varchar(100) NOT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态  0：禁用   1：正常',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `user_type` int(1) NOT NULL DEFAULT '1' COMMENT '用户类型0：教职工，1：学生',
  `dept_name` varchar(100) DEFAULT NULL COMMENT '部门名称',
  `register` tinyint(1) DEFAULT '0' COMMENT '是否注册人脸 0：否 ；1:是',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '110', 'admin', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', '123', '123', '1', '1', '2018-01-09 13:24:23', '0', null, '0');
INSERT INTO `sys_user` VALUES ('2', '111', 'chen', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', '123', '123', '1', null, '2018-01-09 13:24:40', '0', null, '0');
INSERT INTO `sys_user` VALUES ('4', '14477218', '陈强', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', '749829987@qq.com', '123', '1', '28', '2018-01-14 20:48:04', '1', '软件142', '1');
INSERT INTO `sys_user` VALUES ('5', '14477219', '戴韩健', '14cebc45f22b3dceb862232c2d42ede05f9458405195ddcd2c8f2dca18a13c5f', 'VxWnv4VvPBZAJygBZihw', '1234@qq.com', '123412341234', '1', '28', '2018-01-15 22:37:12', '1', '软件142', '0');
INSERT INTO `sys_user` VALUES ('6', '14477220', '花兴荣', '0eb7fb4a9b0a650b5bf5fe728846d9b5a9eb5c337acc46e37674c5ba9cc7addd', 'rRGXlUEuLbdW1rg724JK', '123', '123', '1', '28', '2018-01-15 22:40:02', '1', '软件142', '0');
INSERT INTO `sys_user` VALUES ('7', '110110', '张老师', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', '110110@qq.com', '1234', '1', '2', '2018-01-19 14:34:09', '0', null, '0');
INSERT INTO `sys_user` VALUES ('8', '110111', '王老师', 'ac9be754e3b8a6a8b426d34e4cc3eff609d56bac6a39f62850c9d5c92cd23547', 'a5WKWiuxh525AyzLWV6s', '1243@qq.com', '123123', '1', '2', '2018-01-20 13:18:26', '0', null, '0');
INSERT INTO `sys_user` VALUES ('9', '14477221', '黄志华', 'd2db91a73941b712e9be870294fd43b3fe3b559a2b8aab1eb1eb244834297dbd', 'CSsipN5TDSXHfWstDbNC', '1234@qq.com', '1234', '1', '28', '2018-01-20 23:34:46', '1', '软件142', '0');
INSERT INTO `sys_user` VALUES ('10', '14477221', '沈鑫', '3622cb7a29b19ec152675ea3fb496853e1cb91c8a09039aa5e6a11d7b80f7c47', 'hAdyjJeP1ejqLus8GfHB', '1234@qq.com', '1234', '1', '28', '2018-01-20 23:35:16', '1', '软件142', '0');
INSERT INTO `sys_user` VALUES ('11', '14477222', '王坤', '20334ca3edd2d1067bf663cd26a45da8a5f19fe0392e029640587ef5bbde12c4', 'yPKwB1MCZGlDTBFrSm4f', '1234@qq.com', '1234', '1', '28', '2018-01-20 23:35:46', '1', '软件142', '0');
INSERT INTO `sys_user` VALUES ('12', '15416115', '杭登极', '12d4070fa7a6bc784e9e2a51069889cc9a9a3ecc16cfefff313554f9f27d8393', 'dVlnrAN6zG4tWS6niFRI', '1234@qq.com', '1234', '1', '37', '2018-01-20 23:37:56', '1', '计算机151', '1');
INSERT INTO `sys_user` VALUES ('13', '15416118', '孔祥月', '278c3f312e68ef3d4c73d8eba63c97fd95e4c53f6cdf3e5edf19e67a9e6e2838', 'hF99q0Fh9lig1mGGE1CX', '1234@qq.com', '1234', '1', '37', '2018-01-20 23:38:22', '1', '计算机151', '1');
INSERT INTO `sys_user` VALUES ('14', '15416120', '刘朝', 'cf11b1a891bca540f8e13d0c829a2a4b7f022e487361c7875956a866dba17689', 'ovgLtfGQ4x7yD1rxziGK', '15416120@qq.com', '1234', '1', '37', '2018-01-20 23:39:23', '1', '计算机151', '0');
INSERT INTO `sys_user` VALUES ('15', '15416121', '刘蒙', 'f90dc008d744a0d380b5a5bff5c38ad8c47cab23e5d00c386c0d8d8b748c8fd3', '8hWU7q00BnpNORnoMG3a', '15416121@qq.com', '15416121', '1', '37', '2018-01-20 23:39:54', '1', '计算机151', '0');
INSERT INTO `sys_user` VALUES ('16', '15416122', '刘文豪', '0fba2fed3b3c0cbdff20b524b278cbb5446929e03afe33d2ace9d6620cac2f65', 'OKzOeDI69ze3u4f1UYLc', '15416121@qq.com', '15416121', '1', '37', '2018-01-20 23:40:39', '1', '计算机151', '0');
INSERT INTO `sys_user` VALUES ('17', '15416123', '刘星', '6b9d0551bfc5e52571ebd6f2b6b53e90fa18e7bffbad8262130c4cf9ba7425da', 'h4fYmbyZw9qyQ9wnadxm', '15416121@qq.com', '15416121', '1', '37', '2018-01-20 23:41:08', '1', '计算机151', '0');
INSERT INTO `sys_user` VALUES ('18', '15416124', '盛斯龙', 'ac0cf8388f17a254e66f2f224e5a7b52e458229671e4a870c7a56891570578da', 'cRWLcEizerVcAyNyIru1', '15416124@qq.com', '15416124', '1', '37', '2018-01-20 23:41:34', '1', '计算机151', '0');
INSERT INTO `sys_user` VALUES ('19', '15430229', '徐一斌', '7298ddc66fbb771c02aba7f449c017393736e18e9f190bcdffa4615f85c9f3ea', 'FeKpHAwl30gwRd1Zmp2Y', '15430229@qq.com', '15430229', '1', '37', '2018-01-20 23:41:58', '1', '计算机151', '0');
INSERT INTO `sys_user` VALUES ('20', '15446318', '王欣智', 'cbcdd59e3d2411602c5c046be83a7e0340ad0b8ee3603d46bfd75b3941057c25', 'FNdxDptq95n9HB8Jn0aU', '15446318@qq.com', '15446318', '1', '37', '2018-01-20 23:42:29', '1', '计算机151', '0');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('4', '6', '3');
INSERT INTO `sys_user_role` VALUES ('8', '7', '4');
INSERT INTO `sys_user_role` VALUES ('9', '8', '4');
INSERT INTO `sys_user_role` VALUES ('10', '4', '3');
INSERT INTO `sys_user_role` VALUES ('11', '5', '3');
INSERT INTO `sys_user_role` VALUES ('12', '9', '3');
INSERT INTO `sys_user_role` VALUES ('13', '10', '3');
INSERT INTO `sys_user_role` VALUES ('14', '11', '3');
INSERT INTO `sys_user_role` VALUES ('15', '12', '3');
INSERT INTO `sys_user_role` VALUES ('17', '14', '3');
INSERT INTO `sys_user_role` VALUES ('18', '15', '3');
INSERT INTO `sys_user_role` VALUES ('19', '16', '3');
INSERT INTO `sys_user_role` VALUES ('20', '17', '3');
INSERT INTO `sys_user_role` VALUES ('21', '19', '3');
INSERT INTO `sys_user_role` VALUES ('22', '20', '3');
INSERT INTO `sys_user_role` VALUES ('24', '13', '3');

-- ----------------------------
-- Table structure for sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token` (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) NOT NULL COMMENT 'token',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `token` (`token`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统用户Token';

-- ----------------------------
-- Records of sys_user_token
-- ----------------------------
INSERT INTO `sys_user_token` VALUES ('1', '8197195e395e0ffb8607f036a0133cb5', '2018-02-24 01:05:57', '2018-02-23 13:05:57');
INSERT INTO `sys_user_token` VALUES ('3', '75cf5aea610618ffdcb548432006295f', '2018-01-15 07:31:24', '2018-01-14 19:31:24');
INSERT INTO `sys_user_token` VALUES ('4', 'd35e6f1a7403da87a35cee77527c021c', '2018-02-24 03:16:50', '2018-02-23 15:16:50');
INSERT INTO `sys_user_token` VALUES ('7', '7540a6eb433e428ac7510fe9fb3fa6bf', '2018-02-24 03:57:01', '2018-02-23 15:57:01');
