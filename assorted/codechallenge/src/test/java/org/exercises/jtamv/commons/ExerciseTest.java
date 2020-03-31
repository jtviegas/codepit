package org.exercises.jtamv.commons;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class ExerciseTest {

    private Exercise o;
    @Before
    public void setUp() throws Exception {
	o = new Exercise();
    }

    @Test
    public void test() {
	
	o.doit();
	fail("Not yet implemented");
    }

}
