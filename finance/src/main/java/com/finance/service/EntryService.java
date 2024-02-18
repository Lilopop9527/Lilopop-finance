package com.finance.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.common.core.exception.Asserts;
import com.common.core.pojo.PageBody;
import com.finance.dao.EntryRepository;
import com.finance.pojo.base.Entry;
import com.finance.pojo.dto.EntryDTO;
import com.finance.pojo.vo.EntryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author: Lilopop
 * @description:
 */
@Service
public class EntryService {
    @Autowired
    private EntryRepository entryRepository;

    /**
     * 保存条目信息
     * @param entry 条目信息
     */
    public synchronized EntryVO save(EntryDTO entry){
        Entry e = entryRepository.searchEntryByNameAndParentAndDeleted(entry.getName(), entry.getParent(),1);
        if (ObjectUtil.isNotEmpty(e)){
            Asserts.fail("已存在相同条目");
        }
        e = new Entry();
        BeanUtil.copyProperties(entry,e);
        e.setDeleted(0);
        Entry save = entryRepository.save(e);
        return createVO(save);
    }

    /**
     * 更新条目信息
     * @param dto 已更新的信息
     * @return 更新后的信息
     */
    public synchronized EntryVO update(EntryDTO dto){
        Entry e = entryRepository.findEntryById(dto.getId());
        if (ObjectUtil.isEmpty(e))
            Asserts.fail("未找到指定对象");
        e.setName(dto.getName());
        e.setParent(dto.getParent());
        Entry save = entryRepository.save(e);
        return createVO(save);
    }

    /**
     * 根据条目id设置状态位
     * @param id 条目id
     */
    public EntryVO delete(Long id,Integer del){
        Entry entry = entryRepository.findEntryById(id);
        if (ObjectUtil.isEmpty(entry))
            Asserts.fail("未找到指定条目信息");
        if (!Objects.equals(entry.getDeleted(), del))
            return null;
        entry.setDeleted(del == 1?0:1);
        Entry e = entryRepository.save(entry);
        return createVO(e);
    }

    /**
     * 分页查询所有条目信息
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @return 查询结果
     */
    public PageBody<EntryVO> listAll(Integer pageNum, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        List<EntryVO> vos = new ArrayList<>();
        //Map<Long,List<Entry>> map = new HashMap<>();
        Page<Entry> page = entryRepository.findAll(pageable);
        for (Entry e:page.getContent()) {
            vos.add(createVO(e));
        }
//        for (Entry e:map.get(0L)) {
//            EntryVO entryVO = new EntryVO(e.getId(),e.getName(),e.getParent());
//            entryVO.setChild(createVOs(map.getOrDefault(e.getId(),new ArrayList<>())));
//            vos.add(entryVO);
//        }
        vos.sort((a,b)->a.getId()>b.getId()?1:-1);
        return new PageBody<>(page.getTotalElements(),vos,pageNum+1,pageSize);
    }

    /**
     * 获取所有生效的条目信息
     * @return 结果集
     */
    public List<EntryVO> listAll(){
        List<Entry> entries = entryRepository.searchEntriesByDeleted(0);
        List<EntryVO> vos = createVOs(entries);
        vos.sort((a,b)->a.getId()>b.getId()?1:-1);
        return vos;
    }


    /**
     * 根据条目名称查询结果
     * @param pageNum 页码
     * @param pageSize 每页内容
     * @param name 条目名关键字
     * @return 查询结果
     */
    public PageBody<EntryVO> listByName(Integer pageNum, Integer pageSize,String name){
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<Entry> page = entryRepository.searchEntriesByNameIsLike(name,pageable);
        List<EntryVO> vos = new ArrayList<>();
        for (Entry e:page.getContent()) {
            vos.add(createVO(e));
        }
        return new PageBody<>(page.getTotalElements(),vos,pageNum+1,pageSize);
    }

    /**
     * 只搜索一级条目
     * @param pageNum 页码
     * @param pageSize 每页容量
     * @return 查询结果
     */
    public PageBody<EntryVO> listByFirst(Integer pageNum,Integer pageSize){
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<Entry> page = entryRepository.searchEntriesByParent(0L,pageable);
        List<EntryVO> vos = new ArrayList<>();
        for (Entry e:page.getContent()) {
            EntryVO vo = createVO(e);
            vos.add(vo);
        }
        return new PageBody<>(page.getTotalElements(),vos,pageNum+1,pageSize);
    }

    /**
     * 获取所有生效的一级列表
     * @return 所有一级列表
     */
    public List<EntryVO> listByFirst(){
        List<Entry> entries = entryRepository.searchEntriesByParentAndDeleted(0L, 0);
        if (CollectionUtil.isEmpty(entries))
            Asserts.fail("未找到一级条目");
        return createVOs(entries);
    }

    public EntryVO createVO(Entry entry){
        EntryVO vo = new EntryVO();
        BeanUtil.copyProperties(entry,vo);
        return vo;
    }

    public List<EntryVO> createVOs(List<Entry> vos){
        List<EntryVO> list = new ArrayList<>();
        for (Entry v:vos) {
            list.add(createVO(v));
        }
        return list;
    }
}
