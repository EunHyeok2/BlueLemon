package com.blue.service;

import java.util.ArrayList;
import java.util.List;

import com.blue.dto.LikeVO;
import com.blue.dto.PostVO;
import com.blue.dto.TagVO;


public interface PostService {

	// 게시글 전체 조회 (뉴스피드)
	ArrayList<PostVO> getlistPost(String member_Id);
	
	// 게시글 좋아요 여부 체크
	String getLikeYN(PostVO voForLikeYN);
	
	// 게시글 좋아요 처리
	void changeLike(LikeVO vo);
	
	// 인기글 조회 (우측)
	List<PostVO> getHottestFeed();
	
	// 게시글 작성 (모달창)
	void insertPost(PostVO vo);
	
	// 게시글 상세보기(모달창)
	PostVO getpostDetail(int post_Seq);
	
	// 프로필의 개인 포스트들
	ArrayList<PostVO> getMemberPost(String member_Id);
	
	// 게시글 좋아요 조회
	int getPost_Like_Count(int post_Seq);
	
	// 관리자 - 전체 게시글 조회
	ArrayList<PostVO> getAllPost();

	// 게시글 삭제
	void deletePost(int post_Seq);
	
	// 게시글 추가 시 필요한 가장높은 시퀀스 조회
	int postSeqCheck();
	
	// 게시글 추가의 해시태그 인서트
	void insertTag(TagVO vo);
}
