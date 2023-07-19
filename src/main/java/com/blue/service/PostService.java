package com.blue.service;

import java.util.ArrayList;
import java.util.List;
 
import com.blue.dto.LikeVO;
import com.blue.dto.PostVO;
import com.blue.dto.TagVO;


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
	
	// �������� �� ����Ʈ��
	ArrayList<PostVO> getMyPost(String member_Id);
	
	// �������� ���� ����Ʈ��
	ArrayList<PostVO> getMemberPost(PostVO vo);
	
	// �Խñ� ���ƿ� ��ȸ
	int getPost_Like_Count(int post_Seq);
	
	// ������ - ��ü �Խñ� ��ȸ
	ArrayList<PostVO> getAllPost();

	// �Խñ� ����
	void deletePost(int post_Seq);
	
	// �Խñ� �߰� �� �ʿ��� ������� ������ ��ȸ
	int postSeqCheck();
	
	// �Խñ� �߰��� �ؽ��±� �μ�Ʈ
	void insertTag(TagVO vo);
	
	// �Խñ� �ؽ��±� ��ȸ
	ArrayList<TagVO> getHashtagList(int post_Seq);
	
	// �� ȸ���� ��ü �ؽ��±� ����
	void deleteOneMemsTag(String member_Id);
	
	// ������ �Խñ� ������
	PostVO selectPostDetail(int post_Seq);
	
	// �ؽ� �±� �˻� ���
	ArrayList<PostVO> getHashTagPost(String hashTag);
	
	// ������ ������ ������ �ؽ��±�
	ArrayList<TagVO> getTodaysTag();
	
	// �Խñ� ���� ó��
	void updatePost(PostVO vo);
		
	// �Խñ� �ؽ��±� ������ ���� ó��
	void deleteTag(int post_Seq);
	
	// �˶��� ���� �Խñ� �ۼ��� ����
	String getPostWriter(int post_Seq);
	
	// �Խñ� �̹��� ������ ���� ����ڰ� �ۼ��� �Խñ��� ������ ��ȣ
	List<Integer> seqForUser(String member_Id);
}
