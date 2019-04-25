package com.meeting.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meeting.common.ParticipantsList;
import com.meeting.pojo.Meeting;
import com.meeting.pojo.Participants;
import com.meeting.pojo.User;
import com.meeting.service.ParticipantsService;
import com.meeting.service.UserService;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@Controller
public class PageController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ParticipantsService participantsService;
	
	@RequestMapping("/")
	public String showIndex(HttpServletRequest request){
		
		//��ȡcookie����
		Cookie[] cookies = request.getCookies();
		if(cookies == null){
			return "index";
		}else{
			//����cookie���飬��ȡ�û���������
			String userid = null;
			String userpw = null;
			for(Cookie c:cookies){
				if(c.getName().equals("userid")){
					userid = c.getValue();
				}
				if(c.getName().equals("userpw")){
					userpw = c.getValue();
				}
			}
			//�����û���������
			User user = userService.findUserById(userid);
			if(user == null){
				return "index";
			}
			if(!user.getUserpw().equals(userpw)){
				return "index";
			}
		 	}
		
		return "homepage";
		
		//return "index";
	}
	
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page){
		
		return page;
	}
	
	@RequestMapping("excel")
	@ResponseBody
	public Map<String, String> excelExport(HttpSession session) throws IOException{
		Map<String, String> map = new HashMap<String, String>();
		String fileDir = "C:/Users/XHJ/Desktop";
		String filePath = fileDir + "/" + "�λ���Ա����" + ".xls";
		if (!new File(fileDir).exists()) {
			new File(filePath).mkdirs();
		}
		File excel = new File(filePath);
		int i = 1;
		while(excel.exists()){
			int tmppath = filePath.lastIndexOf(".");
			if (i!=1) {
				tmppath= filePath.indexOf("(");
			}
			filePath = filePath.substring(0, tmppath) +"("+ (i++)+").xls";
			excel = new File(filePath);
		}
		excel.createNewFile();
		
		Meeting meeting = (Meeting) session.getAttribute("meeting");
        String resultcount = excelExportFromDB(excel,meeting);
        
        map.put("path",filePath);
        map.put("resultcount",resultcount);
		
		return map;
	}

	private String excelExportFromDB(File excel,Meeting meeting) {
		// TODO Auto-generated method stub
		 int count =0;
	        try {
	        
	            //��ʼ������һ��workbook
	            WritableWorkbook writableWorkbook = null;
	            //������д��excel�Ĺ�����
//	            File file = new File(fileName);
	            if (!excel.exists()) {
	                excel.createNewFile();
	            }
	            //��filenameΪ�ļ�������һ��workbook
	            writableWorkbook = Workbook.createWorkbook(excel);
	            //����������
	            WritableSheet writableSheet = writableWorkbook.createSheet("Sheet1", 0);

	            //��ѯ���ݿ��ȡѧ������ѧԺ��Ʒ������������������
	          
	    		List<Participants> list = participantsService.findPartpantsByMP(meeting.getMnum());
	    		
	    		List<ParticipantsList> participantsLists = new ArrayList<ParticipantsList>();
	    		/*
	    		 * ɸѡ���������Ĳλ���Ա
	    		 * */
	    		for (int i = 0; i < list.size(); i++) {
	    			if(list.get(i).getPflag()==0){
	    				list.remove(i);
	    			}
	    		}
	    		
	    		for(int i=0;i<list.size();i++){
	    			ParticipantsList participantsList = new ParticipantsList();
	    			Participants participants = new Participants();
	    			participants = list.get(i);
	    			participantsList.setPflag(participants.getPflag());
	    			participantsList.setPname(participants.getPname());
	    			participantsList.setPsex(participants.getPsex());
	    			participantsList.setPunit(participants.getPunit());
	    			participantsLists.add(participantsList);
	    			
	    		}

	            //Ҫ���뵽����
	            Label labelName = new Label(0, 0, "����");
	            Label labelSex = new Label(1, 0, "�Ա�");
	            Label labelUnit = new Label(2, 0, "��λ");
	            Label labelFlag = new Label(3, 0, "�Ƿ���׼���");

	            writableSheet.addCell(labelName);
	            writableSheet.addCell(labelSex);
	            writableSheet.addCell(labelUnit);
	            writableSheet.addCell(labelFlag);
	          
	            for (int i = 0; i < participantsLists.size(); i++) {
	                    Label labelName_i = new Label(0, i + 1, participantsLists.get(i).getPname());
	                    Label labelSex_i = new Label(1, i + 1, participantsLists.get(i).getPsex());
	                    Label labelUnit_i = new Label(2, i + 1, participantsLists.get(i).getPunit());
	                    String pflagString_i = null;
	                    if (participantsLists.get(i).getPflag() != null) {
	                    	if (participantsLists.get(i).getPflag() == 1) {
		                    	pflagString_i = "��׼���";
							}else if (participantsLists.get(i).getPflag() == 2) {
								pflagString_i = "�ܾ����";
							}
						}else{
							pflagString_i = "�ܾ����";
						}
	                   
	                    Label labelFlag_i = new Label(3, i + 1,pflagString_i);
	                    
	                    writableSheet.addCell(labelName_i);
	                    writableSheet.addCell(labelSex_i);
	                    writableSheet.addCell(labelUnit_i);
	                    writableSheet.addCell(labelFlag_i);
	                    count++;
	            }
	            //д���ĵ�
	            writableWorkbook.write();
	            //�ر�excel����������
	            writableWorkbook.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return String.valueOf(count);
	} 
}
