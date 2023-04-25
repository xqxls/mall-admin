package com.xqxls.mall.cache;

import cn.hutool.core.collection.CollUtil;
import com.xqxls.mall.common.service.RedisService;
import com.xqxls.mall.entity.UmsAdminEntity;
import com.xqxls.mall.entity.UmsAdminRoleRelationEntity;
import com.xqxls.mall.entity.UmsResourceEntity;
import com.xqxls.mall.mapper.UmsAdminDao;
import com.xqxls.mall.mapper.UmsAdminRoleRelationDao;
import com.xqxls.mall.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台用户缓存管理Service实现类
 * Created by macro on 2020/3/13.
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
    private String REDIS_DATABASE;

    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;

    @Value("${redis.key.admin}")
    private String REDIS_KEY_ADMIN;

    @Value("${redis.key.resourceList}")
    private String REDIS_KEY_RESOURCE_LIST;

    @Override
    public void delAdmin(Long adminId) {
        UmsAdminEntity admin = umsAdminService.selectByPrimaryKey(adminId);
        if (admin != null) {
            String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + admin.getUsername();
            redisService.del(key);
        }
    }

    @Override
    public void delResourceList(Long adminId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
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
            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
            List<String> keys = relationList.stream().map(relation -> keyPrefix + relation.getAdminId()).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    @Override
    public void delResourceListByResource(Long resourceId) {
        List<Long> adminIdList = umsAdminDao.getAdminIdList(resourceId);
        if (CollUtil.isNotEmpty(adminIdList)) {
            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
            List<String> keys = adminIdList.stream().map(adminId -> keyPrefix + adminId).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    @Override
    public UmsAdminEntity getAdmin(String username) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + username;
        return (UmsAdminEntity) redisService.get(key);
    }

    @Override
    public void setAdmin(UmsAdminEntity admin) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + admin.getUsername();
        redisService.set(key, admin, REDIS_EXPIRE);
    }

    @Override
    public List<UmsResourceEntity> getResourceList(Long adminId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        return (List<UmsResourceEntity>) redisService.get(key);
    }

    @Override
    public void setResourceList(Long adminId, List<UmsResourceEntity> resourceList) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        redisService.set(key, resourceList, REDIS_EXPIRE);
    }
}
