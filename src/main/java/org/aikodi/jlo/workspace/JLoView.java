package org.aikodi.jlo.workspace;

import org.aikodi.chameleon.core.language.Language;
import org.aikodi.chameleon.core.namespace.RootNamespace;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.view.ObjectOrientedView;

public class JLoView extends ObjectOrientedView {

    public JLoView(RootNamespace namespace, Language language) {
        super(namespace, language);
    }

    @Override
    public Type topLevelType() {
        return null;
    }
}
