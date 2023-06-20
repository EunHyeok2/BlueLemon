package com.blue.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.SessionAttributes;

import com.blue.dto.FollowVO;
import com.blue.dto.MemberVO;

@SessionAttributes("loginUser")
public interface MemberService {

	// ȸ�� ID�� �������� ȸ�� ��ȸ
	MemberVO getMember(String member_Id);
	
	// ȸ�� ID�� �������� ȸ�� ��ȸ(������)
	MemberVO getMemberInfo(String member_Id);

	// ȸ�� ���� ���� ��ȸ
	int confirmID(String member_Id);
	
	// ȸ�� Insert
	void insertMember(MemberVO vo);

	// �α��� ó��
	int doLogin(MemberVO vo);
	
	//ȸ������ ����
	void updateMember(MemberVO vo);
	
	// ��й�ȣ ����
	void changePwd(MemberVO vo);
	
	// ���̵� �ߺ�üũ
	boolean checkDuplicate(String member_Id);

	// ȸ�� Ż��
	void deleteMember(MemberVO vo);
	
	// ���� �ȷο� �ߴµ� ���� �ȷο� ���� ��� ��õ
	List<MemberVO> getRecommendMember(String member_Id);
	
	// �ȷο� / ���ȷο� ó��
	void changeFollow(FollowVO vo);
	
	// ��ü ȸ���� ������ �̹��� ��ȸ
	HashMap<String, String> getMemberProfile();
	
	// �ȷο��� ���� ���� ��� ��ȸ
	List<MemberVO> getMostFamousMember();
	
	// �ȷο��� �̹���
	List<MemberVO> getFollowers(String member_Id);
	
	// �ȷ����� �̹���
	List<MemberVO> getFollowings(String member_Id);
}