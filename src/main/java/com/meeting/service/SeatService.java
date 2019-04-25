package com.meeting.service;

import java.util.List;

import com.meeting.pojo.ExpandedSeat;
import com.meeting.pojo.Seat;

/*
 * ��λ�����
 * */
public interface SeatService {
	
	/*
	 * ��ѯĳ���û���ĳ���������λ
	 * */
	public int selectSeatInUserid(long mnum,int pnum);
	
	/*
	 * ���ݻ���ţ��鿴δ�������λ����Ա
	 * */
	public List<ExpandedSeat> selectNotDistributeByMnum(long mnum);
	
	/*
	 * ���ݻ���ţ��鿴�Է������λ����Ա
	 * */
	public List<Seat> selectDistributeByMnum(long mnum);
	
	/*
	 * ���ݻ���ţ�ɾ��������λ
	 * */
	public void deleteSeat(long mnum);
	
	/*
	 * ������λ
	 * */
	public void insertSeat(Seat seat);
	
}
