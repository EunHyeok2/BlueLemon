package com.blue.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blue.dao.MemberDAO;
import com.blue.dto.FollowVO;
import com.blue.dto.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO memberDao;
	
	@Override
	public MemberVO getMember(String member_id) {		
		//System.out.println("[�α��� - 7 - if - 2] getMember()�� ���� MemberService�� ���� MemberServiceImpl�� ��");
		return memberDao.getMember(member_id);
	}

	@Override
	public MemberVO getMemberInfo(String member_Id) {
		return memberDao.getMemberInfo(member_Id);
	}

	@Override
	public int confirmID(String member_id) {		
		return memberDao.confirmID(member_id);
	}

	@Override
	public void insertMember(MemberVO vo) {		
		memberDao.insertMember(vo);		
	}

	@Override
	public int doLogin(MemberVO vo) {
		//System.out.println("[�α��� - 3] doLogin()�� ���� MemberService�� ���� MemberServiceImpl�� ��");
		return memberDao.doLogin(vo);
	}

	@Override
	public List<MemberVO> getRecommendMember(String member_Id) {
		//System.out.println("[�����õ - 3] getRecommendMember()�� ���� MemberService�� ���� MemberServiceImpl�� ��");
		return memberDao.getRecommendMember(member_Id);
	}

	@Override
	public void changeFollow(FollowVO vo) {
		System.out.println("[�ȷο�, ���ȷο� - 8] changeFollow()�� ���� MemberService�� ���� MemberServiceImpl�� ��");
		//System.out.println("Impl : �ȷο� : " + vo.getFollower() + ", �ȷ��� : " + vo.getFollowing());
		memberDao.changeFollow(vo);
	}
	@Override
	public void updateMember(MemberVO vo) {
		memberDao.updateMember(vo);
		
	}
	
	@Override
	public boolean checkDuplicate(String member_Id) {
		int result = memberDao.confirmID(member_Id);
		return result == 1;
	}
	
	
	@Override
	public HashMap<String, String> getMemberProfile() {
		
		return memberDao.memberProfile();
	}

	@Override
	public List<MemberVO> getMostFamousMember() {
		return memberDao.getMostFamousMember();
	}

	 //ȸ�� Ż��
	@Override
	public void deleteMember(String member_Id) {
		memberDao.deleteMember(member_Id);
	    
	}

	@Override
	public List<MemberVO> getFollowers(String member_Id) {
		return memberDao.getFollowers(member_Id);
	}

	@Override
	public List<MemberVO> getFollowings(String member_Id) {
		return memberDao.getFollowings(member_Id);
	}

	// ������ - ��ü ȸ�� ��ȸ
	@Override
	public List<MemberVO> getAllMember() {
		return memberDao.getAllMember();
	}

	@Override
	public String searchId(MemberVO vo) {
		return memberDao.searchId(vo);
	}

	@Override
	public MemberVO findPassword(MemberVO vo) {
		return memberDao.findPassword(vo);
	}

	@Override
	public void updatePassword(MemberVO vo) {
		memberDao.updatePassword(vo);
		
	}
	
	@Override
	public void updateMember2(MemberVO vo) {
		memberDao.updateMember2(vo);
		
	}

	@Override
	public boolean checkPassword(String member_Id, String member_Password) {
		return memberDao.checkPassword(member_Id, member_Password);
	}

	@Override
	public String selectPwdByIdNameEmail(MemberVO vo) {
		return memberDao.PwdByIdNameEmail(vo);
	}

	@Override
	public List<MemberVO> searchMembers(String keyword) {
		 return	memberDao.searchMembers(keyword);
	}

	@Override
	public String checkFollow(FollowVO check_Vo) {
		return memberDao.checkFollow(check_Vo);
	}

	// ������ ������ ȸ���� ��Ȳ �׷���
	@Override
	public List<Integer> getMemberTendency() {
		return memberDao.getMemberTendency();
	}

	
}
