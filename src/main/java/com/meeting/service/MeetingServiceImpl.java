package com.meeting.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meeting.mapper.MeetingMapper;
import com.meeting.pojo.Meeting;
import com.meeting.pojo.MeetingExample;
import com.meeting.pojo.MeetingExample.Criteria;
@Service
public class MeetingServiceImpl implements MeetingService {
	
	@Autowired
	MeetingMapper meetingMapper;

	public Meeting getMeetingById(long id) {
		// TODO Auto-generated method stub
		
		return meetingMapper.selectByPrimaryKey(id);
	}

	
	
	/*
	 * ��������
	 * �β�
	 * 	��¼��������ƣ�����ʱ�䣬����ص㣬��������
	 * 	�Ǳ�¼�������̣�����ָ��
	 * 	
	 * ҵ��
	 * 	����ţ������ֶ�
	 *  ���鴴��ʱ�䣺��¼����ǰʱ��
	 *  �������ʱ�䣺�Ǳ�¼
	 *  ����״̬����¼��ö��ֵ�����У�1����������0����
	 *  
	 *  �Ƿ���Ҫ�òͣ���¼���ǣ�1������0��Ĭ�ϣ���
	 *  �Ƿ���Ҫס�ޣ���¼���ǣ�1������0��Ĭ�ϣ���
	 *  
	 *  
	 * 		
	 * 
	 * */
	@Override
	public void createMeeting(Meeting meeting) {
		// TODO Auto-generated method stub
		//���ô���ʱ��
		long time = System.currentTimeMillis();
		Timestamp timestamp = new Timestamp(time);
		meeting.setMcreate(timestamp);
		
		//���û���״̬,����
		meeting.setMflag(1);
		
		//�����Ƿ��òͣ���
		meeting.setMeat(0);
		
		//�����Ƿ�ס�ޣ���
		meeting.setMhotel(0);
		
		//���û�������
		meeting.setMtype(0);
		meetingMapper.insert(meeting);
	}

	
	/*
	 * �����û�id��ȡ������Ϣ
	 * */
	@Override
	public List<Meeting> findMeetingView(String userid) {
		// TODO Auto-generated method stub
	
		MeetingExample example = new MeetingExample();
		Criteria criteria = example.createCriteria();
		criteria.andUseridEqualTo(userid);
		List<Meeting> meetings = meetingMapper.selectByExample(example);
		return meetings;
	}


	/*
	 * ����mnum��ȡ����
	 * */
	@Override
	public Meeting findMeetingById(long mnum) {
		// TODO Auto-generated method stub
		Meeting meeting = meetingMapper.selectByPrimaryKey(mnum);
		return meeting;
	}


	/*
	 * ���»�����Ϣ
	 * */
	@Override
	public void updateMeeting(Meeting meeting) {
		// TODO Auto-generated method stub
		Meeting meeting2 = meetingMapper.selectByPrimaryKey(meeting.getMnum());
		meeting2.setMname(meeting.getMname());
		meeting2.setMtime(meeting.getMtime());
		meeting2.setMguide(meeting.getMguide());
		meeting2.setMflow(meeting.getMflow());
		meeting2.setMcontent(meeting.getMcontent());
		meeting2.setMplace(meeting.getMplace());
		meetingMapper.updateByPrimaryKey(meeting2);
		
	}



	@Override
	public void updateMeetingById(Meeting meeting) {
		// TODO Auto-generated method stub
		meetingMapper.updateByPrimaryKey(meeting);
	}


	/*
	 * ���ݻ�������ģ����ѯ����
	 * */
	@Override
	public List<Meeting> selectMeetingByName(String name, String userid,long mnum) {
		// TODO Auto-generated method stub
		MeetingExample example = new MeetingExample();
		Criteria criteria = example.createCriteria();
		name = "%"+name+"%";
		List<Meeting> meetings = new ArrayList<Meeting>();
		//mnum=0,��ʾ��ѯ�û��Լ������Ļ���
		if(mnum == 0){
			criteria.andMnameLike(name);
			criteria.andUseridEqualTo(userid);
			meetings = meetingMapper.selectByExample(example);
		}
		//mnum��=0����ʾ�û�����Ļ���
		else{
			criteria.andMnameLike(name);
			criteria.andMnumEqualTo(mnum);
			meetings = meetingMapper.selectByExample(example);
		}
		return meetings;
	}

}
