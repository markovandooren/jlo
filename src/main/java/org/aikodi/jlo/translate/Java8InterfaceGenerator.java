package org.aikodi.jlo.translate;

import java.util.List;

import org.aikodi.chameleon.core.declaration.Declaration;
import org.aikodi.chameleon.core.document.Document;
import org.aikodi.chameleon.core.element.Element;
import org.aikodi.chameleon.core.lookup.LookupException;
import org.aikodi.chameleon.core.property.StaticChameleonProperty;
import org.aikodi.chameleon.core.reference.CrossReference;
import org.aikodi.chameleon.exception.ChameleonProgrammerException;
import org.aikodi.chameleon.oo.expression.Expression;
import org.aikodi.chameleon.oo.expression.ExpressionFactory;
import org.aikodi.chameleon.oo.expression.MethodInvocation;
import org.aikodi.chameleon.oo.expression.NameExpression;
import org.aikodi.chameleon.oo.method.*;
import org.aikodi.chameleon.oo.plugin.ObjectOrientedFactory;
import org.aikodi.chameleon.oo.statement.Block;
import org.aikodi.chameleon.oo.type.DeclarationWithType;
import org.aikodi.chameleon.oo.type.Type;
import org.aikodi.chameleon.oo.type.generics.TypeParameter;
import org.aikodi.chameleon.oo.type.inheritance.SubtypeRelation;
import org.aikodi.chameleon.oo.variable.FormalParameter;
import org.aikodi.chameleon.oo.variable.VariableDeclaration;
import org.aikodi.chameleon.support.expression.AssignmentExpression;
import org.aikodi.chameleon.support.member.simplename.method.NormalMethod;
import org.aikodi.chameleon.support.member.simplename.method.RegularMethodInvocation;
import org.aikodi.chameleon.support.member.simplename.variable.MemberVariableDeclarator;
import org.aikodi.chameleon.support.modifier.Abstract;
import org.aikodi.chameleon.support.modifier.Constructor;
import org.aikodi.chameleon.support.modifier.Interface;
import org.aikodi.chameleon.support.modifier.Public;
import org.aikodi.chameleon.support.modifier.Static;
import org.aikodi.chameleon.support.statement.ReturnStatement;
import org.aikodi.chameleon.support.statement.StatementExpression;
import org.aikodi.chameleon.support.variable.LocalVariableDeclarator;
import org.aikodi.chameleon.util.Util;
import org.aikodi.java.core.expression.invocation.ConstructorInvocation;
import org.aikodi.java.core.language.Java7;
import org.aikodi.java.core.method.JavaMethod;
import org.aikodi.java.core.modifier.Default;
import org.aikodi.java.core.type.ArrayTypeReference;
import org.aikodi.java.core.type.BasicJavaTypeReference;
import org.aikodi.java.core.type.JavaEqualityTypeArgument;
import org.aikodi.jlo.model.expression.OuterTarget;
import org.aikodi.jlo.model.subobject.Subobject;
import org.aikodi.jlo.model.subobject.SubobjectType;
import org.aikodi.jlo.model.type.TypeMemberDeclarator;
import org.aikodi.rejuse.action.Nothing;
import org.aikodi.rejuse.action.UniversalConsumer;

import javax.print.Doc;

public class Java8InterfaceGenerator extends AbstractJava8Generator {

    public Document createInterface(Document javaDocument) throws LookupException {
        changeClassesToInterfaces(javaDocument);
        replaceSubobjects(javaDocument);
        replaceSubobjectReferences(javaDocument);
        replaceFields(javaDocument);
        makeNonPrivateMethodsPublic(javaDocument);
        renameConstructorCalls(javaDocument);
        inferMissingReturnTypes(javaDocument);
        replaceExpressionImplementations(javaDocument);
        // makeImplicitlyAbstractMethodsAbstract(result);
        makeMethodsDefault(javaDocument);
        createConstructors(javaDocument);
        createDefaultConstructor(javaDocument);
        addTypeParameterToOwnClass(javaDocument);
        addOuterMethod(javaDocument);
        replaceOuterTargets(javaDocument);
        // transformKeywordTypeReferences(javaDocument);
        return javaDocument;
    }

