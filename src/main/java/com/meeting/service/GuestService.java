package com.meeting.service;

import java.util.List;

import com.meeting.pojo.Guest;

public interface GuestService {

	/*
	 * ��Ӽα�
	 * */
	public void addGuest(long mnum,String[] gnameString,String[] gphoneString,
							String[] gtitleString,String[] gintroductionString);
	
	/*
	 * ���ݻ���Ż�ȡ�α���Ϣ
	 * */
	public List<Guest> getGuest(long mnum);
	
	//����������ѯ�α�
	public Guest getGuestById(int gnum);
	//��Ӽα�
	public void insertGuest(Guest guest);
	
	//���¼α���Ϣ
	public void updateGuest(Guest guest);
	
	//ɾ���α���Ϣ
	public void deleteGuest(int gnum);
	}
