package com.blue.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.blue.dto.AlarmVO;
import com.blue.dto.FollowVO;
import com.blue.dto.MemberVO;
import com.blue.dto.PostVO;
import com.blue.service.AlarmService;
import com.blue.service.FollowService;
import com.blue.service.MemberService;
import com.blue.service.PostService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
//��Ʈ�ѷ����� 'loginUser', 'profileMap' �̶�� �̸����� �� ��ü�� �����Ҷ� ���ǿ� ���ÿ� �����Ѵ�.
@SessionAttributes({"loginUser", "profileMap"})	
public class FollowController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private PostService postService;
	@Autowired
	private FollowService followService;
	@Autowired
	private AlarmService alarmService;
	
	@GetMapping("/follow")
	public String getFollow(Model model, HttpSession session, @RequestParam String member_Id) {
		
		if(session.getAttribute("loginUser") == null) {
			
			//System.out.println("���ǰ� ����");			
			model.addAttribute("message", "�α����� ���ּ���");			
			
			return "login";			
		} else {
			
			String session_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
			
			String profileImage = memberService.getMemberInfo(member_Id).getMember_Profile_Image();


			
			// �˶� ����Ʈ�� ��� �κ�
	    	List<AlarmVO> alarmList = alarmService.getAllAlarm(session_Id);
	    	
	    	int alarmListSize = alarmList.size();
	    	
	    	// �˶��� ������ �ľ��ϴ� �κ�
	    	for(int j=0; j<alarmList.size(); j++) {
	    		int kind = alarmList.get(j).getKind();
	    		if(kind == 1) {
	    			alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "�Բ��� ȸ������ �ȷο� <br>�Ͽ����ϴ�.");
	    		}else if(kind == 2) {
	    			alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "�Բ��� ȸ������ �Խñۿ� <br>���ƿ並 �������ϴ�.");
	    		}else if(kind == 3) {
	    			alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "�Բ��� ȸ������ �Խñۿ� <br>����� �޾ҽ��ϴ�.");
	    		}else if(kind == 4) {
	    			alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "�Բ��� ȸ������ ��ۿ� <br>���ƿ並 �������ϴ�.");
	    		}else if(kind == 5) {
	    			alarmList.get(j).setMessage("ȸ���Բ��� �����Ͻ� ������ <br>����� �޷Ƚ��ϴ�.");
	    		}
	    	}
			
			//System.out.println("���ǰ� ����");			
			List<FollowVO> following_Id = followService.getFollowing(member_Id);			
			List<FollowVO> follower_Id = followService.getFollower(member_Id);			
			List<MemberVO> following_info = new ArrayList<MemberVO>();			
			List<MemberVO> follower_info = new ArrayList<MemberVO>();
			
			for(FollowVO id : following_Id) {
				
				MemberVO following_member = memberService.getMemberInfo(id.getFollowing());				
				if(following_member == null) {
					
					//System.out.println("�ȷ��� ��� �� ĭ");
				} else {
					
					following_info.add(following_member);
					//System.out.println("�ȷ��� ��� : " + following_member);
				}
			}
			
			for(FollowVO id : follower_Id) {
				
				MemberVO follower_member = memberService.getMemberInfo(id.getFollower());
				
				if(follower_member == null) {
					// System.out.println("�ȷο� ��� �� ĭ");
				} else {
					follower_info.add(follower_member);
					//System.out.println("�ȷο� ��� : " + follower_member);
				}
			}
			
			//System.out.println("�ȷ��� �� : " + following_info.size());
			//System.out.println("�ȷο� �� : " + follower_info.size());
			
			int followingTotalPageNum = 1;
			
			if(following_info.size() % 10 != 0 && following_info.size() > 10) {
				followingTotalPageNum = following_info.size() / 10 + 1;
			} else if(following_info.size() % 10 != 0 && following_info.size() < 10) {
				followingTotalPageNum = 1;
			} else if(following_info.size() % 10 == 0) {
				followingTotalPageNum = following_info.size() / 10;
			}
			
			int followerTotalPageNum = 1;
			
			if(follower_info.size() % 10 != 0 && follower_info.size() > 10) {
				followerTotalPageNum = follower_info.size() / 10 + 1;
			} else if(follower_info.size() % 10 != 0 && follower_info.size() < 10) {
				followerTotalPageNum = 1;
			} else if(follower_info.size() % 10 == 0) {
				followerTotalPageNum = follower_info.size() / 10;
			}
			
			//System.out.println("�ȷ��� ������ �� : " + followingTotalPageNum);
			//System.out.println("�ȷο� ������ �� : " + followerTotalPageNum);			
			
			int followingLoadRow = 10;
			
			if(following_info.size() <= 10) {
				followingLoadRow = following_info.size();
			}
			
			int followerLoadRow = 10;
			
			if(follower_info.size() <= 10) {
				followerLoadRow = follower_info.size();
			}
			
			for(int i = 0; i < followerLoadRow; i++) {
				//System.out.println("�ȷο� ���̵�");
				//System.out.println(follower_info.get(i).getMember_Id());
				for(int j = 0; j < followingLoadRow; j++) {
					//System.out.println("�ȷ��� ���̵�");
					//System.out.println(following_info.get(j).getMember_Id());
					if(follower_info.get(i).getMember_Id().equals(following_info.get(j).getMember_Id())) {
						follower_info.get(i).setBothFollow(1);
						//System.out.println("setBoth �Է� Ȯ�� : " + follower_info.get(i).getBothFollow());
					}
				}
			}
			
			model.addAttribute("following", following_info);
			model.addAttribute("followingLoadRow", followingLoadRow);
			model.addAttribute("followingTotalPageNum", followingTotalPageNum);
			model.addAttribute("followingPageNum", 1);
			
			model.addAttribute("follower", follower_info);
			model.addAttribute("followerLoadRow", followerLoadRow);
			model.addAttribute("followerTotalPageNum", followerTotalPageNum);
			model.addAttribute("followerPageNum", 1);
			
			// ȭ�� ���� Hottest Feed
			List<PostVO> hottestFeed = postService.getHottestFeed();
			model.addAttribute("hottestFeed", hottestFeed);	
			
			model.addAttribute("profileImage", profileImage);

			
			model.addAttribute("alarmList", alarmList);
			model.addAttribute("alarmListSize", alarmListSize);
			
			return "follow";
		
		}
	}	
	
	// �ȷο� ����
	@PostMapping("/changeFollow")
	@ResponseBody   
	public String changeFollow(@RequestBody String member_Id, HttpSession session) {
		//System.out.println("[�ȷο�, ���ȷο� - 3] PostMapping���� /changeFollow ��ƿ�, ������� ���̵� : " + member_Id);
		String follower = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
		//System.out.println("[�ȷο�, ���ȷο� - 4] ���ǿ��� �α��� ���� ���̵� �޾ƿͼ� follower�� ���� : " + follower);
		try {
			//System.out.println("[�ȷο�, ���ȷο� - 5 - try] js���� data�� ������ {member_Id: ���̵�} �̷� ���̴ϱ� �ű⼭ ������ ������ �ʿ�");
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(member_Id);
			  
			String following = jsonNode.get("member_Id").asText();
			
			//System.out.println("[�ȷο�, ���ȷο� - 6] ������ following�� ��� follower�� following�� FollowVO ��ü�� �����");
			FollowVO vo = new FollowVO();
			vo.setFollower(follower);
			vo.setFollowing(following);
		
			//System.out.println("[�ȷο�, ���ȷο� - 7] FollowVO��ü vo�� ������ changeFollow() ��û");
			memberService.changeFollow(vo);
			//System.out.println("[�ȷο�, ���ȷο� - 12] changeFollow �ϰ� js�� success ����");
			
			return "success";
		} catch (Exception e) {
			//System.out.println("[�ȷο�, ���ȷο� - 5 - catch] JSON �Ľ� ������ ���");
			// JSON �Ľ� ���� ó��
			e.printStackTrace();
			return "error";
		}
	}

	@PostMapping("/moreLoadFollowing")
	@ResponseBody
	public Map<String, Object> moreLoadFollwing(@RequestBody Map<String, Integer> requestBody, HttpSession session) {
		

		// 0. ajax��û�� ���� response�� ������ ���� Map ���� ����
		Map<String, Object> dataMap = new HashMap<>();
		
		// 1. ajax���� �޾ƿ� ��ü �޾Ƴ���
		int followingTotalPageNum = requestBody.get("followingTotalPageNum");
	    int followingPageNum = requestBody.get("followingPageNum");
		
		
	    //[�ȷο�, ���ȷο� - 3] PostMapping���� /moreLoadFollwing ��ƿ�, �� ������ �� : followerTotalPageNum
		String sessionId = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
		
		//System.out.println("if���� ���� ������ �ѹ� : "+ followingPageNum);
		//System.out.println("if���� ��ü ������ �� : " + followingTotalPageNum);
		
		int LocalPageFirstNum = followingPageNum*10+1;
		int LocalPageLastNum = followingPageNum*10+10;
		
		if(followingPageNum < followingTotalPageNum) {
			followingPageNum++;
		} else {
			//System.out.println("���� ������ �ѹ��� ��ü ������ �ѹ��� ���ų� Ŀ�� ��ž");
			dataMap.put("message", "�ҷ��� �����Ͱ� �������� �ʽ��ϴ�1");
			return dataMap;
		}
		
		//System.out.println("if���� : " + followingPageNum);
		
		FollowVO followVo = new FollowVO();
		
		followVo.setFollower(sessionId);
		followVo.setFollowingLocalPageFirstNum(LocalPageFirstNum);
		followVo.setFollowingLocalPageLastNum(LocalPageLastNum);
		
		//System.out.println("ù��° �� : " + LocalPageFirstNum);
		//System.out.println("������ �� : " + LocalPageLastNum);
		
		//System.out.println(sessionId);
		
		// �ȷο� �߰� �ε��ϱ�(��~�� �������� ��ȸ)
		List<FollowVO> following_Id  = followService.getMoreFollowing(followVo);
		
		if(following_Id == null) {
			dataMap.put("message", "�ҷ��� �����Ͱ� �������� �ʽ��ϴ�2");
			return dataMap;
		}else if(following_Id.size() == 0) {
			dataMap.put("message", "�ҷ��� �����Ͱ� �������� �ʽ��ϴ�3");
			return dataMap;
		}
		
		List<MemberVO> following_info = new ArrayList<MemberVO>();
		
		for(FollowVO id : following_Id) {
			
			MemberVO following_member = memberService.getMemberInfo(id.getFollowing());
			
			if(following_member == null) {
				//System.out.println("�ȷ��� ��� �� ĭ");
			}else {
				following_info.add(following_member);
				//System.out.println("�ȷ��� ��� : " + following_member);
			}
		}
		
		dataMap.put("following_info", following_info);
		dataMap.put("following_size", following_Id.size());
		dataMap.put("followingPageNum", followingPageNum);
		dataMap.put("followingTotalPageNum", followingTotalPageNum);
		
		return dataMap;
	}
	
	
	@PostMapping("/moreLoadFollower")
	@ResponseBody
	public Map<String, Object> moreLoadFollwer(@RequestBody Map<String, Integer> requestBody, HttpSession session) {
		
		// 0. ajax��û�� ���� response�� ������ ���� Map ���� ����
		Map<String, Object> dataMap = new HashMap<>();
		
		// 1. ajax���� �޾ƿ� ��ü �޾Ƴ���
		int followerTotalPageNum = requestBody.get("followerTotalPageNum");
	    int followerPageNum = requestBody.get("followerPageNum");
	    
	    //System.out.println("��Ż ������ �ѹ� : " + followerTotalPageNum);
	    //System.out.println("���� ������ �ѹ� : " + followerPageNum);
				
	    //[�ȷο�, ���ȷο� - 3] PostMapping���� /moreLoadFollwing ��ƿ�, �� ������ �� : followerTotalPageNum
		String sessionId = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
		
		//System.out.println("if���� ���� ������ �ѹ� : "+ followerPageNum);
		//System.out.println("if���� ��ü ������ �� : " + followerTotalPageNum);
		
		int LocalPageFirstNum = followerPageNum*10+1;
		int LocalPageLastNum = followerPageNum*10+10;
		
		if(followerPageNum < followerTotalPageNum) {
			followerPageNum++;
		} else {
			//System.out.println("���� ������ �ѹ��� ��ü ������ �ѹ��� ���ų� Ŀ�� ��ž");
			dataMap.put("message", "�ҷ��� �����Ͱ� �������� �ʽ��ϴ�1");
			return dataMap;
		}
		
		//System.out.println("if���� ���� ������ �ѹ� : " + followerPageNum);
		
		FollowVO followVo = new FollowVO();
		
		followVo.setFollowing(sessionId);
		followVo.setFollowerLocalPageFirstNum(LocalPageFirstNum);
		followVo.setFollowerLocalPageLastNum(LocalPageLastNum);
		
		//System.out.println("ù��° �� : " + LocalPageFirstNum);
		//System.out.println("������ �� : " + LocalPageLastNum);
		
		// �ȷο� �߰� �ε��ϱ�(��~�� �������� ��ȸ)
		List<FollowVO> follower_Id  = followService.getMoreFollower(followVo);
				
		if(follower_Id == null) {
			dataMap.put("message", "�ҷ��� �����Ͱ� �������� �ʽ��ϴ�2");
			return dataMap;
		} else if(follower_Id.size() == 0) {
			dataMap.put("message", "�ҷ��� �����Ͱ� �������� �ʽ��ϴ�3");
			return dataMap;
		}
		
		List<MemberVO> follower_info = new ArrayList<MemberVO>();
		
		for(FollowVO id : follower_Id) {
			
			MemberVO follower_member = memberService.getMemberInfo(id.getFollower());
			
			if(follower_member == null) {
				//System.out.println("�ȷο� ��� �� ĭ");
			} else {
				follower_info.add(follower_member);
				//System.out.println("�ȷο� ��� : " + follower_member);
			}
		}
		
		//�ε��� �ȷο����� ���� �ȷ����� ����� �ִ��� ���ϱ� ���� �ȷ��� ����� ��ȸ
		List<FollowVO> following_Id = followService.getFollowing(sessionId);
		
		dataMap.put("follower_size", follower_Id.size());
		dataMap.put("followerPageNum", followerPageNum);
		dataMap.put("followerTotalPageNum", followerTotalPageNum);
		dataMap.put("follower_info", follower_info);
		dataMap.put("following_Id", following_Id);
		dataMap.put("following_size", following_Id.size());

		return dataMap;
	}
	
	@GetMapping("/alarmFollow")
	public String alarmFollow(Model model, HttpSession session, @RequestParam String member_Id, @RequestParam int alarm_Seq) {
		
		if(session.getAttribute("loginUser") == null) {
			
			//System.out.println("���ǰ� ����");			
			model.addAttribute("message", "�α����� ���ּ���");			
			
			return "login";			
		} else {
			
			//�˶� ����
			alarmService.deleteAlarm(alarm_Seq);
			
			String session_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
			
			// �˶� ����Ʈ�� ��� �κ�
	    	List<AlarmVO> alarmList = alarmService.getAllAlarm(session_Id);
	    	
	    	int alarmListSize = alarmList.size();
	    	
	    	// �˶��� ������ �ľ��ϴ� �κ�
	    	for(int j=0; j<alarmList.size(); j++) {
	    		int kind = alarmList.get(j).getKind();
	    		if(kind == 1) {
	    			alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "�Բ��� ȸ������ �ȷο� <br>�Ͽ����ϴ�.");
	    		}else if(kind == 2) {
	    			alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "�Բ��� ȸ������ �Խñۿ� <br>���ƿ並 �������ϴ�.");
	    		}else if(kind == 3) {
	    			alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "�Բ��� ȸ������ �Խñۿ� <br>����� �޾ҽ��ϴ�.");
	    		}else if(kind == 4) {
	    			alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "�Բ��� ȸ������ ��ۿ� <br>���ƿ並 �������ϴ�.");
	    		}else if(kind == 5) {
	    			alarmList.get(j).setMessage("ȸ���Բ��� �����Ͻ� ������ <br>����� �޷Ƚ��ϴ�.");
	    		}
	    	}
			
			//System.out.println("���ǰ� ����");			
			List<FollowVO> following_Id = followService.getFollowing(member_Id);			
			List<FollowVO> follower_Id = followService.getFollower(member_Id);			
			List<MemberVO> following_info = new ArrayList<MemberVO>();			
			List<MemberVO> follower_info = new ArrayList<MemberVO>();
			
			for(FollowVO id : following_Id) {
				
				MemberVO following_member = memberService.getMemberInfo(id.getFollowing());				
				if(following_member == null) {
					
					//System.out.println("�ȷ��� ��� �� ĭ");
				} else {
					
					following_info.add(following_member);
					//System.out.println("�ȷ��� ��� : " + following_member);
				}
			}
			
			for(FollowVO id : follower_Id) {
				
				MemberVO follower_member = memberService.getMemberInfo(id.getFollower());
				
				if(follower_member == null) {
					// System.out.println("�ȷο� ��� �� ĭ");
				} else {
					follower_info.add(follower_member);
					//System.out.println("�ȷο� ��� : " + follower_member);
				}
			}
			
			//System.out.println("�ȷ��� �� : " + following_info.size());
			//System.out.println("�ȷο� �� : " + follower_info.size());
			
			int followingTotalPageNum = 1;
			
			if(following_info.size() % 10 != 0 && following_info.size() > 10) {
				followingTotalPageNum = following_info.size() / 10 + 1;
			} else if(following_info.size() % 10 != 0 && following_info.size() < 10) {
				followingTotalPageNum = 1;
			} else if(following_info.size() % 10 == 0) {
				followingTotalPageNum = following_info.size() / 10;
			}
			
			int followerTotalPageNum = 1;
			
			if(follower_info.size() % 10 != 0 && follower_info.size() > 10) {
				followerTotalPageNum = follower_info.size() / 10 + 1;
			} else if(follower_info.size() % 10 != 0 && follower_info.size() < 10) {
				followerTotalPageNum = 1;
			} else if(follower_info.size() % 10 == 0) {
				followerTotalPageNum = follower_info.size() / 10;
			}
			
			//System.out.println("�ȷ��� ������ �� : " + followingTotalPageNum);
			//System.out.println("�ȷο� ������ �� : " + followerTotalPageNum);			
			
			int followingLoadRow = 10;
			
			if(following_info.size() <= 10) {
				followingLoadRow = following_info.size();
			}
			
			int followerLoadRow = 10;
			
			if(follower_info.size() <= 10) {
				followerLoadRow = follower_info.size();
			}
			
			for(int i = 0; i < followerLoadRow; i++) {
				//System.out.println("�ȷο� ���̵�");
				//System.out.println(follower_info.get(i).getMember_Id());
				for(int j = 0; j < followingLoadRow; j++) {
					//System.out.println("�ȷ��� ���̵�");
					//System.out.println(following_info.get(j).getMember_Id());
					if(follower_info.get(i).getMember_Id().equals(following_info.get(j).getMember_Id())) {
						follower_info.get(i).setBothFollow(1);
						//System.out.println("setBoth �Է� Ȯ�� : " + follower_info.get(i).getBothFollow());
					}
				}
			}
			
			//System.out.println("�ȷ��� ��� �� �� : " + followingLoadRow);
			//System.out.println("�ȷο� ��� �� �� : " + followerLoadRow);

			model.addAttribute("following", following_info);
			model.addAttribute("followingLoadRow", followingLoadRow);
			model.addAttribute("followingTotalPageNum", followingTotalPageNum);
			model.addAttribute("followingPageNum", 1);
			
			model.addAttribute("follower", follower_info);
			model.addAttribute("followerLoadRow", followerLoadRow);
			model.addAttribute("followerTotalPageNum", followerTotalPageNum);
			model.addAttribute("followerPageNum", 1);
			
			// ȭ�� ���� Hottest Feed
			List<PostVO> hottestFeed = postService.getHottestFeed();
			model.addAttribute("hottestFeed", hottestFeed);
			
			model.addAttribute("alarmList", alarmList);
			model.addAttribute("alarmListSize", alarmListSize);
			
			return "follow";
		
		}
	}	
}
