package d4.revature.collections;

<<<<<<< HEAD
public class Book implements Comparable<Book> {
	private String name;
	private String author;
	private int pageCount;
	
	
	public Book(String name, String author, int pageCount) {
		super();
		this.name = name;
		this.author = author;
		this.pageCount = pageCount;
	}
	
=======
public class Book implements Comparable<Book>{
	
	private String name;
	private String author;
	private int pageCount;
>>>>>>> 2d976666d41267e0920e072e3c4d76944d25cc5e
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
<<<<<<< HEAD
	/*
	 * In a caompareTo method, you need to return one of 3 things:
	 * a neg number, zero, or a positive number. 
	 * 
	 * */
	@Override
	public int compareTo(Book book) {
		if(this.getPageCount()< book.getPageCount()) {
			return -1;
		}else if(this.getPageCount() > book.getPageCount()) {
			return 1;
		}
		return 0;
	}
	@Override
	public String toString() {
		return "Book [name=" + name + ", author=" + author + ", pageCount=" + pageCount + "]";
	}

=======
>>>>>>> 2d976666d41267e0920e072e3c4d76944d25cc5e
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
<<<<<<< HEAD
=======
	public Book(String name, String author, int pageCount) {
		super();
		this.name = name;
		this.author = author;
		this.pageCount = pageCount;
	}
	
	@Override
	public String toString() {
		return "Book [name=" + name + ", author=" + author + ", pageCount=" + pageCount + "]";
	}
	/*
	 * In a compareTo method, you need to return one of 3 things:
	 * a negative number, zero, or a positive number.
	 */
	@Override
	public int compareTo(Book book) {
		if(this.getPageCount() < book.getPageCount()){
			return -1;
		}else if(this.getPageCount() > book.getPageCount()){
			return 1;
		}
		return 0;
	}
	
>>>>>>> 2d976666d41267e0920e072e3c4d76944d25cc5e
	
}
