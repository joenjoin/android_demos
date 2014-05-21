package org.youdian.android_demos.json;

import java.util.HashMap;

public class Person {
	private long[] phone;
	private HashMap<String,String> address;

	private String name;
	private int age;
	public long[] getPhone() {
		return phone;
	}
	public void setPhone(long[] phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public HashMap<String, String> getAddress() {
		return address;
	}
	public void setAddress(HashMap<String, String> address) {
		this.address = address;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s="name="+getName()+",age="+getAge()+",phone="+getPhone()[0]+" "+getPhone()[1]+",address="+getAddress();
		return s;
	}
	
	
}
