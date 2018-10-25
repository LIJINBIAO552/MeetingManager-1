package com.meeting.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.code.kaptcha.Constants;
import com.meeting.pojo.User;
import com.meeting.service.UserService;

@Controller
@RequestMapping("/User")
public class UserController {
	
	@Autowired
	UserService userService;
	
	/**
	 * ��֤����֤
	 */
	public boolean VerifyCodeUtil(HttpServletRequest request){
		String code=request.getParameter("authCode"); 
		String original =(String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if (!StringUtils.isEmpty(code)) {
            if (code.equalsIgnoreCase(original)) {
            	return true;
            }
        }
		return false;
	}
	
	/*
	 * ��¼
	 * */
	@RequestMapping(value="login",method = RequestMethod.POST)
	public void login(User user,HttpServletRequest request,
    		HttpServletResponse response) throws IOException{
		//���ý��ַ���"UTF-8"����������ͻ��������
		response.setCharacterEncoding("UTF-8");
		//��ȡPrintWriter�����
		PrintWriter out = response.getWriter();
		//��֤����֤
		
		//�����û���
		User userDb = userService.findUserById(user.getUserid());
		if( userDb == null ){
			out.write("{\"status\":0}");//�û�������
			return;
		}
		//�����û�����
		if( !userDb.getUserpw().equals(user.getUserpw())){
			out.write("{\"status\":-1}");//�������
			return;
		}
		//���ﲻʹ��shiro��֤��������ɵ�¼�������û���Ϣ���ػ���Χ
        request.getSession().setAttribute("user", user);
        //��ȡWEB��������IP��ַ
  		System.out.println(request.getLocalAddr());
  		//��ȡWEB��������������
  		System.out.println(request.getLocalName());
  		
  		//��¼�ɹ�
  		out.write("{\"status\":1}");
  		//ˢ�»��棬�ܹ��ý����߸������
  		out.flush();
  		return;
	}
	
	/*
	 * ��̨ע��
	 * */
	@RequestMapping(value="add",method = RequestMethod.POST)
	public void add(User user,HttpServletRequest request,
    		HttpServletResponse response) throws IOException{
		//���ý��ַ���"UTF-8"����������ͻ��������
		response.setCharacterEncoding("UTF-8");
		//��ȡPrintWriter�����
		PrintWriter out = response.getWriter();
		//��֤����֤
		boolean checkCode = VerifyCodeUtil(request);
		if(!checkCode){
			out.write("{\"status\":2}");
		}
		//�ж��û����Ƿ�ע��
		User userDbByID = userService.findUserById(user.getUserid());
		if(userDbByID != null && userDbByID.getUserid() == user.getUserid()){
			out.write("{\"status\":3}");
		}
		
		//�ж��ֻ������Ƿ�ע��
		User userDbByPhone = userService.findUserByPhone(user.getPhone()); 
		if(userDbByPhone != null && userDbByPhone.getPhone() == user.getPhone()){
			out.write("{\"status\":4}");
		}
		userService.InsertUser(user);
		//��ȡWEB��������IP��ַ
		System.out.println(request.getLocalAddr());
		//��ȡWEB��������������
		System.out.println(request.getLocalName());
		//����ɹ�����
		out.write("{\"status\":1}");
		
	}
	
}
