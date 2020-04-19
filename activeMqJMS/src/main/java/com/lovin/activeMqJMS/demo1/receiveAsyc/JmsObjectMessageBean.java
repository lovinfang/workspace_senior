package com.lovin.activeMqJMS.demo1.receiveAsyc;

import java.io.Serializable;

public class JmsObjectMessageBean implements Serializable{

	private static final long serialVersionUID = 2620024932905963095L;
	private String userName;
	private int age = 16;
	private boolean flag = true;

	public JmsObjectMessageBean(String userName,int age,boolean flag){
	  this.setUserName(userName);
	  this.setAge(age);
	  this.setFlag(flag);
	}

	public String getUserName() {
	  return userName;
	}

	public void setUserName(String userName) {
	  this.userName = userName;
	}

	public int getAge() {
	  return age;
	}

	public void setAge(int age) {
	  this.age = age;
	}

	public boolean isFlag() {
	  return flag;
	}

	public void setFlag(boolean flag) {
	  this.flag = flag;
	}
}
