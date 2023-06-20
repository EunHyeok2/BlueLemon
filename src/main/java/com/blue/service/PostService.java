package com.blue.service;

import java.util.ArrayList;
import java.util.List;

import com.blue.dto.LikeVO;
import com.blue.dto.PostVO;

public interface PostService {

	// �Խñ� ��ü ��ȸ (�����ǵ�)
	ArrayList<PostVO> getlistPost(String member_Id);
	
	// �Խñ� ���ƿ� ���� üũ
	String getLikeYN(PostVO voForLikeYN);
	
	// �Խñ� ���ƿ� ó��
	void changeLike(LikeVO vo);
	
	// �α�� ��ȸ (����)
	List<PostVO> getHottestFeed();
	
	// �Խñ� �ۼ� (���â)
	void insertPost(PostVO vo);
	
	// �Խñ� �󼼺���(���â)
	PostVO getpostDetail(int post_Seq);
	
	// �������� ���� ����Ʈ��
	ArrayList<PostVO> getMemberPost(String member_Id);
	
	// �Խñ� ���ƿ� ��ȸ
	int getPost_Like_Count(int post_Seq);

}
