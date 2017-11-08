package org.aprestos.labs.snippets.impl.junit4;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Verifier;

public class Rules {

    private static String sequence;

    public static class UsesVerifier {
      @Rule
      public final Verifier collector = new Verifier() {
        @Override
        protected void verify() {
          sequence += "verify ";
        }
      };

      @Test
      public void example() {
        sequence += "test ";
      }
      
      @Test
      public void verifierRunsAfterTest() {
        sequence = "";
        //assertThat(testResult(UsesVerifier.class), isSuccessful());
        assertEquals("test verify ", sequence);
      }

    }

}
