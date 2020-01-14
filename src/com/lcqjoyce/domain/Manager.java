package com.lcqjoyce.domain;

import java.util.Date;

import lombok.Data;
@Data
public class Manager {
	private String m_name;

	private String m_pwd;

	private String m_number;

	private Date m_locktime;

	private int m_resudie;

	private String m_ps;

	public Manager() {
		super();
	}

	public String toString() {
		return "1 用户名：" + m_name + "\n2 用户密码：" + "*********" + "\n5 电话号码：" + m_number + "\n";
	}
}
