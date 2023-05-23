package com.example.mysite.service.meta.impl;

import com.example.mysite.constant.ErrorConstant;
import com.example.mysite.constant.Types;
import com.example.mysite.constant.WebConstant;
import com.example.mysite.dao.MetaDao;
import com.example.mysite.dao.RelationshipsDao;
import com.example.mysite.dto.MetaDto;
import com.example.mysite.dto.condition.MetaCond;
import com.example.mysite.entity.Content;
import com.example.mysite.entity.Meta;
import com.example.mysite.entity.Relationships;
import com.example.mysite.exception.BusinessException;
import com.example.mysite.service.content.ContentService;
import com.example.mysite.service.meta.MetaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Xufeng Jiang
 * @date 2023年04月25日 11:38
 * @description
 */
@Service
public class MetaServiceImpl implements MetaService {

    @Autowired
    private MetaDao metaDao;

    @Autowired
    private RelationshipsDao relationshipsDao;

    @Autowired
    private ContentService contentService;

    @Override
    public List<Meta> findAll() {
        return metaDao.findAll();
    }

    @Override
    @Cacheable(value = "metaCache",key = "'getMetaList_' + #p0")
    public List<MetaDto> getMetaList(String type, String orderby, int limit) {
        if (StringUtils.isNotBlank(type)) {
            if (StringUtils.isBlank(orderby)) {
                orderby = "count desc, a.mid desc";
            }
            if (limit < 1 || limit > WebConstant.MAX_POSTS) {
                limit = 10;
            }
            Map<String, Object> paraMap = new HashMap<>();
            paraMap.put("type", type);
            paraMap.put("order", orderby);
            paraMap.put("limit", limit);
            return metaDao.selectFromSql(paraMap);
        }
        return null;
    }

    @Override
    @Cacheable(value = "metaCache",key = "'getMetas_' + #p0")
    public List<Meta> getMetas(MetaCond metaCond) {
        return metaDao.getMetasByCond(metaCond);
    }

    @Override
    @CacheEvict(value = "metaCache",allEntries = true,beforeInvocation = true)
    public void addMeta(Meta meta) {
        if (null == meta)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        metaDao.addMeta(meta);
    }

    @Override
    @CacheEvict(value = "metaCache",allEntries = true,beforeInvocation = true)
    public void saveMeta(String type, String name, Integer mid) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(name)) {
            MetaCond metaCond = new MetaCond();
            metaCond.setName(name);
            metaCond.setType(type);
            List<Meta> metas = metaDao.getMetasByCond(metaCond);
            if (null == metas || metas.size() == 0) {
                Meta metaDomain = new Meta();
                metaDomain.setName(name);
                if (null != mid) {
                    Meta meta = metaDao.getMetaById(mid);
                    if (null != meta)
                        metaDomain.setMid(mid);

                    metaDao.updateMeta(metaDomain);
                    //更新原有的文章分类
                    if (meta != null) {
                        contentService.updateCategory(meta.getName(), name);
                    }
                } else {
                    metaDomain.setType(type);
                    metaDao.addMeta(metaDomain);
                }
            } else {
                throw BusinessException.withErrorCode(ErrorConstant.Meta.META_IS_EXIST);

            }

        }
    }

    @Override
    @CacheEvict(value = "metaCache",allEntries = true,beforeInvocation = true)
    public void deleteMetaById(Integer mid) {
        if (null == mid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);

        Meta meta = metaDao.getMetaById(mid);
        if (null != meta){
            String type = meta.getType();
            String name = meta.getName();
            metaDao.deleteMetaById(mid);
            //需要把相关的数据删除
            List<Relationships> relationShips = relationshipsDao.getRelationShipByMid(mid);
            if (null != relationShips && relationShips.size() > 0){
                for (Relationships relationShip : relationShips) {
                    Content article = contentService.getArticleById(relationShip.getCid());
                    if (null != article){
                        Content temp = new Content();
                        temp.setCid(relationShip.getCid());
                        if (type.equals(Types.CATEGORY.getType())) {
                            temp.setCategories(reMeta(name, article.getCategories()));
                        }
                        if (type.equals(Types.TAG.getType())) {
                            temp.setTags(reMeta(name, article.getTags()));
                        }
                        //将删除的资源去除
                        contentService.updateArticleById(temp);
                    }
                }
                relationshipsDao.deleteRelationShipByMid(mid);
            }
        }
    }

    @Override
    @CacheEvict(value = "metaCache",allEntries = true,beforeInvocation = true)
    public void updateMeta(Meta meta) {
        if (null == meta || 0 == meta.getMid())
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);
        metaDao.updateMeta(meta);
    }

    @Override
    @CacheEvict(value = "metaCache",allEntries = true,beforeInvocation = true)
    public void addMetas(Integer cid, String names, String type) {
        if (null == cid)
            throw BusinessException.withErrorCode(ErrorConstant.Common.PARAM_IS_EMPTY);

        if (StringUtils.isNotBlank(names) && StringUtils.isNotBlank(type)) {
            String[] nameArr = StringUtils.split(names, ",");
            for (String name : nameArr) {
                this.saveOrUpdate(cid, name, type);
            }
        }
    }

    @Override
    @CacheEvict(value = "metaCache",allEntries = true,beforeInvocation = true)
    public void saveOrUpdate(Integer cid, String name, String type) {
        MetaCond metaCond = new MetaCond();
        metaCond.setName(name);
        metaCond.setType(type);
        List<Meta> metas = this.getMetas(metaCond); //根据name和type查询Meta

        int mid;
        Meta metaDomain;
        if (metas.size() == 1) {
            Meta meta = metas.get(0);
            mid = meta.getMid();
        } else if (metas.size() > 1) {
            throw BusinessException.withErrorCode(ErrorConstant.Meta.NOT_ONE_RESULT);
        } else {
            metaDomain = new Meta();
            metaDomain.setSlug(name);
            metaDomain.setName(name);
            metaDomain.setType(type);
            this.addMeta(metaDomain);
            mid = metaDomain.getMid();
        }
        if (mid != 0) {
            Long count = relationshipsDao.getCountById(cid, mid);
            if (count == 0) {
                Relationships relationShip = new Relationships();
                relationShip.setCid(cid);
                relationShip.setMid(mid);
                relationshipsDao.addRelationShip(relationShip);
            }

        }

    }

    private String reMeta(String name, String metas) {
        String[] ms = StringUtils.split(metas, ",");
        StringBuilder sbuf = new StringBuilder();
        for (String m : ms) {
            if (!name.equals(m)) {
                sbuf.append(",").append(m);
            }
        }
        if (sbuf.length() > 0) {
            return sbuf.substring(1);
        }
        return "";
    }
}
