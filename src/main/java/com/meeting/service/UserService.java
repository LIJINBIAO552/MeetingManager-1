package com.meeting.service;



import com.meeting.pojo.User;


public interface UserService {
	
	//����һ���û���¼
	public void InsertUser(User user);
	
	//����id��ѯ�û���¼
	public User findUserById(String userid);
	
	//����phone��ѯ�û���¼
	public User findUserByPhone(String phone);


}
