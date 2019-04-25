package com.meeting.mapper;

import java.util.List;
import java.util.Map;

import javax.print.DocFlavor.STRING;

import com.meeting.pojo.Eattype;
import com.meeting.pojo.ExpandedEat;
import com.meeting.pojo.ExpandedHotel;
import com.meeting.pojo.Hoteltype;
import com.meeting.pojo.User;
import com.meeting.pojo.UserEat;
import com.meeting.pojo.UserHotel;

public interface EatHotelMapper {

	public List<ExpandedEat> selectSeatParticipants(Map<String, Long> map);
	
	/*
	 * ����ѯ��eat,eattype
	 * 1������mnum��pnum��ѯeat���etnum
	 * 2������etnum,��ѯeattype���¼
	 * */
	public List<Eattype> selectEatTypeByUserid(Map<String,Long> map);
	
	/*
	 * ����ѯ��user,participants,eat
	 * ��ѯ���ݣ����زμ�ĳ�������ĳ���ò����͵��û���Ϣ
	 * ���������mnum,etnum
	 * 
	 * */
	public List<UserEat> selectUserByMnumEtnum(Map<String, Long> map);
	
	public List<ExpandedHotel> selectHotelParticipants(Map<String, Long> map);
	
	/*
	 * ����ѯ��user,participants,hotel
	 * ��ѯ���ݣ����زμ�ĳ�������ĳ���ò����͵��û���Ϣ
	 * ���������mnum,hnum
	 * 
	 * */
	public List<UserHotel> selectUserByMnumHtnum(Map<String, Long> map);
	
	public List<Hoteltype> selectHotelTypeByUserid(Map<String, Long> map);
	
}
