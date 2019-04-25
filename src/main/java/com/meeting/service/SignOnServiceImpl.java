package com.meeting.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meeting.mapper.ExpandedSignonMapper;
import com.meeting.mapper.SignonMapper;
import com.meeting.pojo.ExpandedSignon;
import com.meeting.pojo.Signon;
import com.meeting.pojo.SignonExample;
import com.meeting.pojo.SignonExample.Criteria;

/*
 * ǩ��
 * */
@Service
public class SignOnServiceImpl implements SignOnService {
	
	@Autowired
	SignonMapper signonMapper;
	
	@Autowired
	ExpandedSignonMapper expandedSignonMapper;
	/*
	 * �λ���Աǩ��
	 * */
	@Override
	public Map<String, String> SignOnMeeting(Signon signon) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		
		SignonExample example = new SignonExample();
		Criteria criteria = example.createCriteria();
		criteria.andMnumEqualTo(signon.getMnum());
		criteria.andUseridEqualTo(signon.getUserid());
		List<Signon> list = signonMapper.selectByExample(example);
		Signon result_signon = list.get(0);
		
		if(result_signon.getSflag() == null){
			//δǩ��
			result_signon.setSflag(1);
			Date date = Calendar.getInstance().getTime();
			result_signon.setSigntime(date);
			signonMapper.updateByPrimaryKey(result_signon);
			map.put("msg", "ǩ���ɹ�");
		}else{
			//��ǩ��
			map.put("msg", "��ǩ������Ҫ�ظ�ǩ����");
		}
	
		return map;
	}
	
	
	/*
	 * ���ݻ���Ų�ѯǩ����
	 * */
	@Override
	public List<ExpandedSignon> SignOnList(Map<String, Long> map) {
		// TODO Auto-generated method stub
		List<ExpandedSignon> list = new ArrayList<ExpandedSignon>();
		
		list = expandedSignonMapper.selcetSignonList(map);
		//System.out.println(list.size()+"a1111111111111");
		return list;
	}

	
	@Override
	public int SignOnCount(long mnum) {
		// TODO Auto-generated method stub
		int count = expandedSignonMapper.countSignonList(mnum);
		return count;
	}

}
