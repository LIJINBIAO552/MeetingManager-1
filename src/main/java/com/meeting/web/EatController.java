package com.meeting.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.synth.SynthToggleButtonUI;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonFormat.Value;
import com.meeting.pojo.Eattype;
import com.meeting.pojo.ExpandedEat;
import com.meeting.pojo.Meeting;
import com.meeting.pojo.UserEat;
import com.meeting.service.EatService;

import net.sf.json.JSONArray;


@Controller
@RequestMapping("/eattype")
public class EatController {

	@Autowired
	EatService eatService;
	
	/*
	 *���ݻ���Ų�ѯס�����
	 * 
	 * */
	@RequestMapping(value="selectEat",method=RequestMethod.POST)
	@ResponseBody
	public List<Eattype> selectEat(HttpSession session){
		List<Eattype> list = new ArrayList<Eattype>();
		Meeting meeting = (Meeting) session.getAttribute("meeting");
		list = eatService.selectEat(meeting.getMnum());
		
		return list;
	}
	
	/*
	 * ����һ���òͼ�¼
	 * */
	@RequestMapping(value="insert",method=RequestMethod.POST,consumes="application/json;charset=utf-8")
	public void insertEat(@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") @RequestBody Eattype eattype,
				HttpSession session,HttpServletResponse response) throws ParseException, IOException{
		
		/*
		 * �޸�ʱ���ʽ
		 * */
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//��ʼʱ��
		long starttime = eattype.getEttimestart().getTime();
		//System.out.println(starttime);
		String dString = format.format(starttime);
		eattype.setEttimestart(format.parse(dString));
		//����ʱ��
		long endtime = eattype.getEttimeend().getTime();
		String eString = format.format(endtime);
		eattype.setEttimeend(format.parse(eString));
		
		/*
		 * ��ȡ�����
		 * */
		Meeting meeting = (Meeting) session.getAttribute("meeting");
		eattype.setMnum(meeting.getMnum());
		
		eatService.insertEat(eattype);
		
		//���ý��ַ���"UTF-8"����������ͻ��������
		response.setCharacterEncoding("UTF-8");
		//��ȡPrintWriter�����
		PrintWriter out = response.getWriter();
		out.write("{\"status\":1}");
  		//ˢ�»��棬�ܹ��ý����߸������
  		out.flush();
  		return;
	}
	
	/*
	 * ����һ���ò���Ϣ
	 * */
	@RequestMapping(value="update",consumes="application/json;charset=utf-8")
	public void updateEat(@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") @RequestBody Eattype eattype,
				HttpServletResponse response) throws ParseException, IOException{
		
		
		/*
		 * ����etnum��ȡ�ò����ͺ�
		 * */
		Eattype eattype2 = eatService.selectByPrimaryKey(eattype.getEtnum());
		
		
		/*
		 * �޸�ʱ���ʽ
		 * */
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//��ʼʱ��
		long starttime = eattype.getEttimestart().getTime();
		String dString = format.format(starttime);
		System.out.println(dString);
		eattype2.setEttimestart(format.parse(dString));
		
		//����ʱ��
		long endtime = eattype.getEttimeend().getTime();
		String eString = format.format(endtime);
		System.out.println(eString);
		eattype2.setEttimeend(format.parse(eString));
		
		//���»���ص�
		eattype2.setEtplace(eattype.getEtplace());
		//���»�������
		eattype2.setEttype(eattype.getEttype());
		System.out.println("---------------");
		System.out.println(eattype2.getEttimestart());
		System.out.println(eattype2.getEttimeend());
		eatService.updateEat(eattype2);
		
		//���ý��ַ���"UTF-8"����������ͻ��������
		response.setCharacterEncoding("UTF-8");
		//��ȡPrintWriter�����
		PrintWriter out = response.getWriter();
		out.write("{\"status\":1}");
  		//ˢ�»��棬�ܹ��ý����߸������
  		out.flush();
  		return;
		
	}
	
	/*
	 * ��������ɾ���òͼ�¼
	 * */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public void deleteEat(int etnum,HttpServletResponse response) throws IOException{
		
		eatService.deleteEat(etnum);
		//���ý��ַ���"UTF-8"����������ͻ��������
		response.setCharacterEncoding("UTF-8");
		//��ȡPrintWriter�����
		PrintWriter out = response.getWriter();
		out.write("{\"status\":1}");
  		//ˢ�»��棬�ܹ��ý����߸������
  		out.flush();
  		return;
	}
	
	@RequestMapping(value="jumpPage",method=RequestMethod.POST)
	@ResponseBody
	public void jumpPage(int etnum){
		
		System.out.println(etnum);
	}
	
	
	@RequestMapping(value="selectEatParticipants",method=RequestMethod.GET)
	public ModelAndView selectEatParticipants(int etnum,HttpSession session){
		ModelAndView movi = new ModelAndView();
		/*
		 * ��ȡ�����
		 * */
		Meeting meeting = (Meeting) session.getAttribute("meeting");
		
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("etnum", (long) etnum);
		map.put("mnum", meeting.getMnum());
		List<ExpandedEat> list = eatService.selectEatParticipants(map);
		JSONArray listArray = JSONArray.fromObject(list);
		movi.addObject("list", listArray);
		movi.addObject("etnum", etnum);
		movi.setViewName("selectEatParticipants");
		return movi;
	}
	
	@RequestMapping(value="insertEat",method=RequestMethod.POST)
	public void insertEat(@RequestParam(value="res[]",required=false) int[] res,int etnum,
			HttpSession session,HttpServletResponse response) throws IOException{
		System.out.println(res[0]);
		/*
		 * ��ȡ�����
		 * */
		Meeting meeting = (Meeting) session.getAttribute("meeting");
		eatService.insertEatAarray(res, etnum, meeting.getMnum());
		
		//���ý��ַ���"UTF-8"����������ͻ��������
		response.setCharacterEncoding("UTF-8");
		//��ȡPrintWriter�����
		PrintWriter out = response.getWriter();
		out.write("{\"status\":1}");
  		//ˢ�»��棬�ܹ��ý����߸������
  		out.flush();
  		return;
  		}
	
	/*
	 * �����ȡ�û���Ϣ
	 * */
	@RequestMapping(value="selectUser",method=RequestMethod.POST)
	@ResponseBody
	public List<UserEat> selectUser(int etnum,HttpSession session){
		
		/*
		 * ��ȡ�����
		 * */
		Meeting meeting = (Meeting) session.getAttribute("meeting");
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("mnum", meeting.getMnum());
		map.put("etnum", (long) etnum);
		List<UserEat> list = eatService.selectUserByMnumEtnum(map);
		
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).toString());
		}
		return list;
		
	}
	
	
	/*
	 * ����eatnumɾ����¼
	 * */
	@RequestMapping(value="deleteEat",method=RequestMethod.POST)
	public void deleteSpecificEat(Integer eatnum,HttpServletResponse response) throws IOException{
		
		eatService.deleteSpecificEat(eatnum);
		
		//���ý��ַ���"UTF-8"����������ͻ��������
		response.setCharacterEncoding("UTF-8");
		//��ȡPrintWriter�����
		PrintWriter out = response.getWriter();
		out.write("{\"status\":1}");
  		//ˢ�»��棬�ܹ��ý����߸������
  		out.flush();
  		return;
	}
  		
	
}
