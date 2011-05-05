grammar SubobjectJava;
options {
  backtrack=true; 
  memoize=true;
  output=AST;
  superClass = ChameleonParser;
}

import JavaP,JLoL;
@header {
package subobjectjava.input;

import chameleon.exception.ModelException;
import chameleon.exception.ChameleonProgrammerException;

import chameleon.core.lookup.LookupStrategyFactory;

import chameleon.core.compilationunit.CompilationUnit;

import chameleon.core.declaration.SimpleNameDeclarationWithParametersSignature;
import chameleon.core.declaration.SimpleNameDeclarationWithParametersHeader;
import chameleon.core.declaration.SimpleNameSignature;
import chameleon.core.declaration.Signature;
import chameleon.core.declaration.QualifiedName;
import chameleon.core.declaration.CompositeQualifiedName;
import chameleon.core.declaration.TargetDeclaration;

import chameleon.core.element.Element;

import chameleon.core.expression.Expression;
import chameleon.core.expression.MethodInvocation;
import chameleon.core.expression.Literal;
import chameleon.core.expression.Assignable;
import chameleon.core.expression.NamedTarget;
import chameleon.core.expression.NamedTargetExpression;
import chameleon.core.expression.InvocationTarget;
import chameleon.core.expression.TargetedExpression;
import chameleon.core.expression.VariableReference;

import chameleon.core.language.Language;

import chameleon.core.member.Member;

import chameleon.core.method.Method;
import chameleon.core.method.Implementation;
import chameleon.core.method.RegularImplementation;

import chameleon.core.method.exception.ExceptionClause;
import chameleon.core.method.exception.TypeExceptionDeclaration;

import chameleon.core.modifier.Modifier;

import chameleon.core.namespace.Namespace;
import chameleon.core.namespace.RootNamespace;
import chameleon.core.namespace.NamespaceOrTypeReference;
import chameleon.core.namespace.NamespaceReference;

import chameleon.core.namespacepart.NamespacePart;
import chameleon.core.namespacepart.Import;
import chameleon.core.namespacepart.TypeImport;
import chameleon.core.namespacepart.DemandImport;

import chameleon.core.reference.CrossReference;

import chameleon.core.statement.Block;
import chameleon.core.statement.Statement;

import chameleon.oo.type.ClassBody;
import chameleon.oo.type.RegularType;
import chameleon.oo.type.Type;
import chameleon.oo.type.TypeReference;
import chameleon.oo.type.TypeElement;
import chameleon.oo.type.TypeWithBody;
import chameleon.oo.type.ParameterBlock;

import chameleon.oo.type.generics.TypeParameter;
import chameleon.oo.type.generics.FormalTypeParameter;
import chameleon.oo.type.generics.ActualTypeArgument;
import chameleon.oo.type.generics.BasicTypeArgument;
import chameleon.oo.type.generics.TypeConstraint;
import chameleon.oo.type.generics.ExtendsConstraint;
import chameleon.oo.type.generics.ExtendsWildcard;
import chameleon.oo.type.generics.SuperWildcard;
import chameleon.oo.type.inheritance.SubtypeRelation;

import chameleon.core.variable.Variable;
import chameleon.core.variable.FormalParameter;
import chameleon.core.variable.VariableDeclaration;
import chameleon.core.variable.VariableDeclarator;

import chameleon.input.InputProcessor;
import chameleon.input.Position2D;

import chameleon.support.expression.RegularLiteral;
import chameleon.support.expression.NullLiteral;
import chameleon.support.expression.AssignmentExpression;
import chameleon.support.expression.ConditionalExpression;
import chameleon.support.expression.ConditionalAndExpression;
import chameleon.support.expression.ConditionalOrExpression;
import chameleon.support.expression.InstanceofExpression;
import chameleon.support.expression.ThisLiteral;
import chameleon.support.expression.FilledArrayIndex;
import chameleon.support.expression.EmptyArrayIndex;
import chameleon.support.expression.ArrayIndex;
import chameleon.support.expression.ClassCastExpression;
import chameleon.support.expression.SuperTarget;

import chameleon.support.member.simplename.method.NormalMethod;
import chameleon.support.member.simplename.variable.MemberVariableDeclarator;
import chameleon.support.member.simplename.operator.infix.InfixOperatorInvocation;
import chameleon.support.member.simplename.operator.prefix.PrefixOperatorInvocation;
import chameleon.support.member.simplename.operator.postfix.PostfixOperatorInvocation;
import chameleon.support.member.simplename.method.RegularMethodInvocation;

import chameleon.support.modifier.Abstract;
import chameleon.support.modifier.Final;
import chameleon.support.modifier.Private;
import chameleon.support.modifier.Protected;
import chameleon.support.modifier.Public;
import chameleon.support.modifier.Static;
import chameleon.support.modifier.Native;
import chameleon.support.modifier.Enum;
import chameleon.support.modifier.Interface;

import chameleon.support.statement.StatementExpression;
import chameleon.support.statement.LocalClassStatement;
import chameleon.support.statement.AssertStatement;
import chameleon.support.statement.IfThenElseStatement;
import chameleon.support.statement.ForStatement;
import chameleon.support.statement.ForControl;
import chameleon.support.statement.ForInit;
import chameleon.support.statement.SimpleForControl;
import chameleon.support.statement.EnhancedForControl;
import chameleon.support.statement.StatementExprList;
import chameleon.support.statement.TryStatement;
import chameleon.support.statement.CatchClause;
import chameleon.support.statement.FinallyClause;
import chameleon.support.statement.DoStatement;
import chameleon.support.statement.WhileStatement;
import chameleon.support.statement.SwitchStatement;
import chameleon.support.statement.SwitchCase;
import chameleon.support.statement.SwitchLabel;
import chameleon.support.statement.CaseLabel;
import chameleon.support.statement.DefaultLabel;
import chameleon.support.statement.EnumLabel;
import chameleon.support.statement.ReturnStatement;
import chameleon.support.statement.ThrowStatement;
import chameleon.support.statement.BreakStatement;
import chameleon.support.statement.ContinueStatement;
import chameleon.support.statement.SynchronizedStatement;
import chameleon.support.statement.EmptyStatement;
import chameleon.support.statement.LabeledStatement;

import chameleon.support.type.EmptyTypeElement;
import chameleon.support.type.StaticInitializer;

import chameleon.support.variable.LocalVariableDeclarator;

import chameleon.support.input.ChameleonParser;

import chameleon.util.Util;

import jnome.core.expression.ArrayInitializer;
import jnome.core.expression.ClassLiteral;
import jnome.core.expression.ArrayAccessExpression;
import jnome.core.expression.ArrayCreationExpression;
import jnome.core.expression.invocation.ConstructorInvocation;
import jnome.core.expression.invocation.JavaMethodInvocation;
import jnome.core.expression.invocation.ThisConstructorDelegation;
import jnome.core.expression.invocation.SuperConstructorDelegation;

import jnome.core.imports.SingleStaticImport;

import jnome.core.language.Java;

import jnome.core.modifier.StrictFP;
import jnome.core.modifier.Transient;
import jnome.core.modifier.Volatile;
import jnome.core.modifier.Synchronized;
import jnome.core.modifier.JavaConstructor;
import jnome.core.modifier.Implements;
import jnome.core.modifier.AnnotationModifier;
import jnome.core.modifier.AnnotationType;

import jnome.core.type.JavaTypeReference;
import jnome.core.type.ArrayTypeReference;
import jnome.core.type.BasicJavaTypeReference;
import jnome.core.type.JavaIntersectionTypeReference;
import jnome.core.type.PureWildcard;

import jnome.core.enumeration.EnumConstant;

import jnome.core.variable.JavaVariableDeclaration;
import jnome.core.variable.MultiFormalParameter;

import jnome.input.JavaFactory;

import subobjectjava.model.component.ComponentRelation;
import subobjectjava.model.component.ConfigurationBlock;
import subobjectjava.model.component.ConfigurationClause;
import subobjectjava.model.component.RenamingClause;
import subobjectjava.model.component.OverridesClause;
import subobjectjava.model.expression.SubobjectConstructorCall;
import subobjectjava.model.expression.ComponentParameterCall;
import subobjectjava.model.expression.OuterTarget;
import subobjectjava.model.expression.RootTarget;
import subobjectjava.model.component.ComponentParameter;
import subobjectjava.model.component.FormalComponentParameter;
import subobjectjava.model.component.SingleFormalComponentParameter;
import subobjectjava.model.component.MultiFormalComponentParameter;
import subobjectjava.model.component.ActualComponentArgument;
import subobjectjava.model.component.SingleActualComponentArgument;
import subobjectjava.model.component.MultiActualComponentArgument;
import subobjectjava.model.component.ComponentParameterTypeReference;
import subobjectjava.model.component.ComponentNameActualArgument;
import subobjectjava.model.component.ParameterReferenceActualArgument;
import subobjectjava.model.component.Export;
import subobjectjava.model.component.InstantiatedMemberSubobjectParameter;

import java.util.List;
import java.util.ArrayList;
}
@members{

  @Override
  public void setLanguage(Language language) {
    gJavaP.setLanguage(language);
  }
  
  @Override
  public Language language() {
    return gJavaP.language();
  }
  
  public CompilationUnit getCompilationUnit() {
    return gJavaP.getCompilationUnit();
  }
	   
  public void setCompilationUnit(CompilationUnit compilationUnit) {
    gJavaP.setCompilationUnit(compilationUnit);
  }
  
  public Namespace getDefaultNamespace() {
    return gJavaP.getDefaultNamespace();
  }
  
    public void setFactory(JavaFactory factory) {
    gJavaP.setFactory(factory);
  }
  
  public JavaFactory factory() {
    return gJavaP.factory();
  }

  public JavaTypeReference typeRef(String qn) {
    return gJavaP.typeRef(qn);
  }

  public JavaTypeReference createTypeReference(CrossReference<?, ? extends TargetDeclaration> target, String name) {
    return gJavaP.createTypeReference(target,name);
  }
  
  public JavaTypeReference createTypeReference(CrossReference<?, ? extends TargetDeclaration> target, SimpleNameSignature signature) {
    return gJavaP.createTypeReference(target,signature);
  }

  public JavaTypeReference createTypeReference(NamedTarget target) {
    return gJavaP.createTypeReference(target);
  }

  public InvocationTarget cloneTarget(InvocationTarget target) {
    return gJavaP.cloneTarget(target);
  }
  
  public RegularMethodInvocation invocation(String name, InvocationTarget target) {
    return gJavaP.invocation(name,target);
  }
}

