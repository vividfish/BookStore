package com.vivid.BookStore.category.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.vivid.BookStore.category.domain.Category;

/**
 * 分类持久层
 * 
 * @author qdmmy6
 *
 */
@Mapper
@Repository
public interface CategoryDao {

	/**
	 * 通过父分类查询子分类
	 * 
	 * @param pid
	 * @return
	 * @throws SQLException
	 */
	public List<Category> findByParent(String pid);
	// throws SQLException {
	// String sql = "select * from t_category where pid=? order by orderBy";
	// List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(),
	// pid);
	// return toCategoryList(mapList);
	// }

	/**
	 * 添加分类
	 * 
	 * @param category
	 * @throws SQLException
	 */
	public void add(Category category);
	// throws SQLException {
	// String sql = "insert into t_category(cid,cname,pid,`desc`)
	// values(?,?,?,?)";
	// /*
	// * 因为一级分类，没有parent，而二级分类有！
	// * 我们这个方法，要兼容两次分类，所以需要判断
	// */
	// String pid = null;//一级分类
	// if(category.getParent() != null) {
	// pid = category.getParent().getCid();
	// }
	// Object[] params = {category.getCid(), category.getCname(), pid,
	// category.getDesc()};
	// qr.update(sql, params);
	// }

	/**
	 * 获取所有父分类，但不带子分类的！
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Category> findParents();
	// throws SQLException {
	// /*
	// * 1. 查询出所有一级分类
	// */
	// String sql = "select * from t_category where pid is null order by
	// orderBy";
	// List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler());
	//
	// return toCategoryList(mapList);
	// }

	/**
	 * 加载分类 即可加载一级分类，也可加载二级分类
	 * 
	 * @param cid
	 * @return
	 * @throws SQLException
	 */
	public Category load(String cid);

	/**
	 * 修改分类 即可修改一级分类，也可修改二级分类
	 * 
	 * @param category
	 * @throws SQLException
	 */
	public boolean edit(Category category);
	// throws SQLException {
	// String sql = "update t_category set cname=?, pid=?, `desc`=? where
	// cid=?";
	// String pid = null;
	// if(category.getParent() != null) {
	// pid = category.getParent().getCid();
	// }
	// Object[] params = {category.getCname(), pid, category.getDesc(),
	// category.getCid()};
	// qr.update(sql, params);
	// }

	/**
	 * 查询指定父分类下子分类的个数
	 * 
	 * @param pid
	 * @return
	 * @throws SQLException
	 */
	public int findChildrenCountByParent(String pid);

	/**
	 * 删除分类
	 * 
	 * @param cid
	 * @throws SQLException
	 */
	public void delete(String cid);

}
