package org.aikodi.jlo.model.type;

import org.aikodi.chameleon.oo.type.generics.FormalTypeParameter;
import org.aikodi.contract.Contract;

import static org.aikodi.contract.Contract.require;
import static org.aikodi.contract.Contract.requireNotNull;

/**
 * A type parameter that has a keyword that is used when instantiating the parent
 * parameterized type.
 */
public class KeywordTypeParameter extends FormalTypeParameter {
    /**
     * Create a formal type parameter with the same name.
     *
     * @param name The name of the type parameter.
     */
    public KeywordTypeParameter(String name, String keyword) {
        super(name);
        requireNotNull(keyword);
        require(!keyword.isEmpty(), "The keyword cannot be an empty string.");

        _keyword = keyword;
    }

    @Override
    protected KeywordTypeParameter cloneSelf() {
        return new KeywordTypeParameter(name(), keyword());
    }

    /**
     * Return the keyword of this type parameter.
     *
     * @return Not null or the empty string.
     */
    public String keyword() {
        return _keyword;
    }

    /**
     * The keyword of this type parameter. Not null or the empty string.
     */
    private String _keyword;
}
