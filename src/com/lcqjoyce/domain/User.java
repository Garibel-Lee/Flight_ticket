package com.lcqjoyce.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class User {
	// 剩余使用次数 0锁定该账户再次登录无效 请等待一小时
	public static final int UserType = 1;
	public static final int LockRemain = 3;

	private Integer p_id;

	private String p_name;

	private String p_pwd;

	private String p_sex;

	private String p_identy;

	private String p_number;

	private Integer p_type;

	private Date p_locktime;

	private int p_resudie;

	private String p_ps;

	public User() {
		super();
	}


	public String toString() {
		return "1 用户名：" + p_name + "\n2 用户密码：" + "*********" + "\n3 性别：" + p_sex + "\n4 身份证号：" + p_identy + "\n5 电话号码：" + p_number
				+ "\n";
	}
}
