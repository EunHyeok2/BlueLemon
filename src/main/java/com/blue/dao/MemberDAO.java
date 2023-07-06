package com.blue.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.blue.dto.AlarmVO;
import com.blue.dto.FollowVO;
import com.blue.dto.MemberVO;
import com.blue.service.AlarmService;

@Repository("MemberDao")
public class MemberDAO {

	@Autowired
	private SqlSessionTemplate mybatis;
	@Autowired
	private AlarmService alarmService;

	// ȸ�� ID�� �������� ȸ�� ��ȸ
	public MemberVO getMember(String member_Id) {
		return mybatis.selectOne("MemberMapper.getMemberInfo", member_Id);
	}

	// ȸ�� ID�� �������� ȸ�� ��ȸ
	public MemberVO getMemberInfo(String member_Id) {
		return mybatis.selectOne("MemberMapper.getMember", member_Id);
	}

	// ȸ�� ���� ���� ��ȸ
	public int confirmID(String member_Id) {
		String member_Password = mybatis.selectOne("MemberMapper.confirmID", member_Id);

		if (member_Password != null) {
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
		int result = -1;
		String pwd = mybatis.selectOne("MemberMapper.confirmID", vo.getMember_Id());
		
		// ID�� ���� ���
		if (pwd == null) {
			result = -1;
			// ���� �α���
		} else if (pwd.equals(vo.getMember_Password())) {
			result = 1;
			// ��й�ȣ Ʋ��
		} else {
			result = 0;
		}
		return result;
	}

	// ȸ������ ����
	// ������ �̹��� ���� ���
	public void updateMember(MemberVO vo) {
		mybatis.selectOne("MemberMapper.memberUpdate", vo);
	}
	
	// ȸ������ ����2
	// ������ �̹��� ���� ��� X
   	public void updateMember2(MemberVO vo) {
      mybatis.selectOne("MemberMapper.memberUpdate2", vo);
    }
	
	// �н����� Ȯ��
	public boolean checkPassword(String member_Id, String member_Password) {
		MemberVO vo = getMember(member_Id);
		return vo != null && vo.getMember_Password().equals(member_Password);
	}

	// ȸ�� Ż��
	public void deleteMember(String member_Id) {
		mybatis.delete("MemberMapper.memberDelete", member_Id);		
	}

	public MemberVO loginUser(MemberVO vo) {
		return mybatis.selectOne("MemberMapper.loginUser", vo);
	}

	// ���̵� ã��
	public String searchId(MemberVO vo) {
		return mybatis.selectOne("MemberMapper.searchId", vo);
	}

	// �н����� ã��
	public MemberVO findPassword(MemberVO vo) {
		return mybatis.selectOne("MemberMapper.findPassword", vo);
	}

	// �н����� ����
	public void updatePassword(MemberVO vo) {
		mybatis.update("MemberMapper.updatePassword", vo);
	}

	// ���̵� �̸��� Ȯ���� �н����� ã��
	public String PwdByIdNameEmail(MemberVO vo) {
		return mybatis.selectOne("MemberMapper.PwdByIdNameEmail", vo);
	}

	// ���̵�� ȸ�� �˻�
	public List<MemberVO> searchMembers(String keyword) {
		return mybatis.selectList("MemberMapper.searchMembers", keyword);
	}

	// ��õ ȸ�� ��ȸ
	public List<MemberVO> getRecommendMember(String member_Id) {
		return mybatis.selectList("MemberMapper.getRecommendMember", member_Id);
	}

	// �ȷο� / ���ȷο� ó��
	public void changeFollow(FollowVO vo) {
		String check = mybatis.selectOne("MemberMapper.checkFollow", vo);
		
		// �˶�
        AlarmVO alarmVO = new AlarmVO();
        alarmVO.setKind(1);
        alarmVO.setFrom_Mem(vo.getFollower());
        alarmVO.setTo_Mem(vo.getFollowing());
        alarmVO.setPost_Seq(0);
        alarmVO.setReply_Seq(0);
        
        // �˶� ���̺� �ش� �˶� �ֳ� Ȯ��
        int result = alarmService.getOneAlarm_Seq(alarmVO);	
        
		// �ȷο� ���� �ƴ� ���
		if (check == null) {
			mybatis.update("MemberMapper.addFollow", vo);
			
			// �˶� ���̺� �ش� �˶� ������ insert
	        if(result == 0) {
		        alarmService.insertAlarm(alarmVO);
	        } else {
	        }
		// �ȷο� ���� ���
		} else {
			mybatis.update("MemberMapper.delFollow", vo);
			
			// �˶� ���̺� �ش� �˶� ������ delete
	        if(result == 0) {
	        } else {
	        	alarmService.deleteAlarm(result);
	        }
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
		List<MemberVO> mostFamous = mybatis.selectList("MemberMapper.MostFamous");
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

	// ������ - ��ü ȸ�� ��ȸ
	public List<MemberVO> getAllMember() {
		return mybatis.selectList("MemberMapper.getAllMember");
	}
	
	// ���� ��븦 �ȷο��߳� üũ
	public String checkFollow(FollowVO check_Vo) {
		String result = null;
		if(mybatis.selectOne("MemberMapper.checkFollow", check_Vo) != null) {
			result = "y";
		} else {
			result = "n";
		}
		return result;
	}

	// ������ ������ ȸ���� ��Ȳ �׷���
	public List<Integer> getMemberTendency() {
		return mybatis.selectList("MemberMapper.getMemberTendency");
	}
}
