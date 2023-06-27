package com.blue.service;

import java.util.ArrayList;
import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blue.dao.PostDAO;
import com.blue.dto.LikeVO;
import com.blue.dto.PostVO;
import com.blue.dto.TagVO;

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

	@Override
	public int postSeqCheck() {
		// �Խñ� �μ�Ʈ�� ������ NEXTVAL �� ���� ����ó��
		int Seq = postDao.postSeqCheck() + 1;
		return Seq;
	}

	@Override
	public void insertTag(TagVO vo) {
		postDao.insertTag(vo);
	}

	@Override
	public ArrayList<TagVO> getHashtagList(int post_Seq) {
		return postDao.getHashtagList(post_Seq);
	}

	@Override
	public void deleteOneMemsTag(String member_Id) {
		postDao.deleteOneMemsTag(member_Id);
	}

	@Override
	public PostVO selectPostDetail(int post_Seq) {
		
		return postDao.selectPostDetail(post_Seq);
	}

	@Override
	public ArrayList<PostVO> getHashTagPost(String hashTag) {
		return postDao.getHashTagPost(hashTag);
	}

	@Override
	public ArrayList<TagVO> getTodaysTag() {
		return postDao.getTodaysTag();
	}

	@Override
	public void updatePost(PostVO vo) {
		postDao.updatePost(vo);
	}

	@Override
	public void deleteTag(int post_Seq) {
		postDao.deleteTag(post_Seq);
	}
}
