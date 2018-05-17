package com.ssxt.web.vo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Assessment {
	private String name;
	private int userId = 0;

	private String addvcd;
	private String addvnm;
	private String position;// 职位
	private int vacation = 0;// 请假
	private int wordTime = 0;// 总时长 工作时长
	private double score = 0;

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getWordTime() {
		return wordTime;
	}

	public void setWordTime(int wordTime) {
		this.wordTime = wordTime;
	}

	private int toWord = 0;// 打卡次数
	private int wordDays = 0;// 应工作天数
	private double attendance;// 出勤率

	private int dealNum = 0;// 维修数量数量
	private int stcdNum = 0;// 管理范围数量
	private int repeat = 0;// 重复
	private double maintenanceRate; // 维修率
	private int ranking = 0;

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAddvcd() {
		return addvcd;
	}

	public void setAddvcd(String addvcd) {
		this.addvcd = addvcd;
	}

	public String getAddvnm() {
		return addvnm;
	}

	public void setAddvnm(String addvnm) {
		this.addvnm = addvnm;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getVacation() {
		return vacation;
	}

	public void setVacation(int vacation) {
		this.vacation = vacation;
	}

	public int getToWord() {
		return toWord;
	}

	public void setToWord(int toWord) {
		this.toWord = toWord;
	}

	public int getWordDays() {
		return wordDays;
	}

	public void setWordDays(int wordDays) {
		this.wordDays = wordDays;
	}

	public double getAttendance() {
		return attendance;
	}

	public void setAttendance(double attendance) {
		this.attendance = attendance;
	}

	public int getDealNum() {
		return dealNum;
	}

	public void setDealNum(int dealNum) {
		this.dealNum = dealNum;
	}

	public int getStcdNum() {
		return stcdNum;
	}

	public void setStcdNum(int stcdNum) {
		this.stcdNum = stcdNum;
	}

	public int getRepeat() {
		return repeat;
	}

	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}

	public double getMaintenanceRate() {
		return maintenanceRate;
	}

	public void setMaintenanceRate(double maintenanceRate) {
		this.maintenanceRate = maintenanceRate;
	}

	public static void sortIntMethod(List<Assessment> list) {
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				Assessment stu1 = (Assessment) o1;
				Assessment stu2 = (Assessment) o2;
				if (stu1.getScore() < stu2.getScore()) {
					return 1;
				} else if (stu1.getStcdNum() == stu2.getStcdNum()) {
					return 0;
				} else {
					return -1;
				}
			}
		});

	}
}
