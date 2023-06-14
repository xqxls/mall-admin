package com.xqxls.mall.cache;

import cn.hutool.core.collection.CollUtil;
import com.xqxls.mall.common.cache.RedisService;
import com.xqxls.mall.entity.UmsAdminEntity;
import com.xqxls.mall.entity.UmsAdminRoleRelationEntity;
import com.xqxls.mall.entity.UmsResourceEntity;
import com.xqxls.mall.mapper.UmsAdminDao;
import com.xqxls.mall.mapper.UmsAdminRoleRelationDao;
import com.xqxls.mall.service.UmsAdminService;
import com.xqxls.mall.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台用户缓存管理Service实现类
 *
 * @Author: huzhuo
 * @Date: Created in 2023/4/25 22:10
 */
@Service
public class UmsAdminCacheServiceImpl implements UmsAdminCacheService {

    @Autowired
    private UmsAdminService umsAdminService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UmsAdminDao umsAdminDao;

    @Autowired
    private UmsAdminRoleRelationDao umsAdminRoleRelationDao;

    @Value("${redis.database}")
    private String redisDatabase;

    @Value("${redis.expire.common}")
    private Long redisExpire;

    @Value("${redis.key.admin}")
    private String redisKeyAdmin;

    @Value("${redis.key.resourceList}")
    private String redisKeyResourceList;

    @Override
    public void delAdmin(Long adminId) {
        UmsAdminEntity admin = umsAdminService.findById(adminId);
        if (admin != null) {
            String key = redisDatabase + ":" + redisKeyAdmin + ":" + admin.getUsername();
            redisService.del(key);
        }
    }

    @Override
    public void delResourceList(Long adminId) {
        String key = redisDatabase + ":" + redisKeyResourceList + ":" + adminId;
        redisService.del(key);
    }

    @Override
    public void delResourceListByRole(Long roleId) {
        Example example = new Example(UmsAdminRoleRelationEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId", roleId);
        List<UmsAdminRoleRelationEntity> relationList = umsAdminRoleRelationDao.selectByExample(example);
        delRelationList(relationList);
    }

    @Override
    public void delResourceListByRoleIds(List<Long> roleIds) {
        Example example = new Example(UmsAdminRoleRelationEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("roleId", roleIds);
        List<UmsAdminRoleRelationEntity> relationList = umsAdminRoleRelationDao.selectByExample(example);
        delRelationList(relationList);
    }

    private void delRelationList(List<UmsAdminRoleRelationEntity> relationList) {
        if (CollUtil.isNotEmpty(relationList)) {
            String keyPrefix = redisDatabase + ":" + redisKeyResourceList + ":";
            List<String> keys = relationList.stream().map(relation -> keyPrefix + relation.getAdminId()).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    @Override
    public void delResourceListByResource(Long resourceId) {
        List<Long> adminIdList = umsAdminDao.getAdminIdList(resourceId);
        if (CollUtil.isNotEmpty(adminIdList)) {
            String keyPrefix = redisDatabase + ":" + redisKeyResourceList + ":";
            List<String> keys = adminIdList.stream().map(adminId -> keyPrefix + adminId).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    @Override
    public UmsAdminEntity getAdmin(String username) {
        String key = redisDatabase + ":" + redisKeyAdmin + ":" + username;
        return (UmsAdminEntity) redisService.get(key);
    }

    @Override
    public void setAdmin(UmsAdminEntity admin) {
        String key = redisDatabase + ":" + redisKeyAdmin + ":" + admin.getUsername();
        redisService.set(key, admin, redisExpire);
    }

    @Override
    public List<UmsResourceEntity> getResourceList(Long adminId) {
        String key = redisDatabase + ":" + redisKeyResourceList + ":" + adminId;
        return ObjectUtil.objectToList(redisService.get(key),UmsResourceEntity.class);
    }

    @Override
    public void setResourceList(Long adminId, List<UmsResourceEntity> resourceList) {
        String key = redisDatabase + ":" + redisKeyResourceList + ":" + adminId;
        redisService.set(key, resourceList, redisExpire);
    }
}
