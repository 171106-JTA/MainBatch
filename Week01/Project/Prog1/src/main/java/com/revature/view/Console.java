package com.revature.view;

import java.io.PrintStream;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.revature.model.Properties;

public class Console {
	private static Console console;
	private static Logger log = Logger.getLogger(Console.class);

	private Console() {
		super();
	}

	public static Console getInstance() {
		if (console == null)
			console = new Console();
		return console;
	}

	public static void printPage(String[] options) {
		for (int i = 0; i < options.length; i++) {
			System.out.println(i + ": " + options[i]);
		}
	}

	public static void print(Object o, int priority) {
		System.out.println(o);
		log(o, priority);
	}

	public static void print(PrintStream ps, Throwable t, int...priority) {
		ps.println(t.getMessage());
		if (Properties.DEBUG)
			t.printStackTrace();
		Console.logErr(t, priority);
	}

	public static void printFinances(double balance, double interest, int limit, int...priority) {
		int level;
			level = priority.length > 0 ? priority[0] : Level.INFO_INT;
		System.out.println(Properties.BALANCE_STR + balance);
		System.out.println(Properties.CRED_LIM_STR + limit);
		System.out.println(Properties.CRED_INT_STR + interest);
		log(Properties.BALANCE_STR + balance + "\n" + Properties.CRED_LIM_STR + limit + "\n" + Properties.CRED_INT_STR
				+ interest, level);
	}

	public static <K, V> void printMap(Map<K, V> map) {
		if (map == null || map.isEmpty()) {
			System.out.println("empty");
			return;
		}

		for (K key : map.keySet()) {
			System.out.println(key + ": " + map.get(key));
		}
	}

	private static void log(Object o, int priority) {
		log.log(Level.toLevel(priority), o);
	}

	private static void logErr(Throwable t, int... priority) {
		if (priority.length > 0)
			log.log(Level.toLevel(priority[0]), "", t);
		else
			log.error("", t);
	}

	public static void logFinances(double balance, double interest, int penalty, int limit, int priority) {
		String logStr = "balance: " + balance + " credit limit " + limit + " interest " + interest + " penalty "
				+ penalty;
		log(logStr, priority);
	}
}