package d4.revature.collections;

import java.util.Comparator;

public class VideoGamePriceComparator implements Comparator<VideoGame> {
	
	public int compare(VideoGame v1, VideoGame v2) {
		if(v1.getPrice() < v2.getPrice()) {
			return -1;
		}
		else if(v1.getPrice() > v2.getPrice()){
			return 1;
		}
		return 0;
	};
}
