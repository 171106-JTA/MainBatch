package d4.revature.collections;

public class VideoGame implements Comparable<VideoGame>{
	private int price;
	private String title;
	private int publishingDate;
	
	public VideoGame(int price, String title, int sku) {
		super();
		this.price = price;
		this.title = title;
		this.publishingDate = sku;
	}
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public int getPublishingDate() {
		return publishingDate;
	}

	public void setPublishingDate(int publishingDate) {
		this.publishingDate = publishingDate;
	}

	@Override
	public int compareTo(VideoGame vg) {
		// TODO Auto-generated method stub
		
		if(this.getPublishingDate() < vg.getPublishingDate()) {
			return -1;
		}
		else if(this.getPublishingDate() > vg.getPublishingDate()) {
			return 1;
		}
		return 0;
	}


}
