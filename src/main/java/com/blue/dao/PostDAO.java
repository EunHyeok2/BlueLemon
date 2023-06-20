package com.blue.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.blue.dto.LikeVO;
import com.blue.dto.PostVO;

@Repository("PostDAO")
public class PostDAO {

	@Autowired
	private SqlSessionTemplate mybatis;
	
	// index������ ��û�� ����Ǵ� �κ�(�Խñ� ����Ʈ)
	public ArrayList<PostVO> listPost(String member_Id) {
	    List<PostVO> resultList = mybatis.selectList("PostMapper.listPost", member_Id);
	    ArrayList<PostVO> arrayList = new ArrayList<PostVO>(resultList);
	    
	    return arrayList;
	}
	
	// �Խñ� ���ƿ� ���� üũ
	public String getLikeYN(PostVO voForLikeYN) {
		String check = mybatis.selectOne("PostMapper.checkLike", voForLikeYN);;
		//System.out.println("[���ƿ� ���� Ȯ�� - 2]DAO - getLikeYN : " + check);
		if (check == null) {
			check = "N";
		} else {
			check = "Y";
		}
		//System.out.println("[���ƿ� ���� Ȯ�� - 3] DAO���� ���� : " + check);
		return check;
	}
	
	// �Խñ� ���ƿ� ó��
	public void changeLike(LikeVO vo) {
		//System.out.println("[�Խñ� ���ƿ� - 6] changeLike()�� ���� DAO�� �� member_Id = " + vo.getMember_Id() + "post_Seq = " + vo.getPost_Seq());
		String check = mybatis.selectOne("PostMapper.checkLike", vo);
		//System.out.println("[�Խñ� ���ƿ� - 7] post-mapping.xml���� checkLike�� ���ƿ� üũ");
		if(check == null) {
			mybatis.update("PostMapper.addLike", vo);
			//System.out.println("[�Խñ� ���ƿ� - 8 - if] ���ƿ� �� �ƴ϶� ���ƿ� ��");
		} else {
			mybatis.update("PostMapper.delLike", vo);
			//System.out.println("[�Խñ� ���ƿ� - 8 - else] ���ƿ� ���̶� ���ƿ� ��� ��");
		}
	}
	public List<PostVO> getHottestFeed() {
		//System.out.println("[�α�� - 4] getHottestFeed()�� ���� postDAO�� ���� post-mapping.xml�� ���� �޾ƿ�");
		return mybatis.selectList("PostMapper.getHottestFeed");
	}
	
	public void insertPost(PostVO vo) {		
		int result = mybatis.insert("PostMapper.insertPost", vo);
		//System.out.println("�Խñ� �ۼ� ��� " + result);
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

	// ���� �������� �Խñ� ���
	public ArrayList<PostVO> getMemberPost(String member_Id) {
		List<PostVO> result =  mybatis.selectList("PostMapper.memberPost", member_Id);
		ArrayList<PostVO> memberPostList = new ArrayList<PostVO>(result);
		return memberPostList;
	}
	
}