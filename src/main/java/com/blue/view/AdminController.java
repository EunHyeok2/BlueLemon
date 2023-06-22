package com.blue.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.blue.dto.TagVO;
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
		
		if(((MemberVO) session.getAttribute("loginUser")).getMember_Id().equals("admin")) {

			return "admin_Index";
		} else {
			
			model.addAttribute("message", "�����ڷ� �α��� ���ּ���");
			
			return "login";			
		}
		
	}
	

	@GetMapping("/member_Table")
	public String member_Table(Model model, HttpSession session) throws ParseException {
		
		if(((MemberVO) session.getAttribute("loginUser")).getMember_Id().equals("admin")) {

			List<MemberVO> allMember = memberService.getAllMember();
			for(int i = 0; i < allMember.size(); i++) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String joinDate = sdf.format(allMember.get(i).getMember_Join_Date());
				allMember.get(i).setMember_Join_Date_String(joinDate);
			}
			
			model.addAttribute("allMember", allMember);
			
			return "member_Table";
		} else {
			model.addAttribute("message", "�����ڷ� �α��� ���ּ���");
			return "login";			
		}		
	}
	
	@GetMapping("/member_Delete_By_Admin")
	public String member_Delete_By_Admin(String member_Id) {
		postService.deleteOneMemsTag(member_Id);
		memberService.deleteMember(member_Id);
		return "redirect:member_Table";
	}
	
	@GetMapping("/post_Table")
	public String post_Table(Model model, HttpSession session) {
		if(((MemberVO) session.getAttribute("loginUser")).getMember_Id().equals("admin")) {
			// ��� ȸ���� �Խñ��� ��ºκ�
			ArrayList<PostVO> postlist = postService.getAllPost();
			//System.out.println("�Խñ� " + postlist.size() + "�� �ҷ���");
			
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
				//System.out.println("replylist�� ����");
				//System.out.println("[�̸����� ��� - 1] replylist�� �ش� �Խñ��� ��� 3���� ������ / ���� �ش� ��� ���ƿ� ������ üũ�� �ȵ�");
				//System.out.println("[�̸����� ��� - 1.5] replylist size : " + replylist.size());
				
				
				// i��°�� �Խñ��� ����� map�� �����ϴ� �۾�
				replymap.put(i, replylist);
				//System.out.println(i + "��° �Խñ� ��� ����" + replymap.get(i));			
				
				
				// i��° �Խñ��� �ؽ��±� üũ    hashmap
				ArrayList<TagVO> hash = postService.getHashtagList(post_Seq);
				hashmap.put(post_Seq, hash);
			}
			
			// ��ü ȸ�� ������ �̹��� ��ȸ
			HashMap<String, String> profilemap = memberService.getMemberProfile();
			//System.out.println("��ü ȸ�� ������: " + profilemap);
			
			int postListSize = postlist.size();
			
			model.addAttribute("profileMap", profilemap);
			model.addAttribute("postList", postlist);
			model.addAttribute("hashMap", hashmap);
			model.addAttribute("postListSize", postListSize);
		
			return "post_Table";
		} else {
			model.addAttribute("message", "�����ڷ� �α��� ���ּ���");
			return "login";			
		}		
		

	}

	
	@GetMapping("/qna_Table")
	public String qna_Table(Model model, HttpSession session) {
		if(((MemberVO) session.getAttribute("loginUser")).getMember_Id().equals("admin")) {

			ArrayList<PostVO> postlist = postService.getAllPost();
			return "qna_Table";
		} else {
			model.addAttribute("message", "�����ڷ� �α��� ���ּ���");
			return "login";			
		}
	}
}
