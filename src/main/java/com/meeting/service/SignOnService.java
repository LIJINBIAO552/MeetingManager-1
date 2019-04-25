package com.meeting.service;

import java.util.List;
import java.util.Map;

import com.meeting.pojo.ExpandedSignon;
import com.meeting.pojo.Signon;

/*
 * ǩ��
 * */
public interface SignOnService {
	
	/*
	 * ���ݻ���Ż�ȡǩ����
	 * */
	public List<ExpandedSignon> SignOnList(Map<String, Long> map);
	
	/*
	 * �λ���Աǩ��
	 * */
	public Map<String, String> SignOnMeeting(Signon sginon);
	
	/*
	 * ǩ������
	 * */
	public int SignOnCount(long mnum);
}
