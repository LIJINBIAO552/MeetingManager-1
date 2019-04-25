package com.meeting.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meeting.mapper.EatHotelMapper;
import com.meeting.mapper.EatMapper;
import com.meeting.mapper.EattypeMapper;
import com.meeting.pojo.Eat;
import com.meeting.pojo.EatExample;
import com.meeting.pojo.Eattype;
import com.meeting.pojo.EattypeExample;
import com.meeting.pojo.EattypeExample.Criteria;
import com.meeting.pojo.ExpandedEat;
import com.meeting.pojo.UserEat;
/*
 * ס��
 * */
@Service
public class EatServiceImpl implements EatService {
	
	@Autowired
	EattypeMapper eattypeMapper;
	
	@Autowired
	EatHotelMapper eatHotelMapper;
	
	@Autowired
	EatMapper eatMapper;
	/*
	 * ���ݻ���Ų�ѯ�û�����ò����
	 * */
	@Override
	public List<Eattype> selectEat(long mnum) {
		// TODO Auto-generated method stub
		List<Eattype> list = new ArrayList<Eattype>();
		EattypeExample example = new EattypeExample();
		Criteria criteria = example.createCriteria();
		criteria.andMnumEqualTo(mnum);
		list = eattypeMapper.selectByExample(example);
		return list;
	}
	
	/*
	 * ����ò�
	 * */
	@Override
	public void insertEat(Eattype eattype) {
		// TODO Auto-generated method stub
		
		/*
		 * ����ʱ��
		 * */
		
		eattypeMapper.insert(eattype);
	}
	
	
	/*
	 * ����
	 * */
	@Override
	public void updateEat(Eattype eattype) {
		// TODO Auto-generated method stub
		
		/*
		 * ����ʱ��
		 * */
		System.out.println("service"+eattype.getEttimestart());
		System.out.println("service"+eattype.getEttimeend());
		eattypeMapper.updateByPrimaryKey(eattype);
	}
	
	/*
	 * ����������ȡ�ò���Ϣ
	 * */
	@Override
	public Eattype selectByPrimaryKey(int etnum) {
		// TODO Auto-generated method stub
		return eattypeMapper.selectByPrimaryKey(etnum);
		
	}
	
	/*
	 * ��������ɾ���ò���Ϣ
	 * */
	@Override
	public void deleteEat(int etnum) {
		// TODO Auto-generated method stub
		eattypeMapper.deleteByPrimaryKey(etnum);
	}
	
	/*
	 * ����mnum��etnum��pflag��ȡδ����Ĳλ���Ա
	 */
	@Override
	public List<ExpandedEat> selectEatParticipants(Map<String, Long> map) {
		// TODO Auto-generated method stub
		
		//���������pflag=1����ʾ��׼���
		map.put("pflag", (long) 1);
		List<ExpandedEat> list = eatHotelMapper.selectSeatParticipants(map);
		return list;
	}
	
	/*
	 * ���ݲλ���Ա�ţ��ò����ͺţ����������ò�
	 * */
	@Override
	public void insertEatAarray(int[] pnumAarray, int etnum, long mnum) {
		// TODO Auto-generated method stub
		
		for(int i=0;i<pnumAarray.length;i++){
			Eat eat = new Eat();
			eat.setPnum(pnumAarray[i]);
			eat.setEtnum(etnum);
			eat.setMnum(mnum);
			eatMapper.insert(eat);
		}
	}
	
	/*
	 * ���ݲλ���Ա�ţ�����ţ���ѯ�ò�����
	 * */
	@Override
	public List<Eattype> selectEattypeByPM(Map<String, Long> map) {
		// TODO Auto-generated method stub
		
		List<Eattype> list = eatHotelMapper.selectEatTypeByUserid(map);
		return list;
	}

	/*
	 * ���ݻ���ţ��ò����ͺţ��ò����ͺţ���ѯ�û���Ϣ
	 */
	@Override
	public List<UserEat> selectUserByMnumEtnum(Map<String, Long> map) {
		// TODO Auto-generated method stub
		List<UserEat> list = eatHotelMapper.selectUserByMnumEtnum(map);
		return list;
	}

	@Override
	public void deleteSpecificEat(int eatnum) {
		// TODO Auto-generated method stub
		eatMapper.deleteByPrimaryKey(eatnum);
	}

}
