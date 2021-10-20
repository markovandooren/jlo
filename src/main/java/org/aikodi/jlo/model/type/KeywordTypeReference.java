///**
// *
// */
//package org.aikodi.jlo.model.type;
//
//import java.util.*;
//
//import org.aikodi.chameleon.core.declaration.Name;
//import org.aikodi.chameleon.core.element.Element;
//import org.aikodi.chameleon.core.lookup.LookupException;
//import org.aikodi.chameleon.core.lookup.LookupRedirector;
//import org.aikodi.chameleon.core.reference.CrossReferenceTarget;
//import org.aikodi.chameleon.core.reference.NameReference;
//import org.aikodi.chameleon.oo.language.LanguageWithBoxing;
//import org.aikodi.chameleon.oo.type.BoxableTypeReference;
//import org.aikodi.chameleon.oo.type.Type;
//import org.aikodi.chameleon.oo.type.generics.GenericTypeReference;
//import org.aikodi.chameleon.oo.type.generics.InstantiatedTypeParameter;
//import org.aikodi.chameleon.oo.type.generics.TypeArgument;
//import org.aikodi.chameleon.oo.type.generics.TypeParameter;
//import org.aikodi.chameleon.util.association.Multi;
//import org.aikodi.java.core.language.Java7;
//import org.aikodi.java.core.type.PureWildcard;
//
///**
// * A type reference that uses keywords for argument to type parameters.
// * Omitted keywords are filled in with a pure wildcard. The arguments must
// * be in the same order in which they appear in the parameterized type.
// *
// * @author Marko van Dooren
// */
//public class KeywordTypeReference extends GenericTypeReference {
//
//    /**
//     * Create a new type reference.
//     * @param fqn The fully qualified name of the reference type.
//     */
//    public KeywordTypeReference(String fqn) {
//        super(fqn);
//    }
//
//    /**
//     * Create a new keyword type reference.
//     * @param target A reference to the target in which the name must be looked up.
//     * @param name The name of the reference type.
//     */
//    public KeywordTypeReference(CrossReferenceTarget target, String name) {
//        super(target, name);
//    }
//
//    private final Multi<KeywordTypeArgument> _typeArguments = new Multi<>(this);
//
//    /**
//     * Return the argument with the given keyword.
//     * @param keyword The keyword for which to find the argument.
//     * @return The keyword type argument that whose keyword is the same as the given keyword.
//     */
//    public KeywordTypeArgument argument(String keyword) {
//        Optional<KeywordTypeArgument> result = explicitTypeArguments().stream().filter(a -> a.keyword().equals(keyword)).findAny();
//        if (result.isPresent()) {
//            return result.get();
//        } else {
//            throw new IllegalArgumentException("There is no keyword type reference with the given name.");
//        }
//    }
//
//    @Override
//    protected boolean canBeGenericType(Type type) {
//        return true;
//    }
//
//    public boolean hasArgument(String name) {
//        return _typeArguments.size() > 0;
//    }
//
//    public void add(KeywordTypeArgument argument) {
//        add(_typeArguments, argument);
//    }
//
//    public List<KeywordTypeArgument> explicitTypeArguments() {
//        return _typeArguments.getOtherEnds();
//    }
//
//    @Override
//    public boolean hasTypeArguments() throws LookupException {
//        return typeConstructor().parameters(TypeParameter.class).size() > 0;
//    }
//
//    @Override
//    public List<TypeArgument> typeArguments() throws LookupException {
//        List<TypeArgument> result = new ArrayList<>();
//        List<TypeParameter> typeParameters = typeConstructor().parameters(TypeParameter.class);
//        List<KeywordTypeArgument> explicitArguments = explicitTypeArguments();
//        int explicitIndex = 0;
//        for (TypeParameter typeParameter : typeParameters) {
//            KeywordTypeParameter casted = (KeywordTypeParameter) typeParameter;
//            KeywordTypeArgument explicit = explicitArguments.get(explicitIndex);
//            if (casted.keyword().equals(explicit.keyword())) {
//                result.add(explicit.argument());
//                explicitIndex++;
//            } else {
//                TypeArgument wildcard = new PureWildcard();
//                wildcard.setUniParent(this);
//                result.add(wildcard);
//            }
//        }
//        return result;
//    }
//
//    @Override
//    public void addArgument(TypeArgument argument) {
//
//    }
//
//    @Override
//    public Type getElement() throws LookupException {
//        JLoType typeConstructor = (JLoType) super.getElement();
//        Type result = new JLoTypeRefinement(typeConstructor);
//        for (KeywordTypeArgument a : explicitTypeArguments()) {
//            // The name is wrong. It won't be found!
//            NameReference<TypeMemberDeclarator> nameReference = new NameReference<>(a.keyword(), TypeMemberDeclarator.class);
//            // The constructor sets the unidirectional parent of the name reference.
//            new LookupRedirector(typeConstructor, nameReference);
//            TypeMemberDeclarator element = nameReference.getElement();
//            TypeParameter typeArgument = new InstantiatedTypeParameter(element.parameter().name(), a.argument());
//            TypeMemberDeclarator declarator = new TypeMemberDeclarator(new Name(a.keyword()), typeArgument);
//            result.add(declarator);
//        }
//        return result;
//    }
//
//    @Override
//    public String toString() {
//        return toString(new HashSet<>());
//    }
//
//    /**
//     * @{inheritDoc}
//     */
//    @Override
//    public String toString(Set<Element> visited) {
//        StringBuilder builder = new StringBuilder(super.toString(visited));
//        builder.append(' ');
//        explicitTypeArguments().forEach(a -> builder.append(a.toString(visited)));
//        return builder.toString();
//    }
//
//    /**
//     * @{inheritDoc}
//     */
//    @Override
//    public KeywordTypeReference cloneSelf() {
//        return new KeywordTypeReference(null);
//    }
//
//}