    protected void replaceOuterTargets(Document javaDocument) {
        javaDocument.lexical().apply(new UniversalConsumer<OuterTarget, Nothing>(OuterTarget.class) {
            @Override
            public void accept(OuterTarget target) throws Nothing {
                RegularMethodInvocation invocation = new RegularMethodInvocation("outer", null);
                target.parentLink().getOtherRelation().replace(target.parentLink(), invocation.parentLink());
            }
        });
    }

    protected void createConstructors(Document javaDocument) throws LookupException {
        javaDocument.lexical().apply(new UniversalConsumer<Method, LookupException>(Method.class) {
            /**
             * @{inheritDoc}
             */
            @Override
            public void accept(Method javaMethod) throws LookupException {
                Method jloMethod = (Method) javaMethod.origin();
                if (!isGenerated(javaMethod) && jloMethod.isTrue(jlo(jloMethod).CONSTRUCTOR())) {
                    Java7 java = java(javaDocument);
                    Type javaParentType = javaMethod.lexical().nearestAncestor(Type.class);
                    if (jloMethod.nbFormalParameters() == 0) {
                        createMainInterface(javaMethod);
                    }

                    strip(Constructor.class).from(Method.class).in(javaMethod);
                    Method javaInstanceMethod = Java8InterfaceGenerator.this.clone(javaMethod);
                    javaParentType.add(javaInstanceMethod);
                    javaInstanceMethod.setName(constructorName(javaMethod));
                    javaInstanceMethod.addModifier(new Default());
                    javaInstanceMethod.setReturnTypeReference(java.createTypeReference("void"));

                    // The static method returns an object of the parent type.
                    // But we still need to add the type parameter to it.
                    BasicJavaTypeReference typeRef = java.createTypeReference(javaParentType.name());
                    javaMethod.setReturnTypeReference(typeRef);

                    BasicJavaTypeReference implementationTypeReference = java.createTypeReference(implementationName(javaParentType));

                    addTypeParameters(java, typeRef, (Type) javaParentType.origin());
                    addTypeParameters(java, implementationTypeReference, (Type) javaParentType.origin());
                    addTypeParameters(java, javaMethod.header(), (Type) javaParentType.origin());

//          ((Type) javaParentType.origin()).members(TypeMemberDeclarator.class).forEach(m -> {
//            javaMethod.header().addTypeParameter(m.clone(m.parameter()));
//            typeRef.addArgument(new JavaEqualityTypeArgument(java.createTypeReference(m.parameter().name())));
//            implementationTypeReference.addArgument(new JavaEqualityTypeArgument(java.createTypeReference(m.parameter().name())));
//          });

                    javaMethod.addModifier(new Static());


                    /**
                     * Overwrite body:
                     *
                     * T result = new T(); result.init$... return result;
                     */
                    Block javaBody = new Block();
                    LocalVariableDeclarator localVariableDeclarator = new LocalVariableDeclarator(typeRef.clone(typeRef));
                    VariableDeclaration declaration = new VariableDeclaration(resultVariableName());

                    declaration.setInitialization(new ConstructorInvocation(implementationTypeReference, null));
                    localVariableDeclarator.add(declaration);
                    javaBody.addStatement(localVariableDeclarator);
                    MethodInvocation invocation = expressionFactory(javaMethod).createInvocation(constructorName(javaMethod),
                            new NameExpression(resultVariableName()));
                    for (FormalParameter parameter : javaMethod.formalParameters()) {
                        invocation.addArgument(new NameExpression(parameter.name()));
                    }
                    javaBody.addStatement(new StatementExpression(invocation));
                    javaBody.addStatement(new ReturnStatement(new NameExpression(resultVariableName())));
                    ((RegularImplementation) ((NormalMethod) javaMethod).implementation()).setBody(javaBody);
                }
            }
        });
    }

