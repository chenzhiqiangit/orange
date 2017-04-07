package com.orange.base;

import com.orange.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * Created by chzq on 2017/4/7.
 */
public class BaseDaoImpl<T extends BaseBo> implements BaseDao<T> {


    public String SQL_SELECT_COUNT = "selectCount";

    public String SQL_SELECT = "select";

    public String SQL_SELECT_BY_ID = "selectById";

    public String SQL_UPDATE_BY_ID = "updateById";

    public String SQL_UPDATE_BY_ID_SELECTIVE = "updateByIdSelective";

    public String SQL_DELETE = "delete";

    public String SQL_DELETE_BY_ID = "deleteById";

    public String SQL_INSERT = "insert";

    public String SQL_SELECT_All = "selectAll";

    protected SqlSession sqlSessionTemplate;

    public void setSqlSessionTemplate(SqlSession sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public static final String SQLNAME_SEPARATOR = ".";

    /**
     * @fields sqlNamespace SqlMapping命名空间
     */
    private String sqlNamespace = getDefaultSqlNamespace();

    /**
     * 获取泛型类型的实体对象类全名
     *
     * @author LiuJunGuang
     * @return
     */
    protected String getDefaultSqlNamespace() {
        Class<?> genericClass = BeanUtils.getGenericClass(this.getClass());
        return genericClass == null ? null : genericClass.getName();
    }

    /**
     * 获取SqlMapping命名空间
     *
     * @author LiuJunGuang
     * @return SqlMapping命名空间
     */
    public String getSqlNamespace() {
        return sqlNamespace;
    }

    /**
     * 设置SqlMapping命名空间。 以改变默认的SqlMapping命名空间， 不能滥用此方法随意改变SqlMapping命名空间。
     *
     * @author LiuJunGuang
     * @param sqlNamespace SqlMapping命名空间
     */
    public void setSqlNamespace(String sqlNamespace) {
        this.sqlNamespace = sqlNamespace;
    }

    /**
     * 将SqlMapping命名空间与给定的SqlMapping名组合在一起。
     *
     * @param sqlName SqlMapping名
     * @return 组合了SqlMapping命名空间后的完整SqlMapping名
     */
    protected String getSqlName(String sqlName) {
        return sqlNamespace + SQLNAME_SEPARATOR + sqlName;
    }

    public <V extends T> V selectOne(T query) {
        Assert.notNull(query);
        Map<String, Object> params = BeanUtils.toMap(query);
        return sqlSessionTemplate.selectOne(getSqlName(SQL_SELECT), params);
    }

    public <V extends T> V selectById(String id) {
        Assert.notNull(id);
        return sqlSessionTemplate.selectOne(getSqlName(SQL_SELECT_BY_ID), id);
    }

    public <V extends T> List<V> selectList(T query) {
        Map<String, Object> params = BeanUtils.toMap(query);
        return sqlSessionTemplate.selectList(getSqlName(SQL_SELECT), params);
    }

    public <V extends T> List<V> selectAll() {
        return sqlSessionTemplate.selectList(getSqlName(SQL_SELECT_All));
    }

    public <K, V extends T> Map<K, V> selectMap(T query, String mapKey) {
        Assert.notNull(mapKey, "[mapKey] - must not be null!");
        Map<String, Object> params = BeanUtils.toMap(query);
        return sqlSessionTemplate.selectMap(getSqlName(SQL_SELECT), params, mapKey);
    }

    /**
     * 设置分页
     *
     * @param pageable 分页信息
     * @return SQL分页参数对象
     */
    protected RowBounds getRowBounds(Pageable pageable) {
        RowBounds bounds = RowBounds.DEFAULT;
        if (null != pageable) {
            bounds = new RowBounds(pageable.getOffset(), pageable.getPageSize());
        }
        return bounds;
    }

    /**
     * 获取分页查询参数
     *
     * @param query 查询对象
     * @param pageable 分页对象
     * @return Map 查询参数
     */
    protected Map<String, Object> getParams(T query, Pageable pageable) {
        Map<String, Object> params = BeanUtils.toMap(query, getRowBounds(pageable));
        if (pageable != null && pageable.getSort() != null) {
            String sorting = pageable.getSort().toString();
            params.put("sorting", sorting.replace(":", ""));
        }
        return params;
    }

    public <V extends T> List<V> selectList(T query, Pageable pageable) {
        return sqlSessionTemplate.selectList(getSqlName(SQL_SELECT), getParams(query, pageable));
    }

    public <V extends T> Page<V> selectPageList(T query, Pageable pageable) {
        List<V> contentList = sqlSessionTemplate.selectList(getSqlName(SQL_SELECT), getParams(query, pageable));
        return new PageImpl<V>(contentList, pageable, this.selectCount(query));
    }

    public <K, V extends T> Map<K, V> selectMap(T query, String mapKey, Pageable pageable) {
        return sqlSessionTemplate.selectMap(getSqlName(SQL_SELECT), getParams(query, pageable), mapKey);
    }

    public Long selectCount() {
        return sqlSessionTemplate.selectOne(getSqlName(SQL_SELECT_COUNT));
    }

    public Long selectCount(T query) {
        Map<String, Object> params = BeanUtils.toMap(query);
        return sqlSessionTemplate.selectOne(getSqlName(SQL_SELECT_COUNT), params);
    }

    public void insert(T entity) {
        Assert.notNull(entity);

        sqlSessionTemplate.insert(getSqlName(SQL_INSERT), entity);
    }

    public int delete(T query) {
        Assert.notNull(query);
        Map<String, Object> params = BeanUtils.toMap(query);
        return sqlSessionTemplate.delete(getSqlName(SQL_DELETE), params);
    }

    public int deleteById(String id) {
        Assert.notNull(id);
        return sqlSessionTemplate.delete(getSqlName(SQL_DELETE_BY_ID), id);
    }

    public int deleteAll() {
        return sqlSessionTemplate.delete(getSqlName(SQL_DELETE));
    }

    public int updateById(T entity) {
        Assert.notNull(entity);
        return sqlSessionTemplate.update(getSqlName(SQL_UPDATE_BY_ID), entity);
    }

    public int updateByIdSelective(T entity) {
        Assert.notNull(entity);
        return sqlSessionTemplate.update(getSqlName(SQL_UPDATE_BY_ID_SELECTIVE), entity);
    }

    public void deleteByIdInBatch(List<String> idList) {
        if (idList == null || idList.isEmpty())
            return;
        for (String id : idList) {
            this.deleteById(id);
        }
    }

    public void updateInBatch(List<T> entityList) {
        if (entityList == null || entityList.isEmpty())
            return;
        for (T entity : entityList) {
            this.updateByIdSelective(entity);
        }
    }

    public void insertInBatch(List<T> entityList) {
        if (entityList == null || entityList.isEmpty())
            return;
        for (T entity : entityList) {
            this.insert(entity);
        }
    }
}
