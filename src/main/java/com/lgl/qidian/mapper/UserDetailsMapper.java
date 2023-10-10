package com.lgl.qidian.mapper;

import com.lgl.qidian.controller.ToBeWriterController;
import com.lgl.qidian.entity.tobe_writer_contoller.ToBeWriterBody;
import org.apache.ibatis.annotations.Mapper;

/**
 * @auther 刘广林
 */
@Mapper
public interface UserDetailsMapper {
    public int insertUserDetails(ToBeWriterBody body);
}
