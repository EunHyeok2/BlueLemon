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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.blue.dto.MemberVO;
import com.blue.dto.PostVO;
import com.blue.dto.QnaVO;
import com.blue.dto.ReplyVO;
import com.blue.service.MemberService;
import com.blue.service.PostService;
import com.blue.service.QnaService;
import com.blue.service.ReplyService;

@Controller
//��Ʈ�ѷ����� 'loginUser', 'profileMap' �̶�� �̸����� �� ��ü�� �����Ҷ� ���ǿ� ���ÿ� �����Ѵ�.
@SessionAttributes({"loginUser", "profileMap"})	
public class AdminController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private PostService postService;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private QnaService qnaService;
	
	
	// index ������ �ε�
	@GetMapping("/admin_Index")
	public String getRecommendMember(Model model, HttpSession session) {
		
		if(session.getAttribute("loginUser") == null) {
			model.addAttribute("message", "�α����� ���ּ���");
			return "login";
		} else {
			
			List<MemberVO> allMember = memberService.getAllMember();
			
			ArrayList<PostVO> postlist = postService.getAllPost();
			
//			Map<Integer, ArrayList<ReplyVO>> replymap = new HashMap<>();
//			
//			// ���ĵ� postlist�� �ε��� ������ ��� ����Ʈ�� ������.
//			// ���ÿ� �� �Խñ��� ���ƿ� ī��Ʈ�� ��� ī��Ʈ�� ����.
//			for(int i=0; i<postlist.size(); i++) {
//				// �ڽ�, �ȷ����� ������� �Խñ��� post_seq�� �ҷ��´�.
//				int post_Seq = postlist.get(i).getPost_Seq();
//				
//				// i��° �Խñ��� ��� ����Ʈ�� ����
//				ArrayList<ReplyVO> replylist = replyService.getReplyPreview(post_Seq);
//				//System.out.println("replylist�� ����");
//				//System.out.println("[�̸����� ��� - 1] replylist�� �ش� �Խñ��� ��� 3���� ������ / ���� �ش� ��� ���ƿ� ������ üũ�� �ȵ�");
//				//System.out.println("[�̸����� ��� - 1.5] replylist size : " + replylist.size());
//				// i��° �Խñ��� ��� ���ƿ� ���� üũ
//				for(int k = 0; k < replylist.size(); k++) {
//					ReplyVO voForReplyCheck = replylist.get(k);
//					String realReply_Member_Id = replylist.get(k).getMember_Id();
//					voForReplyCheck.setMember_Id(member_Id);
//					//System.out.println("[�̸����� ��� - 2] ��� ���ƿ� ������ Ȯ���Ϸ� ����");				
//					String reply_LikeYN = replyService.getCheckReplyLike(voForReplyCheck);
//					replylist.get(k).setReply_LikeYN(reply_LikeYN);
//					//System.out.println("[�̸����� ��� - 5] DAO���� ���Ϲ޾Ƽ� set����. �ش� ��� ���ƿ� ���� ? " + replylist.get(k).getReply_LikeYN());
//					replylist.get(k).setMember_Id(realReply_Member_Id);
//				}
//				
//				// i��°�� �Խñ��� ����� map�� �����ϴ� �۾�
//				replymap.put(i, replylist);
//				//System.out.println(i + "��° �Խñ� ��� ����" + replymap.get(i));			
//				
//				// i��° �Խñ��� ���ƿ� ���� üũ
//				PostVO voForLikeYN = new PostVO();
//				voForLikeYN.setMember_Id(member_Id);
//				voForLikeYN.setPost_Seq(post_Seq);
//				//System.out.println("[���ƿ� ���� Ȯ�� - 0] �Խñ� ��ȣ : " + post_Seq);
//				//System.out.println("[���ƿ� ���� Ȯ�� - 1] Setting �� post_LikeYN = " + postlist.get(i).getPost_LikeYN());
//				String post_LikeYN = postService.getLikeYN(voForLikeYN);
//				postlist.get(i).setPost_LikeYN(post_LikeYN);
//				//System.out.println("[���ƿ� ���� Ȯ�� - 4] Setting �� post_LikeYN = " + postlist.get(i).getPost_LikeYN());
//				
//			}
//			
//			// ��ü ȸ�� ������ �̹��� ��ȸ
//			HashMap<String, String> profilemap = memberService.getMemberProfile();
//			//System.out.println("��ü ȸ�� ������: " + profilemap);
//			
//			model.addAttribute("profileMap", profilemap);
//			model.addAttribute("postList", postlist);
//			model.addAttribute("replyMap", replymap);
//			model.addAttribute("recommendMember", recommendMember);
//			model.addAttribute("hottestFeed", hottestFeed);	
			return "admin_Index";
		}
	}
	

	@GetMapping("/member_Table")
	public String member_Table() {
		return "member_Table";
	}
	
	@GetMapping("/post_Table")
	public String post_Table() {
		return "post_Table";
	}
	
	@GetMapping("/qna_Table")
	public String qna_Table() {
		return "qna_Table";
	}
}