identifierRule returns [String element]
    : id=Identifier {retval.element = $id.text;} 
      | ex=Export  {retval.element = $ex.text;} 
      | co=Connector  {retval.element = $co.text;} 
      | ctc=Connect  {retval.element = $ctc.text;} 
      | n=Name  {retval.element = $n.text;} 
      | o=Overrides  {retval.element = $o.text;} 
    ;   


memberDecl returns [TypeElement element]
@after{setLocation(retval.element, (CommonToken)retval.start, (CommonToken)retval.stop);}
    :   gen=genericMethodOrConstructorDecl {retval.element = gen.element;}
    |   mem=memberDeclaration {retval.element = mem.element;}
    |   vmd=voidMethodDeclaration {retval.element = vmd.element;}
    |   cs=constructorDeclaration {retval.element = cs.element;}
    |   id=interfaceDeclaration {retval.element=id.element; gJavaP.addNonTopLevelObjectInheritance(id.element);}
    |   cd=classDeclaration {retval.element=cd.element; gJavaP.addNonTopLevelObjectInheritance(cd.element);}
    |   comp=subobjectDeclaration {retval.element=comp.element;}
    |   exp=exportDeclaration {retval.element=exp.element;}
    |   np=nameParameter {retval.element=np.element;}
    |   con=connector {retval.element = con.element;}
    |   cnct=connection {retval.element = cnct.element;}
    ;

