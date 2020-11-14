package com.cheng.api.modules.sys.mapper;

import com.cheng.api.common.base.CommonMapper;
import com.cheng.api.modules.sys.bean.SysIp;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 区域表(SysIp)表数据库访问层
 *
 * @author fengcheng
 * @since 2020-05-14 21:54:43
 */
@Mapper
@Repository
public interface SysIpMapper extends CommonMapper<SysIp> {

}