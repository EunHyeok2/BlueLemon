package com.blue.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.blue.dto.AlarmVO;
import com.blue.dto.LikeVO;
import com.blue.dto.PostVO;
import com.blue.dto.TagVO;
import com.blue.service.AlarmService;

@Repository("PostDAO")
public class PostDAO {
 
	@Autowired
	private SqlSessionTemplate mybatis;
	
	@Autowired
	private AlarmService alarmService;
	
	// index������ ��û�� ����Ǵ� �κ�(�Խñ� ����Ʈ)
	public ArrayList<PostVO> listPost(String member_Id) {
	    List<PostVO> resultList = mybatis.selectList("PostMapper.listPost", member_Id);
	    ArrayList<PostVO> arrayList = new ArrayList<PostVO>(resultList);
	    
	    return arrayList;
	}
	
	// �Խñ� ���ƿ� ���� üũ
	public String getLikeYN(PostVO voForLikeYN) {
		String check = mybatis.selectOne("PostMapper.checkLike", voForLikeYN);;
		
		if (check == null) {
			check = "N";
		} else {
			check = "Y";
		}
		return check;
	}
	
	// �Խñ� ���ƿ� ó��
	public void changeLike(LikeVO vo) {
		String check = mybatis.selectOne("PostMapper.checkLike", vo);
		
		// �˶�
		String post_Writer = mybatis.selectOne("PostMapper.postWriter", vo.getPost_Seq());
		AlarmVO alarmVO = new AlarmVO();
		alarmVO.setKind(2);
		alarmVO.setFrom_Mem(vo.getMember_Id());
		alarmVO.setTo_Mem(post_Writer);
		alarmVO.setPost_Seq(vo.getPost_Seq());
		alarmVO.setReply_Seq(0);
		
		// �˶� ���̺� �ش� �˶� �ֳ� Ȯ��
        int alarmResult = alarmService.getOneAlarm_Seq(alarmVO);	
        
        // 1. �ش� �Խñۿ� ���ƿ並 ���� ���°� �ƴҶ�
		if(check == null) {
			// ���ƿ� �߰�
			mybatis.update("PostMapper.addLike", vo);
			
			// �˶� ���̺��� �ش� �Խñ��� �˶��� ������  �˶� �߰�
			if(alarmResult == 0) {
				alarmService.insertAlarm(alarmVO);
				
			// �˶� ���̺��� �ش� �Խñ��� �˸��� ������ �ƹ��͵�x
			} else {}
			
		// 2. �ش� �Խñۿ� ���ƿ並 ���� �����϶�
		} else {
			// ���ƿ� ���
			mybatis.update("PostMapper.delLike", vo);
			
			// �Խñ� �ۼ��ڰ� �˶��� Ȯ���ϱ����� �Խñ��� ���ƿ並 ����������� ��쿡 �˸��� ���� ó���ϴ� �κ�
			if(alarmResult == 0) {
				
			// �Խñ� �ۼ��ڰ� �˶��� Ȯ������ �ʾҴٸ� �ش� �˶��� ���� ó���ϴ� �κ�
			} else {
				alarmService.deleteAlarm(alarmResult);
			}
		}
	}
	
	public List<PostVO> getHottestFeed() {
		return mybatis.selectList("PostMapper.getHottestFeed");
	}
	
	public void insertPost(PostVO vo) {		
		int result = mybatis.insert("PostMapper.insertPost", vo);
	}
	
	// �Խñ� ���ƿ� ī��Ʈ
	public int postLikeCount(int post_Seq) {		
		return mybatis.selectOne("LikeMapper.postLikeCount", post_Seq);
	}
	
	// �Խñ� ��� ī��Ʈ
	public int postReplyCount(int post_Seq) {		
		return mybatis.selectOne("ReplyMapper.postReplyCount", post_Seq);
	}
	
	// �Խñ� �󼼺���(���â)
	public PostVO postDetail(int post_Seq) {		
		return mybatis.selectOne("PostMapper.postDetail", post_Seq);
	}

	// �� profile���� ������ �Խñ۸��
	public ArrayList<PostVO> getMyPost(String member_Id) {
		List<PostVO> result =  mybatis.selectList("PostMapper.myPost", member_Id);
		ArrayList<PostVO> myPostList = new ArrayList<PostVO>(result);
		return myPostList;
	}
	
	// ���� �������� �Խñ� ���
	public ArrayList<PostVO> getMemberPost(PostVO vo) {
		List<PostVO> result =  mybatis.selectList("PostMapper.memberPost", vo);
		ArrayList<PostVO> memberPostList = new ArrayList<PostVO>(result);
		return memberPostList;
	}

	public ArrayList<PostVO> getAllPost() {
		List<PostVO> result = mybatis.selectList("PostMapper.getAllPost");
		ArrayList<PostVO> getAllPost = new ArrayList<PostVO>(result);
		return getAllPost;
	}
	
	//�Խñ� ����
	public void deletePost(int post_Seq) {
		mybatis.delete("PostMapper.deletePost", post_Seq);
	}
	
	// �Խñ� �߰� �� �ʿ��� ������� ������ ��ȸ
	public int postSeqCheck() {
		int result = mybatis.selectOne("PostMapper.postSeqCheck");
		System.out.println(result);
		return result;
	}
	
	// �Խñ� �߰��� �ؽ��±� �μ�Ʈ
	public void insertTag(TagVO vo) {
		// ��Ʈ�ѷ������Ѿ�� ��
		mybatis.insert("PostMapper.insertTag", vo);
	}
	
	// �Խñ� �ؽ��±� ��ȸ
	public ArrayList<TagVO> getHashtagList(int post_Seq) {
		List<TagVO> result = mybatis.selectList("PostMapper.postHashtag", post_Seq);
		ArrayList<TagVO> hashList = new ArrayList<TagVO>(result);
		return hashList;
	}
	
	// �� ȸ���� ��ü �ؽ��±� ����
	public void deleteOneMemsTag(String member_Id) {
		mybatis.delete("PostMapper.deleteOneMemsTag", member_Id);
	}
	
	// ������ �Խñ� ����ȸ
	public PostVO selectPostDetail(int post_Seq) {
		return mybatis.selectOne("PostMapper.selectPostDetail", post_Seq); 
	}
	
	// �ؽ��±׷� �Խñ� �˻�
	public ArrayList<PostVO> getHashTagPost(String hashTag){
		List<PostVO> result = mybatis.selectList("PostMapper.getHashTagPost", hashTag);
		ArrayList<PostVO> memberPostList = new ArrayList<PostVO>(result);
		
		return memberPostList;
	}

	public ArrayList<TagVO> getTodaysTag() {
		List<TagVO> result = mybatis.selectList("PostMapper.getTodaysTag");
		ArrayList<TagVO> todaysTag = new ArrayList<TagVO>(result);
		
		return todaysTag;
	}
	
	// �Խñ� ���� ó��
	public void updatePost(PostVO vo) {
		mybatis.update("PostMapper.updatePost", vo);
	}
	
	// �Խñ� �ؽ��±� ������ ���� ó��
	public void deleteTag(int post_Seq) {
		mybatis.delete("PostMapper.deleteTag", post_Seq);
	}

	public String getPostWriter(int post_Seq) {
		return mybatis.selectOne("PostMapper.postWriter", post_Seq);
	}

	// �Խñ� �̹��� ������ ���� ����ڰ� �ۼ��� �Խñ��� ������ ��ȣ
	public List<Integer> seqForUser(String member_Id){
		return mybatis.selectList("PostMapper.seqForUser", member_Id);
	}
	
	
}
