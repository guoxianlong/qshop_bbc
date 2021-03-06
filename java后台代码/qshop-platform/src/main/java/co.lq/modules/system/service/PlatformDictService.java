package co.lq.modules.system.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;

import co.lq.modules.system.domain.Dict;
import co.lq.modules.system.service.dto.DictDTO;
import co.lq.modules.system.service.dto.DictQueryCriteria;

/**
 * @author billy
 * @date 2019-04-10
 */
public interface PlatformDictService {

    /**
     * 分页查询
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Map<String, Object> queryAll(DictQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部数据
     *
     * @param dict /
     * @return /
     */
    List<DictDTO> queryAll(DictQueryCriteria dict);

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    DictDTO findById(Long id);

    /**
     * 创建
     *
     * @param resources /
     * @return /
     */
    DictDTO create(Dict resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(Dict resources);

    /**
     * 删除
     *
     * @param id /
     */
    void delete(Long id);

    /**
     * 导出数据
     *
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<DictDTO> queryAll, HttpServletResponse response) throws IOException;
}
