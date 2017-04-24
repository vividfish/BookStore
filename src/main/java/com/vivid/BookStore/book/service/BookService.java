package com.vivid.BookStore.book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vivid.BookStore.book.dao.BookDao;
import com.vivid.BookStore.book.domain.Book;
import com.vivid.BookStore.category.domain.Category;
import com.vivid.BookStore.pager.PageConstants;
import com.vivid.BookStore.pager.domain.Page;

@Service
public class BookService {
	int ps = PageConstants.BOOK_PAGE_SIZE;

	@Autowired
	private BookDao bookDao;

	/**
	 * 删除图书
	 * 
	 * @param bid
	 */
	public void delete(String bid) {
		bookDao.delete(bid);
	}

	/**
	 * 修改图书
	 * 
	 * @param book
	 */
	public void edit(Book book) {
		bookDao.edit(book);
	}

	/**
	 * 加载图书
	 * 
	 * @param bid
	 * @return
	 */
	public Book load(String bid) {
		return bookDao.findByBid(bid);
	}

	/**
	 * 按分类查
	 * 
	 * @param cid
	 * @param pc
	 * @return
	 */
	public int findBookCountByCategory(String cid) {
		Book book = new Book();
		Category category = new Category();
		category.setCid(cid);
		book.setCategory(category);
		return bookDao.findBookCount(book);
	}

	public Page<Book> findByCategory(String cid, int pc) {
		Book book = new Book();
		Category category = new Category();
		category.setCid(cid);
		book.setCategory(category);
		Page<Book> page = findByCriteria(book, pc);
		return page;
	}

	private Page<Book> findByCriteria(Book book, int pc) {
		int count = bookDao.findBookCount(book);
		List<Book> list = bookDao.findBook(book, (pc - 1) * ps, ps);
		Page<Book> pb = new Page<Book>();

		pb.setBeanList(list);
		pb.setPc(pc);
		pb.setPs(ps);
		pb.setTr(count);
		return pb;
	}

	/**
	 * 按书名查
	 * 
	 * @param bname
	 * @param pc
	 * @return
	 */
	public Page<Book> findByBname(String bname, int pc) {
		Book book = new Book();
		book.setBname(bname);
		Page<Book> page = findByCriteria(book, pc);
		return page;
	}

	/**
	 * 按作者查
	 * 
	 * @param author
	 * @param pc
	 * @return
	 */
	public Page<Book> findByAuthor(String author, int pc) {
		Book book = new Book();
		book.setAuthor(author);
		Page<Book> page = findByCriteria(book, pc);
		return page;
	}

	/**
	 * 按出版社查
	 * 
	 * @param press
	 * @param pc
	 * @return
	 */
	public Page<Book> findByPress(String press, int pc) {
		Book book = new Book();
		book.setPress(press);
		Page<Book> page = findByCriteria(book, pc);
		return page;
	}

	/**
	 * 多条件组合查询
	 * 
	 * @param criteria
	 * @param pc
	 * @return
	 */
	public Page<Book> findByCombination(Book book, int pc) {
		Page<Book> page = findByCriteria(book, pc);
		return page;
	}

	/**
	 * 添加图书
	 * 
	 * @param book
	 */
	public void add(Book book) {
		bookDao.add(book);
	}

	public Book finByBid(String bid) {
		return bookDao.findByBid(bid);
	}

}
