package d4.revature.collections;

import java.util.Comparator;

public class MovieComparator implements Comparator<Movie>{

	@Override
	public int compare(Movie b1, Movie b2) {
		return b1.getRuntTime().compareTo(b2.getRunTime());
	}

}
