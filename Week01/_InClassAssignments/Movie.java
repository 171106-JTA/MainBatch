package d4.revature.collections;

	public class Movie implements Comparable<Movie>{
		
		private String title;
		private String director;
		private int runtTime;
		
		public Movie(String title, String director, int runtTime) {
			super();
			this.title = title;
			this.director = director;
			this.runtTime = runtTime;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDirector() {
			return director;
		}

		public void setDirector(String director) {
			this.director = director;
		}

		public int getRuntTime() {
			return runtTime;
		}

		public void setRuntTime(int runtTime) {
			this.runtTime = runtTime;
		}

		@Override
		public String toString() {
			return "Movie [title=" + title + ", director=" + director
					+ ", runtTime=" + runtTime + "]";
		}
		
		@Override
		public int compareTo(Movie movie) {
			if(this.getRunTime() < book.getRunTime()){
				return -1;
			}else if(this.getRunTime() > movie.getPageCount()){
				return 1;
			}
			return 0;
		}
		
		
}