connector returns [TypeElement element]
 	: ctkw=Connector cp=subobjectParameter {retval.element = cp.element; setKeyword(retval.element,ctkw);}';'	
 	;

connection returns [TypeElement element]
 	: ctkw=Connect name=identifierRule tokw=Identifier arg=subobjectArgument ';'
 	  {retval.element = new InstantiatedMemberSubobjectParameter(new SimpleNameSignature($name.text),arg.element);
 	   setKeyword(retval.element,ctkw);
	   setName(retval.element,name.start);
 	   if($tokw.text.equals("to")) {setKeyword(arg.element,tokw);}
 	  }	
 	;

nameParameter returns [TypeElement element]
    	:	Name identifierRule ('=' memberName)? ';'
    	;
    	
memberName returns [Object element]
 	: identifierRule
 	;    	

exportDeclaration returns [Export element]
    	: xp=Export {retval.element = new Export(); setKeyword(retval.element,xp);} m=map {retval.element.add(m.element);} (',' mm=map {retval.element.add(mm.element);})*	';'
    	;
    	
map returns [RenamingClause element]
	:  oldFQN=fqn {retval.element = new RenamingClause(null, oldFQN.element);} 
	   (id=Identifier newSig=signature 
	   {retval.element.setNewSignature(newSig.element);
	    if($id.text.equals("as")) {setKeyword(retval.element, id);}
	   })?
	;    	

