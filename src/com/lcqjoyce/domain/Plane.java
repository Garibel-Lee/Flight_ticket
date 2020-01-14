package com.lcqjoyce.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
public class Plane {
private static	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Getter
	@Setter
	private Integer pl_id;
	@Getter
	@Setter
	private String pl_name;
	@Getter
	@Setter
	private Integer pl_rid;
	@Getter
	@Setter
	private String pl_st;
	@Getter
	@Setter
	private String pl_rsp;
	@Getter
	@Setter
	private String pl_rep;

	private Date pl_rst;

	private Date pl_ret;
	@Getter
	@Setter
	private Integer pl_ysize;
	@Getter
	@Setter
	private Integer pl_fsize;
	@Getter
	@Setter
	private Integer pl_dsize;
	@Getter
	@Setter
	private Integer pl_ps;
	@Getter
	@Setter
	private Double r_yprice;
	@Getter
	@Setter
	private Double r_fprice;
	@Getter
	@Setter
	private Double r_dprice;

	public Date getPl_rst() {
		return pl_rst;
	}

	public void setPl_rst(Date pl_rst) throws ParseException {
		String str = sdf.format(pl_rst);
		this.pl_rst = sdf.parse(str);
	}

	public Date getPl_ret() {
		return pl_ret;
	}

	public void setPl_ret(Date pl_ret) throws ParseException {
		
		String str = sdf.format(pl_ret);
		this.pl_ret = sdf.parse(str);
	}

	public Plane() {
		super();
	}

}
