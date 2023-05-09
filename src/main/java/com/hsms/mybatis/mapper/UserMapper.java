package com.hsms.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hsms.mybatis.model.User;

/**
 * @author luoliang
 * @date 2018/12/20
 */
@Mapper
public interface UserMapper {
    /**
     * 根据ID查询用户
     *
     * @param id
     * @return
     */
    User findById(Integer id);
    List<User> findAll();

    /**
     * 添加一条用户数据
     *
     * @param user
     */
//    @Insert("insert into COM_USER(name, password, state, address, email) values (#{name}, #{password}, #{state}, #{address}, #{email})")
    void userAdd(User user);

    /**
     * 更新用户数据
     *
     * @param user
     */
    @Update("update user set name=#{name},password=#{password},state=#{state},address=#{address},email=#{email} where id=#{id}")
    void update(User user);

    @Delete("delete from user where id = #{id}")
    void delete(Integer id);

    /**
     * 查询指定状态的用户列表
     *
     * @param state
     * @return
     */
    @Select("select * from COM_USER where state = #{state}")
    List<User> selectList(Integer state);
}
