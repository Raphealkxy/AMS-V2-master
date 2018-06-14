package com.eric.common.utils;

/**
 * 常量
 */
public class Constant {
	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;

    /**
     * 菜单类型
     */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        private MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 用户类型
     */
    public enum UserType {
        /**
         * 管理员
         */
        ADMIN(0),
        /**
         * 教职工
         */
        TEACHER(0),
        /**
         * 学生
         */
        STUDENT(1);

        private int value;

        private UserType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 考勤类型
     */
    public enum AttStatus {
        /**
         * 出勤
         */
        ATT(1),

        /**
         * 未出勤
         */
        UNATT(2),

        /**
         * 请假
         */
        LEAVE(3);
        private int value;
        private AttStatus(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }

    /**
     * 识别进度识别状态，1-上传成功，2正在识别，3，识别完成，4失败
     */
    public enum AttListStatus {
        UPLOADED(1),//上传成功
        IDENTIFYING(2),
        SCUSSESS(3),
        FAILED(4);
        private int value;

        private AttListStatus(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }
}
