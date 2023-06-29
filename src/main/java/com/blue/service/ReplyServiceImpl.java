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
	public ArrayList<ReplyVO> getListReply(int post_Seq) {		
		return replyDao.listReply(post_Seq);
	}

	// ��� ���ƿ� ���� Ȯ��
	@Override
	public String getCheckReplyLike(ReplyVO voForReplyCheck) {		
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

	@Override
	public int getMaxReply_Seq() {
		return replyDao.getMaxReply_Seq();
	}

	@Override
	public void deleteReply(ReplyVO vo) {
		replyDao.deleteReply(vo);
	}

	@Override
	public void deleteReplyLike(ReplyVO vo) {
		replyDao.deleteReplyLike(vo);
	}	

}
