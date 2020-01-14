package com.lcqjoyce.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MyScanner {
	Scanner sc = new Scanner(System.in);

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

	public int getInt() {
		try {
			int number = sc.nextInt();
			return number;
		} finally {

		}

	}
}
