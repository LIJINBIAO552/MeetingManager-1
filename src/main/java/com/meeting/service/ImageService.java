package com.meeting.service;

import java.util.Map;
import java.io.File;
import org.springframework.web.multipart.MultipartFile;

/*
 * ��Ƭ���ϴ�������
 * */
public interface ImageService {
	
	//ͼƬ�ϴ�
	public Map upload(MultipartFile file);
	//ͼƬ����
	public File download(String filename);
	
	//������Ƭ�ϴ�
	public Map meetingImage(MultipartFile file,long mnum);
}
