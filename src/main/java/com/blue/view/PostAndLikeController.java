package com.blue.view;

import java.io.File;
import java.io.IOException;
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
import com.blue.dto.TagVO;
import com.blue.service.MemberService;
import com.blue.service.PostService;
import com.blue.service.ReplyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
//��Ʈ�ѷ����� 'loginUser', 'profileMap' �̶�� �̸����� �� ��ü�� �����Ҷ� ���ǿ� ���ÿ� �����Ѵ�.
@SessionAttributes({"loginUser", "profileMap"})	
public class PostAndLikeController {

	@Autowired
	private PostService postService;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private MemberService memberService;
	
	
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
	@PostMapping("insertPost")
	// @ResponseBody
	public String insertPost(PostVO vo, @RequestParam(value="attach_file", required = false) MultipartFile[] attach_file,
							@RequestParam(value = "fileList[]", required = false) String[] fileList,
						     HttpSession session) {
		
		//System.out.println("==================================�Խñ� �ۼ�=====================================");
		//System.out.println("insertPost vo : " + vo);
		
		// MAX(post_Seq) + 1
		int nextSeq = postService.postSeqCheck();
		// nextval�� ��������ʰ� ������ �������� �����ϱ����� postVO�� set���ش�
		vo.setPost_Seq(nextSeq);
		
		// �ٲ� ���������� ��ºκ�
		Map<Integer, Integer> index = new HashMap<>();
		
		// 1. ����ڰ� �̹����� ÷������ �� 
		if (attach_file != null && attach_file.length > 0) {
			//System.out.println("insertPost file���� : " + attach_file.length);
			
			// 1. �̹��� ���ε� ó�� �κ�
			String folderPath = session.getServletContext().getRealPath("/WEB-INF/template/img/uploads/post/");
			// 1. ���ε��� �̹��� ���� vo ��ü�� ����
			int imgCount = attach_file.length;
			vo.setPost_Image_Count(imgCount);
			
			if(imgCount == 0) { // �̹����� ���ε� ���� �ʾ�����
				//System.out.println("�̹��� ����");
				
			} else if (imgCount == 1 ){ // 1���� �̹����� ���ε� ������
				//System.out.println("�̹��� " + imgCount + " ��");
				MultipartFile file = attach_file[0];
				String fileName = nextSeq + "-" + 1 + ".png";
				//System.out.println(fileName);
				//System.out.println("File Name: " + file.getOriginalFilename());
				try {
		            // ������ ������ ��ο� ����
		            file.transferTo(new File(folderPath + fileName));
		            //System.out.println("���� ���� ����");
		        } catch (IOException e) {
		            e.printStackTrace();
		            //System.out.println("���� ���� ����");
		        }
				
			} else { // 2�� �̻��� �̹����� ���ε� ������
				//System.out.println("�̹��� " + imgCount + " �� �̻�");
				
				if(fileList != null) { // �̹��� ���� ������ ���� ���
					//System.out.println("fileList.length : " + fileList.length);
					for(int k=0; k < fileList.length; k++) {
						//System.out.println("fileListó����");
						String file = fileList[k];
				        int aa = Integer.parseInt(file.substring(4));
				        index.put(k+1 , aa);
				        //System.out.println("�ε�������� ���� : " + index.get(k+1));
				    }
					//System.out.println("�ε���Map������ : " + index.size());
					
					for(int i=1; i < (imgCount+1); i++) {
						//System.out.println("�̹��� " + imgCount + " ��");
						int real = index.get(i);
						//System.out.println("real : " + real);
						MultipartFile file = attach_file[real];
						String fileName = nextSeq + "-" + i + ".png";
						//System.out.println(fileName);
						//System.out.println("File Name: " + file.getOriginalFilename());
						
						try {
				            // ������ ������ ��ο� ����
				            file.transferTo(new File(folderPath + fileName));
				           //System.out.println("���� ���� ����");
				        } catch (IOException e) {
				            e.printStackTrace();
				            //System.out.println("���� ���� ����");
				        }
					}
				} else {  // �̹��� ���� ������ ���� ���
					for(int j=1; j<imgCount+1; j++) {
						index.put(j, j);
						//System.out.println("�ε�������� ���� : " + index.get(j));
					}
					//System.out.println("�ε���Map������ : " + index.size());
					
					for(int i=1; i < (imgCount+1); i++) {
						//System.out.println("�̹��� " + imgCount + " ��");
						int real = (index.get(i)-1);
						//System.out.println("real : " + real);
						MultipartFile file = attach_file[real];
						String fileName = nextSeq + "-" + i + ".png";
						//System.out.println(fileName);
						//System.out.println("File Name: " + file.getOriginalFilename());
						
						try {
				            // ������ ������ ��ο� ����
				            file.transferTo(new File(folderPath + fileName));
				            //System.out.println("���� ���� ����");
				        } catch (IOException e) {
				            e.printStackTrace();
				            //System.out.println("���� ���� ����");
				        }
					}
				}
			}
		}
		
		// 2. �Խñ��� �������θ� üũ���� �ʾҴٸ� n������ set
		if (vo.getPost_Public() == "") {
			vo.setPost_Public("n");
		}
		
		// 3. �μ�Ʈ ó��
		postService.insertPost(vo);
		
		String hashTag = vo.getPost_Hashtag();
		if (hashTag != null && !hashTag.isEmpty()) {
			// 4. �ؽ��±� ó�� �κ�
			
			try { // 2-1. ����ڰ� �Է��� �ؽ��±׵��� json���·� �޾ƿͼ� ����� �� �ְ� �Ľ��ϴ� �۾�
	            ObjectMapper objectMapper = new ObjectMapper();
	            JsonNode jsonNode = objectMapper.readTree(hashTag);
	
	            for (JsonNode node : jsonNode) {
	            	// n��° �ؽ��±� ���� 
	                String value = node.get("value").asText();
	                	
	                TagVO tvo = new TagVO();
	                tvo.setPost_Seq(nextSeq);
	                tvo.setTag_Content(value);
	                postService.insertTag(tvo);
	            }
	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	        }
		}

		return "/index";
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
	
	// �Խñ� ����
	@GetMapping("/postDelete")
	public String postDelete(@RequestParam(value="post_Seq") int post_Seq) {
		
		postService.deletePost(post_Seq);
		
		return "redirect:/index";
	}

	// ������ ���������� �Խñ� �󼼺���
	@GetMapping("/post_Detail")
	public String post_detail(Model model, int post_Seq) {
		System.out.println("������ �Ѱ� ���� post_Seq �� :" + post_Seq);
		
		
		// PostVO �� post_seq�� ���� �Խñ��� ��´�.
		PostVO PostDetail = postService.selectPostDetail(post_Seq);
		System.out.println("�ش� �������� �Խñ� :" + PostDetail);
		// ReplyVO �� post_seq�� ���� ��� ��´�.
		ArrayList<ReplyVO> replyList = replyService.getListReply(post_Seq);
		System.out.println("�ش� �������� ��� : " + replyList);
		// TagVO �� post_seq�� ���� �ؽ��±׸� ��´�.
		ArrayList<TagVO> hash = postService.getHashtagList(post_Seq); 
		System.out.println("�ش� �������� �ؽ��±� : " + hash);
		
		model.addAttribute("post", PostDetail);
		model.addAttribute("reply", replyList);
		model.addAttribute("hash", hash);
		
		
		return "post_Detail";
	}
	
	// �Խñ� ���� (�����ڿ� -> ���� �� ������ �������� �ӹ�)
	@GetMapping("/deletePost")
	public String deletePost(@RequestParam(value="post_Seq") int post_Seq) {
		
		postService.deletePost(post_Seq);
		
		return "redirect:post_Table";
	}
	
	@GetMapping("/search_HashTag")
	public String search_HashTag(@RequestParam(value="tag_Content") String hashTag, HttpSession session, Model model){

		if(session.getAttribute("loginUser") == null) {
			//System.out.println("���ǰ� ����");
			model.addAttribute("message", "�α����� ���ּ���");
			return "login";
		} else {


			/* index�������� �ȷο� �κ� */
			//System.out.println("[�����õ - 1] �α��� �� index ��û�ϸ� GetMapping���� ��ƿ��� ������ loginUser���� Id �̾Ƽ� member_Id�� ����");
			String member_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();

			//System.out.println("[�����õ - 2] member_Id�� ������ memberService�� getRecommendMember ��û");		
			List<MemberVO> recommendMember = memberService.getRecommendMember(member_Id);
			//System.out.println("[�����õ - 5] DAO���� ��õ ����Ʈ�� �޾ƿͼ� List�� �����ϰ� model�� �ø��� index.jsp ȣ��");

			//System.out.println("[�α�� - 1] �α��� �� index ��û�ϸ� GetMapping���� ��ƿ�");
			//System.out.println("[�α�� - 2] postService�� getHottestFeed ��û");
	    	List<PostVO> hottestFeed = postService.getHottestFeed();
	    	//System.out.println("[�α�� - 5] DAO���� hottestFeed �޾ƿͼ� List�� �����ϰ� model�� �ø�");


			/* index�������� �����ǵ� �κ� */
			// �ڽ�, �ȷ����� ������� �Խñ��� ��ºκ�
			ArrayList<PostVO> postlist = postService.getHashTagPost(hashTag);
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
				// i��° �Խñ��� ��� ���ƿ� ���� üũ
				for(int k = 0; k < replylist.size(); k++) {
					ReplyVO voForReplyCheck = replylist.get(k);
					String realReply_Member_Id = replylist.get(k).getMember_Id();
					voForReplyCheck.setMember_Id(member_Id);
					//System.out.println("[�̸����� ��� - 2] ��� ���ƿ� ������ Ȯ���Ϸ� ����");				
					String reply_LikeYN = replyService.getCheckReplyLike(voForReplyCheck);
					replylist.get(k).setReply_LikeYN(reply_LikeYN);
					//System.out.println("[�̸����� ��� - 5] DAO���� ���Ϲ޾Ƽ� set����. �ش� ��� ���ƿ� ���� ? " + replylist.get(k).getReply_LikeYN());
					replylist.get(k).setMember_Id(realReply_Member_Id);
				}

				// i��°�� �Խñ��� ����� map�� �����ϴ� �۾�
				replymap.put(i, replylist);
				//System.out.println(i + "��° �Խñ� ��� ����" + replymap.get(i));			

				// i��° �Խñ��� ���ƿ� ���� üũ
				PostVO voForLikeYN = new PostVO();
				voForLikeYN.setMember_Id(member_Id);
				voForLikeYN.setPost_Seq(post_Seq);
				//System.out.println("[���ƿ� ���� Ȯ�� - 0] �Խñ� ��ȣ : " + post_Seq);
				//System.out.println("[���ƿ� ���� Ȯ�� - 1] Setting �� post_LikeYN = " + postlist.get(i).getPost_LikeYN());
				String post_LikeYN = postService.getLikeYN(voForLikeYN);
				postlist.get(i).setPost_LikeYN(post_LikeYN);
				//System.out.println("[���ƿ� ���� Ȯ�� - 4] Setting �� post_LikeYN = " + postlist.get(i).getPost_LikeYN());

				// i��° �Խñ��� �ؽ��±� üũ    hashmap
				ArrayList<TagVO> hash = postService.getHashtagList(post_Seq);
				hashmap.put(post_Seq, hash);
			}

			int postListSize = postlist.size();

			// ��ü ȸ�� ������ �̹��� ��ȸ
			HashMap<String, String> profilemap = memberService.getMemberProfile();
			//System.out.println("��ü ȸ�� ������: " + profilemap);

			List<MemberVO> searchFollow = memberService.searchMembers(hashTag);
		    //System.out.println("[PEOPLE �� - 4] SEARCH PEOPLE LIST�� �޾ƿ��� ����");

			List<MemberVO> myFollowing = memberService.getFollowings(member_Id);

			for(int i=0; i<myFollowing.size(); i++) {
				for(int j=0; j<searchFollow.size(); j++) {
					if(myFollowing.get(i).getMember_Id().equals(searchFollow.get(j).getMember_Id())) {
						searchFollow.get(j).setBothFollow(1);
					}
				}
			}

		    List<MemberVO> mostFamous = memberService.getMostFamousMember();
		    //System.out.println("[PEOPLE �� - 7] MOST FAMOUS LIST�� �޾ƿ��� ����");

		    int searchFollowSize = searchFollow.size();

		    model.addAttribute("searchFollow", searchFollow);
		    model.addAttribute("mostFamous", mostFamous);
		    model.addAttribute("searchFollowSize", searchFollowSize);

			model.addAttribute("hashTag", hashTag);
			model.addAttribute("profileMap", profilemap);
			model.addAttribute("postList", postlist);
			model.addAttribute("replyMap", replymap);
			model.addAttribute("recommendMember", recommendMember);
			model.addAttribute("hottestFeed", hottestFeed);	
			model.addAttribute("member_Id", member_Id);
			model.addAttribute("hashMap", hashmap);
			model.addAttribute("hashTag", hashTag);
			model.addAttribute("postListSize", postListSize);

			return "searchIndex";
		}
	}

