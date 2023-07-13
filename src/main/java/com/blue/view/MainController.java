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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.blue.dto.AlarmVO;
import com.blue.dto.FollowVO;
import com.blue.dto.MemberVO;
import com.blue.dto.PostVO;
import com.blue.dto.QnaVO;
import com.blue.dto.ReplyVO;
import com.blue.dto.TagVO;
import com.blue.service.AlarmService;
import com.blue.service.MemberService;
import com.blue.service.PostService;
import com.blue.service.QnaService;
import com.blue.service.ReplyService;

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
	private QnaService qnaService;
	@Autowired
	private AlarmService alarmService;
	
	// �α��� �������� �̵�
	@GetMapping(value="/login")
	public String login(){
		return "login";
	}
	
	// �α��� ó��
	@PostMapping("/loginProc")
	public String LoginAction(Model model, @ModelAttribute("vo") MemberVO vo) {
		int result = memberService.doLogin(vo);
		
		if(result == 1) {			
			if(vo.getMember_Id().equals("admin")) {
				model.addAttribute("loginUser", memberService.getMember(vo.getMember_Id()));
				return "redirect:admin_Index";
			} else {
				model.addAttribute("loginUser", memberService.getMember(vo.getMember_Id()));
				return "redirect:index";
			}
		} else {
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
	@RequestMapping("/index")
	public String getRecommendMember(Model model, HttpSession session) {
		
		if(session.getAttribute("loginUser") == null) {
			model.addAttribute("message", "�α����� ���ּ���");
			return "login";
		} else {
									/* index�������� �ȷο� �κ� */
			String member_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
			
			String profileImage = memberService.getMemberInfo(member_Id).getMember_Profile_Image();
			
			List<MemberVO> recommendMember = memberService.getRecommendMember(member_Id);
	    	List<PostVO> hottestFeed = postService.getHottestFeed();
			
	    	// �˶� ����Ʈ�� ��� �κ�
	    	List<AlarmVO> alarmList = alarmService.getAllAlarm(member_Id);
	    	
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
			
									/* index�������� �����ǵ� �κ� */
			// �ڽ�, �ȷ����� ������� �Խñ��� ��ºκ�
			ArrayList<PostVO> postlist = postService.getlistPost(member_Id);
			for(int i = 0; i < postlist.size(); i++) {
				postlist.get(i).setPost_Content(postlist.get(i).getPost_Content().replace("\n", "<br>"));
			}
			
			// �� post_seq�� ���� ��۵��� ������ ����.
			Map<Integer, ArrayList<ReplyVO>> replymap = new HashMap<>();
			
			// �� post_seq�� ���� �ؽ��±׵��� ������ ����.
			Map<Integer, ArrayList<TagVO>> hashmap = new HashMap<>();
			
			// ���ĵ� postlist�� �ε��� ������ ��� ����Ʈ�� ������.
			// ���ÿ� �� �Խñ��� ���ƿ� ī��Ʈ�� ��� ī��Ʈ�� ����.
			for(int i=0; i<postlist.size(); i++) {
				// �ڽ�, �ȷ����� ������� �Խñ��� post_seq�� �ҷ��´�.
				int post_Seq = postlist.get(i).getPost_Seq();
				
				// i��° �Խñ��� ��� ����Ʈ�� ����
				ArrayList<ReplyVO> replylist = replyService.getReplyPreview(post_Seq);
				
				// i��° �Խñ��� ��� ���ƿ� ���� üũ
				for(int k = 0; k < replylist.size(); k++) {
					ReplyVO voForReplyCheck = replylist.get(k);
					String realReply_Member_Id = replylist.get(k).getMember_Id();
					voForReplyCheck.setMember_Id(member_Id);
					String reply_LikeYN = replyService.getCheckReplyLike(voForReplyCheck);
					replylist.get(k).setReply_LikeYN(reply_LikeYN);
					replylist.get(k).setMember_Id(realReply_Member_Id);
				}
				
				// i��°�� �Խñ��� ����� map�� �����ϴ� �۾�
				replymap.put(i, replylist);
				
				// i��° �Խñ��� ���ƿ� ���� üũ
				PostVO voForLikeYN = new PostVO();
				voForLikeYN.setMember_Id(member_Id);
				voForLikeYN.setPost_Seq(post_Seq);
				String post_LikeYN = postService.getLikeYN(voForLikeYN);
				postlist.get(i).setPost_LikeYN(post_LikeYN);
				
				// i��° �Խñ��� �ؽ��±� üũ    hashmap
				ArrayList<TagVO> hash = postService.getHashtagList(post_Seq);
				hashmap.put(post_Seq, hash);
			}
			
			// ��ü ȸ�� ������ �̹��� ��ȸ
			HashMap<String, String> profilemap = memberService.getMemberProfile();
			
			model.addAttribute("profileImage", profileImage);
			model.addAttribute("alarmList", alarmList);
			model.addAttribute("alarmListSize", alarmListSize);
			model.addAttribute("profileMap", profilemap);
			model.addAttribute("postList", postlist);
			model.addAttribute("replyMap", replymap);
			model.addAttribute("recommendMember", recommendMember);
			model.addAttribute("hottestFeed", hottestFeed);	
			model.addAttribute("member_Id", member_Id);
			model.addAttribute("hashMap", hashmap);
			
			return "index";
		}
	}
	
	// PEOPLE �� List ��������
	@GetMapping("/people_List")
	public ResponseEntity<Map<String, Object>> people_List(HttpSession session, Model model) {
	    String member_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
	    
	    List<MemberVO> canFollow = memberService.getRecommendMember(member_Id);
	    
	    List<MemberVO> mostFamous = memberService.getMostFamousMember();
	    
	    for(int i = 0 ; i < mostFamous.size() ; i++) {
	    	String check_Id = mostFamous.get(i).getMember_Id();
	    	FollowVO check_Vo = new FollowVO();
	    	check_Vo.setFollower(member_Id);
	    	check_Vo.setFollowing(check_Id);
	    	String followCheck = memberService.checkFollow(check_Vo);
	    	mostFamous.get(i).setFollow_Check(followCheck);
	    }
	    Map<String, Object> responseData = new HashMap<>();
	    responseData.put("canFollow", canFollow);
	    responseData.put("mostFamous", mostFamous);
	    responseData.put("member_Id", member_Id);
	    
	    return ResponseEntity.ok(responseData);
	}

	// Profile �̵�
	@GetMapping("/profile")
	public String makeProfile(@RequestParam("member_Id") String member_Id, Model model, HttpSession session) {
		if(session.getAttribute("loginUser") == null) {
			model.addAttribute("message", "�α����� ���ּ���");
			return "login";
		} else {
			MemberVO member = memberService.getMember(member_Id);
			String loginUser_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
			
			FollowVO checkVo = new FollowVO();
			checkVo.setFollower(loginUser_Id);
			checkVo.setFollowing(member_Id);
			String followCheck = memberService.checkFollow(checkVo);
			member.setFollow_Check(followCheck);
			
			// �ȷο� �ȷο� ���� �ؿ� ���� ���׶�� �̹����� ä�� �뵵
			List<MemberVO> followers = memberService.getFollowers(member_Id);
			int followers_Size = followers.size();
			List<MemberVO> followings = memberService.getFollowings(member_Id);
			int followings_Size = followings.size();
			model.addAttribute("followers", followers);
			model.addAttribute("followers_Size", followers_Size);
			model.addAttribute("followings", followings);
			model.addAttribute("followings_Size", followings_Size);
			
			
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
			
			// 1. �̵��� ������ �������� ���Ǿ��̵�� ���ٸ� public���� ������� ��ü ��ȸ
	    	// 2. �̵��� ������ �������� ���Ǿ��̵�� �ٸ��ٸ� public = 'y' �Խñ۸� ��ȸ
	    	PostVO pvo = new PostVO();
	    	pvo.setMember_Id(member_Id);
	    	pvo.setSession_Id(session_Id);
	    	
			ArrayList<PostVO> postlist = postService.getMemberPost(pvo);
			
			// �� post_seq�� ���� ��۵��� ������ ����.
			Map<Integer, ArrayList<ReplyVO>> replymap = new HashMap<>();
			
			// �� post_seq�� ���� �ؽ��±׵��� ������ ����.
			Map<Integer, ArrayList<TagVO>> hashmap = new HashMap<>();
					
			// ���ĵ� postlist�� �ε��� ������ ��� ����Ʈ�� ������.
			// ���ÿ� �� �Խñ��� ���ƿ� ī��Ʈ�� ��� ī��Ʈ�� ����.
			for(int i = 0; i < postlist.size(); i++) {
				
				postlist.get(i).setPost_Content(postlist.get(i).getPost_Content().replace("\n", "<br>"));
				
				int post_Seq = postlist.get(i).getPost_Seq();
				
				// i��° �Խñ��� ��� ����Ʈ�� ����
				ArrayList<ReplyVO> replylist = replyService.getReplyPreview(post_Seq);
				// i��° �Խñ��� ��� ���ƿ� ���� üũ
				for(int k = 0; k < replylist.size(); k++) {
					ReplyVO voForReplyCheck = replylist.get(k);
					String realReply_Member_Id = replylist.get(k).getMember_Id();
					voForReplyCheck.setMember_Id(loginUser_Id);		
					String reply_LikeYN = replyService.getCheckReplyLike(voForReplyCheck);
					replylist.get(k).setReply_LikeYN(reply_LikeYN);
					replylist.get(k).setMember_Id(realReply_Member_Id);
				}
				
				// i��°�� �Խñ��� ����� map�� �����ϴ� �۾�
				replymap.put(i, replylist);
				
				// i��° �Խñ��� ���ƿ� ���� üũ
				PostVO voForLikeYN = new PostVO();
				voForLikeYN.setMember_Id(loginUser_Id);
				voForLikeYN.setPost_Seq(post_Seq);
				String post_LikeYN = postService.getLikeYN(voForLikeYN);
				int post_Like_Count = postService.getPost_Like_Count(post_Seq);
				postlist.get(i).setPost_Like_Count(post_Like_Count);
				postlist.get(i).setPost_LikeYN(post_LikeYN);
				
				// i��° �Խñ��� �ؽ��±� üũ    hashmap
				ArrayList<TagVO> hash = postService.getHashtagList(post_Seq);
				hashmap.put(post_Seq, hash);
				
			}
			
			// ��ü ȸ�� ������ �̹��� ��ȸ
			HashMap<String, String> profilemap = memberService.getMemberProfile();
			
			// ȭ�� ���� Hottest Feed
			List<PostVO> hottestFeed = postService.getHottestFeed();
			
			model.addAttribute("profileImage", profileImage);
			model.addAttribute("loginUser_Id", loginUser_Id);
			model.addAttribute("member", member);
			model.addAttribute("member_Id", member_Id);
			model.addAttribute("postlist", postlist);
			model.addAttribute("profileMap", profilemap);
			model.addAttribute("replyMap", replymap);
			model.addAttribute("hottestFeed", hottestFeed);
			model.addAttribute("hashMap", hashmap);
			model.addAttribute("alarmList", alarmList);
			model.addAttribute("alarmListSize", alarmListSize);
			
			return "profile";
		}
	} 
	
	
	// Profile �̵�
	@PostMapping("/profileInfinite")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> MoreProfilePost(@RequestBody Map<String, String> requestBody, Model model, HttpSession session) {
		
		String member_Id = requestBody.get("member_Id");
		
		String loginUser_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
		
		// 1. �̵��� ������ �������� ���Ǿ��̵�� ���ٸ� public���� ������� ��ü ��ȸ
    	// 2. �̵��� ������ �������� ���Ǿ��̵�� �ٸ��ٸ� public = 'y' �Խñ۸� ��ȸ
		PostVO pvo = new PostVO();
    	pvo.setMember_Id(member_Id);
    	pvo.setSession_Id(loginUser_Id);
    	
		ArrayList<PostVO> postlist = postService.getMemberPost(pvo);
		
		// �� post_seq�� ���� ��۵��� ������ ����.
		Map<Integer, ArrayList<ReplyVO>> replymap = new HashMap<>();
		
		// �� post_seq�� ���� �ؽ��±׵��� ������ ����.
		Map<Integer, ArrayList<TagVO>> hashmap = new HashMap<>();
				
		// ���ĵ� postlist�� �ε��� ������ ��� ����Ʈ�� ������.
		// ���ÿ� �� �Խñ��� ���ƿ� ī��Ʈ�� ��� ī��Ʈ�� ����.
		for(int i = 0; i < postlist.size(); i++) {
			
			int post_Seq = postlist.get(i).getPost_Seq();
			
			// i��° �Խñ��� ��� ����Ʈ�� ����
			ArrayList<ReplyVO> replylist = replyService.getReplyPreview(post_Seq);
			// i��° �Խñ��� ��� ���ƿ� ���� üũ
			for(int k = 0; k < replylist.size(); k++) {
				ReplyVO voForReplyCheck = replylist.get(k);
				String realReply_Member_Id = replylist.get(k).getMember_Id();
				voForReplyCheck.setMember_Id(loginUser_Id);		
				String reply_LikeYN = replyService.getCheckReplyLike(voForReplyCheck);
				replylist.get(k).setReply_LikeYN(reply_LikeYN);
				replylist.get(k).setMember_Id(realReply_Member_Id);
			}
			
			// i��°�� �Խñ��� ����� map�� �����ϴ� �۾�
			replymap.put(i, replylist);
			
			// i��° �Խñ��� ���ƿ� ���� üũ
			PostVO voForLikeYN = new PostVO();
			voForLikeYN.setMember_Id(loginUser_Id);
			voForLikeYN.setPost_Seq(post_Seq);
			String post_LikeYN = postService.getLikeYN(voForLikeYN);
			int post_Like_Count = postService.getPost_Like_Count(post_Seq);
			postlist.get(i).setPost_Like_Count(post_Like_Count);
			postlist.get(i).setPost_LikeYN(post_LikeYN);
			
			// i��° �Խñ��� �ؽ��±� üũ    hashmap
			ArrayList<TagVO> hash = postService.getHashtagList(post_Seq);
			hashmap.put(post_Seq, hash);			
		}
		
		// ��ü ȸ�� ������ �̹��� ��ȸ
		HashMap<String, String> profilemap = memberService.getMemberProfile();
		
		Map<String, Object> responseData = new HashMap<>();
		
		responseData.put("session_Id", loginUser_Id);
		responseData.put("postlist", postlist);
		responseData.put("profileMap", profilemap);
		responseData.put("replyMap", replymap);
		responseData.put("hashMap", hashmap);
	      
	    return ResponseEntity.ok(responseData);
	} 
	
	@GetMapping("trending_List")
	public ResponseEntity<Map<String, Object>> trending_List(HttpSession session, Model model) {
	      
	    String member_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
	      
	    List<PostVO> hottestFeed = postService.getHottestFeed();
	       
	    // �� post_seq�� ���� ��۵��� ������ ����.
	    Map<Integer, ArrayList<ReplyVO>> replymap = new HashMap<>();

	    // �� post_seq�� ���� �ؽ��±׵��� ������ ����.
	 	Map<Integer, ArrayList<TagVO>> hashmap = new HashMap<>();
	 	
	    // ���ĵ� postlist�� �ε��� ������ ��� ����Ʈ�� ������.
	    // ���ÿ� �� �Խñ��� ���ƿ� ī��Ʈ�� ��� ī��Ʈ�� ����.
	    for(int i=0; i<hottestFeed.size(); i++) {
	        // �ڽ�, �ȷ����� ������� �Խñ��� post_seq�� �ҷ��´�.
	        int post_Seq = hottestFeed.get(i).getPost_Seq();
	        
	        // i��° �Խñ��� ��� ����Ʈ�� ����
	        ArrayList<ReplyVO> replylist = replyService.getReplyPreview(post_Seq);
	        
	        // i��° �Խñ��� ��� ���ƿ� ���� üũ
	        for(int k = 0; k < replylist.size(); k++) {
	            ReplyVO voForReplyCheck = replylist.get(k);
	            String realReply_Member_Id = replylist.get(k).getMember_Id();
	            voForReplyCheck.setMember_Id(member_Id);
	            String reply_LikeYN = replyService.getCheckReplyLike(voForReplyCheck);
	            replylist.get(k).setReply_LikeYN(reply_LikeYN);
	            replylist.get(k).setMember_Id(realReply_Member_Id);
	        }
	         
	        // i��°�� �Խñ��� ����� map�� �����ϴ� �۾�
	        replymap.put(i, replylist);
	        
	        // i��° �Խñ��� ���ƿ� ���� üũ
	        PostVO voForLikeYN = new PostVO();
	        voForLikeYN.setMember_Id(member_Id);
	        voForLikeYN.setPost_Seq(post_Seq);
	        String post_LikeYN = postService.getLikeYN(voForLikeYN);
	        hottestFeed.get(i).setPost_LikeYN(post_LikeYN);
	        
	        // i��° �Խñ��� �ؽ��±� üũ    hashmap
	     	ArrayList<TagVO> hash = postService.getHashtagList(post_Seq);
	     	hashmap.put(post_Seq, hash);
	    }
	      	      
	    // ��ü ȸ�� ������ �̹��� ��ȸ
	    HashMap<String, String> profilemap = memberService.getMemberProfile();
	      
	    Map<String, Object> responseData = new HashMap<>();
	      
	    responseData.put("session_Id", member_Id);
	    responseData.put("trending_profileMap", profilemap);
	    responseData.put("trending_postList", hottestFeed);
	    responseData.put("trending_replyMap", replymap);
	    responseData.put("hashMap", hashmap);
	      
	    return ResponseEntity.ok(responseData);
	}
	 
	// Contact Form
	@GetMapping("/contact")
	public String contactPage(HttpSession session, Model model) {
		
		String member_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
		
		if(session.getAttribute("loginUser") == null) {
			model.addAttribute("message", "�α����� ���ּ���");
			return "login";
		} else {
			
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
			
			// ���� Hottest Feed
			List<PostVO> hottestFeed = postService.getHottestFeed();
			model.addAttribute("hottestFeed", hottestFeed);
			
		    // ���� �ۼ��� qna
			List<QnaVO> qnaList = qnaService.getMyQna(member_Id);
			
			for(int i = 0; i < qnaList.size(); i++) {
				qnaList.get(i).setQna_Message(qnaList.get(i).getQna_Message().replace("\n", "<br>"));
				if(qnaList.get(i).getQna_Done().equals("2")) {
					qnaList.get(i).setQna_Answer(qnaList.get(i).getQna_Answer().replace("\n", "<br>"));
				} else {
					
				}
			}
			
			model.addAttribute("profileImage", profileImage);
			model.addAttribute("qnaList", qnaList);
			model.addAttribute("alarmList", alarmList);
			model.addAttribute("alarmListSize", alarmListSize);
			
			return "contact";
			
		}
	}
	
	// Contact Form
	@GetMapping("/alarmContact")
	public String alarmContact(HttpSession session, Model model, @RequestParam("alarm_Seq") int alarm_Seq) {
		
		String member_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
		
		if(session.getAttribute("loginUser") == null) {
			model.addAttribute("message", "�α����� ���ּ���");
			return "login";
		} else {
			
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
			
	    	model.addAttribute("alarmList", alarmList);
			model.addAttribute("alarmListSize", alarmListSize);
	    	
			// ���� Hottest Feed
			List<PostVO> hottestFeed = postService.getHottestFeed();
			model.addAttribute("hottestFeed", hottestFeed);
		    // ���� �ۼ��� qna
			List<QnaVO> qnaList = qnaService.getMyQna(member_Id);
			model.addAttribute("qnaList", qnaList);
			
			
			return "contact";
		}
	}
	
	@PostMapping("/qna")
	public String qnaSending(@RequestParam("member_Id") String member_Id,
            				 @RequestParam("qna_Title") String qna_Title,
            				 @RequestParam("qna_Message") String qna_Message, HttpSession session, Model model) {
		
		if(session.getAttribute("loginUser") == null) {
			model.addAttribute("message", "�α����� ���ּ���");
			return "login";
		} else {
			QnaVO vo = new QnaVO();
			int qna_Seq = qnaService.checkMaxSeq() + 1;
			vo.setQna_Seq(qna_Seq);
			vo.setMember_Id(member_Id);
			
			vo.setQna_Title(qna_Title.replace("<", "{"));
			vo.setQna_Message(qna_Message.replace("<", "{"));
			System.out.println("/qna ��ƿ� qnaVO : " + vo);
			qnaService.insertQna(vo);
			
			return "redirect:contact";
		}
	}
	
	// index ������ �ε�
	@GetMapping("/feedInfinite")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> feedInfinite(HttpSession session) {

		String member_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();

								/* index�������� �����ǵ� �κ� */
		// �ڽ�, �ȷ����� ������� �Խñ��� ��ºκ�
		ArrayList<PostVO> postlist = postService.getlistPost(member_Id);
		for(int i = 0; i < postlist.size(); i++) {
			postlist.get(i).setPost_Content(postlist.get(i).getPost_Content().replace("\n", "<br>"));
		}

		// �� post_seq�� ���� ��۵��� ������ ����.
		Map<Integer, ArrayList<ReplyVO>> replymap = new HashMap<>();
		
		// �� post_seq�� ���� �ؽ��±׵��� ������ ����.
		Map<Integer, ArrayList<TagVO>> hashmap = new HashMap<>();

		// ���ĵ� postlist�� �ε��� ������ ��� ����Ʈ�� ������.
		// ���ÿ� �� �Խñ��� ���ƿ� ī��Ʈ�� ��� ī��Ʈ�� ����.
		for(int i=0; i<postlist.size(); i++) {
			// �ڽ�, �ȷ����� ������� �Խñ��� post_seq�� �ҷ��´�.
			int post_Seq = postlist.get(i).getPost_Seq();

			// i��° �Խñ��� ��� ����Ʈ�� ����
			ArrayList<ReplyVO> replylist = replyService.getReplyPreview(post_Seq);			
			
			// i��° �Խñ��� ��� ���ƿ� ���� üũ
			for(int k = 0; k < replylist.size(); k++) {
				ReplyVO voForReplyCheck = replylist.get(k);
				String realReply_Member_Id = replylist.get(k).getMember_Id();
				voForReplyCheck.setMember_Id(member_Id);
				String reply_LikeYN = replyService.getCheckReplyLike(voForReplyCheck);
				replylist.get(k).setReply_LikeYN(reply_LikeYN);
				replylist.get(k).setMember_Id(realReply_Member_Id);
			}

			// i��°�� �Խñ��� ����� map�� �����ϴ� �۾�
			replymap.put(i, replylist);

			// i��° �Խñ��� ���ƿ� ���� üũ
			PostVO voForLikeYN = new PostVO();
			voForLikeYN.setMember_Id(member_Id);
			voForLikeYN.setPost_Seq(post_Seq);
			String post_LikeYN = postService.getLikeYN(voForLikeYN);
			postlist.get(i).setPost_LikeYN(post_LikeYN);

			// i��° �Խñ��� �ؽ��±� üũ    hashmap			
			ArrayList<TagVO> hash = postService.getHashtagList(post_Seq);
			hashmap.put(post_Seq, hash);
		}

		// ��ü ȸ�� ������ �̹��� ��ȸ
		HashMap<String, String> profilemap = memberService.getMemberProfile();

		Map<String, Object> responseData = new HashMap<>();
		
		responseData.put("profileMap", profilemap);
		responseData.put("postList", postlist);
		responseData.put("replyMap", replymap);
		responseData.put("session_Id", member_Id);
		responseData.put("hashMap", hashmap);

		return ResponseEntity.ok(responseData);

	}
	
	@PostMapping("/deleteAlarm")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> deleteAlarm(@RequestBody Map<String, Integer> requestBody){
		
		int alarm_Seq = requestBody.get("alarm_Seq");
		
		alarmService.deleteAlarm(alarm_Seq);
		
		Map<String, Object> responseData = new HashMap<>();
		
		responseData.put("message", "�˶� ���� ����");
		
		return ResponseEntity.ok(responseData);
	}
	
	@GetMapping("/alarmIndex")
	public String alarmIndex(Model model, HttpSession session,@RequestParam("post_Seq") int P_Seq, @RequestParam("alarm_Seq") int alarm_Seq) {
		
		if(session.getAttribute("loginUser") == null) {
			model.addAttribute("message", "�α����� ���ּ���");
			return "login";
		} else {
			
			// Ŭ���� �˶��� ����
	    	alarmService.deleteAlarm(alarm_Seq);
	    	
									/* index�������� �ȷο� �κ� */
			String member_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
			
			List<MemberVO> recommendMember = memberService.getRecommendMember(member_Id);
			
	    	List<PostVO> hottestFeed = postService.getHottestFeed();
			
	    	// �˶� ����Ʈ�� ��� �κ�
	    	List<AlarmVO> alarmList = alarmService.getAllAlarm(member_Id);
	    	
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
			
									/* index�������� �����ǵ� �κ� */
			// �ڽ�, �ȷ����� ������� �Խñ��� ��ºκ�
			ArrayList<PostVO> postlist = new ArrayList<PostVO>();
			
			postlist.add(postService.selectPostDetail(P_Seq));
			
			// �� post_seq�� ���� ��۵��� ������ ����.
			Map<Integer, ArrayList<ReplyVO>> replymap = new HashMap<>();
			
			// �� post_seq�� ���� �ؽ��±׵��� ������ ����.
			Map<Integer, ArrayList<TagVO>> hashmap = new HashMap<>();
			
			// ���ĵ� postlist�� �ε��� ������ ��� ����Ʈ�� ������.
			// ���ÿ� �� �Խñ��� ���ƿ� ī��Ʈ�� ��� ī��Ʈ�� ����.
			for(int i=0; i<postlist.size(); i++) {
				// �ڽ�, �ȷ����� ������� �Խñ��� post_seq�� �ҷ��´�.
				int post_Seq = postlist.get(i).getPost_Seq();
				
				// i��° �Խñ��� ��� ����Ʈ�� ����
				ArrayList<ReplyVO> replylist = replyService.getReplyPreview(post_Seq);
				// i��° �Խñ��� ��� ���ƿ� ���� üũ
				for(int k = 0; k < replylist.size(); k++) {
					ReplyVO voForReplyCheck = replylist.get(k);
					String realReply_Member_Id = replylist.get(k).getMember_Id();
					voForReplyCheck.setMember_Id(member_Id);
					String reply_LikeYN = replyService.getCheckReplyLike(voForReplyCheck);
					replylist.get(k).setReply_LikeYN(reply_LikeYN);
					replylist.get(k).setMember_Id(realReply_Member_Id);
				}
				
				// i��°�� �Խñ��� ����� map�� �����ϴ� �۾�
				replymap.put(i, replylist);
				
				// i��° �Խñ��� ���ƿ� ���� üũ
				PostVO voForLikeYN = new PostVO();
				voForLikeYN.setMember_Id(member_Id);
				voForLikeYN.setPost_Seq(post_Seq);
				String post_LikeYN = postService.getLikeYN(voForLikeYN);
				postlist.get(i).setPost_LikeYN(post_LikeYN);
				
				// i��° �Խñ��� �ؽ��±� üũ    hashmap
				ArrayList<TagVO> hash = postService.getHashtagList(post_Seq);
				hashmap.put(post_Seq, hash);
			}
			
			// ��ü ȸ�� ������ �̹��� ��ȸ
			HashMap<String, String> profilemap = memberService.getMemberProfile();
			
			model.addAttribute("alarmList", alarmList);
			model.addAttribute("alarmListSize", alarmListSize);
			model.addAttribute("profileMap", profilemap);
			model.addAttribute("postList", postlist);
			model.addAttribute("replyMap", replymap);
			model.addAttribute("recommendMember", recommendMember);
			model.addAttribute("hottestFeed", hottestFeed);	
			model.addAttribute("member_Id", member_Id);
			model.addAttribute("hashMap", hashmap);
		
		return "index";
		}
	
	}
	
	@GetMapping("/deleteQna")
	public String deleteQna(@RequestParam("qna_Seq") int qna_Seq, HttpSession session, Model model) {
		if(session.getAttribute("loginUser") == null) {
			model.addAttribute("message", "�α����� ���ּ���");
			return "login";
		} else {
			qnaService.deleteQna(qna_Seq);
			
			return "redirect:contact";
		}
	}
	
}
