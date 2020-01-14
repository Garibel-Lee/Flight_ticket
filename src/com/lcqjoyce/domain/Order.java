package com.lcqjoyce.domain;


import lombok.Data;

/**
 * @author 13059
 *
 */
@Data
public class Order {

	private Integer o_id;
	
	private String 	o_number;
	
	private Integer o_pid;

	private Integer o_rid;

	private Integer o_sslevel;

	private Double o_price;

	private Double o_eprice;

	private String o_ps;
	
	public Order() {
		super();
	}

}
