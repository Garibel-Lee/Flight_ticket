package com.lcqjoyce.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MyScanner {
	

	public String getString() {
		BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
		String str = null;
		try {
			str = buf.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
		return str;
	}

	@SuppressWarnings("resource")
	public int getInt() {
		int number;
		while (true) {
			try {
				number= new Scanner(System.in).nextInt();
				break;				
			} catch (Exception e) {
				System.out.println("输入数字有误，请重新输入");
				continue;
			}
		}
		return number;
	}
	
}
