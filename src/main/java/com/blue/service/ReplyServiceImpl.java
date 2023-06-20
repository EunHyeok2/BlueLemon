package com.blue.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blue.dao.ReplyDAO;
import com.blue.dto.LikeVO;
import com.blue.dto.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyDAO replyDao;
	
	// �� �Խñ� ��� 3������ ��ȸ
	@Override
	public ArrayList<ReplyVO> getReplyPreview(int post_Seq) {
		
		return replyDao.replyPreview(post_Seq);
	}

	// �Խñ� �󼼺��� ���â�� ��� ����Ʈ
	@Override
	public ArrayList<ReplyVO> listReply(int post_Seq) {
		
		return replyDao.listReply(post_Seq);
	}

	// ��� ���ƿ� ���� Ȯ��
	@Override
	public String checkReplyLike(ReplyVO voForReplyCheck) {
		
		return replyDao.checkReplyLike(voForReplyCheck);
	}

	// ��� ���ƿ� ó��
	@Override
	public void changeReplyLike(LikeVO vo) {
		replyDao.changeReplyLike(vo);
		
	}

	@Override
	public void insertReply(ReplyVO vo) {
		replyDao.insertReply(vo);
	}
	
	

}