subobjectDeclaration returns [ComponentRelation element]
    	:	cp='subobject' tp=type? name=identifierRule 
    	        {retval.element = new ComponentRelation(new SimpleNameSignature($name.text), $tp.element);
    	         setKeyword(retval.element,cp);
    	         setName(retval.element,name.start);
    	        }
    	        (body=classBody {retval.element.setBody($body.element);} | ';')  // cfg=configurationBlock? 
//    	      if(cfg != null) {retval.element.setConfigurationBlock($cfg.element);}
    	       
    	;
configurationBlock returns [ConfigurationBlock element] 
@after{setLocation(retval.element, (CommonToken)retval.start, (CommonToken)retval.stop);}
        : al='alias' '{' {retval.element = new ConfigurationBlock(); setKeyword(retval.element,al);} (cl=configurationClause{retval.element.add(cl.element);} (',' cll=configurationClause{retval.element.add(cll.element);})*)? '}'
        ;
        
configurationClause returns [ConfigurationClause element]
@after{setLocation(retval.element, (CommonToken)retval.start, (CommonToken)retval.stop);}
	: sig=signature ov='>' f=fqn
	     {retval.element = new OverridesClause(sig.element, f.element);
	      setKeyword(retval.element, ov);
	     }
	|
	 sigg=signature al='=' ff=fqn 
	     {retval.element = new RenamingClause(sigg.element, ff.element);
	      setKeyword(retval.element, al);
	     }
	;
	
signature returns [Signature element]
        : sig=identifierRule {retval.element = new SimpleNameSignature($sig.text);}
        | sigg=identifierRule {retval.element = new SimpleNameDeclarationWithParametersSignature($sigg.text);} 
                '(' (t=type {((SimpleNameDeclarationWithParametersSignature)retval.element).add(t.element);} 
                 (',' tt=type {((SimpleNameDeclarationWithParametersSignature)retval.element).add(tt.element);})*)?')'
        ;
        
fqn returns [QualifiedName element] 
        :	sig=signature {retval.element=sig.element;}
        |     id=identifierRule '.' ff=fqn {
              Signature signature = new SimpleNameSignature($id.text);
              if(ff.element instanceof CompositeQualifiedName) {
                ((CompositeQualifiedName)ff.element).prefix(signature); 
                retval.element = ff.element;
              } else {
                retval.element=new CompositeQualifiedName();
                ((CompositeQualifiedName)retval.element).append(signature);
                ((CompositeQualifiedName)retval.element).append((Signature)ff.element);
              }
              }
        ;
        
