package com.ssxt.web.vo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MaintDistance {

	private int id;
	private String name;
	private double distance;
	private int exist;

	public int getExist() {
		return exist;
	}

	public void setExist(int exist) {
		this.exist = exist;
	}

	private String taskNo;
	private int state;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public static void sortIntMethod(List<MaintDistance> list) {
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				MaintDistance stu1 = (MaintDistance) o1;
				MaintDistance stu2 = (MaintDistance) o2;
				if (stu1.getDistance() > stu2.getDistance()) {
					return 1;
				} else if (stu1.getDistance() == stu2.getDistance()) {
					return 0;
				} else {
					return -1;
				}
			}
		});

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
