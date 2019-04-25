package com.meeting.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.zookeeper.server.SessionTracker.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.SerializableString;
import com.meeting.mapper.ParticipantsMapper;
import com.meeting.pojo.ExpandedSeat;
import com.meeting.pojo.Meeting;
import com.meeting.pojo.Participants;
import com.meeting.pojo.Seat;
import com.meeting.pojo.Seattype;
import com.meeting.service.MeetingService;
import com.meeting.service.ParticipantsService;
import com.meeting.service.SeatService;
import com.meeting.service.SeatTypeService;

import net.sf.json.JSONArray;



@Controller
@RequestMapping("/seat")
public class SeatTypeController {
	
	@Autowired
	SeatTypeService seatTypeService;
	
	@Autowired
	ParticipantsService participantsService;
	
	@Autowired
	SeatService seatService;
	
	@RequestMapping(value="seat")
	@ResponseBody
	public ModelAndView selectSeat(HttpSession session){
		ModelAndView movi = new ModelAndView();
		
		Meeting meeting = (Meeting) session.getAttribute("meeting");
		
		
		Seattype seattype = new Seattype();
		List<Seattype> seatList = seatTypeService.selectByMnum(meeting.getMnum());
		if(seatList.size()>0){
			seattype = seatList.get(0);
		}
		
		/*
		 * δ����
		 * */
		List<ExpandedSeat> notDistributeList  = seatService.selectNotDistributeByMnum(meeting.getMnum());
		
		List<String> notDistributeName = new ArrayList<String>();
		if(notDistributeList != null){
			for(int i=0;i<notDistributeList.size();i++){
				ExpandedSeat expandedSeat = notDistributeList.get(i);
				String name = expandedSeat.getPname();
				System.out.println(name);
				notDistributeName.add(name);
			}
		}
		
		List<Participants> list = participantsService.findPartipantsByMnum(meeting.getMnum());
		List<String> names = new ArrayList<String>();
		List<Integer> numbers = new ArrayList<Integer>();
		if(list != null){
			for(int i=0;i<list.size();i++){
				Participants participants = list.get(i);
				names.add(participants.getPname());
				numbers.add(participants.getPnum());
			}
			
		}
		
		/*
		 * �ѷ���Ĳλ���Ա�źͲλ���Ա��
		 * */
		List<Seat> seatDistributeList = seatService.selectDistributeByMnum(meeting.getMnum());
		List<String> distribute = new ArrayList<String>();
		List<String> distributeNumber = new ArrayList<String>();
		//��ȡ��λ����
		if(seattype.getRow() != null){
		int length = seattype.getRow()*seattype.getLine();

		for(int i=0;i<length;i++){
			distribute.add(null);
			distributeNumber.add(null);
		}
		for(int i=0;i<length;i++){
			
			for(int j=0;j<seatDistributeList.size();j++){
				if(seatDistributeList.get(j).getSeatnum()==i){
					distribute.set(i, participantsService.findPartipantsByPnum(seatDistributeList.get(j).getPnum()).getPname());
					distributeNumber.set(i, seatDistributeList.get(j).getPnum().toString());
					break;
				}
			}

			
		}}
		
		JSONArray jsonArray = JSONArray.fromObject(names);
		JSONArray numbersArray = JSONArray.fromObject(numbers);
		JSONObject seatListJsonObject = (JSONObject) JSONObject.toJSON(seattype);
		JSONArray distributeArray = JSONArray.fromObject(distribute);
		JSONArray distributeNumberArray = JSONArray.fromObject(distributeNumber);
		JSONArray notDistributeNameArray = JSONArray.fromObject(notDistributeName);
		movi.addObject("names", jsonArray);
		movi.addObject("numbers",numbersArray);
		movi.addObject("seattype", seatListJsonObject);
		movi.addObject("distribute", distributeArray);
		movi.addObject("distributeNumber", distributeNumberArray);
		movi.addObject("notDistributeName", notDistributeNameArray);
		movi.setViewName("seat");
		return movi;
	}
	
	/*
	 * ��λ����
	 * */
	@RequestMapping(value="updateSeat",method = RequestMethod.POST)
	public void updateSeat(@RequestParam("name")String[] name,@RequestParam("numbers")String[] numbers,
				int hang,int lie,HttpSession session,HttpServletResponse response) throws IOException{
		
		/*
		 * �����λ�������
		 * */
		Meeting meeting = (Meeting) session.getAttribute("meeting");
		seatTypeService.deleteSeatyMnum(meeting.getMnum());
		seatService.deleteSeat(meeting.getMnum());
		
		/*
		 * ������λ������
		 * */
		Seattype seattype = new Seattype();
		seattype.setMnum(meeting.getMnum());
		seattype.setLine(hang);
		seattype.setRow(lie/hang);
		seatTypeService.insertSeattype(seattype);
		
		
		/*
		 * ������λ����λ��Ϣ
		 * */
		for(int i = 0;i<numbers.length;i++){
			if(!numbers[i].isEmpty()){
				Seat seat = new Seat();
				seat.setMnum(meeting.getMnum());
				seat.setPnum(Integer.valueOf(numbers[i]));
				seat.setSeatnum(i);
				seatService.insertSeat(seat);
			}
		}
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		//��ȥPrintWriter������
		PrintWriter out = response.getWriter();
		//��������ɹ�
  		out.write("{\"status\":1}");
  		//ˢ�»��棬�ܹ��ý����߸������
  		out.flush();
	}
	
}
