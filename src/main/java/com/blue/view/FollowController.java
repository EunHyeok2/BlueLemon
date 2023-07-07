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
import com.blue.dto.RequestVO;
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
				} else if(kind == 2) {
					alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "�Բ��� ȸ������ �Խñۿ� <br>���ƿ並 �������ϴ�.");
				} else if(kind == 3) {
					alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "�Բ��� ȸ������ �Խñۿ� <br>����� �޾ҽ��ϴ�.");
				} else if(kind == 4) {
					alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "�Բ��� ȸ������ ��ۿ� <br>���ƿ並 �������ϴ�.");
				} else if(kind == 5) {
					alarmList.get(j).setMessage("ȸ���Բ��� �����Ͻ� ������ <br>����� �޷Ƚ��ϴ�.");
				}
			}
			List<FollowVO> following_Id = followService.getFollowing(member_Id);         
			List<FollowVO> follower_Id = followService.getFollower(member_Id);         
			List<MemberVO> following_info = new ArrayList<MemberVO>();         
			List<MemberVO> follower_info = new ArrayList<MemberVO>();
         
			for(FollowVO id : following_Id) {
            
	            MemberVO following_member = memberService.getMemberInfo(id.getFollowing());            
	            if(following_member == null) {
	            } else {
	            	following_info.add(following_member);
	            }
			}
         
			for(FollowVO id : follower_Id) {
            
				MemberVO follower_member = memberService.getMemberInfo(id.getFollower());
            
				if(follower_member == null) {
				} else {
					follower_info.add(follower_member);
				}
			}
         
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
         
			int followingLoadRow = 10;
         
			if(following_info.size() <= 10) {
				followingLoadRow = following_info.size();
			}
         
			int followerLoadRow = 10;
         
			if(follower_info.size() <= 10) {
				followerLoadRow = follower_info.size();
			}
         
			for(int i = 0; i < followerLoadRow; i++) {
				for(int j = 0; j < followingLoadRow; j++) {
					if(follower_info.get(i).getMember_Id().equals(following_info.get(j).getMember_Id())) {
						follower_info.get(i).setBothFollow(1);
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
			model.addAttribute("member_Id", member_Id);
			model.addAttribute("alarmList", alarmList);
			model.addAttribute("alarmListSize", alarmListSize);
         
			return "follow";
      
		}
   }   
	
	// �ȷο� ����
	@PostMapping("/changeFollow")
	@ResponseBody   
	public String changeFollow(@RequestBody String member_Id, HttpSession session) {
		String follower = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(member_Id);
			  
			String following = jsonNode.get("member_Id").asText();
			
			FollowVO vo = new FollowVO();
			vo.setFollower(follower);
			vo.setFollowing(following);
		
			memberService.changeFollow(vo);
			
			return "success";
		} catch (Exception e) {
			// JSON �Ľ� ���� ó��
			e.printStackTrace();
			return "error";
		}
	}

	@PostMapping("/moreLoadFollowing")
	@ResponseBody
	public Map<String, Object> moreLoadFollowing(@RequestBody RequestVO request, HttpSession session) {

		// 0. ajax��û�� ���� response�� ������ ���� Map ���� ����
		Map<String, Object> dataMap = new HashMap<>();
      
		// 1. ajax���� �޾ƿ� ��ü �޾Ƴ���
		int followingTotalPageNum = request.getFollowingTotalPageNum();
		int followingPageNum = request.getFollowingPageNum();
		String member_Id = request.getMember_Id();
		
		String sessionId = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
      
		int LocalPageFirstNum = followingPageNum*10+1;
		int LocalPageLastNum = followingPageNum*10+10;
      
		if(followingPageNum < followingTotalPageNum) {
			followingPageNum++;
		} else {
			dataMap.put("message", "�ҷ��� �����Ͱ� �������� �ʽ��ϴ�1");
			return dataMap;
		}
      
		FollowVO followVo = new FollowVO();
      
		followVo.setFollower(member_Id);
		followVo.setFollowingLocalPageFirstNum(LocalPageFirstNum);
		followVo.setFollowingLocalPageLastNum(LocalPageLastNum);

		// �ȷο� �߰� �ε��ϱ�(��~�� �������� ��ȸ)
		List<FollowVO> following_Id  = followService.getMoreFollowing(followVo);
      
		if(following_Id == null) {
			dataMap.put("message", "�ҷ��� �����Ͱ� �������� �ʽ��ϴ�2");
			return dataMap;
		} else if(following_Id.size() == 0) {
			dataMap.put("message", "�ҷ��� �����Ͱ� �������� �ʽ��ϴ�3");
			return dataMap;
		}
      
		List<MemberVO> following_info = new ArrayList<MemberVO>();
      
		for(FollowVO id : following_Id) {
         
			MemberVO following_member = memberService.getMemberInfo(id.getFollowing());
         
			if(following_member == null) {
			} else {
				following_info.add(following_member);
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
	public Map<String, Object> moreLoadFollower(@RequestBody RequestVO request, HttpSession session) {
		// 0. ajax��û�� ���� response�� ������ ���� Map ���� ����
		Map<String, Object> dataMap = new HashMap<>();
      
        // 1. ajax���� �޾ƿ� ��ü �޾Ƴ���
		int followerTotalPageNum = request.getFollowerTotalPageNum();
	    int followerPageNum = request.getFollowerPageNum();
	    String member_Id = request.getMember_Id();
	    
        String sessionId = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
      
	    int LocalPageFirstNum = followerPageNum*10+1;
	    int LocalPageLastNum = followerPageNum*10+10;
      
       if(followerPageNum < followerTotalPageNum) {
    	   followerPageNum++;
       } else {
    	   dataMap.put("message", "�ҷ��� �����Ͱ� �������� �ʽ��ϴ�1");
    	   return dataMap;
       }
      
       FollowVO followVo = new FollowVO();
      
       followVo.setFollowing(member_Id);
       followVo.setFollowerLocalPageFirstNum(LocalPageFirstNum);
       followVo.setFollowerLocalPageLastNum(LocalPageLastNum);
      
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
    	   } else {
    		   follower_info.add(follower_member);
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
			
			model.addAttribute("message", "�α����� ���ּ���");			
			
			return "login";			
		} else {
			
			//�˶� ����
			alarmService.deleteAlarm(alarm_Seq);
			
			String session_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
			
			String profileImage = memberService.getMemberInfo(session_Id).getMember_Profile_Image();
			
			// �˶� ����Ʈ�� ��� �κ�
	    	List<AlarmVO> alarmList = alarmService.getAllAlarm(session_Id);
	    	
	    	int alarmListSize = alarmList.size();
	    	
	    	// �˶��� ������ �ľ��ϴ� �κ�
	    	for(int j=0; j<alarmList.size(); j++) {
	    		int kind = alarmList.get(j).getKind();
	    		if(kind == 1) {
	    			alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "�Բ��� ȸ������ �ȷο� <br>�Ͽ����ϴ�.");
	    		} else if(kind == 2) {
	    			alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "�Բ��� ȸ������ �Խñۿ� <br>���ƿ並 �������ϴ�.");
	    		} else if(kind == 3) {
	    			alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "�Բ��� ȸ������ �Խñۿ� <br>����� �޾ҽ��ϴ�.");
	    		} else if(kind == 4) {
	    			alarmList.get(j).setMessage(alarmList.get(j).getFrom_Mem() + "�Բ��� ȸ������ ��ۿ� <br>���ƿ並 �������ϴ�.");
	    		} else if(kind == 5) {
	    			alarmList.get(j).setMessage("ȸ���Բ��� �����Ͻ� ������ <br>����� �޷Ƚ��ϴ�.");
	    		}
	    	}
			
			List<FollowVO> following_Id = followService.getFollowing(member_Id);			
			List<FollowVO> follower_Id = followService.getFollower(member_Id);			
			List<MemberVO> following_info = new ArrayList<MemberVO>();			
			List<MemberVO> follower_info = new ArrayList<MemberVO>();
			
			for(FollowVO id : following_Id) {
				
				MemberVO following_member = memberService.getMemberInfo(id.getFollowing());				
				if(following_member == null) {
				} else {
					following_info.add(following_member);
				}
			}
			
			for(FollowVO id : follower_Id) {
				
				MemberVO follower_member = memberService.getMemberInfo(id.getFollower());
				
				if(follower_member == null) {
				} else {
					follower_info.add(follower_member);
				}
			}
			
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
			
			int followingLoadRow = 10;
			
			if(following_info.size() <= 10) {
				followingLoadRow = following_info.size();
			}
			
			int followerLoadRow = 10;
			
			if(follower_info.size() <= 10) {
				followerLoadRow = follower_info.size();
			}
			
			for(int i = 0; i < followerLoadRow; i++) {
				for(int j = 0; j < followingLoadRow; j++) {
					if(follower_info.get(i).getMember_Id().equals(following_info.get(j).getMember_Id())) {
						follower_info.get(i).setBothFollow(1);
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
}
