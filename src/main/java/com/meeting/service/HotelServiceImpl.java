package com.meeting.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meeting.mapper.EatHotelMapper;
import com.meeting.mapper.HotelMapper;
import com.meeting.mapper.HoteltypeMapper;
import com.meeting.pojo.ExpandedEat;
import com.meeting.pojo.ExpandedHotel;
import com.meeting.pojo.Hotel;
import com.meeting.pojo.HotelExample;
import com.meeting.pojo.Hoteltype;
import com.meeting.pojo.HoteltypeExample;
import com.meeting.pojo.HoteltypeExample.Criteria;
import com.meeting.pojo.UserHotel;

@Service
public class HotelServiceImpl implements HotelService{
	
	@Autowired
	HoteltypeMapper hoteltypeMapper;
	/*
	 * ���ݻ���Ż�ȡס������
	 * */
	
	@Autowired
	EatHotelMapper eatHotelMapper;
	
	@Autowired
	HotelMapper hotelMapper;
	@Override
	public List<Hoteltype> selectHotelByMnum(long mnum) {
		// TODO Auto-generated method stub
		HoteltypeExample example = new HoteltypeExample();
		Criteria criteria = example.createCriteria();
		criteria.andMnumEqualTo(mnum);
		List<Hoteltype> list = hoteltypeMapper.selectByExample(example);
		return list;
	}
	
	/*
	 * ����ס������
	 * */
	@Override
	public void updateHotel(Hoteltype hoteltype) {
		// TODO Auto-generated method stub
		hoteltypeMapper.updateByPrimaryKey(hoteltype);
	}
	
	/*
	 * ����������ȡס������
	 * */
	@Override
	public Hoteltype selectHotelByPrimaryKey(int htnum) {
		// TODO Auto-generated method stub
		
		return hoteltypeMapper.selectByPrimaryKey(htnum);
	}
	
	/*
	 * �����������
	 */
	@Override
	public void insertHotel(Hoteltype hoteltype) {
		// TODO Auto-generated method stub
		hoteltypeMapper.insert(hoteltype);
	}
	
	/*
	 * ɾ����������
	 * */
	@Override
	public void deleteHotel(int htnum) {
		// TODO Auto-generated method stub
		hoteltypeMapper.deleteByPrimaryKey(htnum);
	}

	@Override
	public List<ExpandedHotel> selectHotelParticipants(Map<String, Long> map) {
		// TODO Auto-generated method stub
		//���������pflag=1����ʾ��׼���
		map.put("pflag", (long) 1);
		List<ExpandedHotel> list = eatHotelMapper.selectHotelParticipants(map);
		return list;
		
	}

	@Override
	public List<UserHotel> selectUserByHotel(Map<String, Long> map) {
		// TODO Auto-generated method stub
		List<UserHotel> list = eatHotelMapper.selectUserByMnumHtnum(map);
		return list;
	}

	@Override
	public void insertHotel(Hotel hotel) {
		// TODO Auto-generated method stub
		hotelMapper.insert(hotel);
	}

	@Override
	public void deleteHotelTab(int hnum) {
		// TODO Auto-generated method stub
		hotelMapper.deleteByPrimaryKey(hnum);
	}

	@Override
	public List<Hoteltype> seleteHoteltypeByUserid(Map<String, Long> map) {
		// TODO Auto-generated method stub
		List<Hoteltype> list = eatHotelMapper.selectHotelTypeByUserid(map);
		return list;
	}

}
