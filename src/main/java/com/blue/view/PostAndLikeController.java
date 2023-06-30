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

import com.blue.dto.AlarmVO;
import com.blue.dto.LikeVO;
import com.blue.dto.MemberVO;
import com.blue.dto.PostVO;
import com.blue.dto.ReplyVO;
import com.blue.dto.TagVO;
import com.blue.service.AlarmService;
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
	@Autowired
	private AlarmService alarmService;
	
	// ���ƿ� ����(PostMapping)
	@PostMapping("/changeLike")
	@ResponseBody
	public String changeLike(@RequestBody Map<String, Integer> requestBody, HttpSession session) {
		//System.out.println("[�Խñ� ���ƿ� - 3] PostMapping���� /changeLike �� Map �������� ��ƿ�.");
	    int post_Seq = requestBody.get("post_Seq");
	    PostVO postVO = postService.getpostDetail(post_Seq);
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
		System.out.println("==================================�Խñ� �ۼ�=====================================");
		
		// MAX(post_Seq) + 1
		int nextSeq = 0;
		nextSeq = postService.postSeqCheck();
		// nextval�� ��������ʰ� ������ �������� �����ϱ����� postVO�� set���ش�
		vo.setPost_Seq(nextSeq);
		
		// �ٲ� ���������� ��ºκ�
		Map<Integer, Integer> index = new HashMap<>();
		
		// 1. ����ڰ� �̹����� ÷������ �� 
		if (attach_file != null && attach_file.length > 0) {
			//System.out.println("insertPost file���� : " + attach_file.length);
			
			// 1. �̹��� ���ε� ó�� �κ�
			String folderPath = session.getServletContext().getRealPath("/WEB-INF/template/img/uploads/post/");
			System.out.println(folderPath);
			// 1. ���ε��� �̹��� ���� vo ��ü�� ����
			int imgCount = attach_file.length;
			vo.setPost_Image_Count(imgCount);
			
			if(imgCount == 0) { // �̹����� ���ε� ���� �ʾ�����
				System.out.println("�̹��� ����");
				
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
		if (vo.getPost_Public() == null) {
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
	                // Ư������ ��ȯ
	                value = value.replace("<", "��").replace(">", "��").replace("#", "");

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
		
		// 6. �Խñ��� �ؽ��±� 
		ArrayList<TagVO> hashTag = postService.getHashtagList(post_Seq);
		
		
 		// �Խñ� ������ VO
 		dataMap.put("post", postInfo);  
 		// �Խñ��� ��� ����Ʈ
 		dataMap.put("replies", replyList);
 		// �Խñ��� �ؽ��±�
 		dataMap.put("hashtag", hashTag);
 		// ��ü ȸ���� ������ �̹���
 		dataMap.put("profile", profileMap);
		
		return dataMap;
	}
	
	
	// �Խñ� �󼼺��� ������ (��� ����Ʈ, ���� ����)
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
		
		// 5. �Խñ��� �ؽ��±� 
		ArrayList<TagVO> hashTag = postService.getHashtagList(post_Seq);
		
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
 		// �Խñ��� �ؽ��±�
 		dataMap.put("hashtag", hashTag);
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
		// �˸� ����� ���ÿ� �����ϱ� ���� reply_Seq�� nextVal���� ������������ ����
		int next_Reply_Seq = 0;
		next_Reply_Seq = replyService.getMaxReply_Seq() + 1;
		
		ReplyVO rep = new ReplyVO();
		rep.setReply_Seq(next_Reply_Seq);
		rep.setMember_Id(member_Id);
		rep.setPost_Seq(post_Seq);
		rep.setReply_Content(reply_Content);
		//System.out.println("�μ�Ʈ �������� ���� ��ü ���빰: " + rep);
		
		// 3. insert������ ����
		replyService.insertReply(rep);
		
		// �˶�
		String postWriter = postService.getPostWriter(post_Seq);
		
		AlarmVO alarmVO = new AlarmVO();
		alarmVO.setKind(3);
		alarmVO.setFrom_Mem(member_Id);
		alarmVO.setTo_Mem(postWriter);
		alarmVO.setPost_Seq(post_Seq);
		alarmVO.setReply_Seq(next_Reply_Seq);
		
		alarmService.insertAlarm(alarmVO);
		
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
	public String postDelete(@RequestParam(value="post_Seq") int post_Seq, HttpSession session) {
		
		postService.deletePost(post_Seq);
		//System.out.println("����Ʈ ������ : " + post_Seq);
		// 1. �̹��� ���ε� �������
		String folderPath = session.getServletContext().getRealPath("/WEB-INF/template/img/uploads/post/");
		// �������� �̹��� ��ü�� folder�� �����Ѵ�
		File folder = new File(folderPath);
		// folder�� ���ϵ��� ����Ʈȭ ��Ų��.
		File[] files = folder.listFiles();
		
		if (files != null) {
		    for (File file : files) {
		        // ���� ��ο��� ���� �̸��� �����մϴ�.
		        String fileName = file.getPath().substring(file.getPath().lastIndexOf('\\') + 1);
		        
		        // ���� �̸��� "-"�� �������� �и��մϴ�.
		        String[] parts = fileName.split("-");
		        
		        // ���� �̸��� ��� �� �κ����� �̷���� �ְ�, ù ��° �κ��� post_Seq�� ��ġ�ϴ��� Ȯ���մϴ�.
		        if (parts.length >= 2 && parts[0].equals(String.valueOf(post_Seq))) {
		            // ������ �����մϴ�.
		            if (file.delete()) {
		                // ���� ������ ������ ���, ���� �޽����� ����մϴ�.
		                System.out.println("���� ����: " + fileName);
		            } else {
		                // ���� ������ ������ ���, ���� �޽����� ����մϴ�.
		                System.out.println("���� ���� ����: " + fileName);
		            }
		        }
		    }
	    } else {
	        System.out.println("���� �̹��� ����");
	    }
		return "redirect:/index";
	}
	
	// �Խñ� ���� View
	@GetMapping("/postEditView")
	@ResponseBody
	public Map<String, Object> postEditView(HttpSession session, @RequestParam("post_Seq") int post_Seq){
		
		// ajax��û�� ���� ������ ������ ���� ��ü ����
		Map<String, Object> dataMap = new HashMap<>();
		
		// ���ǿ��� ���̵� ����
		String member_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
		
		// �Խñ� ������ ����
		PostVO postInfo = postService.getpostDetail(post_Seq);
		
		// �ؽ��±� ����Ʈ
		ArrayList<TagVO> hashlist = postService.getHashtagList(post_Seq);
		
		// �̹��� ���
		// �̹��� ��θ� �ݵ�� �����η� �������� �ʾƵ� �̹����� ǥ���� �� �ִ�.
		//String folderPath = session.getServletContext().getRealPath("img/uploads/post/");
		String folderPath = "img/uploads/post/";
		
		dataMap.put("hashList", hashlist);
		dataMap.put("post", postInfo);
		dataMap.put("folderPath", folderPath);
		return dataMap;
	}
	
	// �Խñ� ���� Action
	@PostMapping("postEditAction")
	public String postEditAction(PostVO vo, @RequestParam(value="editAttach_file", required = false) MultipartFile[] attach_file,
								@RequestParam(value = "deletedStrings", required = false) String[] deletedStrings,	
								@RequestParam(value = "alreadyFileNo", required = false) int alreadyFileNo,
								@RequestParam(value = "currentEditFileNo", required = false) int currentEditFileNo,
								HttpSession session, int post_Seq) {

		System.out.println("==================================�Խñ� ����=====================================");
		System.out.println("insertPost vo : " + vo);
		vo.setPost_Seq(post_Seq);
		
		System.out.println("attach_file : " + attach_file.length);
		System.out.println("deletedStrings : " + deletedStrings.length);
		int deleteStrings = deletedStrings.length;
		System.out.println("alreadyFileNo : " + alreadyFileNo);
		System.out.println("currentEditFileNo : " + currentEditFileNo);
		
		
		
		// 1. �̹��� ���ε� �������
		String folderPath = session.getServletContext().getRealPath("/WEB-INF/template/img/uploads/post/");
		// 1. �̹��� ��� �����
		String imagePath = "img/uploads/post/";
		
		int imgCount = attach_file.length;
		System.out.println("imgCount : " + imgCount);
		vo.setPost_Image_Count(currentEditFileNo);
		
		
		if(imgCount == 0) { // ������ ����� �̹����� ������
			System.out.println("�̹��� ����");
			
			// ���� ���� ����
			// �������� �̹��� ��ü�� folder�� �����Ѵ�
			File folder = new File(folderPath);
			// folder�� ���ϵ��� ����Ʈȭ ��Ų��.
			File[] files = folder.listFiles();
			
			// �����̹����� ����������
			if (files != null && deletedStrings.length > 0) {
			 for (String deletedString : deletedStrings) {
			        String fileName = deletedString.substring(deletedString.lastIndexOf('/') + 1);
			        String absoluteFilePath = folderPath + fileName;
			        File fileToDelete = new File(absoluteFilePath);
			        if (fileToDelete.delete()) {
			            System.out.println("���� ����: " + fileName);
			            
			            // ������ ���� ������ ���ϵ� �̸� ����
			            int deletedIndex = Integer.parseInt(fileName.split("-")[1].split("\\.")[0]);
			            for (int i = deletedIndex + 1; i <= files.length; i++) {
			                String originalFilePath = folderPath + post_Seq + "-" + i + ".png";
			                String newFilePath = folderPath + post_Seq + "-" + (i - 1) + ".png";
			                File originalFile = new File(originalFilePath);
			                File newFile = new File(newFilePath);
			                if (originalFile.renameTo(newFile)) {
			                    System.out.println("���� �̸� ����: " + originalFilePath + " -> " + newFilePath);
			                } else {
			                    System.out.println("���� �̸� ���� ����: " + originalFilePath);
			                }
			            }
			        } else {
			            System.out.println("���� ���� ����: " + fileName);
			        }
			    }
			}else {
				System.out.println("���� �̹��� ����");
			}
			
		} else if (imgCount == 1 ){ // ������ ����� �̹����� 1�� �϶�
			// ���� ����  ���� �κ�
			MultipartFile file = attach_file[0];
			String fileName = post_Seq + "-" + (alreadyFileNo-deleteStrings+1) + ".png";
			System.out.println(fileName);
			System.out.println("File Name: " + file.getOriginalFilename());
			
			// �������� ���� ���� üũ�� ó��
			File folder = new File(folderPath);
			File[] files = folder.listFiles();
			if (files != null && deletedStrings.length > 0) {
			 for (String deletedString : deletedStrings) {
			        String alreadyFileName = deletedString.substring(deletedString.lastIndexOf('/') + 1);
			        String absoluteFilePath = folderPath + alreadyFileName;
			        File fileToDelete = new File(absoluteFilePath);
			        if (fileToDelete.delete()) {
			            System.out.println("���� ����: " + alreadyFileName);
			            
			            // ������ ���� ������ ���ϵ� �̸� ����
			            int deletedIndex = Integer.parseInt(alreadyFileName.split("-")[1].split("\\.")[0]);
			            for (int i = deletedIndex + 1; i <= files.length; i++) {
			                String originalFilePath = folderPath + post_Seq + "-" + i + ".png";
			                String newFilePath = folderPath + post_Seq + "-" + (i - 1) + ".png";
			                File originalFile = new File(originalFilePath);
			                File newFile = new File(newFilePath);
			                if (originalFile.renameTo(newFile)) {
			                    System.out.println("���� �̸� ����: " + originalFilePath + " -> " + newFilePath);
			                } else {
			                    System.out.println("���� �̸� ���� ����: " + originalFilePath);
			                }
			            }
			            
			        } else {
			            System.out.println("���� ���� ����: " + alreadyFileName);
			        }
			    }
			}else {
				System.out.println("���� �̹��� ����");
			}
			try {
		        // ���� ���� ���� ó�� �κ�
		        file.transferTo(new File(folderPath + fileName));
		        System.out.println("���� ���� ����");
		    } catch (IOException e) {
		        e.printStackTrace();
		        System.out.println("���� ���� ����");
		    }
			
		} else { // ������ ����� �̹����� 2�� �̻��϶�
			System.out.println("�̹��� " + imgCount + " �� ");
			
			// �������� ���� ���� üũ�� ó��
			File folder = new File(folderPath);
			File[] files = folder.listFiles();
			if (files != null && deletedStrings.length > 0) {
			 for (String deletedString : deletedStrings) {
			        String alreadyFileName = deletedString.substring(deletedString.lastIndexOf('/') + 1);
			        String absoluteFilePath = folderPath + alreadyFileName;
			        File fileToDelete = new File(absoluteFilePath);
			        if (fileToDelete.delete()) {
			            System.out.println("���� ����: " + alreadyFileName);
			            
			            // ������ ���� ������ ���ϵ� �̸� ����
			            int deletedIndex = Integer.parseInt(alreadyFileName.split("-")[1].split("\\.")[0]);
			            for (int i = deletedIndex + 1; i <= files.length; i++) {
			                String originalFilePath = folderPath + post_Seq + "-" + i + ".png";
			                String newFilePath = folderPath + post_Seq + "-" + (i - 1) + ".png";
			                File originalFile = new File(originalFilePath);
			                File newFile = new File(newFilePath);
			                if (originalFile.renameTo(newFile)) {
			                    System.out.println("���� �̸� ����: " + originalFilePath + " -> " + newFilePath);
			                } else {
			                    System.out.println("���� �̸� ���� ����: " + originalFilePath);
			                }
			            }
			        } else {
			            System.out.println("���� ���� ����: " + alreadyFileName);
			        }
			    }
			}else {
				System.out.println("���� �̹��� ����");
			}
			// ���� ���� ���� ó�� �κ�
			for(int i=0; i<attach_file.length; i++) {
				System.out.println("�߰��� �̹��� " + imgCount + " ��");
				MultipartFile file = attach_file[i];     
				String fileName = post_Seq + "-" + (alreadyFileNo-deleteStrings+(i+1)) + ".png";
				System.out.println(fileName);
				System.out.println("File Name: " + file.getOriginalFilename());
				
				try {
		            // ������ ������ ��ο� ����
		            file.transferTo(new File(folderPath + fileName));
		            System.out.println("���� ���� ����");
		        } catch (IOException e) {
		            e.printStackTrace();
		            System.out.println("���� ���� ����");
		        }
			}
		}
		
		if (vo.getPost_Public() == null) {
		vo.setPost_Public("n");
		}
		// 2. ���� ó��
		postService.updatePost(vo);
		System.out.println("�ؽ��±� ���� ��");
		// 3. �ؽ��±� ���� �� ���� ó��
		postService.deleteTag(post_Seq);
		System.out.println("�ؽ��±� ����");
		
		// 4. �ؽ��±� ó�� �κ� 
		String hashTag = vo.getPost_Hashtag();
		if (hashTag != null && !hashTag.isEmpty()) {
			try { // 2-1. ����ڰ� �Է��� �ؽ��±׵��� json���·� �޾ƿͼ� ����� �� �ְ� �Ľ��ϴ� �۾�
			    ObjectMapper objectMapper = new ObjectMapper();
			    JsonNode jsonNode = objectMapper.readTree(hashTag);
			
			    for (JsonNode node : jsonNode) {
			    	// n��° �ؽ��±� ����
			        String value = node.get("value").asText();
			        // Ư������ ��ȯ
		            value = value.replace("<", "��").replace(">", "��").replace("#", "");

			        TagVO tvo = new TagVO();
			        tvo.setPost_Seq(post_Seq);
			        tvo.setTag_Content(value);
			        postService.insertTag(tvo);
			    }
			} catch (JsonProcessingException e) {
			    e.printStackTrace();
			}
		}
		return "/index";
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


	    	// �˶� ����Ʈ�� ��� �κ�
	    	List<AlarmVO> alarmList = alarmService.getAllAlarm(member_Id);
	    	
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
				postlist.get(i).setPost_Content(postlist.get(i).getPost_Content().replace("\n", "<br>"));
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
		    
		    List<MemberVO> followingList = memberService.getFollowings(member_Id);
		    
		    for(int i = 0; i < mostFamous.size(); i++) {
				//System.out.println("�ȷο� ���̵�");
				//System.out.println(follower_info.get(i).getMember_Id());
				for(int j = 0; j < followingList.size(); j++) {
					//System.out.println("�ȷ��� ���̵�");
					//System.out.println(following_info.get(j).getMember_Id());
					if(mostFamous.get(i).getMember_Id().equals(followingList.get(j).getMember_Id())) {
						mostFamous.get(i).setBothFollow(1);
						//System.out.println("setBoth �Է� Ȯ�� : " + follower_info.get(i).getBothFollow());
					}
				}
			}
		    

		    int searchFollowSize = searchFollow.size();

		    model.addAttribute("searchFollow", searchFollow);
		    model.addAttribute("mostFamous", mostFamous);
		    model.addAttribute("searchFollowSize", searchFollowSize);
		    
		    model.addAttribute("alarmList", alarmList);
			model.addAttribute("alarmListSize", alarmListSize);
			
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
			postlist.get(i).setPost_Content(postlist.get(i).getPost_Content().replace("\n", "<br>"));
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
	
	@PostMapping("/selectOnePost")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> selectOnePost(@RequestBody Map<String, Integer> requestbody, HttpSession session){
		
		int post_Seq = requestbody.get("post_Seq");
		
		String member_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();
		
		//�Խñ� 
		PostVO postVO = postService.selectPostDetail(post_Seq);
		//���
		ArrayList<ReplyVO> replylist = replyService.getReplyPreview(post_Seq);
		//�ؽ��±�
		ArrayList<TagVO> hash = postService.getHashtagList(post_Seq);
		
		// ��ü ȸ���� �̹��� Map�� ���ǿ��� �޾ƿ�
		HashMap<String, String> profileMap = (HashMap<String, String>) session.getAttribute("profileMap");
		
		Map<String, Object> responseData = new HashMap<>();

		responseData.put("postVO", postVO);
		
		responseData.put("replylist", replylist);
		
		responseData.put("hash", hash);
		
		responseData.put("profileMap", profileMap);
		
		responseData.put("session_Id", member_Id);

		return ResponseEntity.ok(responseData);
		
	}
	
	
	@PostMapping("deleteReply")
	@ResponseBody
	public Map<String, Object>  deleteReply(@RequestParam(value="post_Seq") int post_Seq,
											@RequestParam(value="reply_Seq") int reply_Seq, HttpSession session){
		
		// 0. ajax��û�� ���� response�� ������ ���� Map ���� ����
		Map<String, Object> dataMap = new HashMap<>();
		
		// 1. session ���̵� �޾ƿ���
		String member_Id = ((MemberVO) session.getAttribute("loginUser")).getMember_Id();

		// 2. delete �������� ������ vo ��ü ���� ����
		ReplyVO rep = new ReplyVO();
		rep.setReply_Seq(reply_Seq);
		rep.setPost_Seq(post_Seq);
		
		// 3. delete������ ����
		replyService.deleteReply(rep);
		
		// - ���ƿ� ���� ó��
		replyService.deleteReplyLike(rep);
		
		// 4. �� �ۼ��� �޾ƿ���
		String postWriter = postService.getPostWriter(post_Seq);
		
		// - �˶� ���� ó��
		AlarmVO aVO = new AlarmVO();
		aVO.setKind(3);
		aVO.setFrom_Mem(member_Id);
		aVO.setTo_Mem(postWriter);
		aVO.setPost_Seq(post_Seq);
		aVO.setReply_Seq(reply_Seq);
		int alarm_Seq = alarmService.getOneAlarm_Seq(aVO);
		
		if(alarm_Seq > 0) {
			alarmService.deleteAlarm(alarm_Seq);
		}
		
		
		// 5. �Խñ��� ��۸���Ʈ�� ����ϱ� ���� ArrayList<ReplyVO> �� ����
		ArrayList<ReplyVO> replyList = replyService.getListReply(post_Seq);
		
		// 6. ��ü ȸ���� �̹��� Map�� ���ǿ��� �޾ƿ�
		HashMap<String, String> profileMap = (HashMap<String, String>) session.getAttribute("profileMap");
		
		// 7. �ش� �Խñ��� �������� ��ȸ�ؼ� ������(�Խñ��� ��� ī��Ʈ ������ ����)
		PostVO postInfo = postService.getpostDetail(post_Seq);
		
		// 8. �Խñ� ����� ���ƿ� ���� üũ
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
