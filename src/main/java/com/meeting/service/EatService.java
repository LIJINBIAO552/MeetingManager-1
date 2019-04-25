package com.meeting.service;

import java.util.List;
import java.util.Map;

import com.meeting.pojo.Eattype;
import com.meeting.pojo.ExpandedEat;
import com.meeting.pojo.UserEat;

/*
 * �ò�
 * */
public interface EatService {
	
	//����������ȡ�ò���Ϣ
	public Eattype selectByPrimaryKey(int etnum);
	
	public List<Eattype> selectEat(long mnum);
	
	//����ò�
	public void insertEat(Eattype eattype);
	
	//�����ò�
	public void updateEat(Eattype eattype);
	
	//ɾ���ò�����
	public void deleteEat(int etnum);
	
	//����mnum��etnum��pflag��ȡδ����Ĳλ���Ա
	public List<ExpandedEat> selectEatParticipants(Map<String, Long> map);
	
	//���ݲλ���Ա�ţ��ò����ͺţ����������ò�
	public void insertEatAarray(int[] pnumAarray,int etnum,long mnum);
	
	//���ݲλ���Ա�ţ�����ţ���ѯ�ò�����
	public List<Eattype> selectEattypeByPM(Map<String, Long> map);
	
	//���ݻ���ţ��ò����ͺţ��ò����ͺţ���ѯ�û���Ϣ
	public List<UserEat> selectUserByMnumEtnum(Map<String, Long> map);
	
	//ɾ��ĳ���˵��ò�
	public void deleteSpecificEat(int eatnum);
}
