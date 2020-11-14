package com.cheng.api.modules.sys.mapper;

import com.cheng.api.common.base.CommonMapper;
import com.cheng.api.modules.sys.bean.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 角色表(SysRole)表数据库访问层
 *
 * @author fengcheng
 * @since 2019-07-08 11:15:47
 */
@Mapper
@Repository
public interface SysRoleMapper extends CommonMapper<SysRole> {

}
