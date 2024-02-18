package com.finance.controller;

import cn.hutool.core.util.ObjectUtil;
import com.common.core.pojo.CommonData;
import com.common.core.pojo.PageBody;
import com.finance.pojo.base.Entry;
import com.finance.pojo.dto.EntryDTO;
import com.finance.pojo.vo.EntryVO;
import com.finance.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Lilopop
 * @description:
 */
@RestController
@RequestMapping("/entry")
public class EntryController {

    @Autowired
    private EntryService entryService;

    @PostMapping("/save")
    public CommonData<EntryVO> save(EntryDTO dto){
        EntryVO vo = entryService.save(dto);
        return new CommonData<>(200,"success",vo);
    }

    @PutMapping("/update")
    public CommonData<EntryVO> update(EntryDTO dto){
        EntryVO vo = entryService.update(dto);
        return new CommonData<>(200,"success",vo);
    }

    @DeleteMapping("/del")
    public CommonData<EntryVO> del(Long id,Integer del){
        EntryVO vo = entryService.delete(id,del);
        return new CommonData<>(200,"success",vo);
    }

    @GetMapping("/l")
    public CommonData<PageBody<EntryVO>> getAll(Integer pageNum, Integer pageSize){
        PageBody<EntryVO> page = entryService.listAll(pageNum-1,pageSize);
        return new CommonData<>(200,"success",page);
    }

    @GetMapping("/la")
    public CommonData<List<EntryVO>> getAll(){
        List<EntryVO> vos = entryService.listAll();
        return new CommonData<>(200,"success",vos);
    }

    @GetMapping("/lbyn")
    public CommonData<PageBody<EntryVO>> getByName(Integer pageNum, Integer pageSize,String name){
        if (ObjectUtil.isEmpty(name)){
            return new CommonData<>(201,"empty message",null);
        }
        PageBody<EntryVO> page = entryService.listByName(pageNum-1,pageSize,name);
        return new CommonData<>(200,"success",page);
    }

    @GetMapping("/lbyf")
    public CommonData<PageBody<EntryVO>> getFirst(Integer pageNum, Integer pageSize){
        PageBody<EntryVO> page = entryService.listByFirst(pageNum-1, pageSize);
        return new CommonData<>(200,"success",page);
    }

    @GetMapping("/lbyfa")
    public CommonData<List<EntryVO>> getFirstAll(){
        List<EntryVO> vos = entryService.listByFirst();
        return new CommonData<>(200,"success",vos);
    }
}
