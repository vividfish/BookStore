package com.vivid.BookStore.category.dao;

import java.sql.SQLException;
import java.util.List;

import com.vivid.BookStore.category.domain.Category;

/**
 * 分类持久层
 * 
 * @author qdmmy6
 *
 */
public interface CategoryDao {

	/*
	 * 把一个Map中的数据映射到Category中
	 */
	// Category toCategory(Map<String, Object> map);
	// {
	// /*
	// * map {cid:xx, cname:xx, pid:xx, desc:xx, orderBy:xx}
	// * Category{cid:xx, cname:xx, parent:(cid=pid), desc:xx}
	// */
	// Category category = CommonUtils.toBean(map, Category.class);
	// String pid = (String)map.get("pid");// 如果是一级分类，那么pid是null
	// if(pid != null) {//如果父分类ID不为空，
	// /*
	// * 使用一个父分类对象来拦截pid
	// * 再把父分类设置给category
	// */
	// Category parent = new Category();
	// parent.setCid(pid);
	// category.setParent(parent);
	// }
	// return category;
	// }

	/*
	 * 可以把多个Map(List<Map>)映射成多个Category(List<Category>)
	 */
	// private List<Category> toCategoryList(List<Map<String,Object>> mapList) {
	// List<Category> categoryList = new ArrayList<Category>();//创建一个空集合
	// for(Map<String,Object> map : mapList) {//循环遍历每个Map
	// Category c = toCategory(map);//把一个Map转换成一个Category
	// categoryList.add(c);//添加到集合中
	// }
	// return categoryList;//返回集合
	// }



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
