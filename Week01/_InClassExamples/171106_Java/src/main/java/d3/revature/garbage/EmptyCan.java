package d3.revature.garbage;

public class EmptyCan {
	private int id;
	
	public EmptyCan(int id){
		this.id = id;
		System.out.println(id + " CREATED");
	}
	
	@Override
	protected void finalize() throws Throwable {
		System.out.println("\t\t" + id + " COLLECTED");
	}
}
