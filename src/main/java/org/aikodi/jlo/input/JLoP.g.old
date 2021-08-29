parser grammar JLoP;

options {
  backtrack=true; 
  memoize=true;
  output=AST;
  superClass = ChameleonParser;
}

import JavaP;

@members{

  @Override
  public void setView(View view) {
    super.setView(view);
    gJavaP.setView(view);
  }
  
  @Override
  public View view() {
    return gJavaP.view();
  }
  
  public Language language() {
    return view().language();
  }
  
  public Document getDocument() {
    return gJavaP.getDocument();
  }
	   
  public void setDocument(Document compilationUnit) {
    gJavaP.setDocument(compilationUnit);
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

  public JavaTypeReference createTypeReference(CrossReference<? extends TargetDeclaration> target, String name) {
    return gJavaP.createTypeReference(target,name);
  }
  
  public JavaTypeReference createTypeReference(NamedTarget target) {
    return gJavaP.createTypeReference(target);
  }

  public CrossReferenceTarget cloneTarget(CrossReferenceTarget target) {
    return gJavaP.cloneTarget(target);
  }
  
  public MethodInvocation invocation(String name, CrossReferenceTarget target) {
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
    |   id=interfaceDeclaration {retval.element=id.element; }
    |   cd=classDeclaration {retval.element=cd.element;}
    |   comp=subobjectDeclaration {retval.element=comp.element;}
    |   exp=exportDeclaration {retval.element=exp.element;}
    |   ov=overridesClause {retval.element=ov.element;}
    |   np=nameParameter {retval.element=np.element;}
    |   con=connector {retval.element = con.element;}
    |   cnct=connection {retval.element = cnct.element;}
    ;

connector returns [TypeElement element]
 	: ctkw=Connector cp=subobjectParameter {retval.element = cp.element; setKeyword(retval.element,ctkw);}';'	
 	;

connection returns [TypeElement element]
 	: ctkw=Connect name=identifierRule tokw=identifierRule arg=subobjectArgument cl=';'
 	  {retval.element = new InstantiatedMemberSubobjectParameter($name.text,arg.element);
 	   setKeyword(retval.element,ctkw);
	   setName(retval.element,name.start);
 	   if($tokw.text.equals("to")) {setKeyword(arg.element,tokw.start);}
 	   setLocation(retval.element, ctkw,cl);
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

overridesClause returns [Overrides element]
     	: newSig=signature ov=Overrides oldFQN=fqn {retval.element = new Overrides(newSig.element,oldFQN.element); setKeyword(retval.element,ov);}
     	;

map returns [RenamingClause element]
	:  oldFQN=fqn {retval.element = new RenamingClause(null, oldFQN.element);} 
	   (id=identifierRule newSig=signature 
	   {retval.element.setNewSignature(newSig.element);
	    if($id.text.equals("as")) {setKeyword(retval.element, id.start);}
	   })?
	;    	

subobjectDeclaration returns [SubobjectRelation element]
    	:	cp='subobject' name=identifierRule tp=type?
    	        {retval.element = new SubobjectRelation(new SimpleNameSignature($name.text), $tp.element);
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
@after{
  check_null(retval.element);
}
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
    

nonTargetPrimary returns [Expression element]
@after{
  check_null(retval.element);
}
  	:
  	lit=literal {retval.element = lit.element;}
  	| at='#' id=identifierRule '(' ex=expression {retval.element = ex.element;} stop=')'
           {retval.element = new ComponentParameterCall(ex.element, $id.text);
              setLocation(retval.element,at,stop);
              setKeyword(retval.element,at);
           }
        | okw='outer' supsuf=superSuffix 
           {retval.element = supsuf.element;
            CrossReferenceTarget tar = new OuterTarget();
            ((TargetedExpression)retval.element).setTarget(tar);
            setLocation(retval.element,okw,okw); // put locations on the SuperTarget.
            setKeyword(tar,okw);
           }
        | rkw='rooot' supsuf=superSuffix 
           {retval.element = supsuf.element;
            CrossReferenceTarget tar = new RootTarget();
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
	  {retval.element = new SingleFormalComponentParameter($id.text,tcontainer.element,tcomp.element);
	   setLocation(retval.element,id.start,tcomp.stop);
	   setKeyword(tcontainer.element,arrow);
	   setName(retval.element,id.start);
	   }
	;

multiSubobjectParameter returns [ComponentParameter element]
	:  id=identifierRule tcontainer=type arrow='->' '[' tcomp=type fin=']'
	  {retval.element = new MultiFormalComponentParameter($id.text,tcontainer.element,tcomp.element);
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
	                     setLocation(retval.element,id.start,id.stop);
                           }
        | at='@' idd=identifierRule {retval.element = new ParameterReferenceActualArgument($idd.text);
        			     setLocation(retval.element,at,idd.stop);}
	;
	
multiSubobjectArgument returns [MultiActualComponentArgument element]
@init{retval.element = new MultiActualComponentArgument();}
	:
	 start='[' (single=singleSubobjectArgument {retval.element.add(single.element);} 
	     (',' singlee=singleSubobjectArgument {retval.element.add(singlee.element);} )* )?
	 stop=']' { setLocation(retval.element,start,stop);}
	;	
