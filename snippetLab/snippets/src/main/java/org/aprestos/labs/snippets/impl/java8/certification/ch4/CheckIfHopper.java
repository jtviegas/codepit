package org.aprestos.labs.snippets.impl.java8.certification.ch4;

public class CheckIfHopper implements CheckTrait {
    public boolean test(Animal a) {
	return a.canHop();
    }

}
