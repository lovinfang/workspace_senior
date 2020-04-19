package com.lovin.zookeeper.bean;

public class Student {

	private String name;
	
	private String phone;

	public String getName() {
		return name;
	}

	public Student setName(String name) {
		this.name = name;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public Student setPhone(String phone) {
		this.phone = phone;
		return this;
	}
	
	public static Student build(){
		return new Student();
	}
	
//	Fluent 风格
//	Student s = Student.build().setName("lovin").setPhone("12345678");
//	System.out.println(s.getName()+s.getPhone());
}