expression returns [Expression element]
    :   ex=conditionalExpression {retval.element=ex.element;} (op=assignmentOperator exx=expression 
        {String txt = $op.text; 
         if(txt.equals("=")) {
           retval.element = new AssignmentExpression(ex.element,exx.element);
         } else {
           retval.element = new InfixOperatorInvocation($op.text,ex.element);
           ((InfixOperatorInvocation)retval.element).addArgument(exx.element);
         }
         setLocation(retval.element,op.start,op.stop,"__NAME");
         setLocation(retval.element,retval.start,exx.stop);
        }
        )?
        | sb='subobject' '.' id=identifierRule args=arguments {
          retval.element = new SubobjectConstructorCall($id.text, args.element);
         setLocation(retval.element,sb,args.stop);
         setKeyword(retval.element,sb);
           }
    ;
    
//conditionalOrExpression returns [Expression element]
//    :   ex=componentParameterCall {retval.element = ex.element;} ( '||' exx=componentParameterCall 
//        {retval.element = new ConditionalOrExpression(retval.element, exx.element);
//         setLocation(retval.element,retval.start,exx.stop);
//        })*
//    ;

//componentParameterCall returns [Expression element]
// 	: ex=conditionalAndExpression {retval.element = ex.element;} (at='#' id=Identifier 
//            {retval.element = new ComponentParameterCall(ex.element, $id.text);
//              setLocation(retval.element,ex.start,id);
//              setKeyword(retval.element,at);
//             })?
//  	;

// NEEDS_TARGET


nonTargetPrimary returns [Expression element]
  	:
  	lit=literal {retval.element = lit.element;}
  	| at='#' id=identifierRule '(' ex=expression {retval.element = ex.element;} stop=')'
           {retval.element = new ComponentParameterCall(ex.element, $id.text);
              setLocation(retval.element,at,stop);
              setKeyword(retval.element,at);
           }
        | okw='outer' supsuf=superSuffix 
           {retval.element = supsuf.element;
            InvocationTarget tar = new OuterTarget();
            ((TargetedExpression)retval.element).setTarget(tar);
            setLocation(retval.element,okw,okw); // put locations on the SuperTarget.
            setKeyword(tar,okw);
           }
        | rkw='rooot' supsuf=superSuffix 
           {retval.element = supsuf.element;
            InvocationTarget tar = new RootTarget();
            ((TargetedExpression)retval.element).setTarget(tar);
            setLocation(retval.element,rkw,rkw); // put locations on the SuperTarget.
            setKeyword(tar,rkw);
           }
  	;

   
subobjectParameter returns [ComponentParameter element]
	: single=singleSubobjectParameter {retval.element = single.element;}
	 | multi=multiSubobjectParameter {retval.element = multi.element;}
	;

singleSubobjectParameter returns [ComponentParameter element]
	: id=identifierRule tcontainer=type arrow='->' tcomp=type 
	  {retval.element = new SingleFormalComponentParameter(new SimpleNameSignature($id.text),tcontainer.element,tcomp.element);
	   setLocation(retval.element,id.start,tcomp.stop);
	   setKeyword(tcontainer.element,arrow);
	   setName(retval.element,id.start);
	   }
	;

multiSubobjectParameter returns [ComponentParameter element]
	:  id=identifierRule tcontainer=type arrow='->' '[' tcomp=type fin=']'
	  {retval.element = new MultiFormalComponentParameter(new SimpleNameSignature($id.text),tcontainer.element,tcomp.element);
	   setLocation(retval.element,id.start,fin);
	   setKeyword(tcontainer.element,arrow);
	   setName(retval.element,id.start);
	   }
	;

subobjectArgument returns [ActualComponentArgument element]
 	:
 	  s=singleSubobjectArgument {retval.element=s.element;} 
 	 | ss=multiSubobjectArgument {retval.element=ss.element;}
 	;
 	
singleSubobjectArgument returns [SingleActualComponentArgument element]
	:
	 id=identifierRule {retval.element = new ComponentNameActualArgument($id.text);
}
        | '@' idd=identifierRule {retval.element = new ParameterReferenceActualArgument($idd.text);}
	;
	
multiSubobjectArgument returns [MultiActualComponentArgument element]
@init{retval.element = new MultiActualComponentArgument();}
	:
	 '[' (single=singleSubobjectArgument {retval.element.add(single.element);} 
	     (',' singlee=singleSubobjectArgument {retval.element.add(singlee.element);} )* )?
	 ']' 
	;	