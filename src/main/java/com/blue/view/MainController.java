package com.blue.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.blue.dto.FollowVO;
import com.blue.dto.LikeVO;
import com.blue.dto.MemberVO;
import com.blue.dto.PostVO;
import com.blue.dto.ReplyVO;
import com.blue.service.FollowService;
import com.blue.service.MemberService;
import com.blue.service.PostService;
import com.blue.service.ReplyService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
//��Ʈ�ѷ����� 'loginUser', 'profileMap' �̶�� �̸����� �� ��ü�� �����Ҷ� ���ǿ� ���ÿ� �����Ѵ�.
@SessionAttributes({"loginUser", "profileMap"})	
public class MainController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private PostService postService;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private FollowService followService;
	//�׽�Ʈ�� Ǯ������Ʈ �Ұ��� ��������������
	// �α��� �������� �̵�
	@GetMapping(value="/login")
	public String login(){
		System.out.println("[�α��� - 1] http://localhost:8082/blue/login �Է� �� '/login'�� GetMapping�ؼ� loginâ���� �̵�");
		return "login";
	}
	
	// �α��� ó��
	@PostMapping("/loginProc")
	public String LoginAction(Model model, @ModelAttribute("vo") MemberVO vo) {
		System.out.println("[�α��� - 2] login.jsp���� /loginProc ��û�� �� ��ƿ�");
		int result = memberService.doLogin(vo);
		
		if(result == 1) {			
			System.out.println("[�α��� - 7 - if - 1] MemberDAO���� �޾ƿ� result�� �α��� ó���ϰ� memberService�� getMemberȣ��");
			System.out.println("[�α��� - 7 - if - 5] getMember�� �޾ƿ� ȸ�� ������ loginUser��� �̸����� ���ǿ� �ø��� index.jsp ȣ��");
			model.addAttribute("loginUser", memberService.getMember(vo.getMember_Id()));
			return "redirect:index";
		} else {
			System.out.println("[�α��� - 7 - else - 1] ������ �ƴϸ� login_fail.jsp ȣ�� -> alert �� login.jsp��");
			return "login_fail";
		}
	}

	// �α׾ƿ� ó��
	@GetMapping("/logout")
	public String logout(SessionStatus status) {
		status.setComplete();
		return "login";
	}
	
	// index ������ �ε�
	@GetMapping("/index")
	public String getRecommendMember(Model model, HttpSession session) {
		
		if(session.getAttribute("loginUser") == null) {
			System.out.println("���ǰ� ����");
			model.addAttribute("message", "�α����� ���ּ���");
			return "login";
		} else {
									/* index�������� �ȷο� �κ� */
			System.out.println("[�����õ - 1] �α��� �� index ��û�ϸ� GetMapping���� ��ƿ��� ������ loginUser���� Id �̾Ƽ� member_Id�� ����");
			String member_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
			
			System.out.println("[�����õ - 2] member_Id�� ������ memberService�� getRecommendMember ��û");		
			List<MemberVO> recommendMember = memberService.getRecommendMember(member_Id);
			System.out.println("[�����õ - 5] DAO���� ��õ ����Ʈ�� �޾ƿͼ� List�� �����ϰ� model�� �ø��� index.jsp ȣ��");
			
			System.out.println("[�α�� - 1] �α��� �� index ��û�ϸ� GetMapping���� ��ƿ�");
	    	System.out.println("[�α�� - 2] postService�� getHottestFeed ��û");
	    	List<PostVO> hottestFeed = postService.getHottestFeed();
	    	System.out.println("[�α�� - 5] DAO���� hottestFeed �޾ƿͼ� List�� �����ϰ� model�� �ø�");
			
			
									/* index�������� �����ǵ� �κ� */
			// �ڽ�, �ȷ����� ������� �Խñ��� ��ºκ�
			ArrayList<PostVO> postlist = postService.getlistPost(member_Id);
			System.out.println("�Խñ� " + postlist.size() + "�� �ҷ���");
			
			// �� post_seq�� ���� ��۵��� ������ ����.
			Map<Integer, ArrayList<ReplyVO>> replymap = new HashMap<>();
			
			// ���ĵ� postlist�� �ε��� ������ ��� ����Ʈ�� ������.
			// ���ÿ� �� �Խñ��� ���ƿ� ī��Ʈ�� ��� ī��Ʈ�� ����.
			for(int i=0; i<postlist.size(); i++) {
				// �ڽ�, �ȷ����� ������� �Խñ��� post_seq�� �ҷ��´�.
				int post_Seq = postlist.get(i).getPost_Seq();
				
				// i��° �Խñ��� ��� ����Ʈ�� ����
				ArrayList<ReplyVO> replylist = replyService.getReplyPreview(post_Seq);
				System.out.println("replylist�� ����");
				System.out.println("[�̸����� ��� - 1] replylist�� �ش� �Խñ��� ��� 3���� ������ / ���� �ش� ��� ���ƿ� ������ üũ�� �ȵ�");
				System.out.println("[�̸����� ��� - 1.5] replylist size : " + replylist.size());
				// i��° �Խñ��� ��� ���ƿ� ���� üũ
				for(int k = 0; k < replylist.size(); k++) {
					ReplyVO voForReplyCheck = replylist.get(k);
					String realReply_Member_Id = replylist.get(k).getMember_Id();
					voForReplyCheck.setMember_Id(member_Id);
					System.out.println("[�̸����� ��� - 2] ��� ���ƿ� ������ Ȯ���Ϸ� ����");				
					String reply_LikeYN = replyService.checkReplyLike(voForReplyCheck);
					replylist.get(k).setReply_LikeYN(reply_LikeYN);
					System.out.println("[�̸����� ��� - 5] DAO���� ���Ϲ޾Ƽ� set����. �ش� ��� ���ƿ� ���� ? " + replylist.get(k).getReply_LikeYN());
					replylist.get(k).setMember_Id(realReply_Member_Id);
				}
				
				// i��°�� �Խñ��� ����� map�� �����ϴ� �۾�
				replymap.put(i, replylist);
				System.out.println(i + "��° �Խñ� ��� ����" + replymap.get(i));			
				
				// i��° �Խñ��� ���ƿ� ���� üũ
				PostVO voForLikeYN = new PostVO();
				voForLikeYN.setMember_Id(member_Id);
				voForLikeYN.setPost_Seq(post_Seq);
				System.out.println("[���ƿ� ���� Ȯ�� - 0] �Խñ� ��ȣ : " + post_Seq);
				System.out.println("[���ƿ� ���� Ȯ�� - 1] Setting �� post_LikeYN = " + postlist.get(i).getPost_LikeYN());
				String post_LikeYN = postService.getLikeYN(voForLikeYN);
				postlist.get(i).setPost_LikeYN(post_LikeYN);
				System.out.println("[���ƿ� ���� Ȯ�� - 4] Setting �� post_LikeYN = " + postlist.get(i).getPost_LikeYN());
				
			}
			
			// ��ü ȸ�� ������ �̹��� ��ȸ
			HashMap<String, String> profilemap = memberService.getMemberProfile();
			System.out.println("��ü ȸ�� ������: " + profilemap);
			
			model.addAttribute("profileMap", profilemap);
			model.addAttribute("postList", postlist);
			model.addAttribute("replyMap", replymap);
			model.addAttribute("recommendMember", recommendMember);
			model.addAttribute("hottestFeed", hottestFeed);	
			return "index";
		}
	}
	
	@GetMapping("/follow")
	public String getFollow(Model model, HttpSession session, @RequestParam String member_Id) {
		
		if(session.getAttribute("loginUser") == null) {
			
			System.out.println("���ǰ� ����");
			
			model.addAttribute("message", "�α����� ���ּ���");
			
			return "login";
			
		} else {
			
			System.out.println("���ǰ� ����");			
			
			List<FollowVO> following_Id = followService.getFollowing(member_Id);
			
			List<FollowVO> follower_Id = followService.getFollower(member_Id);
			
			List<MemberVO> following_info = new ArrayList<MemberVO>();
			
			List<MemberVO> follower_info = new ArrayList<MemberVO>();
			
			for(FollowVO id : following_Id) {
				
				MemberVO following_member = memberService.getMemberInfo(id.getFollowing());
				
				if(following_member == null) {
					System.out.println("�ȷ��� ��� �� ĭ");
				}else {
					following_info.add(following_member);
					System.out.println("�ȷ��� ��� : " + following_member);
				}
			}
			
			for(FollowVO id : follower_Id) {
				
				MemberVO follower_member = memberService.getMemberInfo(id.getFollower());
				
					if(follower_member == null) {
					System.out.println("�ȷο� ��� �� ĭ");
				}else {
					follower_info.add(follower_member);
					System.out.println("�ȷο� ��� : " + follower_member);
				}
			}
			
			System.out.println("�ȷ��� �� : " + following_info.size());
			System.out.println("�ȷο� �� : " + follower_info.size());
			
			int followingTotalPageNum = 1;
			
			if(following_info.size() % 10 != 0 && following_info.size() > 10) {
				followingTotalPageNum = following_info.size() / 10 + 1;
			}else if(following_info.size() % 10 != 0 && following_info.size() < 10) {
				followingTotalPageNum = 1;
			}else if(following_info.size() % 10 == 0) {
				followingTotalPageNum = following_info.size() / 10;
			}
			
			int followerTotalPageNum = 1;
			
			if(follower_info.size() % 10 != 0 && follower_info.size() > 10) {
				followerTotalPageNum = follower_info.size() / 10 + 1;
			}else if(follower_info.size() % 10 != 0 && follower_info.size() < 10) {
				followerTotalPageNum = 1;
			}else if(follower_info.size() % 10 == 0) {
				followerTotalPageNum = follower_info.size() / 10;
			}
			
			System.out.println("�ȷ��� ������ �� : " + followingTotalPageNum);
			System.out.println("�ȷο� ������ �� : " + followerTotalPageNum);
			
			
			int followingLoadRow = 10;
			
			if(following_info.size() <= 10) {
				followingLoadRow = following_info.size();
			}
			
			int followerLoadRow = 10;
			
			if(follower_info.size() <= 10) {
				followerLoadRow = follower_info.size();
			}
			
			
			
			for(int i = 0; i < followerLoadRow; i++) {
				System.out.println("�ȷο� ���̵�");
				System.out.println(follower_info.get(i).getMember_Id());
				for(int j = 0; j < followingLoadRow; j++) {
					System.out.println("�ȷ��� ���̵�");
					System.out.println(following_info.get(j).getMember_Id());
					if(follower_info.get(i).getMember_Id().equals(following_info.get(j).getMember_Id())) {
						follower_info.get(i).setBothFollow(1);
						System.out.println("setBoth �Է� Ȯ�� : " + follower_info.get(i).getBothFollow());
					}
				}
			}
			
			System.out.println("�ȷ��� ��� �� �� : " + followingLoadRow);
			System.out.println("�ȷο� ��� �� �� : " + followerLoadRow);

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
			
			//model.addAttribute("following_Id", following_Id);
			//model.addAttribute("following_size", following_Id.size());
			
			return "follow";
		
		}
	}	
	
	// �ȷο� ����
	@PostMapping("/changeFollow")
	@ResponseBody   
	public String changeFollow(@RequestBody String member_Id, HttpSession session) {
		System.out.println("[�ȷο�, ���ȷο� - 3] PostMapping���� /changeFollow ��ƿ�, ������� ���̵� : " + member_Id);
		String follower = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
		System.out.println("[�ȷο�, ���ȷο� - 4] ���ǿ��� �α��� ���� ���̵� �޾ƿͼ� follower�� ���� : " + follower);
		try {
			System.out.println("[�ȷο�, ���ȷο� - 5 - try] js���� data�� ������ {member_Id: ���̵�} �̷� ���̴ϱ� �ű⼭ ������ ������ �ʿ�");
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(member_Id);
			  
			String following = jsonNode.get("member_Id").asText();
			
			System.out.println("[�ȷο�, ���ȷο� - 6] ������ following�� ��� follower�� following�� FollowVO ��ü�� �����");
			FollowVO vo = new FollowVO();
			vo.setFollower(follower);
			vo.setFollowing(following);
		
			System.out.println("[�ȷο�, ���ȷο� - 7] FollowVO��ü vo�� ������ changeFollow() ��û");
			memberService.changeFollow(vo);
			System.out.println("[�ȷο�, ���ȷο� - 12] changeFollow �ϰ� js�� success ����");
			return "success";
		} catch (Exception e) {
			System.out.println("[�ȷο�, ���ȷο� - 5 - catch] JSON �Ľ� ������ ���");
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
		
		System.out.println("if���� ���� ������ �ѹ� : "+ followingPageNum);
		System.out.println("if���� ��ü ������ �� : " + followingTotalPageNum);
		
		int LocalPageFirstNum = followingPageNum*10+1;
		int LocalPageLastNum = followingPageNum*10+10;
		
		if(followingPageNum < followingTotalPageNum) {
			followingPageNum++;
		}else {
			System.out.println("���� ������ �ѹ��� ��ü ������ �ѹ��� ���ų� Ŀ�� ��ž");
			dataMap.put("message", "�ҷ��� �����Ͱ� �������� �ʽ��ϴ�1");
			return dataMap;
		}
		
		System.out.println("if���� : " + followingPageNum);
		
		FollowVO followVo = new FollowVO();
		
		followVo.setFollower(sessionId);
		followVo.setFollowingLocalPageFirstNum(LocalPageFirstNum);
		followVo.setFollowingLocalPageLastNum(LocalPageLastNum);
		
		System.out.println("ù��° �� : " + LocalPageFirstNum);
		System.out.println("������ �� : " + LocalPageLastNum);
		
		System.out.println(sessionId);
		
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
				System.out.println("�ȷ��� ��� �� ĭ");
			}else {
				following_info.add(following_member);
				System.out.println("�ȷ��� ��� : " + following_member);
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
	    
	    System.out.println("��Ż ������ �ѹ� : " + followerTotalPageNum);
	    System.out.println("���� ������ �ѹ� : " + followerPageNum);
		
		
	    //[�ȷο�, ���ȷο� - 3] PostMapping���� /moreLoadFollwing ��ƿ�, �� ������ �� : followerTotalPageNum
		String sessionId = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
		
		System.out.println("if���� ���� ������ �ѹ� : "+ followerPageNum);
		System.out.println("if���� ��ü ������ �� : " + followerTotalPageNum);
		
		int LocalPageFirstNum = followerPageNum*10+1;
		int LocalPageLastNum = followerPageNum*10+10;
		
		if(followerPageNum < followerTotalPageNum) {
			followerPageNum++;
		}else {
			System.out.println("���� ������ �ѹ��� ��ü ������ �ѹ��� ���ų� Ŀ�� ��ž");
			dataMap.put("message", "�ҷ��� �����Ͱ� �������� �ʽ��ϴ�1");
			return dataMap;
		}
		
		System.out.println("if���� ���� ������ �ѹ� : " + followerPageNum);
		
		
		
		FollowVO followVo = new FollowVO();
		
		followVo.setFollowing(sessionId);
		followVo.setFollowerLocalPageFirstNum(LocalPageFirstNum);
		followVo.setFollowerLocalPageLastNum(LocalPageLastNum);
		
		System.out.println("ù��° �� : " + LocalPageFirstNum);
		System.out.println("������ �� : " + LocalPageLastNum);
		
		// �ȷο� �߰� �ε��ϱ�(��~�� �������� ��ȸ)
		List<FollowVO> follower_Id  = followService.getMoreFollower(followVo);
		
		
		if(follower_Id == null) {
			dataMap.put("message", "�ҷ��� �����Ͱ� �������� �ʽ��ϴ�2");
			return dataMap;
		}else if(follower_Id.size() == 0) {
			dataMap.put("message", "�ҷ��� �����Ͱ� �������� �ʽ��ϴ�3");
			return dataMap;
		}
		
		List<MemberVO> follower_info = new ArrayList<MemberVO>();
		
		for(FollowVO id : follower_Id) {
			
			MemberVO follower_member = memberService.getMemberInfo(id.getFollower());
			
			if(follower_member == null) {
				System.out.println("�ȷο� ��� �� ĭ");
			}else {
				follower_info.add(follower_member);
				System.out.println("�ȷο� ��� : " + follower_member);
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
	
	// ���ƿ� ����(PostMapping)
	@PostMapping("/changeLike")
	@ResponseBody
	public String changeLike(@RequestBody Map<String, Integer> requestBody, HttpSession session) {
	    System.out.println("[�Խñ� ���ƿ� - 3] PostMapping���� /changeLike �� Map �������� ��ƿ�.");
	    int post_Seq = requestBody.get("post_Seq");
	    String member_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
	    System.out.println("[�Խñ� ���ƿ� - 4] ���ǿ��� �α��� ���� ���̵� �޾ƿ� member_Id = " + member_Id);
	    try {
	        LikeVO vo = new LikeVO();
	        vo.setMember_Id(member_Id);
	        vo.setPost_Seq(post_Seq);

	        System.out.println("[�Խñ� ���ƿ� - 5] LikeVO��ü vo�� ������ changeLike() ��û ����");
	        postService.changeLike(vo);
	        System.out.println("[�Խñ� ���ƿ� - 9] changeLike �ϰ� js�� success ����");
	        return "success";
	    } catch (Exception e) {
	        System.out.println("[���ƿ� - 5 - catch] JSON �Ľ� ������ ���");
	        // JSON �Ľ� ���� ó��
	        e.printStackTrace();
	        return "error";
	    }
	}
	
	// �̸����� ��� ���ƿ� ����
	@PostMapping("/changeReplyLike")
	@ResponseBody
	public String changeReplyLike(@RequestBody Map<String, Integer> requestBody, HttpSession session) {
	    System.out.println("[�̸����� ��� ���ƿ� - 3] PostMapping���� /changeReplyLike �� Map �������� ��ƿ�.");
	    int post_Seq = requestBody.get("post_Seq");
	    int reply_Seq = requestBody.get("reply_Seq");
	    String member_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
	    System.out.println("[�̸����� ��� ���ƿ� - 4] ���ǿ��� �α��� ���� ���̵� �޾ƿ� member_Id = " + member_Id);
	    try {
	        LikeVO vo = new LikeVO();
	        vo.setMember_Id(member_Id);
	        vo.setPost_Seq(post_Seq);
	        vo.setReply_Seq(reply_Seq);
	        
	        System.out.println("[�Խñ� ���ƿ� - 5] LikeVO��ü vo�� ������ changeLike() ��û ����");
	        replyService.changeReplyLike(vo);
	        System.out.println("[�Խñ� ���ƿ� - 9] changeLike �ϰ� js�� success ����");
	        return "success";
	    } catch (Exception e) {
	        System.out.println("[���ƿ� - 5 - catch] JSON �Ľ� ������ ���");
	        // JSON �Ľ� ���� ó��
	        e.printStackTrace();
	        return "error";
	    }
	}
	
	// �Խñ� �ۼ�
	@PostMapping("/insertPost")
	public String insertPost(PostVO vo, @RequestParam("uploadImgs") MultipartFile[] uploadImgs) {
		
		System.out.println("insertPost vo : " + vo);
		System.out.println("insertPost file : " + uploadImgs.length);
		
		// �Խñ��� �������θ� üũ���� �ʾҴٸ� n������ set
		if(vo.getPost_Public() == "") {
			vo.setPost_Public("n");
		}
		postService.insertPost(vo);
		
		return "redirect:/index";
	}
	
	// �Խñ� �󼼺��� ������ (���â)
	@GetMapping("/modal")
	@ResponseBody
	public Map<String, Object> modal(@RequestParam int post_Seq, HttpSession session) {
		// 0. ���ǿ� ����Ǿ��ִ� ���̵� �ҷ���
		String member_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
		
		// 0. ajax��û�� ���� response�� ������ ���� Map ���� ����
		Map<String, Object> dataMap = new HashMap<>();
		
		// 1. �Խñ� ������������ Ŭ���� �Խñ��� ������ ����ϱ� ���� PostVO �� ����
		PostVO postInfo = postService.getpostDetail(post_Seq);
		
		// 2. �Խñ��� ��۸���Ʈ�� ����ϱ� ���� ArrayList<ReplyVO> �� ����
		ArrayList<ReplyVO> replyList = replyService.listReply(post_Seq);
		
        // 3. ��ü ȸ�� ������ �̹��� ��ȸ
		HashMap<String, String> profileMap = (HashMap<String, String>) session.getAttribute("profileMap");
		System.out.println("���ǰ�ü�� profileMap�� �ҷ��� : " + profileMap);
		

		// 4. �Խñ��� ���ƿ� ���� üũ
		// ��ȸ�� ���� ��ü ����
		PostVO LikeYN = new PostVO();
		LikeYN.setMember_Id(member_Id);
		LikeYN.setPost_Seq(post_Seq);
		
		// ��ȸ ��� ����
		String post_LikeYN = postService.getLikeYN(LikeYN);
		postInfo.setPost_LikeYN(post_LikeYN);
		
		// 5. �Խñ� ����� ���ƿ� ���� üũ
		for(int i=0; i<replyList.size(); i++) {
			// ��ȸ�� ���� ��ü ����
			ReplyVO replyLikeYN = new ReplyVO();
			replyLikeYN.setMember_Id(member_Id);
			replyLikeYN.setPost_Seq(post_Seq);
			replyLikeYN.setReply_Seq(replyList.get(i).getReply_Seq());
			
			// ��ȸ ��� ����
			String reply_LikeYN = replyService.checkReplyLike(replyLikeYN);
			replyList.get(i).setReply_LikeYN(reply_LikeYN);
		}
		
 		// �Խñ� ������ VO
 		dataMap.put("post", postInfo);  
 		// �Խñ��� ��� ����Ʈ
 		dataMap.put("replies", replyList);
 		// ��ü ȸ���� ������ �̹���
 		dataMap.put("profile", profileMap);
		
		return dataMap;
	}
	
	
	// �Խñ� �󼼺��� ������ (��� ����Ʈ��)
	@GetMapping("/replyModal")
	@ResponseBody
	public Map<String, Object> modalReply(@RequestParam int post_Seq, HttpSession session) {
		// 0. ���ǿ� ����Ǿ��ִ� ���̵� �ҷ���
		String member_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
				
		// 0. ajax��û�� ���� response�� ������ ���� Map ���� ����
		Map<String, Object> dataMap = new HashMap<>();
		
		// 1. �Խñ� ������������ Ŭ���� �Խñ��� ������ ����ϱ� ���� PostVO �� ����
		PostVO postInfo = postService.getpostDetail(post_Seq);
		
		// 2. �Խñ��� ��۸���Ʈ�� ����ϱ� ���� ArrayList<ReplyVO> �� ����
		ArrayList<ReplyVO> replyList = replyService.listReply(post_Seq);
		
        // 3. ��ü ȸ�� ������ �̹��� ��ȸ
		HashMap<String, String> profileMap = (HashMap<String, String>) session.getAttribute("profileMap");
 		
		// 4. �Խñ��� ���ƿ� ���� üũ
		// ��ȸ�� ���� ��ü ����
		PostVO LikeYN = new PostVO();
		LikeYN.setMember_Id(member_Id);
		LikeYN.setPost_Seq(post_Seq);
		
		// ��ȸ ��� ����
		String post_LikeYN = postService.getLikeYN(LikeYN);
		postInfo.setPost_LikeYN(post_LikeYN);
		
		// 5. �Խñ� ����� ���ƿ� ���� üũ
		for(int i=0; i<replyList.size(); i++) {
			// ��ȸ�� ���� ��ü ����
			ReplyVO replyLikeYN = new ReplyVO();
			replyLikeYN.setMember_Id(member_Id);
			replyLikeYN.setPost_Seq(post_Seq);
			replyLikeYN.setReply_Seq(replyList.get(i).getReply_Seq());
			
			// ��ȸ ��� ����
			String reply_LikeYN = replyService.checkReplyLike(replyLikeYN);
			replyList.get(i).setReply_LikeYN(reply_LikeYN);
		}
		
 		// �Խñ� ������ VO
 		dataMap.put("post", postInfo);  
 		// �Խñ��� ��� ����Ʈ
 		dataMap.put("replies", replyList);
 		// ��ü ȸ���� ������ �̹���
 		dataMap.put("profile", profileMap);
		
		return dataMap;
	}
	
	
	
	// ��� �μ�Ʈ
	@GetMapping("/insertReply")
	@ResponseBody
	public Map<String, Object> insertReply(@RequestParam("post_Seq") int post_Seq, 
						   @RequestParam("reply_Content") String reply_Content, HttpSession session) {
		// ajax���� ���� ����.
		System.out.println("��Ʈ�ѷ��� ����Ʈ ������: " + post_Seq + ", ��Ʈ�ѷ��� ���ö��� ������: " + reply_Content);
		
		// 0. ajax��û�� ���� response�� ������ ���� Map ���� ����
		Map<String, Object> dataMap = new HashMap<>();
		
		// 1. ���ǰ�ü�� 'loginUfser'��ü�� MemberVO ��ü�� ���� �Ľ��ؼ� getter �޼ҵ��� getMember_Id�� ȣ���� ���̵� �����´�.
		String member_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
		System.out.println("���� ���̵�: " + member_Id);

		// 2. insert�������� �Ķ���� ��ü�� ���� ��������
		ReplyVO rep = new ReplyVO();
		rep.setMember_Id(member_Id);
		rep.setPost_Seq(post_Seq);
		rep.setReply_Content(reply_Content);
		System.out.println("�μ�Ʈ �������� ���� ��ü ���빰: " + rep);
		
		// 3. insert������ ����
		replyService.insertReply(rep);
		
		// 4. �Խñ��� ��۸���Ʈ�� ����ϱ� ���� ArrayList<ReplyVO> �� ����
		ArrayList<ReplyVO> replyList = replyService.listReply(post_Seq);
		
		// 5. ��ü ȸ���� �̹��� Map�� ���ǿ��� �޾ƿ�
		HashMap<String, String> profileMap = (HashMap<String, String>) session.getAttribute("profileMap");
		
		// 6. �ش� �Խñ��� �������� ��ȸ�ؼ� ������(�Խñ��� ��� ī��Ʈ ������ ����)
		PostVO postInfo = postService.getpostDetail(post_Seq);
		
		// 7. �Խñ� ����� ���ƿ� ���� üũ
		for(int i=0; i<replyList.size(); i++) {

			// ��ȸ�� ���� ��ü ����
			ReplyVO replyLikeYN = new ReplyVO();
			replyLikeYN.setMember_Id(member_Id);
			replyLikeYN.setPost_Seq(post_Seq);
			replyLikeYN.setReply_Seq(replyList.get(i).getReply_Seq());
			
			// ��ȸ ��� ����
			String reply_LikeYN = replyService.checkReplyLike(replyLikeYN);
			replyList.get(i).setReply_LikeYN(reply_LikeYN);
		}
		
		// 8. ajax�� ���伺��(success)�� response�� �� ���� ����
		dataMap.put("postInfo", postInfo);
		dataMap.put("replies", replyList);
		dataMap.put("profile", profileMap);
		
		return dataMap;
	}

	// PEOPLE �� List ��������
	@GetMapping("/people_List")
	public ResponseEntity<Map<String, Object>> people_List(HttpSession session, Model model) {
	    System.out.println("[PEOPLE �� - 3] people_List GET MAPPING���� ��");
	    String member_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
	    
	    List<MemberVO> canFollow = memberService.getRecommendMember(member_Id);
	    System.out.println("[PEOPLE �� - 4] CAN FOLLOW LIST�� �޾ƿ��� ����");
	    
	    List<MemberVO> mostFamous = memberService.getMostFamousMember();
	    for(MemberVO vo : mostFamous) {
	        System.out.println(vo);
	    }
	    System.out.println("[PEOPLE �� - 7] MOST FAMOUS LIST�� �޾ƿ��� ����");
	    
	    Map<String, Object> responseData = new HashMap<>();
	    responseData.put("canFollow", canFollow);
	    responseData.put("mostFamous", mostFamous);
	    
	    return ResponseEntity.ok(responseData);
	}

	// Profile �̵�
	@GetMapping("/profile")
	public String makeProfile(@RequestParam("member_Id") String member_Id, Model model, HttpSession session) {
		System.out.println("[������ ������ - 1] GET MAPPING���� MainController�� ��. ������ ��� : " + member_Id);
		MemberVO member = memberService.getMember(member_Id);
		String loginUser_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
		
		// �ȷο� �ȷο� ���� �ؿ� ���� ���׶�� �̹����� ä�� �뵵
		List<MemberVO> followers = memberService.getFollowers(member_Id);
		int followers_Size = followers.size();
		List<MemberVO> followings = memberService.getFollowings(member_Id);
		int followings_Size = followings.size();
		model.addAttribute("followers", followers);
		model.addAttribute("followers_Size", followers_Size);
		model.addAttribute("followings", followings);
		model.addAttribute("followings_Size", followings_Size);
		
		System.out.println("[������ ������] ��� ��� vo");
		System.out.println(member);
		// ���� �� ���� postlist�� ����
		ArrayList<PostVO> postlist = postService.getMemberPost(member_Id);
		System.out.println("[������ ������] "+ member_Id + "�� �� �� ����Ʈ ����");
		for(PostVO abc : postlist) {
			System.out.println(abc);
		}
		System.out.println("[������ ������] " + member_Id + "�� �� �� ����Ʈ ��");
		System.out.println("[������ ������ - 2] �ش� ����� ������ �ۼ��� ����Ʈ���� ��ƿ�");		
		
		// �� post_seq�� ���� ��۵��� ������ ����.
		Map<Integer, ArrayList<ReplyVO>> replymap = new HashMap<>();
				
		// ���ĵ� postlist�� �ε��� ������ ��� ����Ʈ�� ������.
		// ���ÿ� �� �Խñ��� ���ƿ� ī��Ʈ�� ��� ī��Ʈ�� ����.
		for(int i = 0; i < postlist.size(); i++) {
			
			int post_Seq = postlist.get(i).getPost_Seq();
			
			// i��° �Խñ��� ��� ����Ʈ�� ����
			ArrayList<ReplyVO> replylist = replyService.getReplyPreview(post_Seq);
			System.out.println("[������ ������] " + i +"���� �Խñ��� �̸����� ��� ����Ʈ ����");
			for(ReplyVO abc : replylist) {
				System.out.println(abc);
			}
			System.out.println("[������ ������] " + (i) +"���� �Խñ��� �̸����� ��� ����Ʈ ��");
			// i��° �Խñ��� ��� ���ƿ� ���� üũ
			for(int k = 0; k < replylist.size(); k++) {
				ReplyVO voForReplyCheck = replylist.get(k);
				String realReply_Member_Id = replylist.get(k).getMember_Id();
				voForReplyCheck.setMember_Id(loginUser_Id);		
				String reply_LikeYN = replyService.checkReplyLike(voForReplyCheck);
				replylist.get(k).setReply_LikeYN(reply_LikeYN);
				replylist.get(k).setMember_Id(realReply_Member_Id);
				System.out.println("[������ ������] " + i+ "���� �Խñ��� " + (k) + "��° ��� ���ƿ� ���� : " + reply_LikeYN);

				System.out.println("[������ ������ - 3] �ش� ��� ���ƿ� ������ üũ��");
			}
			
			// i��°�� �Խñ��� ����� map�� �����ϴ� �۾�
			replymap.put(i, replylist);
			System.out.println("[������ ������ - 4] �Խñ� �� ����� ������");		
			
			// i��° �Խñ��� ���ƿ� ���� üũ
			PostVO voForLikeYN = new PostVO();
			voForLikeYN.setMember_Id(loginUser_Id);
			voForLikeYN.setPost_Seq(post_Seq);
			String post_LikeYN = postService.getLikeYN(voForLikeYN);
			int post_Like_Count = postService.getPost_Like_Count(post_Seq);
			postlist.get(i).setPost_Like_Count(post_Like_Count);
			postlist.get(i).setPost_LikeYN(post_LikeYN);
			System.out.println("[������ ������ - 5] " + i + "��° �Խñ� ���ƿ� ������ üũ�� : " + post_LikeYN);
			
		}
		
		// ��ü ȸ�� ������ �̹��� ��ȸ
		HashMap<String, String> profilemap = memberService.getMemberProfile();
		System.out.println("��ü ȸ�� ������: " + profilemap);
		
		System.out.println("[������ ������ - 6] ����غ� ���� postvo �غ�");
		for(PostVO abc : postlist) {
			System.out.println(abc);
		}
		System.out.println("[������ ������ - 6] ����غ� �Ϸ�");
		
		// ȭ�� ���� Hottest Feed
		List<PostVO> hottestFeed = postService.getHottestFeed();
		
		model.addAttribute("member", member);
		model.addAttribute("postlist", postlist);
		model.addAttribute("profileMap", profilemap);
		model.addAttribute("replyMap", replymap);
		model.addAttribute("hottestFeed", hottestFeed);	
		return "profile";
	}
	
	 @GetMapping("trending_List")
	   public ResponseEntity<Map<String, Object>> trending_List(HttpSession session, Model model) {
	      
	      System.out.println("[�����õ - 1] �α��� �� trending_List ��û�ϸ� GetMapping���� ��ƿ��� ������ loginUser���� Id �̾Ƽ� member_Id�� ����");
	      String member_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
	      
	      System.out.println("[�α�� - 1] index���� tranding tab��û�ϸ� js�� ��Ƽ� ����  GetMapping���� ��ƿ�");
	       System.out.println("[�α�� - 2] postService�� getHottestFeed ��û");
	       List<PostVO> hottestFeed = postService.getHottestFeed();
	       System.out.println("[�α�� - 5] DAO���� hottestFeed �޾ƿͼ� List�� �����ϰ� model�� �ø�");
	      
	      
	      // �� post_seq�� ���� ��۵��� ������ ����.
	      Map<Integer, ArrayList<ReplyVO>> replymap = new HashMap<>();
	      
	      // ���ĵ� postlist�� �ε��� ������ ��� ����Ʈ�� ������.
	      // ���ÿ� �� �Խñ��� ���ƿ� ī��Ʈ�� ��� ī��Ʈ�� ����.
	      for(int i=0; i<hottestFeed.size(); i++) {
	         // �ڽ�, �ȷ����� ������� �Խñ��� post_seq�� �ҷ��´�.
	         int post_Seq = hottestFeed.get(i).getPost_Seq();
	         
	         // i��° �Խñ��� ��� ����Ʈ�� ����
	         ArrayList<ReplyVO> replylist = replyService.getReplyPreview(post_Seq);
	         System.out.println("replylist�� ����");
	         System.out.println("[�̸����� ��� - 1] replylist�� �ش� �Խñ��� ��� 3���� ������ / ���� �ش� ��� ���ƿ� ������ üũ�� �ȵ�");
	         System.out.println("[�̸����� ��� - 1.5] replylist size : " + replylist.size());
	         // i��° �Խñ��� ��� ���ƿ� ���� üũ
	         for(int k = 0; k < replylist.size(); k++) {
	            ReplyVO voForReplyCheck = replylist.get(k);
	            String realReply_Member_Id = replylist.get(k).getMember_Id();
	            voForReplyCheck.setMember_Id(member_Id);
	            System.out.println("[�̸����� ��� - 2] ��� ���ƿ� ������ Ȯ���Ϸ� ����");            
	            String reply_LikeYN = replyService.checkReplyLike(voForReplyCheck);
	            replylist.get(k).setReply_LikeYN(reply_LikeYN);
	            System.out.println("[�̸����� ��� - 5] DAO���� ���Ϲ޾Ƽ� set����. �ش� ��� ���ƿ� ���� ? " + replylist.get(k).getReply_LikeYN());
	            replylist.get(k).setMember_Id(realReply_Member_Id);
	         }
	         
	         // i��°�� �Խñ��� ����� map�� �����ϴ� �۾�
	         replymap.put(i, replylist);
	         System.out.println(i + "��° �Խñ� ��� ����" + replymap.get(i));         
	         
	         // i��° �Խñ��� ���ƿ� ���� üũ
	         PostVO voForLikeYN = new PostVO();
	         voForLikeYN.setMember_Id(member_Id);
	         voForLikeYN.setPost_Seq(post_Seq);
	         System.out.println("[���ƿ� ���� Ȯ�� - 0] �Խñ� ��ȣ : " + post_Seq);
	         System.out.println("[���ƿ� ���� Ȯ�� - 1] Setting �� post_LikeYN = " + hottestFeed.get(i).getPost_LikeYN());
	         String post_LikeYN = postService.getLikeYN(voForLikeYN);
	         hottestFeed.get(i).setPost_LikeYN(post_LikeYN);
	         System.out.println("[���ƿ� ���� Ȯ�� - 4] Setting �� post_LikeYN = " + hottestFeed.get(i).getPost_LikeYN());
	         
	      }
	      
	      
	      // ��ü ȸ�� ������ �̹��� ��ȸ
	      HashMap<String, String> profilemap = memberService.getMemberProfile();
	      
	      
	      
	      System.out.println("��ü ȸ�� ������: " + profilemap);
	      
	      Map<String, Object> responseData = new HashMap<>();
	      
	      responseData.put("session_Id", member_Id);
	      responseData.put("trending_profileMap", profilemap);
	      responseData.put("trending_postList", hottestFeed);
	      responseData.put("trending_replyMap", replymap);
	      
	      return ResponseEntity.ok(responseData);
	   }
}
