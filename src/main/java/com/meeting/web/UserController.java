package com.meeting.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;
import com.meeting.pojo.User;
import com.meeting.service.ImageService;
import com.meeting.service.UserService;

@Controller
@RequestMapping("/User")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ImageService imageService;
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
  		//session.setAttribute("userDb", userDb);
  		//�����û���cookie
  		Cookie useridCookie = new Cookie("userid", user.getUserid());
  		//������Чʱ��3��
  		useridCookie.setMaxAge(60*60*24*3);
  		//useridCookie.setMaxAge(60);
  		useridCookie.setPath("/");
  		response.addCookie(useridCookie);
  		
  		//��������cookie
  		Cookie passwdCookie = new Cookie("userpw", user.getUserpw());
  		//������Чʱ��30����
  		passwdCookie.setMaxAge(60*60*24*3);
  		//useridCookie.setMaxAge(60);
  		passwdCookie.setPath("/");
  		response.addCookie(passwdCookie);
  		
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
	
	/*
	 * �˳���¼
	 * */
	@RequestMapping(value="cancel",method=RequestMethod.POST)
	public void cancel(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		//���cookie����
		Cookie[] cookies = request.getCookies();
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
		//�����û���cookie
  		Cookie useridCookie = new Cookie("userid", userid);
  		useridCookie.setMaxAge(0);
  		useridCookie.setPath("/");
  		response.addCookie(useridCookie);
  		
  		//��������cookie
  		Cookie passwdCookie = new Cookie("userpw", userpw);
  		passwdCookie.setMaxAge(0);
  		passwdCookie.setPath("/");
  		response.addCookie(passwdCookie);
  		
  		out.write("{\"status\":1}");
  		out.flush();
  		
	}
	
	@RequestMapping(value="index",method=RequestMethod.GET)
	public String changeToIndex(){
		return "index";
	}
	
	/*
	 * ��ȡ�û���Ϣ
	 * */
	@RequestMapping(value="find")
	public ModelAndView findUser(HttpServletRequest request,HttpServletResponse response){
		ModelAndView movi = new ModelAndView();
		
		//ʹ��cookie��ȡ�û���
		String userid = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if(cookie.getName().equals("userid")){
				userid = cookie.getValue();
				break;
			}
		}
		
		//����userid��ȡ�û���Ϣ
		User user = userService.findUserById(userid);
		
		//�ж��û�ͷ���Ƿ�Ϊ�գ����գ�����Ĭ����Ƭ
		if(user.getHeadphoto() == null){
			user.setHeadphoto("headphoto.png");
		}
		movi.addObject("user", user);
		movi.setViewName("UserInfo"); 
		return movi;
	}
	
	@RequestMapping(value="UpdateUserInfo",method=RequestMethod.POST)
	public ModelAndView UpdateUserInfo(@RequestParam(value="pic1",required=false) MultipartFile pic1,User user,
			HttpServletRequest request){
		ModelAndView movi = new ModelAndView();
		
		//ʹ��cookie��ȡ�û���
		String userid = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if(cookie.getName().equals("userid")){
				userid = cookie.getValue();
				break;
			}
		}
		/*
		 * ����userid��ȡ�û���Ϣ
		 * */
		User user2 = userService.findUserById(userid);
		
		/*
		 * �ж��Ƿ��޸�ͷ��
		 * */
		if(!pic1.isEmpty()){
			Map map = imageService.upload(pic1);
			String url = (String) map.get("url");
			user2.setHeadphoto(url);
		}
		
		/*
		 * �޸������û���Ϣ
		 * */
		user2.setUsername(user.getUsername());
		user2.setEmail(user.getEmail());
		user2.setUsersex(user.getUsersex());
		user2.setPhone(user.getPhone());
		
		userService.updateUser(user2);
		
		User resultUser = userService.findUserById(user2.getUserid());
		movi.addObject("user", resultUser);
		movi.setViewName("homepage");
		return movi;
	}
	
}
