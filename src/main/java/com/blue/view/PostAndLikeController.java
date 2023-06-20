package com.blue.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.blue.dto.LikeVO;
import com.blue.dto.MemberVO;
import com.blue.dto.PostVO;
import com.blue.dto.ReplyVO;
import com.blue.service.PostService;
import com.blue.service.ReplyService;

@Controller
//��Ʈ�ѷ����� 'loginUser', 'profileMap' �̶�� �̸����� �� ��ü�� �����Ҷ� ���ǿ� ���ÿ� �����Ѵ�.
@SessionAttributes({"loginUser", "profileMap"})	
public class PostAndLikeController {

	@Autowired
	private PostService postService;
	@Autowired
	private ReplyService replyService;
	
	
	// ���ƿ� ����(PostMapping)
	@PostMapping("/changeLike")
	@ResponseBody
	public String changeLike(@RequestBody Map<String, Integer> requestBody, HttpSession session) {
		//System.out.println("[�Խñ� ���ƿ� - 3] PostMapping���� /changeLike �� Map �������� ��ƿ�.");
	    int post_Seq = requestBody.get("post_Seq");
	    String member_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
	    //System.out.println("[�Խñ� ���ƿ� - 4] ���ǿ��� �α��� ���� ���̵� �޾ƿ� member_Id = " + member_Id);
	    try {
	        LikeVO vo = new LikeVO();
	        vo.setMember_Id(member_Id);
	        vo.setPost_Seq(post_Seq);

	        // System.out.println("[�Խñ� ���ƿ� - 5] LikeVO��ü vo�� ������ changeLike() ��û ����");
	        postService.changeLike(vo);
	        // System.out.println("[�Խñ� ���ƿ� - 9] changeLike �ϰ� js�� success ����");
	        return "success";
	    } catch (Exception e) {
	    	// System.out.println("[���ƿ� - 5 - catch] JSON �Ľ� ������ ���");
	        // JSON �Ľ� ���� ó��
	        e.printStackTrace();
	        return "error";
	    }
	}
	
	// �̸����� ��� ���ƿ� ����
	@PostMapping("/changeReplyLike")
	@ResponseBody
	public String changeReplyLike(@RequestBody Map<String, Integer> requestBody, HttpSession session) {
		//System.out.println("[�̸����� ��� ���ƿ� - 3] PostMapping���� /changeReplyLike �� Map �������� ��ƿ�.");
	    int post_Seq = requestBody.get("post_Seq");
	    int reply_Seq = requestBody.get("reply_Seq");
	    String member_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
	    //System.out.println("[�̸����� ��� ���ƿ� - 4] ���ǿ��� �α��� ���� ���̵� �޾ƿ� member_Id = " + member_Id);
	    try {
	        LikeVO vo = new LikeVO();
	        vo.setMember_Id(member_Id);
	        vo.setPost_Seq(post_Seq);
	        vo.setReply_Seq(reply_Seq);
	        
	        //System.out.println("[�Խñ� ���ƿ� - 5] LikeVO��ü vo�� ������ changeLike() ��û ����");
	        replyService.changeReplyLike(vo);
	        //System.out.println("[�Խñ� ���ƿ� - 9] changeLike �ϰ� js�� success ����");
	        return "success";
	    } catch (Exception e) {
	    	//System.out.println("[���ƿ� - 5 - catch] JSON �Ľ� ������ ���");
	        // JSON �Ľ� ���� ó��
	        e.printStackTrace();
	        return "error";
	    }
	}
	
	// �Խñ� �ۼ�
	@PostMapping("/insertPost")
	public String insertPost(PostVO vo, @RequestParam("uploadImgs") MultipartFile[] uploadImgs) {
		
		//System.out.println("insertPost vo : " + vo);
		//System.out.println("insertPost file : " + uploadImgs.length);
		
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
		ArrayList<ReplyVO> replyList = replyService.getListReply(post_Seq);
		
        // 3. ��ü ȸ�� ������ �̹��� ��ȸ
		HashMap<String, String> profileMap = (HashMap<String, String>) session.getAttribute("profileMap");
		//System.out.println("���ǰ�ü�� profileMap�� �ҷ��� : " + profileMap);
	
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
			String reply_LikeYN = replyService.getCheckReplyLike(replyLikeYN);
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
		ArrayList<ReplyVO> replyList = replyService.getListReply(post_Seq);
		
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
			String reply_LikeYN = replyService.getCheckReplyLike(replyLikeYN);
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
		//System.out.println("��Ʈ�ѷ��� ����Ʈ ������: " + post_Seq + ", ��Ʈ�ѷ��� ���ö��� ������: " + reply_Content);
		
		// 0. ajax��û�� ���� response�� ������ ���� Map ���� ����
		Map<String, Object> dataMap = new HashMap<>();
		
		// 1. ���ǰ�ü�� 'loginUfser'��ü�� MemberVO ��ü�� ���� �Ľ��ؼ� getter �޼ҵ��� getMember_Id�� ȣ���� ���̵� �����´�.
		String member_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
		//System.out.println("���� ���̵�: " + member_Id);

		// 2. insert�������� �Ķ���� ��ü�� ���� ��������
		ReplyVO rep = new ReplyVO();
		rep.setMember_Id(member_Id);
		rep.setPost_Seq(post_Seq);
		rep.setReply_Content(reply_Content);
		//System.out.println("�μ�Ʈ �������� ���� ��ü ���빰: " + rep);
		
		// 3. insert������ ����
		replyService.insertReply(rep);
		
		// 4. �Խñ��� ��۸���Ʈ�� ����ϱ� ���� ArrayList<ReplyVO> �� ����
		ArrayList<ReplyVO> replyList = replyService.getListReply(post_Seq);
		
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
			String reply_LikeYN = replyService.getCheckReplyLike(replyLikeYN);
			replyList.get(i).setReply_LikeYN(reply_LikeYN);
		}
		
		// 8. ajax�� ���伺��(success)�� response�� �� ���� ����
		dataMap.put("postInfo", postInfo);
		dataMap.put("replies", replyList);
		dataMap.put("profile", profileMap);
		
		return dataMap;
	}
}