    protected void createMainInterface(Method javaMethod) {
        Java7 java = java(javaMethod);
        Type javaParentType = javaMethod.lexical().nearestAncestor(Type.class);
        ObjectOrientedFactory factory = java.plugin(ObjectOrientedFactory.class);
        Type javaMainType = factory.createRegularType(javaMethod.name());
        javaMainType.addModifier(new Interface());
        javaParentType.add(javaMainType);
        Method javaMainMethod = factory.createNormalMethod("main", java.createTypeReference("void"));
        javaMainType.add(javaMainMethod);
        javaMainMethod.addModifier(new Public());
        javaMainMethod.addModifier(new Static());
        Block body = new Block();
        javaMainMethod.setImplementation(new RegularImplementation(body));
        javaMainMethod.header().addFormalParameter(new FormalParameter("args", new ArrayTypeReference(java.createTypeReference("String"))));
    }

    protected void createDefaultConstructor(Document javaDocument) {
        javaDocument.lexical().apply(Type.class, t -> {
            if (!isGenerated(t)) {
                boolean hasConstructor = t.directlyDeclaredMembers().stream().anyMatch(m -> {
                    Declaration jloMember = (Declaration) m.origin();
                    return !isGenerated(m) && jloMember.isTrue(jlo(jloMember).CONSTRUCTOR());
                });
                if (!hasConstructor) {
                    Method method = new JavaMethod(new SimpleNameMethodHeader("init$new", java(t).createTypeReference(t)));
                    method.addModifier(new Static());
                    method.addModifier(new Public());
                    t.add(method);
                    Block body = new Block();
                    method.setImplementation(new RegularImplementation(body));
                    body.addStatement(new ReturnStatement(new ConstructorInvocation(java(javaDocument).createTypeReference(implementationName(t)), null)));
                }
            }
        });
    }

    protected String resultVariableName() {
        return "$result";
    }

    protected String constructorName(Method method) {
        return "init$" + method.name();
    }

    protected void addOuterMethod(Document javaDocument) throws LookupException {
        add(type -> {
            Type outerType = type.lexical().nearestAncestor(Type.class);
            BasicJavaTypeReference returnTypeReference = java(javaDocument).createTypeReference(outerType);
            addTypeArguments(java(type), returnTypeReference, (Type)outerType.origin());
            SimpleNameMethodHeader header = new SimpleNameMethodHeader("outer", returnTypeReference);
            return new NormalMethod(header);
        })
                .to(Type.class)
                .in(javaDocument)
                .whenTranslated(type -> type.lexical().nearestAncestor(Type.class) != null);
    }

    protected void changeClassesToInterfaces(Document javaDocument) {
        add(new Interface()).to(Type.class).in(javaDocument).whenOrigin(t -> !t.isTrue(java(t).INTERFACE()));
        strip(Abstract.class).from(Type.class).in(javaDocument);
        strip(java(javaDocument).PUBLIC).from(Type.class).in(javaDocument);
        add(new Public()).to(Type.class).in(javaDocument).always();
    }

    protected void makeMethodsDefault(Document javaDocument) {
        add(new Default()).to(Method.class).in(javaDocument).whenTranslated(m -> {
            return !m.isTrue(java(m).CONSTRUCTOR()) && !m.isTrue(java(m).ABSTRACT());
        });
    }

    protected void replaceExpressionImplementations(Document javaDocument) {
        javaDocument.lexical().apply(ExpressionImplementation.class, implementation -> {
            Block body = new Block();
            // We move the expression instead of cloning it
            body.addStatement(new ReturnStatement(implementation.expression()));
            RegularImplementation replacement = new RegularImplementation(body);
            implementation.replaceWith(replacement);
        });
    }

