package com.meeting.mapper;

import java.util.List;

import com.meeting.pojo.ExpandedSeat;

/*
 * ��λ��seat��չ��mapper
 * */
public interface ExpandedSeatMapper {

	/*
	 * ����ѯ��seat,participants��
	 * */
	List<ExpandedSeat> selectSeatParticipants(long mnum);
}
