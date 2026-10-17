package com.xuecheng.media.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuecheng.media.model.po.MediaProcess;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author itcast
 */
public interface MediaProcessMapper extends BaseMapper<MediaProcess> {

    /**
     * @description 根据分片参数获取待处理任务
     * @param shardTotal  分片总数
     * @param shardIndex  分片序号
     * @param count 任务数
     * @return java.util.List<com.xuecheng.media.model.po.MediaProcess>
     * @author Mr.M
     * @date 2022/9/14 8:54
     */
    @Select("select * from media_process t where t.id % #{shardTotal} = #{shardIndex} and (t.status = '1' or t.status = '3') and t.fail_count < 3 limit #{count}")
    List<MediaProcess> selectListByShardIndex(@Param("shardTotal") int shardTotal, @Param("shardIndex") int shardIndex, @Param("count") int count);


    @Update("update media_process m set m.status='4' where (m.status='1' or m.status='3') and m.fail_count<3 and m.id=#{id}")
    int startTask(@Param("id") long id);

    @Select("select * from media_process t where t.id % #{shardTotal} = #{shardIndex} and t.status = '4' and EXTRACT(MINUTE FROM (CURRENT_TIMESTAMP - t.create_date)) >= 30 limit #{count}")
    List<MediaProcess> selectTimeoutTask(@Param("shardTotal") int shardTotal, @Param("shardIndex") int shardIndex, @Param("count") int count);

    @Update("update media_process m set m.status='3',m.fail_count=m.fail_count+1 where m.status='4' and m.id=#{processId}")
    int startCheckTask(@Param("processId") long processId);

}
