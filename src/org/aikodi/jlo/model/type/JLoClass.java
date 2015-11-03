package org.aikodi.jlo.model.type;

import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.TypeFixer;

public class JLoClass extends RegularJLoType {

  public JLoClass(String name) {
    super(name);
  }

  @Override
  public boolean sameAs(Type other, TypeFixer trace) throws LookupException {
    return false;
  }
}
