import java.util.*;

public class CompPojo implements Comparable<CompPojo> {
	private List<CompPojo> list;
	private int id;
	private String name;
	private int age;	

	public CompPojo(int id, String name, int age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "CompPojo [id=" + id + ", name=" + name + ", age=" + age + "]";
	}

	@Override
	public int compareTo(CompPojo o) {
		if(this.id > o.id)
			return 1;
		if(this.id < o.id)
			return -1;
		return 0;
	}
	
	public static void main(String[] args) {
		List<CompPojo> list = new LinkedList<CompPojo>();

		CompPojo cp1 = new CompPojo(0, "C", 20);
		CompPojo cp2 = new CompPojo(1, "B", 10);
		CompPojo cp3 = new CompPojo(2, "A", 30);

		list.add(cp1);
		list.add(cp2);
		list.add(cp3);
				
		System.out.println(list);
		
		Collections.sort(list, new Comparator<CompPojo>() {

			@Override
			public int compare(CompPojo cp1, CompPojo cp2) {
				return cp1.id > cp2.id ? -1 : 1;
			}
		});
		
		System.out.println(list);
	}
	
}
