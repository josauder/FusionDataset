package de.kdld16.hpi.resolver;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by jonathan on 24.11.16.
 */
public class ResolverChooserTest {

    @Test
    public void testResolverChooser(){

        assertTrue(ResolverChooser.getResolver("<>",0).getClass()==ModeResolver.class);

    }

}