	@PostMapping("/getMoreSearchHashTag")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getMoreSearchHashTag(@RequestBody Map<String, String> requestbody, HttpSession session, Model model){


			//System.out.println("[�����õ - 1] �α��� �� index ��û�ϸ� GetMapping���� ��ƿ��� ������ loginUser���� Id �̾Ƽ� member_Id�� ����");
			String member_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();

			String hashTag = requestbody.get("hashTag");

			/* index�������� �����ǵ� �κ� */
			// �ڽ�, �ȷ����� ������� �Խñ��� ��ºκ�
			ArrayList<PostVO> postlist = postService.getHashTagPost(hashTag);
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
				// i��° �Խñ��� ��� ���ƿ� ���� üũ
				for(int k = 0; k < replylist.size(); k++) {
					ReplyVO voForReplyCheck = replylist.get(k);
					String realReply_Member_Id = replylist.get(k).getMember_Id();
					voForReplyCheck.setMember_Id(member_Id);
					//System.out.println("[�̸����� ��� - 2] ��� ���ƿ� ������ Ȯ���Ϸ� ����");				
					String reply_LikeYN = replyService.getCheckReplyLike(voForReplyCheck);
					replylist.get(k).setReply_LikeYN(reply_LikeYN);
					//System.out.println("[�̸����� ��� - 5] DAO���� ���Ϲ޾Ƽ� set����. �ش� ��� ���ƿ� ���� ? " + replylist.get(k).getReply_LikeYN());
					replylist.get(k).setMember_Id(realReply_Member_Id);
				}

				// i��°�� �Խñ��� ����� map�� �����ϴ� �۾�
				replymap.put(i, replylist);
				//System.out.println(i + "��° �Խñ� ��� ����" + replymap.get(i));			

				// i��° �Խñ��� ���ƿ� ���� üũ
				PostVO voForLikeYN = new PostVO();
				voForLikeYN.setMember_Id(member_Id);
				voForLikeYN.setPost_Seq(post_Seq);
				//System.out.println("[���ƿ� ���� Ȯ�� - 0] �Խñ� ��ȣ : " + post_Seq);
				//System.out.println("[���ƿ� ���� Ȯ�� - 1] Setting �� post_LikeYN = " + postlist.get(i).getPost_LikeYN());
				String post_LikeYN = postService.getLikeYN(voForLikeYN);
				postlist.get(i).setPost_LikeYN(post_LikeYN);
				//System.out.println("[���ƿ� ���� Ȯ�� - 4] Setting �� post_LikeYN = " + postlist.get(i).getPost_LikeYN());

				// i��° �Խñ��� �ؽ��±� üũ    hashmap
				ArrayList<TagVO> hash = postService.getHashtagList(post_Seq);
				hashmap.put(post_Seq, hash);
			}

			// ��ü ȸ�� ������ �̹��� ��ȸ
			HashMap<String, String> profilemap = memberService.getMemberProfile();
			//System.out.println("��ü ȸ�� ������: " + profilemap);



			Map<String, Object> responseData = new HashMap<>();

			responseData.put("profileMap", profilemap);
			responseData.put("postList", postlist);
			responseData.put("replyMap", replymap);
			responseData.put("member_Id", member_Id);
			responseData.put("hashMap", hashmap);

			return ResponseEntity.ok(responseData);

	}
	
}
