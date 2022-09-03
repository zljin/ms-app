/*
 Navicat Premium Data Transfer

 Source Server         : tensqure
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : 192.168.2.6:3306
 Source Schema         : ms_app

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 03/09/2022 12:06:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item`  (
  `id` bigint(20) NOT NULL,
  `title` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `price` decimal(10, 2) NOT NULL DEFAULT 0.00,
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `sales` int(11) NOT NULL DEFAULT 0,
  `img_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES (41, 'Redmi K40S', 2199.00, 'Redmi K40S 骁龙870 三星E4 AMOLED 120Hz直屏 OIS光学防抖 67W快充 幻镜 12GB+256GB 5G智能手机 小米红米', 10000, 'https://img10.360buyimg.com/n1/s450x450_jfs/t1/15461/19/18435/77072/630f0a51Eac2f09a2/c21f6dde132d17d4.jpg.avif');
INSERT INTO `item` VALUES (42, 'iPhone 13', 5399.20, 'Apple iPhone 13 (A2634) 128GB 粉色 支持移动联通电信5G 双卡双待手机', 20000, 'https://img13.360buyimg.com/n1/s450x450_jfs/t1/87369/27/30646/29254/63107c1aEc5d920c7/cca8b9ea1ab47b71.jpg.avif');
INSERT INTO `item` VALUES (763404919783817216, '联想拯救者Y700', 2599.20, '联想拯救者Y700 8.8英寸游戏平板 骁龙870 2.5k 120Hz 100%DCI-P3色域 游戏视野模式 双X轴线性马达 12G+256G', 1000, 'https://img13.360buyimg.com/n1/s450x450_jfs/t1/120483/2/30805/76004/630ddc3cE21c1e940/ac5321959771f718.jpg.avif');

-- ----------------------------
-- Table structure for item_stock
-- ----------------------------
DROP TABLE IF EXISTS `item_stock`;
CREATE TABLE `item_stock`  (
  `id` bigint(20) NOT NULL,
  `stock` int(11) NOT NULL DEFAULT 0,
  `item_id` bigint(20) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item_stock
-- ----------------------------
INSERT INTO `item_stock` VALUES (25, 100, 41);
INSERT INTO `item_stock` VALUES (26, 56, 42);
INSERT INTO `item_stock` VALUES (763404919808983040, 1000, 763404919783817216);

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `user_id` int(11) NOT NULL DEFAULT 0,
  `item_id` int(11) NOT NULL DEFAULT 0,
  `item_price` decimal(10, 2) NOT NULL DEFAULT 0.00,
  `amount` int(11) NOT NULL DEFAULT 0,
  `order_price` decimal(10, 2) NOT NULL DEFAULT 0.00,
  `promo_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for promo
-- ----------------------------
DROP TABLE IF EXISTS `promo`;
CREATE TABLE `promo`  (
  `id` bigint(20) NOT NULL,
  `promo_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `start_date` datetime(0) NOT NULL DEFAULT '1000-01-01 00:00:00',
  `end_date` datetime(0) NOT NULL DEFAULT '1000-01-01 00:00:00',
  `item_id` bigint(20) NOT NULL DEFAULT 0,
  `promo_item_price` decimal(10, 2) NOT NULL DEFAULT 0.00,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of promo
-- ----------------------------
INSERT INTO `promo` VALUES (763404919825760256, '联想拯救者Y700 300折扣卷', '1000-01-01 00:00:00', '1000-01-01 00:00:00', 763404919783817216, 2299.10);

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '\"\"',
  `gender` tinyint(4) NOT NULL DEFAULT -1 COMMENT '1为男性，2为女性',
  `age` int(11) NOT NULL DEFAULT 0,
  `telphone` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '“”',
  `register_mode` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '“”' COMMENT '//byphone,bywechar,byalipay',
  `third_party_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '“”',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `telphone_unique_index`(`telphone`) USING BTREE,
  UNIQUE INDEX `user_info_email_uindex`(`email`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (763398861757612032, 'leonard', 1, 24, '15073311125', 'by email', 'wechat', 'leonard_zou@163.com');

-- ----------------------------
-- Table structure for user_password
-- ----------------------------
DROP TABLE IF EXISTS `user_password`;
CREATE TABLE `user_password`  (
  `id` bigint(20) NOT NULL,
  `encrpt_password` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '“”',
  `user_id` bigint(20) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_password
-- ----------------------------
INSERT INTO `user_password` VALUES (763398862801993728, 'l5bTm7V+bwx2rsBIOj+uYw==', 763398861757612032);

SET FOREIGN_KEY_CHECKS = 1;
