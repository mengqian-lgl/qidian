package com.lgl.qidian.mapper;

import com.lgl.qidian.entity.tobe_writer_contoller.AdjudicationWriterDo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @auther 刘广林
 */
@Mapper
public interface AdjudicationWriterMapper {

    public int insertAdjudicationWriter(AdjudicationWriterDo adjudicationWriterDo);

    public int deleteByUserId(Long userId);

    public int update(AdjudicationWriterDo adjudicationWriterDo);
}
