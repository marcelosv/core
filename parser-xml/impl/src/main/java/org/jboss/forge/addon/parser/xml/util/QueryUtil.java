/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.parser.xml.util;

import org.jboss.forge.addon.parser.xml.Node;
import org.jboss.forge.addon.parser.xml.NodeImpl;
import org.jboss.forge.addon.parser.xml.Pattern;
import org.jboss.forge.addon.parser.xml.query.PatternImpl;
import org.jboss.forge.addon.parser.xml.query.Query;

/**
 * Helper util for building {@link Query} implementations
 * 
 * @author <a href="mailto:andrew.rubinger@jboss.org">ALR</a>
 */
public final class QueryUtil
{

   // -------------------------------------------------------------------------------------||
   // Constructor ------------------------------------------------------------------------||
   // -------------------------------------------------------------------------------------||

   /**
    * No instances allowed
    */
   private QueryUtil()
   {
      throw new UnsupportedOperationException("No instances");
   }

   // -------------------------------------------------------------------------------------||
   // Functional Methods -----------------------------------------------------------------||
   // -------------------------------------------------------------------------------------||

   /**
    * Validates input
    * 
    * @param node
    * @param patterns
    * @throws IllegalArgumentException If the {@link NodeImpl} is not specified or no {@link PatternImpl}s are specified
    */
   public static void validateNodeAndPatterns(final Node node, final Pattern... patterns)
            throws IllegalArgumentException
   {
      // Precondition checks
      if (node == null)
      {
         throw new IllegalArgumentException("node must be specified");
      }
      if ((patterns == null) || (patterns.length == 0))
      {
         throw new IllegalArgumentException("At least one pattern must be specified");
      }

      // OK
   }
}
