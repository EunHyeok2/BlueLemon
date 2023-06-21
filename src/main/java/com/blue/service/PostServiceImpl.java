package com.blue.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blue.dao.PostDAO;
import com.blue.dto.LikeVO;
import com.blue.dto.PostVO;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostDAO postDao;
	
	// �Խñ� ��������
	@Override
	public ArrayList<PostVO> getlistPost(String member_Id) {		
		return postDao.listPost(member_Id);
	}
	
	// �Խñ� ���ƿ� ���� üũ
	@Override
	public String getLikeYN(PostVO voForLikeYN) {
		return postDao.getLikeYN(voForLikeYN);
		
	}

	// �Խñ� ���ƿ� ó��
	@Override
	public void changeLike(LikeVO vo) {
		postDao.changeLike(vo);
	}
	
	// �α�� ��ȸ
	@Override
	public List<PostVO> getHottestFeed() {
		//System.out.println("[�α�� - 3] getHottestFeed()�� ���� postService�� ���� postServiceImpl�� ��");
		return postDao.getHottestFeed();
	}

	// �Խñ� �ۼ�
	@Override
	public void insertPost(PostVO vo) {
		postDao.insertPost(vo);
	}
	
	// �Խñ� �󼼺���
	@Override
	public PostVO getpostDetail(int post_Seq) {
		return postDao.postDetail(post_Seq);
	}

	@Override
	public ArrayList<PostVO> getMemberPost(String member_Id) {		
		return postDao.getMemberPost(member_Id);
	}

	@Override
	public int getPost_Like_Count(int post_Seq) {
		return postDao.postLikeCount(post_Seq);
	}

	// �����ڿ� ��ü �Խñ� ��ȸ
	@Override
	public ArrayList<PostVO> getAllPost() {
		return postDao.getAllPost();
	}
	
	@Override
	public void deletePost(int post_Seq) {
		postDao.deletePost(post_Seq);
	}

	
}
