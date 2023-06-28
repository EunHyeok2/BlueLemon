package com.blue.service;

import java.util.List;

import com.blue.dto.AlarmVO;

public interface AlarmService {

	public void insertAlarm(AlarmVO alarmVO);
	
	// �� ȸ���� ��ü �˶� ��������
	List<AlarmVO> getAllAlarm(String member_Id);
	
	// �ش� �˶��� seq ��������
	int getOneAlarm_Seq(AlarmVO alarmVO);
	
	// �˶� ����
	void deleteAlarm(int alarm_Seq);
}
