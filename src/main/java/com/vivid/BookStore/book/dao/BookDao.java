package com.vivid.BookStore.book.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.vivid.BookStore.book.domain.Book;

@Mapper
@Repository
public interface BookDao {

	public int countAll();

	/**
	 * 删除图书
	 * 
	 * @param bid
	 * @throws SQLException
	 */
	public void delete(String bid);

	/**
	 * 修改图书
	 * 
	 * @param book
	 *            String sql = "insert into
	 *            t_book(bid,bname,author,price,currPrice," +
	 *            "discount,press,publishtime,edition,pageNum,wordNum,printtime,"
	 *            + "booksize,paper,cid,image_w,image_b)" +
	 * @throws SQLException
	 */
	public void edit(Book book);

	// public void edit(Book book) throws SQLException {
	// String sql = "update t_book set bname=?,author=?,price=?,currPrice=?," +
	// "discount=?,press=?,publishtime=?,edition=?,pageNum=?,wordNum=?," +
	// "printtime=?,booksize=?,paper=?,cid=? where bid=?";
	// Object[] params = {book.getBname(),book.getAuthor(),
	// book.getPrice(),book.getCurrPrice(),book.getDiscount(),
	// book.getPress(),book.getPublishtime(),book.getEdition(),
	// book.getPageNum(),book.getWordNum(),book.getPrinttime(),
	// book.getBooksize(),book.getPaper(),
	// book.getCategory().getCid(),book.getBid()};
	// qr.update(sql, params);
	// }
	//
	/**
	 * 按bid查询
	 * 
	 * @param bid
	 * @return
	 * @throws SQLException
	 */
	public Book findByBid(String bid);
	// public Book findByBid(String bid) throws SQLException {
	// String sql = "SELECT * FROM t_book b, t_category c WHERE b.cid=c.cid AND
	// b.bid=?";
	// // 一行记录中，包含了很多的book的属性，还有一个cid属性
	// Map<String,Object> map = qr.query(sql, new MapHandler(), bid);
	// // 把Map中除了cid以外的其他属性映射到Book对象中
	// Book book = CommonUtils.toBean(map, Book.class);
	// // 把Map中cid属性映射到Category中，即这个Category只有cid
	// Category category = CommonUtils.toBean(map, Category.class);
	// // 两者建立关系
	// book.setCategory(category);
	//
	// // 把pid获取出来，创建一个Category parnet，把pid赋给它，然后再把parent赋给category
	// if(map.get("pid") != null) {
	// Category parent = new Category();
	// parent.setCid((String)map.get("pid"));
	// category.setParent(parent);
	// }
	// return book;
	// }

	/**
	 * 添加图书
	 * 
	 * @param book
	 * @throws SQLException
	 */
	public void add(Book book);
	// public void add(Book book) throws SQLException {
	// String sql = "insert into t_book(bid,bname,author,price,currPrice," +
	// "discount,press,publishtime,edition,pageNum,wordNum,printtime," +
	// "booksize,paper,cid,image_w,image_b)" +
	// " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	// Object[] params = {book.getBid(),book.getBname(),book.getAuthor(),
	// book.getPrice(),book.getCurrPrice(),book.getDiscount(),
	// book.getPress(),book.getPublishtime(),book.getEdition(),
	// book.getPageNum(),book.getWordNum(),book.getPrinttime(),
	// book.getBooksize(),book.getPaper(), book.getCategory().getCid(),
	// book.getImage_w(),book.getImage_b()};
	// qr.update(sql, params);
	// }

	public List<Book> findBook(@Param("book") Book book, @Param("pos") int pos, @Param("ps") int ps);

	public int findBookCount(Book book);
}
