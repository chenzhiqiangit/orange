package com.orange.dao.system;

import com.orange.entity.Permission;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * Created by chzq on 2017/5/5.
 */

@MapperScan
public interface SystemMapper {
    List<Permission> selectPermissionByUser(Long userId);
}
