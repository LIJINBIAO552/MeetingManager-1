package com.meeting.service;

import java.util.List;

import com.meeting.pojo.Seattype;

public interface SeatTypeService {

	/*
	 * ���ݻ���Ż�ȡ��λ���к���
	 * */
	public List<Seattype> selectByMnum(long mnum);
	
	/*
	 * ���ݻ����ɾ����λ��
	 * */
	public void deleteSeatyMnum(long mnum);
	
	/*
	 * ������λ��
	 * */
	public void insertSeattype(Seattype seattype);
}
