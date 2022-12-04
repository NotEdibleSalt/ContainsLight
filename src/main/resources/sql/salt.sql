/*
 Navicat Premium Data Transfer

 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Schema         : salt

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 16/09/2022 17:00:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号id',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态 ENABLE： 启用  DISABLE：禁用',
  `random` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '随机值',
  `del` tinyint(1) NOT NULL COMMENT '是否删除',
  `create_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `create_at` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新人',
  `update_at` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '账户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('1493150197750956032', 'admin', '$2a$10$BL2Ajg4H7PQY/LVJYRD0CObZGb8k1r1WmtN3BL1i8ZZNAINFr0Fe.', 'ENABLE', '003023', 0, 'default', '2022-02-14 17:08:43', 'admin', '2022-05-12 14:49:04');
INSERT INTO `account` VALUES ('1523578945021149184', 'test', '$2a$10$f.56tc0XyyWMKXq0vFtgJO7NNQ2PSFb1LNwReuo1Rq2jevr7AeJ4C', 'ENABLE', '907483', 0, 'default', '2022-05-09 16:21:42', 'admin', '2022-05-09 16:26:47');

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user`  (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '后台管理用户id',
  `account_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '账号id',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '邮箱',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '手机号',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '姓名',
  `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '头像(相对路径)',
  `gender` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '性别',
  `del` tinyint(1) NOT NULL COMMENT '是否删除',
  `create_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `create_at` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新人',
  `update_at` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_uid`(`account_id`) USING BTREE,
  INDEX `idx_email`(`email`) USING BTREE,
  INDEX `idx_phone`(`phone`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '后台管理用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_user
-- ----------------------------
INSERT INTO `admin_user` VALUES ('1493150201181896704', '1493150197750956032', '1', '1', 'admin', 'admin', '1', '2', 0, 'default', '2022-02-14 17:08:44', 'default', '2022-05-07 13:26:44');
INSERT INTO `admin_user` VALUES ('1523578945151172608', '1523578945021149184', '', '1', 'test', 'aaa', '', '1', 0, 'default', '2022-05-09 16:21:42', 'admin', '2022-05-10 15:14:19');

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `article_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型',
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作者',
  `article_status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '状态',
  `introduction` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '简介',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `del` tinyint(1) NOT NULL COMMENT '是否删除',
  `create_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `create_at` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新人',
  `update_at` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES ('1526106165124005888', '嘿嘿嘿嘿嘿', 'DEFAULT', NULL, 'LOWER_SHELF', 'ewtery', '# 一级标题\nahfkjajskf\n\n## 二级标题\nsjdfksjfk\n\n### 三级标题\nsfsdj\n\n- ajkds\n- rewoejr\n\n1. sdjfk\n2. erioweer\n\n\n```js\nlet a = \"a\"\nconsole.log(a)\n```\n\n', 0, 'admin', '2022-05-16 15:43:58', 'admin', '2022-09-08 10:10:32');

-- ----------------------------
-- Table structure for authority
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority`  (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `module` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属模块',
  `method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求方式',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '接口uri',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `del` tinyint(1) NOT NULL COMMENT '是否删除',
  `create_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '创建人',
  `create_at` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新人',
  `update_at` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_ms_menu`(`url`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统权限' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of authority
-- ----------------------------
INSERT INTO `authority` VALUES ('1526008312011685888', '分页查询角色', '角色管理', 'GET', '/roles', '分页查询角色', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312011685889', '修改字典项', '字典管理', 'PUT', '/dicts/{dictId}/items/{id}', '修改字典项', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312011685890', '新增字典', '字典管理', 'POST', '/dicts', '新增字典', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312011685891', '删除角色', '角色管理', 'DELETE', '/roles/{id}', '删除角色', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312011685892', '启用角色', '角色管理', 'PUT', '/roles/{id}/enable', '启用角色', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312032657408', '分页查询字典', '字典管理', 'GET', '/dicts', '分页查询字典', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312057823232', '删除文章', '文章管理', 'DELETE', '/articles/{articleId}', '删除文章', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312070406144', '禁用账号', '用户管理', 'PUT', '/accounts/{id}/disable', '禁用账号', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312133320704', '删除字典', '字典管理', 'DELETE', '/dicts/{id}', '删除字典', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312221401088', '修改角色', '角色管理', 'PUT', '/roles/{id}', '修改角色', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312225595392', '删除字典项', '字典管理', 'DELETE', '/dicts/{dictId}/items/{id}', '删除字典项', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312238178304', '根据字典名获取所有字典项', '字典管理', 'GET', '/dicts/type/{dictType}/items', '根据字典名获取所有字典项', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312254955520', '获取字典的全部字典项', '字典管理', 'GET', '/dicts/{dictId}/items', '获取字典的全部字典项', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312254955521', '分页查询文章', '文章管理', 'GET', '/articles', '分页查询文章', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312254955522', '根据id查询字典', '字典管理', 'GET', '/dicts/{id}', '根据id查询字典', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312271732736', '保存角色关联的权限', '角色管理', 'POST', '/roles/{roleId}/authoitys', '保存角色关联的权限', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312275927040', '获取角色关联的权限id集合', '角色管理', 'GET', '/roles/{roleId}/authoitys/list', '获取角色关联的权限id集合', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312275927041', '通过id查询角色', '角色管理', 'GET', '/roles/{id}', '通过id查询角色', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312275927042', '新增角色', '角色管理', 'POST', '/roles', '新增角色', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312309481472', '启用账号', '用户管理', 'PUT', '/accounts/{id}/enable', '启用账号', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312313675776', '下架文章', '文章管理', 'PUT', '/articles/{articleId}/lower-shelf', '下架文章', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312322064384', '获取全部角色', '角色管理', 'GET', '/roles/list', '获取全部角色', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312322064385', '发布文章', '文章管理', 'PUT', '/articles/{articleId}/publish', '发布文章', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312326258688', '禁用角色', '角色管理', 'PUT', '/roles/{id}/disable', '禁用角色', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312338841600', '保存文章类型和简介', '文章管理', 'PUT', '/articles/{articleId}/', '保存文章类型和简介', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312351424512', '新增文章', '文章管理', 'POST', '/articles', '新增文章', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312364007424', '通过id查询文章信息', '文章管理', 'GET', '/articles/{articleId}', '通过id查询文章信息', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312393367552', '删除管理员用户', '用户管理', 'DELETE', '/admins/{id}', '删除管理员用户', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312393367553', '更新管理员用户', '用户管理', 'PUT', '/admins/{id}', '更新管理员用户', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312393367554', '更新权限', '权限管理', 'PUT', '/authoritys/{id}', '更新权限', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312401756160', '新增管理员用户', '用户管理', 'POST', '/admins', '新增管理员用户', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312414339072', '更新文章', '文章管理', 'PUT', '/articles/{articleId}', '更新文章', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312460476416', '分页查询权限', '权限管理', 'GET', '/authoritys', '分页查询权限', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312460476417', '删除权限', '权限管理', 'DELETE', '/authoritys/{id}', '删除权限', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312460476418', '查询管理员用户信息', '用户管理', 'GET', '/admins/{id}', '查询管理员用户信息', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312468865024', '新增权限', '权限管理', 'POST', '/authoritys', '新增权限', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312552751104', '条件查询管理员用户', '用户管理', 'GET', '/admins', '条件查询管理员用户', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008312552751105', '根据id查询权限', '权限管理', 'GET', '/authoritys/{id}', '根据id查询权限', 0, 'default', '2022-05-16 09:15:08', 'default', '2022-05-16 09:15:08');
INSERT INTO `authority` VALUES ('1526008314448576512', '查询字典项', '字典管理', 'GET', '/dicts/{dictId}/items/{dictItemId}', '查询字典项', 0, 'default', '2022-05-16 09:15:09', 'default', '2022-05-16 09:15:09');
INSERT INTO `authority` VALUES ('1526008314943504384', '新增字典项', '字典管理', 'POST', '/dicts/{dictId}/items', '新增字典项', 0, 'default', '2022-05-16 09:15:09', 'default', '2022-05-16 09:15:09');
INSERT INTO `authority` VALUES ('1526008314947698688', '更新字典', '字典管理', 'PUT', '/dicts/{id}', '更新字典', 0, 'default', '2022-05-16 09:15:09', 'default', '2022-05-16 09:15:09');

-- ----------------------------
-- Table structure for dict
-- ----------------------------
DROP TABLE IF EXISTS `dict`;
CREATE TABLE `dict`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '编号',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `system` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否系统内置',
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态  ENABLE： 启用 DISABLE：禁用',
  `del` tinyint(1) NOT NULL COMMENT '是否删除',
  `create_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_at` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新人',
  `update_at` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of dict
-- ----------------------------
INSERT INTO `dict` VALUES ('1493139728273244160', '性别', 'Sex', '1： 男\n2：女', 1, 'ENABLE', 0, NULL, NULL, NULL, '2022-02-15 11:11:09');
INSERT INTO `dict` VALUES ('1494491480750817280', '可用状态', 'AvailableStatus', 'ENABLE：使用中\r\nDISABLE：禁用', 1, 'ENABLE', 0, NULL, NULL, NULL, '2022-02-18 10:32:42');
INSERT INTO `dict` VALUES ('1514158462957780992', '文章状态', 'ArticleStatus', '', 0, 'ENABLE', 0, 'default', '2022-04-13 16:28:04', 'default', '2022-04-13 16:28:04');
INSERT INTO `dict` VALUES ('1514162546842533888', '文章类型', 'ArticleType', '', 0, 'ENABLE', 0, 'default', '2022-04-13 16:44:18', 'default', '2022-04-13 16:44:18');
INSERT INTO `dict` VALUES ('1522132822028976128', '请求方式', 'RequestMethod', '', 1, 'ENABLE', 0, 'default', '2022-05-05 16:35:19', 'default', '2022-05-05 16:35:19');
INSERT INTO `dict` VALUES ('1570316775768195072', '菜单类型', 'MenuType', '', 0, 'ENABLE', 0, 'admin', '2022-09-15 15:41:09', 'admin', '2022-09-15 15:41:09');

-- ----------------------------
-- Table structure for dict_item
-- ----------------------------
DROP TABLE IF EXISTS `dict_item`;
CREATE TABLE `dict_item`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '编号',
  `dict_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典ID',
  `value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '值',
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标签',
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序（升序）',
  `del` tinyint(1) NOT NULL COMMENT '是否删除',
  `create_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_at` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新人',
  `update_at` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sys_dict_value`(`value`) USING BTREE,
  INDEX `sys_dict_label`(`label`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典项' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of dict_item
-- ----------------------------
INSERT INTO `dict_item` VALUES ('1493496705998389248', '1493139728273244160', '1', '男', '', 1, 0, NULL, NULL, '', '2022-02-15 16:05:37');
INSERT INTO `dict_item` VALUES ('1493506504748171264', '1493139728273244160', '2', '女', '', 1, 0, NULL, NULL, '', '2022-02-15 16:44:33');
INSERT INTO `dict_item` VALUES ('1494491603786530816', '1494491480750817280', 'ENABLE', '使用中', '', 1, 0, NULL, NULL, '', '2022-02-18 10:33:35');
INSERT INTO `dict_item` VALUES ('1494491652289462272', '1494491480750817280', 'DISABLE', '禁用', '', 2, 0, NULL, NULL, 'admin', '2022-05-06 14:14:53');
INSERT INTO `dict_item` VALUES ('1514158623939362816', '1514158462957780992', 'DRAFT', '草稿', '', 1, 0, 'default', '2022-04-13 16:28:42', 'default', '2022-04-13 16:28:42');
INSERT INTO `dict_item` VALUES ('1514158690326806528', '1514158462957780992', 'PUBLISHING', '发布中', '', 1, 0, 'default', '2022-04-13 16:28:58', 'default', '2022-04-13 16:28:58');
INSERT INTO `dict_item` VALUES ('1514158807805067264', '1514158462957780992', 'LOWER_SHELF', '下架', '', 1, 0, 'default', '2022-04-13 16:29:26', 'default', '2022-04-13 16:29:26');
INSERT INTO `dict_item` VALUES ('1514162812610412544', '1514162546842533888', 'DEFAULT', '默认', '', 1, 0, 'default', '2022-04-13 16:45:21', 'default', '2022-04-13 16:45:21');
INSERT INTO `dict_item` VALUES ('1514162869573255168', '1514162546842533888', 'RECIPES', '食谱', '', 1, 0, 'default', '2022-04-13 16:45:35', 'default', '2022-04-13 16:45:35');
INSERT INTO `dict_item` VALUES ('1514162929392418816', '1514162546842533888', 'POLICY', '政策', '', 1, 0, 'default', '2022-04-13 16:45:49', 'default', '2022-04-13 16:45:49');
INSERT INTO `dict_item` VALUES ('1522132917025767424', '1522132822028976128', 'GET', 'Get', '', 1, 0, 'default', '2022-05-05 16:35:42', 'default', '2022-05-05 16:35:42');
INSERT INTO `dict_item` VALUES ('1522132966845710336', '1522132822028976128', 'POST', 'Post', '', 2, 0, 'default', '2022-05-05 16:35:54', 'default', '2022-05-05 16:35:54');
INSERT INTO `dict_item` VALUES ('1522133009241735168', '1522132822028976128', 'PUT', 'Put', '', 1, 0, 'default', '2022-05-05 16:36:04', 'default', '2022-05-05 16:36:04');
INSERT INTO `dict_item` VALUES ('1522133140011745280', '1522132822028976128', 'DELETE', 'Delete', '', 1, 0, 'default', '2022-05-05 16:36:35', 'default', '2022-05-05 16:36:35');
INSERT INTO `dict_item` VALUES ('1570317953583284224', '1570316775768195072', 'TopMenu', '顶级菜单', '', 1, 0, 'admin', '2022-09-15 15:45:50', 'admin', '2022-09-15 15:45:50');
INSERT INTO `dict_item` VALUES ('1570318103747756032', '1570316775768195072', 'LeafMenu', '子菜单', '', 2, 0, 'admin', '2022-09-15 15:46:26', 'admin', '2022-09-15 15:46:26');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单id',
  `parent_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父菜单id',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单类型',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单路径',
  `route_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '路由标题',
  `route_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '路由路径',
  `sort_number` int(4) NULL DEFAULT 1 COMMENT '排序值',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态 ENABLE： 启用  DISABLE：禁用',
  `del` tinyint(1) NOT NULL COMMENT '是否删除',
  `create_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `create_at` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新人',
  `update_at` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1570286907055669248', NULL, 'TopMenu', '系统设置', '@/components/layout/Content.vue', 'Setting', '/setting', 1, NULL, 'ENABLE', 0, 'admin', '2022-09-15 13:42:28', 'admin', '2022-09-16 14:59:20');
INSERT INTO `menu` VALUES ('1570321248477511680', '1570286907055669248', 'LeafMenu', '用户管理', '@/views/user/UserManage.vue', 'UserManage', '/users', 1, NULL, 'ENABLE', 0, 'admin', '2022-09-15 15:58:55', 'admin', '2022-09-16 14:59:11');
INSERT INTO `menu` VALUES ('1570346416029564928', '1570286907055669248', 'LeafMenu', '角色管理', '@/views/role/RoleManage.vue', 'RoleManage', '/roles', 2, '', 'ENABLE', 0, 'admin', '2022-09-15 17:38:56', 'admin', '2022-09-16 14:58:59');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '自增id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色描述',
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态 1： 启用 2：禁用',
  `del` tinyint(1) NOT NULL COMMENT '是否删除',
  `create_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `create_at` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_at` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1522400561922572288', '权限管理', '权限管理模块的所有接口', 'DISABLE', 0, 'admin', '2022-05-06 10:19:13', 'admin', '2022-05-12 14:49:39');
INSERT INTO `role` VALUES ('1523576799894700032', '角色管理', '角色管理功能相关权限', 'DISABLE', 0, 'admin', '2022-05-09 16:13:10', 'admin', '2022-05-12 14:49:50');
INSERT INTO `role` VALUES ('1567800553843458048', '用户管理', '用户管理模块所有接口', 'DISABLE', 0, 'admin', '2022-09-08 17:02:35', 'admin', '2022-09-08 17:04:28');
INSERT INTO `role` VALUES ('1568119465688694784', '字典管理', '字典模块相关接口', 'DISABLE', 0, 'admin', '2022-09-09 06:09:50', 'admin', '2022-09-09 06:09:50');

-- ----------------------------
-- Table structure for role_account
-- ----------------------------
DROP TABLE IF EXISTS `role_account`;
CREATE TABLE `role_account`  (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
  `account_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '管理员id',
  `role_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色id',
  `del` tinyint(1) NOT NULL COMMENT '是否删除',
  `create_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `create_at` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新人',
  `update_at` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_staff_id`(`account_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色、账号关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_account
-- ----------------------------
INSERT INTO `role_account` VALUES ('1522810139508211712', '1493150197750956032', '1522400561922572288', 0, 'default', '2022-05-07 13:26:44', 'default', '2022-05-07 13:26:44');
INSERT INTO `role_account` VALUES ('1523924377131810816', '1523578945021149184', '1522400561922572288', 0, 'admin', '2022-05-10 15:14:19', 'admin', '2022-05-10 15:14:19');
INSERT INTO `role_account` VALUES ('1555091018313891840', '1555091016212545536', '1522400561922572288', 0, 'admin', '2022-08-04 15:19:26', 'admin', '2022-08-04 15:19:26');
INSERT INTO `role_account` VALUES ('1555092287573196800', '1555092285887086592', '1522400561922572288', 0, 'admin', '2022-08-04 15:24:28', 'admin', '2022-08-04 15:24:28');
INSERT INTO `role_account` VALUES ('1555096344253693952', '1555096342567583744', '1522400561922572288', 0, 'admin', '2022-08-04 15:40:35', 'admin', '2022-08-04 15:40:35');
INSERT INTO `role_account` VALUES ('1555097435431239680', '1555097433724157952', '1522400561922572288', 0, 'admin', '2022-08-04 15:44:56', 'admin', '2022-08-04 15:44:56');
INSERT INTO `role_account` VALUES ('1555097856593887232', '1555097854911971328', '1522400561922572288', 0, 'admin', '2022-08-04 15:46:36', 'admin', '2022-08-04 15:46:36');
INSERT INTO `role_account` VALUES ('1555098081198866432', '1555098079424675840', '1522400561922572288', 0, 'admin', '2022-08-04 15:47:30', 'admin', '2022-08-04 15:47:30');
INSERT INTO `role_account` VALUES ('1555098398267277312', '1555098396564389888', '1522400561922572288', 0, 'admin', '2022-08-04 15:48:45', 'admin', '2022-08-04 15:48:45');
INSERT INTO `role_account` VALUES ('1555098953786064896', '1555098952066400256', '1522400561922572288', 0, 'admin', '2022-08-04 15:50:58', 'admin', '2022-08-04 15:50:58');

-- ----------------------------
-- Table structure for role_authority
-- ----------------------------
DROP TABLE IF EXISTS `role_authority`;
CREATE TABLE `role_authority`  (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `role_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色id',
  `authority_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限id',
  `del` tinyint(1) NOT NULL COMMENT '是否删除',
  `create_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `create_at` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新人',
  `update_at` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色、权限关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_authority
-- ----------------------------

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `role_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色id',
  `menu_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单id',
  `del` tinyint(1) NOT NULL COMMENT '是否删除',
  `create_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `create_at` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新人',
  `update_at` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色、菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(64) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `birthday` datetime NULL DEFAULT NULL,
  `sex` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
