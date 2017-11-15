package extra.revature.lambdas;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class LambdasAndStreams {

	public static void main(String[] args) {
		PerformMath addition = (ca, d) -> (ca + d);
		PerformMath subtraction = (a,b) -> (a - b);
		/*
		 * A lambda expression is an expression that makes use of '->'
		 * to define that reference at run time. In this case, we provided
		 * implementation to the signature in the functional interface, 
		 * 'PerformMath'
		 * 
		 * Lambda syntax goes as follows:
		 * 
		 * (parameters to be used) -> (expression to be executed)
		 * Java will determine how to use them, and what gets returned for you.
		 * If you want, you can let Java also determine what datatypes should be
		 * used.
		 * 
		 * Note: if you only have one parameter, parenthesis on the left are
		 * optional: e.g.  a -> (a*a)
		 * And if you want to use NO parameters, parenthesis' are mandatory.
		 * e.g. () -> syso("Stuff");
		 */
		
		System.out.println(addition.operation(2, 5));
		System.out.println(subtraction.operation(2, 5));
		
		PrintString ps = m -> System.out.println("Hello, " + m);
		
		ps.printMessage("I am lambda function!");
		
		/*
		 * Method References
		 * -A method reference provides a way to call a specific method
		 * -on each element of a collection.
		 * A few syntaxes include:
		 * (class::method)
		 * (class::new) <- in the case we want to provide a new instance each time
		 * 
		 */
		
		List<Integer> nums = Arrays.asList(5,2,1,7,8);
		for(Integer i : nums){
			System.out.println(i);
		}
		
		System.out.println("=========");
		
		nums.forEach(System.out::println);
		
		System.out.println("======Sorted=======");
		System.out.println(nums.stream().sorted().collect(Collectors.toList()));
		/*
		 * We take our list: nums
		 * -We take it as a stream. This means we go through each element 
		 * sequentially. Provides a way to perform aggregate functions on them.
		 * -We call sorted(). This is a method that applies to each element of the stream.
		 * -We call collect(), this as the name suggests, collects all the altered elements.
		 * --Inside of it, we provide a way to combine our collection, using Collectors.
		 * --In this case, a list.
		 */
		
		//System.out.println(nums.stream().sorted().filter(n -> n%2==0).collect(Collectors.toList()));
		System.out.println(nums.stream().sorted().filter(Integer -> Integer%2==0).collect(Collectors.toList()));

		/*
		 *In streams we can use the filter() method to determine which values are omitted from the final
		 *result of the transaction. A filter takes something called a 'Predicate' as input. Predicate is a
		 *java8 class that serves to be a test method. IE, each element in the stream must pass the Predicate's
		 *test in order to be used. 
		 *We can either write an implicit predicate via lambda expression, or we can make our own predicate
		 *class. 
		 */	
		
		List<Employee> emps = Arrays.asList(
					new Employee("bobbert", 93, 123),
					new Employee("Little timmy", 75, 15),
					new Employee("jimmy", 20, 100),
					new Employee("Adam", 40, 1),
					new Employee("Debbie", 33, 12)
				);
		System.out.println(emps.stream().filter(is21OrOlder()).collect(Collectors.toList()));
		
		/*
		 * In the case of dynamic predicates, we can use the same method to perform different
		 * tests.
		 */
		System.out.println(nums);
		System.out.println(sumAll(nums,bobbert->bobbert%2==1));
		System.out.println(sumAll(nums,num -> true));
		System.out.println(sumAll(nums,num -> num<2));
		
		//Maps are used to take each element and change it to a result
		System.out.println(nums.stream().map(i -> i*i).collect(Collectors.toList()));
		
		//Statistics libraries
		//We have a statistics library where we can perform data analysis of collections.
		IntSummaryStatistics summary = nums.stream().map(i -> i*i*i).mapToInt(i -> i).summaryStatistics();
		
		System.out.println(summary.getAverage());
		System.out.println(summary.getMax());
		
	}
	
	public static Predicate<Employee> is21OrOlder(){
		return p -> p.getAge()>20 && p.getAge()<80;
	}
	
	public static int sumAll(List<Integer> nums, Predicate<Integer> p){
		int total = 0;
		for(int num: nums){
			if(p.test(num)){
				total += num;
			}
		}
		return total;
	}

}
