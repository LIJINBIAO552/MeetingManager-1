package com.meeting.service;

import java.util.List;
import java.util.Map;

import com.meeting.pojo.ExpandedEat;
import com.meeting.pojo.ExpandedHotel;
import com.meeting.pojo.Hotel;
import com.meeting.pojo.Hoteltype;
import com.meeting.pojo.UserHotel;

public interface HotelService {
	
	/*
	 * ����������ѯס������
	 * */
	public Hoteltype selectHotelByPrimaryKey(int htnum);
	/*
	 * ���ݻ���Ų�ѯס������
	 * */
	public List<Hoteltype> selectHotelByMnum(long mnum);
	
	/*
	 * �����ò�����
	 * */
	public void updateHotel(Hoteltype hoteltype);
	
	/*
	 * �����ò�����
	 * */
	public void insertHotel(Hoteltype hoteltype);
	
	/*
	 * ɾ���ò�����
	 * */
	public void deleteHotel(int htnum);
	
	//����mnum��htnum��pflag��ȡδ����Ĳλ���Ա
	public List<ExpandedHotel> selectHotelParticipants(Map<String, Long> map);
	
	//����mnum,hynum��ȡ�û���Ϣ
	public List<UserHotel> selectUserByHotel(Map<String, Long> map);
	
	//����ס����Ա
	public void insertHotel(Hotel hotel);
	
	//ɾ��ס����Ա
	public void deleteHotelTab(int hnum);
	
	//����mnum,pnum��ȡס������
	public List<Hoteltype> seleteHoteltypeByUserid(Map<String, Long> map);
}
