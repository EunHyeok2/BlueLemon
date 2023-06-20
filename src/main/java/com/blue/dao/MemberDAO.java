package com.blue.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.blue.dto.FollowVO;
import com.blue.dto.MemberVO;

@Repository("MemberDao")
public class MemberDAO {

	@Autowired
	private SqlSessionTemplate mybatis;
	
	// ȸ�� ID�� �������� ȸ�� ��ȸ
	public MemberVO getMember(String member_Id) {
		//System.out.println("[�α��� - 7 - if - 3] getMember()�� ���� MemberServiceImpl���� DAO�� ��");
		//System.out.println("[�α��� - 7 - if - 4] getMember()�� ���� member-mapping�� getMember�� ���� ȸ�������� �޾� ��");
		return mybatis.selectOne("MemberMapper.getMember", member_Id);		
	}
	
	// ȸ�� ID�� �������� ȸ�� ��ȸ
	public MemberVO getMemberInfo(String member_Id) {
		//System.out.println("[�α��� - 7 - if - 3] getMember()�� ���� MemberServiceImpl���� DAO�� ��");
		return mybatis.selectOne("MemberMapper.getMember", member_Id);		
		//System.out.println("[�α��� - 7 - if - 4] getMember()�� ���� member-mapping�� getMember�� ���� ȸ�������� �޾� ��");
	}
	
	// ȸ�� ���� ���� ��ȸ
	public int confirmID(String member_Id) {
		String member_Password = mybatis.selectOne("MemberMapper.confirmID", member_Id);
		
		if(member_Password != null) {
			return 1;
		} else {
			return -1;
		}
	}
	
	// ȸ�� insert
	public void insertMember(MemberVO vo) {
		mybatis.update("MemberMapper.insertMember", vo);
	}
	
	// �α��� ó��
	// confirmID�� ��й�ȣ ��ȸ�ؼ� �Է��� ���� ��
	// ���ϰ� : ID�� �����ϰ� ��й�ȣ�� ������ 1 / ��й�ȣ�� �ٸ��� 0 / ID�� ������ -1
	public int doLogin(MemberVO vo) {
		//System.out.println("[�α��� - 4] doLogin()�� ���� MemberServiceImpl���� DAO�� ��");
		int result = -1;
		String pwd = mybatis.selectOne("MemberMapper.confirmID", vo.getMember_Id());
		//System.out.println("[�α��� - 5] doLogin()�� ���� member-mapping�� confirmID�� ���� PW �޾ƿ�");
		// ID�� ���� ���
		if(pwd == null) {
			result = -1;
		// ���� �α���
		} else if(pwd.equals(vo.getMember_Password())) {
			result = 1;
		// ��й�ȣ Ʋ��
		} else {
			result = 0;
		}
		//System.out.println("[�α��� - 6] ID ����, ��� Ȯ�� �� result�� ���� ��Ƽ� MainController�� ����");
		return result;
	}
	
	// ȸ������ ����
	public void updateMember(MemberVO vo) {	
		mybatis.selectOne("MemberMapper.memberUpdate", vo);		
	}	
		
	//��й�ȣ ����
	public void changePwd(MemberVO vo) {			
		mybatis.selectOne("MemberMapper.changePwd", vo);
	}
	
	// ȸ�� Ż��
	public void deleteMember(MemberVO vo) {
		mybatis.update("MemberMapper.memberDelete", vo.getMember_Id());		
	}
	
	// ��õ ȸ�� ��ȸ
	public List<MemberVO> getRecommendMember(String member_Id) {
		//System.out.println("[�����õ - 4] getRecommendMember()�� ���� MemberServiceImpl���� DAO�� ���� member-mapping.xml�� ���� ó��");
		return mybatis.selectList("MemberMapper.getRecommendMember", member_Id);
	}
	
	// �ȷο� / ���ȷο� ó��
	public void changeFollow(FollowVO vo) {
		//System.out.println("[�ȷο�, ���ȷο� - 9] changeFollow()�� ���� DAO�� ��, follower = " + vo.getFollower() + ", following = " + vo.getFollowing());
		String check = mybatis.selectOne("MemberMapper.checkFollow", vo);
		//System.out.println("[�ȷο�, ���ȷο� - 10] member-mapping.xml���� checkFollow�� �ȷο� üũ");
		// �ȷο� ���� �ƴ� ���
		if(check == null) {
			mybatis.update("MemberMapper.addFollow", vo);
			//System.out.println("[�ȷο�, ���ȷο� - 11 - if] �ȷο� �� �ƴ϶� �ȷο� ��");
		// �ȷο� ���� ���
		} else {
			mybatis.update("MemberMapper.delFollow", vo);
			//System.out.println("[�ȷο�, ���ȷο� - 11 - else] �ȷο� ���̶� �ȷο� ��� ��");
		}		
	}
	
	// ��ü ȸ�� ������ �̹��� ��ȸ
	public HashMap<String, String> memberProfile() {
	    List<HashMap<String, String>> resultList = mybatis.selectList("MemberMapper.memberProfile");
	    HashMap<String, String> profileMap = new HashMap<>();

	    for (HashMap<String, String> row : resultList) {
	        String member_Id = row.get("member_Id");
	        String member_Profile_Image = row.get("member_Profile_Image");
	        profileMap.put(member_Id, member_Profile_Image);
	    }
	    return profileMap;
	}
	
	// �ȷο��� ���� ���� ��� ��ȸ
	public List<MemberVO> getMostFamousMember() {
		//System.out.println("[PEOPLE �� - 5] DAO�� ��");
		List<MemberVO> mostFamous = mybatis.selectList("MemberMapper.MostFamous");
		//System.out.println("[PEOPLE �� - 6] DAO���� DB�� �� mostFamous�� �޾ƿͼ� ���Ͽ�û");
		return mostFamous;
	}

	public List<MemberVO> getFollowers(String member_Id) {
		List<MemberVO> followers = mybatis.selectList("MemberMapper.getFollowers", member_Id);
		return followers;
	}

	public List<MemberVO> getFollowings(String member_Id) {
		List<MemberVO> followings = mybatis.selectList("MemberMapper.getFollowings", member_Id);
		return followings;
	}
}
