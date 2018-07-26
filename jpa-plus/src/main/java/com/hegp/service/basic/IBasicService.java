package com.hegp.service.basic;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.sql.Wrapper;
import java.util.List;

public interface IBasicService<T> {

	void save(Object entity);

	boolean update(Object entity);

	boolean deleteById(Object id);

	boolean deleteByIds(Object[] ids);

	Page<T> selectByPage(Pageable pageable);

	/**
	 * @param page 起始页为0，页码
	 * @param size 每页大小
	 */
	Page<T> selectByPage(int page, int size);

	List<T> selectAll();
//	List<T> selectList(Wrapper wrapper);
}
