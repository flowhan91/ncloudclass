package com.lgdx.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.lgdx.web.entity.MsgMember;

@Mapper
public interface MsgMemberMapper {
	
	@Insert("insert into msg_member values(#{email},#{pw},#{tel},#{address})") //이 안에 sql 구문 적어준다. 
	void join(MsgMember mem);
	
	@Select("select * from msg_member where email = #{email} and pw = #{pw}")
	MsgMember login(MsgMember mem);
	
	//얘네는 jsp에서 가져옴(name) 따라서. 
	//근데 jsp에서 email 태그가 없음. -> 500 error. (넘겨지지 않은 데이터). 
	//
	@Update("update msg_member set pw=#{pw}, tel=#{tel},address=#{address} where email=#{email}")
	MsgMember update(MsgMember mem);
	
	//한개의 행 >> MsgMember
	// 여러개의 행 >> List<MsgMember>
	@Select("select * from msg_member where email!='admin'")
	List<MsgMember> showMember();
	
	@Delete("delete from msg_member where email=#{email}")
	void deleteMember(String email);
	
	@Select("select * from msg_member where email = #{email}")
	MsgMember selectByEmail(String email);
	
	
	
	
	
	
	
}

	
