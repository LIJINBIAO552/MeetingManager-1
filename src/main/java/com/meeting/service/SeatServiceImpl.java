package com.meeting.service;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ListSelectionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meeting.mapper.ExpandedSeatMapper;
import com.meeting.mapper.SeatMapper;
import com.meeting.pojo.ExpandedSeat;
import com.meeting.pojo.Seat;
import com.meeting.pojo.SeatExample;
import com.meeting.pojo.SeatExample.Criteria;

@Service
public class SeatServiceImpl implements SeatService{
	
	@Autowired
	SeatMapper seatMapper;
	
	@Autowired
	ExpandedSeatMapper expandedSeatMapper;
	/*
	 * ���ݻ���źͲλ���Ա�Ż�ȡ��λλ��
	 * */
	@Override
	public int selectSeatInUserid(long mnum, int pnum) {
		// TODO Auto-generated method stub
		SeatExample example = new SeatExample();
		Criteria criteria = example.createCriteria();
		criteria.andMnumEqualTo(mnum);
		criteria.andPnumEqualTo(pnum);
		List<Seat> list = seatMapper.selectByExample(example);
		Seat seat = new Seat();
		if(!list.isEmpty()){
			seat = list.get(0);
		}
		
		return seat.getSeatnum();
	}
	
	/*
	 * ���ݻ���ţ��鿴δ�������λ����Ա
	 * */
	@Override
	public List<ExpandedSeat> selectNotDistributeByMnum(long mnum) {
		// TODO Auto-generated method stub
		List<ExpandedSeat> list = new ArrayList<ExpandedSeat>();
		list = expandedSeatMapper.selectSeatParticipants(mnum);
				
		if(list.isEmpty()){
			list = null;
		}
		return list;
	}
	
	/*
	 * ���ݻ���ţ��鿴�������λ����Ա
	 * */
	@Override
	public List<Seat> selectDistributeByMnum(long mnum) {
		// TODO Auto-generated method stub
		List<Seat> list = new ArrayList<Seat>();
		SeatExample example = new SeatExample();
		Criteria criteria = example.createCriteria();
		criteria.andMnumEqualTo(mnum);
		list = seatMapper.selectByExample(example);
		return list;
	}
	
	/*
	 * ���ݻ���ţ�ɾ��������λ
	 * */
	@Override
	public void deleteSeat(long mnum) {
		// TODO Auto-generated method stub
		SeatExample example = new SeatExample();
		Criteria criteria = example.createCriteria();
		criteria.andMnumEqualTo(mnum);
		seatMapper.deleteByExample(example);
	}
	
	/*
	 * ���������λ����Ϣ
	 * */
	@Override
	public void insertSeat(Seat seat) {
		// TODO Auto-generated method stub
		seatMapper.insert(seat);
	}

}
