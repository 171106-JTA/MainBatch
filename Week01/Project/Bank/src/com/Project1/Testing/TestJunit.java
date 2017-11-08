package com.Project1.Testing;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/*
 * From Tutorial located at
 * https://github.com/junit-team/junit4/wiki/Download-and-Install
 * Access Date: 11/7/2017
 */

public class TestJunit {
   @Test
	
   public void testAdd() {
      String str = "Junit is working fine";
      assertEquals("Junit is working fine",str);
   }
}