    protected void replaceFields(Document javaDocument) throws LookupException {
        javaDocument.lexical().apply(new UniversalConsumer<MemberVariableDeclarator, LookupException>(MemberVariableDeclarator.class) {
            /**
             * @throws LookupException
             * @{inheritDoc}
             */
            @Override
            public void accept(MemberVariableDeclarator javaMemberVariableDeclarator) throws LookupException {
                VariableDeclaration variableDeclaration = javaMemberVariableDeclarator.variableDeclarations().get(0);
                replaceFieldReferences(javaDocument, variableDeclaration);
                MemberVariableDeclarator jloMemberVariableDeclarator = (MemberVariableDeclarator) javaMemberVariableDeclarator.origin();
                Type javaContextType = javaMemberVariableDeclarator.lexical().nearestAncestor(Type.class);
                Method getter = createGetterTemplate(jloMemberVariableDeclarator, javaContextType);
                getter.addModifier(new Abstract());
                getter.addModifier(new Public());
                javaMemberVariableDeclarator.replaceWith(getter);
                Method setter = createSetterTemplate(jloMemberVariableDeclarator, javaContextType);
                setter.addModifier(new Abstract());
                setter.addModifier(new Public());
                getter.lexical().nearestAncestor(Type.class).add(setter);
            }
        });
    }

    protected void replaceFieldReferences(Document javaDocument, VariableDeclaration variableDeclaration) {
        ExpressionFactory expressionFactory = java(variableDeclaration).plugin(ExpressionFactory.class);
        javaDocument.lexical().apply(CrossReference.class, ref -> {
            CrossReference<?> origin = (CrossReference<?>) ref.origin();
            if (!isGenerated(ref)) {
                try {
                    if (origin.getElement().lexical().nearestAncestor(MemberVariableDeclarator.class) == variableDeclaration
                            .lexical().nearestAncestor(MemberVariableDeclarator.class).origin()) {
                        if (!(origin.lexical().parent() instanceof AssignmentExpression)) {
                            ref.replaceWith(expressionFactory.createInvocation(getterName(variableDeclaration), null));
                        } else {
                            AssignmentExpression assignment = (AssignmentExpression) ref.lexical().parent();
                            MethodInvocation createInvocation = expressionFactory.createInvocation(setterName(variableDeclaration),
                                    null);
                            createInvocation.addArgument(assignment.getValue());
                            assignment.replaceWith(createInvocation);
                        }
                    }
                } catch (LookupException e) {
                    throw new ChameleonProgrammerException(e);
                }
            }
        });
    }

