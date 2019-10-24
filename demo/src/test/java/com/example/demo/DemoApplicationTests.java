package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class DemoApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void test() {
		System.out.println(this.fillZero(12L, 9L));
	}

	// 保证rowkey的字典序
	public String fillZero(Long num, Long total) {
		int digitNum = getDigit(num);
		int digitTotal = getDigit(total);

		if (digitNum < digitTotal) {
			StringBuffer stringBuffer = new StringBuffer();
			int subDigit = digitTotal - digitNum;
			for (int i = 0; i < subDigit; i++) {
				stringBuffer.append("0");
			}
			stringBuffer.append(num);
			return stringBuffer.toString();
		} else {
			return num.toString();
		}
	}

	public static Integer getDigit(Long dig) {
		int count = 1;

		while(dig / 10 != 0) {
			count++;
			dig /= 10;
		}

		return count;
	}
}
