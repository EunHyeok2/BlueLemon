package com.blue.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.blue.dto.LikeVO;
import com.blue.dto.ReplyVO;

@Repository("ReplyDAO")
public class ReplyDAO {

	@Autowired
	private SqlSessionTemplate mybatis;
	
	// �� �Խñۿ� ���� ��� 3�������� ���
	public ArrayList<ReplyVO> replyPreview(int post_Seq) {
		Date currentTime = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		//System.out.println(currentTime);
	    List<ReplyVO> resultList = mybatis.selectList("ReplyMapper.replyPreview", post_Seq);
	    for(ReplyVO vo : resultList) {
	    	String wroteTimeString = dateFormat.format(vo.getReply_Date());
	    	try {
				Date wroteTime = dateFormat.parse(wroteTimeString);				
				long timeDiff = currentTime.getTime() - wroteTime.getTime();
				long minutesDiff = timeDiff / (60 * 1000);
				long hoursDiff = minutesDiff / 60;
				long daysDiff = hoursDiff / 24;
				if (minutesDiff <= 60) {
					vo.setReply_WhenDid(minutesDiff + " minutes ago");
				} else if (minutesDiff > 60 & minutesDiff <= 1440){
					vo.setReply_WhenDid(hoursDiff + " hours ago");
				} else {
					vo.setReply_WhenDid(daysDiff + " days ago");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    }
	    ArrayList<ReplyVO> arrayList = new ArrayList<ReplyVO>(resultList);
	    
	    return arrayList;
	}	
	
	// �Խñ� �󼼺��� ���â�� ��� ����Ʈ
	public ArrayList<ReplyVO> listReply(int post_Seq) {
		Date currentTime = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		List<ReplyVO> resultList = mybatis.selectList("ReplyMapper.listReply", post_Seq);
		
		for(ReplyVO vo : resultList) {
	    	String wroteTimeString = dateFormat.format(vo.getReply_Date());
	    	try {
				Date wroteTime = dateFormat.parse(wroteTimeString);
				
				long timeDiff = currentTime.getTime() - wroteTime.getTime();
				
				long minutesDiff = timeDiff / (60 * 1000);
				
				long hoursDiff = minutesDiff / 60;
				
				long daysDiff = hoursDiff / 24;
				
				if (minutesDiff <= 60) {
					vo.setReply_WhenDid(minutesDiff + " minutes ago");
				} else if (minutesDiff > 60 & minutesDiff <= 1440){
					vo.setReply_WhenDid(hoursDiff + " hours ago");
				} else {
					vo.setReply_WhenDid(daysDiff + " days ago");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    }
		
	    ArrayList<ReplyVO> arrayList = new ArrayList<ReplyVO>(resultList);
	    
	    return arrayList;
		
	}

	public String checkReplyLike(ReplyVO voForReplyCheck) {
		String check = mybatis.selectOne("ReplyMapper.checkReplyLike", voForReplyCheck);
		//System.out.println("[�̸����� ��� - 3] DAO - ���ƿ� ���̸� id �� : " + check);
		if (check == null) {
			check = "N";
		} else {
			check = "Y";
		}
		//System.out.println("[�̸����� ��� - 4] DAO���� ���� : " + check);
		return check;
	}

	public void changeReplyLike(LikeVO vo) {
		//System.out.println("[�̸����� ��� ���ƿ� - 6] DAO�� �� member_Id = " + vo.getMember_Id() + ", post_Seq = " + vo.getPost_Seq() + ", reply_Seq = " + vo.getReply_Seq());
		String check = mybatis.selectOne("ReplyMapper.checkReplyLike", vo);
		//System.out.println("[�̸����� ��� ���ƿ� - 7] ��� ���ƿ� ���� üũ�� (���ƿ� ���̸� id ��) : " + check);
		if(check == null) {
			mybatis.update("ReplyMapper.addReplyLike", vo);
			//System.out.println("[�̸����� ��� ���ƿ� - 8 - if] ���ƿ� �� �ƴ϶� ���ƿ� ��");
		} else {
			mybatis.update("ReplyMapper.delReplyLike", vo);
			//System.out.println("[�̸����� ��� ���ƿ� - 8 - else] ���ƿ� ���̶� ���ƿ� ��� ��");
		}
	}
	
	// ��� �μ�Ʈ
	public void insertReply(ReplyVO vo) {
		mybatis.insert("ReplyMapper.insertReply", vo);
	}
}