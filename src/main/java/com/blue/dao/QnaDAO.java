package com.blue.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.blue.dto.QnaVO;

@Repository("QnaDAO")
public class QnaDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;

	public void insertQna(QnaVO vo) {
		mybatis.insert("QnaMapper.insertQna", vo);
	}

	public List<QnaVO> getMyQna(String member_Id) {
		return mybatis.selectList("QnaMapper.getMyQna", member_Id);
	}
}
