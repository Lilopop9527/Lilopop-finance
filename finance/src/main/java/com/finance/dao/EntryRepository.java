package com.finance.dao;

import com.finance.pojo.base.Entry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntryRepository extends JpaRepository<Entry,Long> {

    List<Entry> searchEntriesByParent(Long parent);
    Entry findEntryById(Long id);
    Page<Entry> searchEntriesByParent(Long parent, Pageable pageable);
    Page<Entry> searchEntriesByNameIsLike(String name,Pageable pageable);
    Entry searchEntryByNameAndParentAndDeleted(String name,Long parent,Integer deleted);
    List<Entry> searchEntriesByParentAndDeleted(Long parent,Integer deleted);
    List<Entry> searchEntriesByDeleted(Integer deleted);
}
