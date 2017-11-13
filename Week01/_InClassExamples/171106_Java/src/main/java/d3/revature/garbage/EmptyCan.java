package d3.revature.garbage;

public class EmptyCan {
	private int id;
<<<<<<< HEAD
	public EmptyCan(int id) {
		this.id = id;
		System.out.println(id + " CREATED.");
	}
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("\t\t" + id + "collected");
=======
	
	public EmptyCan(int id){
		this.id = id;
		System.out.println(id + " CREATED");
	}
	
	@Override
	protected void finalize() throws Throwable {
		System.out.println("\t\t" + id + " COLLECTED");
>>>>>>> 908c4a08b3cf9c5cb65e60015f7c54cf564145ce
	}
}
