package com.lgl.qidian.mapper;

import com.lgl.qidian.entity.WriterDo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @auther 刘广林
 */
@Mapper
public interface WriterMapper {
    public int insert(WriterDo writerDo);
}
