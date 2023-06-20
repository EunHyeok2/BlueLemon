package com.blue.service;

import java.util.ArrayList;

import com.blue.dto.LikeVO;
import com.blue.dto.ReplyVO;

public interface ReplyService {

	// �� �Խñ� ��� 3������ ��ȸ
	ArrayList<ReplyVO> getReplyPreview(int post_Seq);
	
	// �Խñ� �󼼺��� ���â�� ��� ����Ʈ
	ArrayList<ReplyVO> listReply(int post_Seq);
	
	// ��� ���ƿ� ���� ��ȸ
	String checkReplyLike(ReplyVO voForReplyCheck);
	
	// ��� ���ƿ� ó��
	void changeReplyLike(LikeVO vo);
	
	// ��� �μ�Ʈ
	void insertReply(ReplyVO vo);
}
