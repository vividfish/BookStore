package com.vivid.BookStore.category.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vivid.BookStore.category.dao.CategoryDao;
import com.vivid.BookStore.category.domain.Category;

/**
 * 分类模块业务层
 * 
 * @author qdmmy6
 *
 */
@Service
public class CategoryService {
	@Autowired
	CategoryDao categoryDao;

	/**
	 * 查询指定父分类下子分类的个数
	 * 
	 * @param pid
	 * @return
	 */
	public int findChildrenCountByParent(String pid) {
		return categoryDao.findChildrenCountByParent(pid);
	}

	/**
	 * 删除分类
	 * 
	 * @param cid
	 */
	public void delete(String cid) {
		categoryDao.delete(cid);
	}

	/**
	 * 修改分类
	 * 
	 * @param category
	 */
	public void edit(Category category) {
		categoryDao.edit(category);
	}

	/**
	 * 加载分类
	 * 
	 * @param cid
	 * @return
	 */
	public Category load(String cid) {
		return categoryDao.load(cid);
	}

	/**
	 * 添加分类
	 * 
	 * @param category
	 */
	public void add(Category category) {
		categoryDao.add(category);
	}

	/**
	 * 查询所有分类
	 * 
	 * @return
	 */
	public List<Category> findAll() {
		List<Category> parents = categoryDao.findParents();
		for (Category parent : parents) {
			// 查询出当前父分类的所有子分类
			List<Category> children = categoryDao.findByParent(parent.getCid());
			// 设置给父分类
			parent.setChildren(children);
		}
		return parents;
	}

	/**
	 * 获取所有父分类，不带子分类
	 * 
	 * @return
	 */
	public List<Category> findParents() {
		return categoryDao.findParents();
	}

	/**
	 * 查询指定父分类下的所有子分类
	 * 
	 * @param pid
	 * @return
	 */
	public List<Category> findChildren(String pid) {
		return categoryDao.findByParent(pid);
	}
}
