package com.meeting.service;

import java.util.List;

import com.meeting.pojo.Meeting;

public interface MeetingService {
	
	Meeting getMeetingById(long id);
	
	//��������
	void createMeeting(Meeting meeting);
	
	//������ͼ
	List<Meeting> findMeetingView(String userid);
	
	//����id��ѯ����
	Meeting findMeetingById(long nnum);
	
	//���»�����Ϣ
	void updateMeeting(Meeting meeting);
	
	//���������޸Ļ�����Ϣ
	void updateMeetingById(Meeting meeting);
	
	//���ݻ�������ģ����ѯ�����¼
	List<Meeting> selectMeetingByName(String name,String userid,long mnums);
}