    protected void makeNonPrivateMethodsPublic(Document javaDocument) {
        javaDocument.lexical().apply(Method.class, m -> {
            Method origin = (Method) m.origin();
            if (!isGenerated(m)) {
                StaticChameleonProperty priv = jlo(origin).PRIVATE;
                if (!origin.is(priv).isTrue()) {
                    try {
                        m.modifiers(m.language(Java7.class).SCOPE_MUTEX).forEach(e -> e.disconnect());
                        m.addModifier(new Public());
                        m.flushCache();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    protected void inferMissingReturnTypes(Document javaDocument) {
        javaDocument.lexical().apply(MethodHeader.class, h -> {
            if (h.returnTypeReference() == null) {
                Implementation implementation = h.lexical().nearestAncestor(Method.class).implementation();
                if (implementation instanceof ExpressionImplementation) {
                    Expression expression = ((ExpressionImplementation) implementation).expression();
                    Expression origin = (Expression) expression.origin();
                    try {
                        BasicJavaTypeReference typeReference = java(origin).createTypeReference(origin.getType());
                        h.setReturnTypeReference(typeReference);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    h.setReturnTypeReference(java(javaDocument).createTypeReference("void"));
                }
            }
        });
    }

    protected void replaceSubobjects(Element javaElement) {
        List<Subobject> subobjects = javaElement.lexical().nearestDescendants(Subobject.class);
        for (Subobject javaSubobject : subobjects) {
            try {
                String subobjectInterfaceName = subobjectInterfaceName(javaSubobject);
                Type javaSubobjectInterface = ooFactory(javaSubobject).createRegularType(subobjectInterfaceName);
                Subobject jloSubobject = (Subobject) javaSubobject.origin();
                Util.debug(jloSubobject.componentType().getFullyQualifiedName().equals("example.Radio.volume.upperBound"));
                javaSubobjectInterface.addModifier(new Interface());
                SubtypeRelation javaSubtypeRelation = new SubtypeRelation(clone(javaSubobject.superClassReference()));
                javaSubobjectInterface.addInheritanceRelation(javaSubtypeRelation);
                Type javaParentType = javaSubobject.lexical().nearestAncestor(Type.class);
                Method getter = createSubobjectGetterTemplate(jloSubobject, javaParentType);
                getter.addModifier(new Abstract());
                getter.addModifier(new Public());
                javaParentType.add(getter);

                // The inner interface is static and must have its own set of type parameters.
                for (TypeParameter javaTypeParameter : javaParentType.parameters(TypeParameter.class)) {
                    javaSubobjectInterface.addParameter(TypeParameter.class, javaTypeParameter.clone(javaTypeParameter));
                }
                applyToSortedTypeMemberDeclarators(jloSubobject.lexical().nearestAncestor(Type.class), m -> {
                    javaSubobjectInterface.addParameter(TypeParameter.class, clone(m.parameter()));
                });

                javaSubobject.replaceWith(javaSubobjectInterface);

                // Copy all the members defined in the subobject itself.
                SubobjectType jloSubobjectType = jloSubobject.lexical().nearestDescendants(SubobjectType.class).get(0);
                List<Declaration> jloSubobjectMembers = jloSubobjectType.directlyDeclaredMembers();
                for (Declaration jloSubobjectMember : jloSubobjectMembers) {
                    Declaration member = cloneAndSetOrigin(jloSubobjectMember);
                    javaSubobjectInterface.add(member);
                }
                replaceSubobjects(javaSubobjectInterface);
            } catch (LookupException e) {
                throw new ChameleonProgrammerException(e);
            }
        }
    }

    protected void replaceSubobjectReferences(Document javaDocument) throws LookupException {
        javaDocument.lexical().apply(NameExpression.class, javaNameExpression -> {
            NameExpression jloNameExpression = (NameExpression) javaNameExpression.origin();
            // Why would this ever not be true?
            DeclarationWithType referencedDeclaration = jloNameExpression.getElement();
            if (referencedDeclaration instanceof Subobject) {
                RegularMethodInvocation invocation = new RegularMethodInvocation(javaNameExpression.name(), javaNameExpression.getTarget());
                javaNameExpression.parentLink().getOtherRelation().replace(javaNameExpression.parentLink(), invocation.parentLink());
            }
        });
    }

    protected void replaceSubobjectsOld(Document javaDocument) {
        javaDocument.lexical().apply(Subobject.class, javaSubobject -> {
            try {
                Type subobjectInterface = ooFactory(javaSubobject).createRegularType(subobjectInterfaceName(javaSubobject));
                Subobject jloSubobject = (Subobject) javaSubobject.origin();
                subobjectInterface.addModifier(new Interface());
                SubtypeRelation javaSubtypeRelation = new SubtypeRelation(clone(javaSubobject.superClassReference()));
                subobjectInterface.addInheritanceRelation(javaSubtypeRelation);
                Type javaParentType = javaSubobject.lexical().nearestAncestor(Type.class);
                Method getter = createSubobjectGetterTemplate(jloSubobject, javaParentType);
                getter.addModifier(new Abstract());
                getter.addModifier(new Public());
                javaParentType.add(getter);
                applyToSortedTypeMemberDeclarators(jloSubobject.lexical().nearestAncestor(Type.class), m -> {
                    subobjectInterface.addParameter(TypeParameter.class, clone(m.parameter()));
                });
                javaSubobject.replaceWith(subobjectInterface);
            } catch (LookupException e) {
                throw new ChameleonProgrammerException(e);
            }
        });
    }

}
