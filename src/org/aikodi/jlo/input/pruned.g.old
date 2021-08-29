grammar SubobjectJava;
options {
  backtrack=true; 
  memoize=true;
  output=AST;
  superClass = ChameleonParser;
import JLoP,JLoL;
@members{
  @Override
  public void setLanguage(Language language) {
    gJLoP.setLanguage(language);
  @Override
  public Language language() {
    return gJLoP.language();
  public CompilationUnit getCompilationUnit() {
    return gJLoP.getCompilationUnit();
  public void setCompilationUnit(CompilationUnit compilationUnit) {
    gJLoP.setCompilationUnit(compilationUnit);
  public Namespace getDefaultNamespace() {
    return gJLoP.getDefaultNamespace();
    public void setFactory(JavaFactory factory) {
    gJLoP.setFactory(factory);
  public JavaFactory factory() {
    return gJLoP.factory();
  public JavaTypeReference typeRef(String qn) {
    return gJLoP.typeRef(qn);
  public JavaTypeReference createTypeReference(CrossReference<?, ? extends TargetDeclaration> target, String name) {
    return gJLoP.createTypeReference(target,name);
  public JavaTypeReference createTypeReference(CrossReference<?, ? extends TargetDeclaration> target, SimpleNameSignature signature) {
    return gJLoP.createTypeReference(target,signature);
  public JavaTypeReference createTypeReference(NamedTarget target) {
    return gJLoP.createTypeReference(target);
  public InvocationTarget cloneTarget(InvocationTarget target) {
    return gJLoP.cloneTarget(target);
  public RegularMethodInvocation invocation(String name, InvocationTarget target) {
    return gJLoP.invocation(name,target);
dummy returns [Object element]
   	;parser grammar JLoP;
options {
  backtrack=true; 
  memoize=true;
  output=AST;
  superClass = ChameleonParser;
import JavaP;
@members{
  @Override
  public void setLanguage(Language language) {
    gJavaP.setLanguage(language);
  @Override
  public Language language() {
    return gJavaP.language();
  public CompilationUnit getCompilationUnit() {
    return gJavaP.getCompilationUnit();
  public void setCompilationUnit(CompilationUnit compilationUnit) {
    gJavaP.setCompilationUnit(compilationUnit);
  public Namespace getDefaultNamespace() {
    return gJavaP.getDefaultNamespace();
    public void setFactory(JavaFactory factory) {
    gJavaP.setFactory(factory);
  public JavaFactory factory() {
    return gJavaP.factory();
  public JavaTypeReference typeRef(String qn) {
    return gJavaP.typeRef(qn);
  public JavaTypeReference createTypeReference(CrossReference<?, ? extends TargetDeclaration> target, String name) {
    return gJavaP.createTypeReference(target,name);
  public JavaTypeReference createTypeReference(CrossReference<?, ? extends TargetDeclaration> target, SimpleNameSignature signature) {
    return gJavaP.createTypeReference(target,signature);
  public JavaTypeReference createTypeReference(NamedTarget target) {
    return gJavaP.createTypeReference(target);
  public InvocationTarget cloneTarget(InvocationTarget target) {
    return gJavaP.cloneTarget(target);
  public RegularMethodInvocation invocation(String name, InvocationTarget target) {
    return gJavaP.invocation(name,target);
identifierRule returns [String element]
    : id=Identifier {retval.element = $id.text;} 
      | ex=Export  {retval.element = $ex.text;} 
      | co=Connector  {retval.element = $co.text;} 
      | ctc=Connect  {retval.element = $ctc.text;} 
      | n=Name  {retval.element = $n.text;} 
      | o=Overrides  {retval.element = $o.text;} 
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
connector returns [TypeElement element]
 	: ctkw=Connector cp=subobjectParameter {retval.element = cp.element; setKeyword(retval.element,ctkw);}';'	
connection returns [TypeElement element]
 	: ctkw=Connect name=identifierRule tokw=Identifier arg=subobjectArgument cl=';'
 	  {retval.element = new InstantiatedMemberSubobjectParameter(new SimpleNameSignature($name.text),arg.element);
 	   setKeyword(retval.element,ctkw);
	   setName(retval.element,name.start);
 	   if($tokw.text.equals("to")) {setKeyword(arg.element,tokw);}
 	   setLocation(retval.element, ctkw,cl);
nameParameter returns [TypeElement element]
    	:	Name identifierRule ('=' memberName)? ';'
memberName returns [Object element]
 	: identifierRule
exportDeclaration returns [Export element]
    	: xp=Export {retval.element = new Export(); setKeyword(retval.element,xp);} m=map {retval.element.add(m.element);} (',' mm=map {retval.element.add(mm.element);})*	';'
map returns [RenamingClause element]
	:  oldFQN=fqn {retval.element = new RenamingClause(null, oldFQN.element);} 
	   (id=Identifier newSig=signature 
	   {retval.element.setNewSignature(newSig.element);
	    if($id.text.equals("as")) {setKeyword(retval.element, id);}
subobjectDeclaration returns [ComponentRelation element]
    	:	cp='subobject' tp=type? name=identifierRule 
    	        {retval.element = new ComponentRelation(new SimpleNameSignature($name.text), $tp.element);
    	         setKeyword(retval.element,cp);
    	         setName(retval.element,name.start);
    	        (body=classBody {retval.element.setBody($body.element);} | ';')  // cfg=configurationBlock? 
//    	      if(cfg != null) {retval.element.setConfigurationBlock($cfg.element);}
configurationBlock returns [ConfigurationBlock element] 
@after{setLocation(retval.element, (CommonToken)retval.start, (CommonToken)retval.stop);}
        : al='alias' '{' {retval.element = new ConfigurationBlock(); setKeyword(retval.element,al);} (cl=configurationClause{retval.element.add(cl.element);} (',' cll=configurationClause{retval.element.add(cll.element);})*)? '}'
configurationClause returns [ConfigurationClause element]
@after{setLocation(retval.element, (CommonToken)retval.start, (CommonToken)retval.stop);}
	: sig=signature ov='>' f=fqn
	     {retval.element = new OverridesClause(sig.element, f.element);
	      setKeyword(retval.element, ov);
	 sigg=signature al='=' ff=fqn 
	     {retval.element = new RenamingClause(sigg.element, ff.element);
	      setKeyword(retval.element, al);
signature returns [Signature element]
        : sig=identifierRule {retval.element = new SimpleNameSignature($sig.text);}
        | sigg=identifierRule {retval.element = new SimpleNameDeclarationWithParametersSignature($sigg.text);} 
                '(' (t=type {((SimpleNameDeclarationWithParametersSignature)retval.element).add(t.element);} 
                 (',' tt=type {((SimpleNameDeclarationWithParametersSignature)retval.element).add(tt.element);})*)?')'
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
expression returns [Expression element]
    :   ex=conditionalExpression {retval.element=ex.element;} (op=assignmentOperator exx=expression 
        {String txt = $op.text; 
         if(txt.equals("=")) {
           retval.element = new AssignmentExpression(ex.element,exx.element);
         } else {
           retval.element = new InfixOperatorInvocation($op.text,ex.element);
           ((InfixOperatorInvocation)retval.element).addArgument(exx.element);
         setLocation(retval.element,op.start,op.stop,"__NAME");
         setLocation(retval.element,retval.start,exx.stop);
        | sb='subobject' '.' id=identifierRule args=arguments {
          retval.element = new SubobjectConstructorCall($id.text, args.element);
         setLocation(retval.element,sb,args.stop);
         setKeyword(retval.element,sb);
//conditionalOrExpression returns [Expression element]
//    :   ex=componentParameterCall {retval.element = ex.element;} ( '||' exx=componentParameterCall 
//        {retval.element = new ConditionalOrExpression(retval.element, exx.element);
//         setLocation(retval.element,retval.start,exx.stop);
//componentParameterCall returns [Expression element]
// 	: ex=conditionalAndExpression {retval.element = ex.element;} (at='#' id=Identifier 
//            {retval.element = new ComponentParameterCall(ex.element, $id.text);
//              setLocation(retval.element,ex.start,id);
//              setKeyword(retval.element,at);
// NEEDS_TARGET
nonTargetPrimary returns [Expression element]
  	lit=literal {retval.element = lit.element;}
  	| at='#' id=identifierRule '(' ex=expression {retval.element = ex.element;} stop=')'
           {retval.element = new ComponentParameterCall(ex.element, $id.text);
              setLocation(retval.element,at,stop);
              setKeyword(retval.element,at);
        | okw='outer' supsuf=superSuffix 
           {retval.element = supsuf.element;
            InvocationTarget tar = new OuterTarget();
            ((TargetedExpression)retval.element).setTarget(tar);
            setLocation(retval.element,okw,okw); // put locations on the SuperTarget.
            setKeyword(tar,okw);
        | rkw='rooot' supsuf=superSuffix 
           {retval.element = supsuf.element;
            InvocationTarget tar = new RootTarget();
            ((TargetedExpression)retval.element).setTarget(tar);
            setLocation(retval.element,rkw,rkw); // put locations on the SuperTarget.
            setKeyword(tar,rkw);
subobjectParameter returns [ComponentParameter element]
	: single=singleSubobjectParameter {retval.element = single.element;}
	 | multi=multiSubobjectParameter {retval.element = multi.element;}
singleSubobjectParameter returns [ComponentParameter element]
	: id=identifierRule tcontainer=type arrow='->' tcomp=type 
	  {retval.element = new SingleFormalComponentParameter(new SimpleNameSignature($id.text),tcontainer.element,tcomp.element);
	   setLocation(retval.element,id.start,tcomp.stop);
	   setKeyword(tcontainer.element,arrow);
	   setName(retval.element,id.start);
multiSubobjectParameter returns [ComponentParameter element]
	:  id=identifierRule tcontainer=type arrow='->' '[' tcomp=type fin=']'
	  {retval.element = new MultiFormalComponentParameter(new SimpleNameSignature($id.text),tcontainer.element,tcomp.element);
	   setLocation(retval.element,id.start,fin);
	   setKeyword(tcontainer.element,arrow);
	   setName(retval.element,id.start);
subobjectArgument returns [ActualComponentArgument element]
 	  s=singleSubobjectArgument {retval.element=s.element;} 
 	 | ss=multiSubobjectArgument {retval.element=ss.element;}
singleSubobjectArgument returns [SingleActualComponentArgument element]
	 id=identifierRule {retval.element = new ComponentNameActualArgument($id.text);
	                     setLocation(retval.element,id.start,id.stop);
        | at='@' idd=identifierRule {retval.element = new ParameterReferenceActualArgument($idd.text);
        			     setLocation(retval.element,at,idd.stop);}
multiSubobjectArgument returns [MultiActualComponentArgument element]
@init{retval.element = new MultiActualComponentArgument();}
	 start='[' (single=singleSubobjectArgument {retval.element.add(single.element);} 
	     (',' singlee=singleSubobjectArgument {retval.element.add(singlee.element);} )* )?
	 stop=']' { setLocation(retval.element,start,stop);}
	;	lexer grammar JLoL;
@header {
  package subobjectjava.input;
@members {
  protected boolean enumIsKeyword = true;
  protected boolean assertIsKeyword = true;
Export
    	:	'export'
Connector
    	:	'connector'
Connect
	:	'connect'
Name
  	:	'name'
Overrides
    	:	'overrides'
