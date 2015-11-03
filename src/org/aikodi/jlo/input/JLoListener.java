// Generated from /home/marko/git/workspace/jlo/src/org/aikodi/jlo/input/JLo.g4 by ANTLR 4.5
package org.aikodi.jlo.input;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link JLoParser}.
 */
public interface JLoListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link JLoParser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void enterCompilationUnit(JLoParser.CompilationUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link JLoParser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void exitCompilationUnit(JLoParser.CompilationUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link JLoParser#namespace}.
	 * @param ctx the parse tree
	 */
	void enterNamespace(JLoParser.NamespaceContext ctx);
	/**
	 * Exit a parse tree produced by {@link JLoParser#namespace}.
	 * @param ctx the parse tree
	 */
	void exitNamespace(JLoParser.NamespaceContext ctx);
	/**
	 * Enter a parse tree produced by {@link JLoParser#importDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterImportDeclaration(JLoParser.ImportDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JLoParser#importDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitImportDeclaration(JLoParser.ImportDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JLoParser#namespaceReference}.
	 * @param ctx the parse tree
	 */
	void enterNamespaceReference(JLoParser.NamespaceReferenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link JLoParser#namespaceReference}.
	 * @param ctx the parse tree
	 */
	void exitNamespaceReference(JLoParser.NamespaceReferenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link JLoParser#klass}.
	 * @param ctx the parse tree
	 */
	void enterKlass(JLoParser.KlassContext ctx);
	/**
	 * Exit a parse tree produced by {@link JLoParser#klass}.
	 * @param ctx the parse tree
	 */
	void exitKlass(JLoParser.KlassContext ctx);
	/**
	 * Enter a parse tree produced by {@link JLoParser#classBody}.
	 * @param ctx the parse tree
	 */
	void enterClassBody(JLoParser.ClassBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link JLoParser#classBody}.
	 * @param ctx the parse tree
	 */
	void exitClassBody(JLoParser.ClassBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link JLoParser#bodyElement}.
	 * @param ctx the parse tree
	 */
	void enterBodyElement(JLoParser.BodyElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link JLoParser#bodyElement}.
	 * @param ctx the parse tree
	 */
	void exitBodyElement(JLoParser.BodyElementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code memberMethod}
	 * labeled alternative in {@link JLoParser#member}.
	 * @param ctx the parse tree
	 */
	void enterMemberMethod(JLoParser.MemberMethodContext ctx);
	/**
	 * Exit a parse tree produced by the {@code memberMethod}
	 * labeled alternative in {@link JLoParser#member}.
	 * @param ctx the parse tree
	 */
	void exitMemberMethod(JLoParser.MemberMethodContext ctx);
	/**
	 * Enter a parse tree produced by the {@code memberSubobject}
	 * labeled alternative in {@link JLoParser#member}.
	 * @param ctx the parse tree
	 */
	void enterMemberSubobject(JLoParser.MemberSubobjectContext ctx);
	/**
	 * Exit a parse tree produced by the {@code memberSubobject}
	 * labeled alternative in {@link JLoParser#member}.
	 * @param ctx the parse tree
	 */
	void exitMemberSubobject(JLoParser.MemberSubobjectContext ctx);
	/**
	 * Enter a parse tree produced by the {@code memberField}
	 * labeled alternative in {@link JLoParser#member}.
	 * @param ctx the parse tree
	 */
	void enterMemberField(JLoParser.MemberFieldContext ctx);
	/**
	 * Exit a parse tree produced by the {@code memberField}
	 * labeled alternative in {@link JLoParser#member}.
	 * @param ctx the parse tree
	 */
	void exitMemberField(JLoParser.MemberFieldContext ctx);
	/**
	 * Enter a parse tree produced by the {@code memberType}
	 * labeled alternative in {@link JLoParser#member}.
	 * @param ctx the parse tree
	 */
	void enterMemberType(JLoParser.MemberTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code memberType}
	 * labeled alternative in {@link JLoParser#member}.
	 * @param ctx the parse tree
	 */
	void exitMemberType(JLoParser.MemberTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subtypeConstraint}
	 * labeled alternative in {@link JLoParser#typeConstraint}.
	 * @param ctx the parse tree
	 */
	void enterSubtypeConstraint(JLoParser.SubtypeConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subtypeConstraint}
	 * labeled alternative in {@link JLoParser#typeConstraint}.
	 * @param ctx the parse tree
	 */
	void exitSubtypeConstraint(JLoParser.SubtypeConstraintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code superTypeConstraint}
	 * labeled alternative in {@link JLoParser#typeConstraint}.
	 * @param ctx the parse tree
	 */
	void enterSuperTypeConstraint(JLoParser.SuperTypeConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code superTypeConstraint}
	 * labeled alternative in {@link JLoParser#typeConstraint}.
	 * @param ctx the parse tree
	 */
	void exitSuperTypeConstraint(JLoParser.SuperTypeConstraintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equalityTypeConstraint}
	 * labeled alternative in {@link JLoParser#typeConstraint}.
	 * @param ctx the parse tree
	 */
	void enterEqualityTypeConstraint(JLoParser.EqualityTypeConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equalityTypeConstraint}
	 * labeled alternative in {@link JLoParser#typeConstraint}.
	 * @param ctx the parse tree
	 */
	void exitEqualityTypeConstraint(JLoParser.EqualityTypeConstraintContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subtypeArgument}
	 * labeled alternative in {@link JLoParser#typeArgument}.
	 * @param ctx the parse tree
	 */
	void enterSubtypeArgument(JLoParser.SubtypeArgumentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subtypeArgument}
	 * labeled alternative in {@link JLoParser#typeArgument}.
	 * @param ctx the parse tree
	 */
	void exitSubtypeArgument(JLoParser.SubtypeArgumentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code superTypeArgument}
	 * labeled alternative in {@link JLoParser#typeArgument}.
	 * @param ctx the parse tree
	 */
	void enterSuperTypeArgument(JLoParser.SuperTypeArgumentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code superTypeArgument}
	 * labeled alternative in {@link JLoParser#typeArgument}.
	 * @param ctx the parse tree
	 */
	void exitSuperTypeArgument(JLoParser.SuperTypeArgumentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equalityTypeArgument}
	 * labeled alternative in {@link JLoParser#typeArgument}.
	 * @param ctx the parse tree
	 */
	void enterEqualityTypeArgument(JLoParser.EqualityTypeArgumentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equalityTypeArgument}
	 * labeled alternative in {@link JLoParser#typeArgument}.
	 * @param ctx the parse tree
	 */
	void exitEqualityTypeArgument(JLoParser.EqualityTypeArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link JLoParser#method}.
	 * @param ctx the parse tree
	 */
	void enterMethod(JLoParser.MethodContext ctx);
	/**
	 * Exit a parse tree produced by {@link JLoParser#method}.
	 * @param ctx the parse tree
	 */
	void exitMethod(JLoParser.MethodContext ctx);
	/**
	 * Enter a parse tree produced by {@link JLoParser#methodHeader}.
	 * @param ctx the parse tree
	 */
	void enterMethodHeader(JLoParser.MethodHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link JLoParser#methodHeader}.
	 * @param ctx the parse tree
	 */
	void exitMethodHeader(JLoParser.MethodHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link JLoParser#returnType}.
	 * @param ctx the parse tree
	 */
	void enterReturnType(JLoParser.ReturnTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JLoParser#returnType}.
	 * @param ctx the parse tree
	 */
	void exitReturnType(JLoParser.ReturnTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JLoParser#keywordBlock}.
	 * @param ctx the parse tree
	 */
	void enterKeywordBlock(JLoParser.KeywordBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link JLoParser#keywordBlock}.
	 * @param ctx the parse tree
	 */
	void exitKeywordBlock(JLoParser.KeywordBlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code abstractModifier}
	 * labeled alternative in {@link JLoParser#modifier}.
	 * @param ctx the parse tree
	 */
	void enterAbstractModifier(JLoParser.AbstractModifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code abstractModifier}
	 * labeled alternative in {@link JLoParser#modifier}.
	 * @param ctx the parse tree
	 */
	void exitAbstractModifier(JLoParser.AbstractModifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code initModifier}
	 * labeled alternative in {@link JLoParser#modifier}.
	 * @param ctx the parse tree
	 */
	void enterInitModifier(JLoParser.InitModifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code initModifier}
	 * labeled alternative in {@link JLoParser#modifier}.
	 * @param ctx the parse tree
	 */
	void exitInitModifier(JLoParser.InitModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link JLoParser#parameters}.
	 * @param ctx the parse tree
	 */
	void enterParameters(JLoParser.ParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link JLoParser#parameters}.
	 * @param ctx the parse tree
	 */
	void exitParameters(JLoParser.ParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link JLoParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(JLoParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link JLoParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void exitParameterList(JLoParser.ParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link JLoParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(JLoParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link JLoParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(JLoParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprImplementation}
	 * labeled alternative in {@link JLoParser#implementation}.
	 * @param ctx the parse tree
	 */
	void enterExprImplementation(JLoParser.ExprImplementationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprImplementation}
	 * labeled alternative in {@link JLoParser#implementation}.
	 * @param ctx the parse tree
	 */
	void exitExprImplementation(JLoParser.ExprImplementationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code abstractImplementation}
	 * labeled alternative in {@link JLoParser#implementation}.
	 * @param ctx the parse tree
	 */
	void enterAbstractImplementation(JLoParser.AbstractImplementationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code abstractImplementation}
	 * labeled alternative in {@link JLoParser#implementation}.
	 * @param ctx the parse tree
	 */
	void exitAbstractImplementation(JLoParser.AbstractImplementationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nativeImplementation}
	 * labeled alternative in {@link JLoParser#implementation}.
	 * @param ctx the parse tree
	 */
	void enterNativeImplementation(JLoParser.NativeImplementationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nativeImplementation}
	 * labeled alternative in {@link JLoParser#implementation}.
	 * @param ctx the parse tree
	 */
	void exitNativeImplementation(JLoParser.NativeImplementationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blockImplementation}
	 * labeled alternative in {@link JLoParser#implementation}.
	 * @param ctx the parse tree
	 */
	void enterBlockImplementation(JLoParser.BlockImplementationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blockImplementation}
	 * labeled alternative in {@link JLoParser#implementation}.
	 * @param ctx the parse tree
	 */
	void exitBlockImplementation(JLoParser.BlockImplementationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JLoParser#subobject}.
	 * @param ctx the parse tree
	 */
	void enterSubobject(JLoParser.SubobjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link JLoParser#subobject}.
	 * @param ctx the parse tree
	 */
	void exitSubobject(JLoParser.SubobjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link JLoParser#inheritanceRelation}.
	 * @param ctx the parse tree
	 */
	void enterInheritanceRelation(JLoParser.InheritanceRelationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JLoParser#inheritanceRelation}.
	 * @param ctx the parse tree
	 */
	void exitInheritanceRelation(JLoParser.InheritanceRelationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code qualifiedType}
	 * labeled alternative in {@link JLoParser#type}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedType(JLoParser.QualifiedTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code qualifiedType}
	 * labeled alternative in {@link JLoParser#type}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedType(JLoParser.QualifiedTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenthesisType}
	 * labeled alternative in {@link JLoParser#type}.
	 * @param ctx the parse tree
	 */
	void enterParenthesisType(JLoParser.ParenthesisTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenthesisType}
	 * labeled alternative in {@link JLoParser#type}.
	 * @param ctx the parse tree
	 */
	void exitParenthesisType(JLoParser.ParenthesisTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code keywordType}
	 * labeled alternative in {@link JLoParser#type}.
	 * @param ctx the parse tree
	 */
	void enterKeywordType(JLoParser.KeywordTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code keywordType}
	 * labeled alternative in {@link JLoParser#type}.
	 * @param ctx the parse tree
	 */
	void exitKeywordType(JLoParser.KeywordTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JLoParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedName(JLoParser.QualifiedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link JLoParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedName(JLoParser.QualifiedNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link JLoParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(JLoParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link JLoParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(JLoParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link JLoParser#arguments}.
	 * @param ctx the parse tree
	 */
	void enterArguments(JLoParser.ArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link JLoParser#arguments}.
	 * @param ctx the parse tree
	 */
	void exitArguments(JLoParser.ArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expressionStatement}
	 * labeled alternative in {@link JLoParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(JLoParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expressionStatement}
	 * labeled alternative in {@link JLoParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(JLoParser.ExpressionStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code returnStatement}
	 * labeled alternative in {@link JLoParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(JLoParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code returnStatement}
	 * labeled alternative in {@link JLoParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(JLoParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignmentStatement}
	 * labeled alternative in {@link JLoParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentStatement(JLoParser.AssignmentStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignmentStatement}
	 * labeled alternative in {@link JLoParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentStatement(JLoParser.AssignmentStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varDeclaration}
	 * labeled alternative in {@link JLoParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclaration(JLoParser.VarDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varDeclaration}
	 * labeled alternative in {@link JLoParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclaration(JLoParser.VarDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code shiftExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterShiftExpression(JLoParser.ShiftExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code shiftExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitShiftExpression(JLoParser.ShiftExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code identifierExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierExpression(JLoParser.IdentifierExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code identifierExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierExpression(JLoParser.IdentifierExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code qualifiedCallExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedCallExpression(JLoParser.QualifiedCallExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code qualifiedCallExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedCallExpression(JLoParser.QualifiedCallExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code lowPriorityNumbericalExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLowPriorityNumbericalExpression(JLoParser.LowPriorityNumbericalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code lowPriorityNumbericalExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLowPriorityNumbericalExpression(JLoParser.LowPriorityNumbericalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code selfCallExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSelfCallExpression(JLoParser.SelfCallExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code selfCallExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSelfCallExpression(JLoParser.SelfCallExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code highPriorityNumbericalExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterHighPriorityNumbericalExpression(JLoParser.HighPriorityNumbericalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code highPriorityNumbericalExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitHighPriorityNumbericalExpression(JLoParser.HighPriorityNumbericalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code superExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSuperExpression(JLoParser.SuperExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code superExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSuperExpression(JLoParser.SuperExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOrExpression(JLoParser.OrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOrExpression(JLoParser.OrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAndExpression(JLoParser.AndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAndExpression(JLoParser.AndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code selfExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSelfExpression(JLoParser.SelfExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code selfExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSelfExpression(JLoParser.SelfExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nullExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNullExpression(JLoParser.NullExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nullExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNullExpression(JLoParser.NullExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orderExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterOrderExpression(JLoParser.OrderExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orderExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitOrderExpression(JLoParser.OrderExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code equalityExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpression(JLoParser.EqualityExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code equalityExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpression(JLoParser.EqualityExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exponentiationExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExponentiationExpression(JLoParser.ExponentiationExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exponentiationExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExponentiationExpression(JLoParser.ExponentiationExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code literalExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLiteralExpression(JLoParser.LiteralExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code literalExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLiteralExpression(JLoParser.LiteralExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParExpression(JLoParser.ParExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParExpression(JLoParser.ParExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolLiteral}
	 * labeled alternative in {@link JLoParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterBoolLiteral(JLoParser.BoolLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolLiteral}
	 * labeled alternative in {@link JLoParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitBoolLiteral(JLoParser.BoolLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code integerLiteral}
	 * labeled alternative in {@link JLoParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterIntegerLiteral(JLoParser.IntegerLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code integerLiteral}
	 * labeled alternative in {@link JLoParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitIntegerLiteral(JLoParser.IntegerLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code floatingPointLiteral}
	 * labeled alternative in {@link JLoParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterFloatingPointLiteral(JLoParser.FloatingPointLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code floatingPointLiteral}
	 * labeled alternative in {@link JLoParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitFloatingPointLiteral(JLoParser.FloatingPointLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code characterLiteral}
	 * labeled alternative in {@link JLoParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterCharacterLiteral(JLoParser.CharacterLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code characterLiteral}
	 * labeled alternative in {@link JLoParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitCharacterLiteral(JLoParser.CharacterLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringLiteral}
	 * labeled alternative in {@link JLoParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterStringLiteral(JLoParser.StringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringLiteral}
	 * labeled alternative in {@link JLoParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitStringLiteral(JLoParser.StringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link JLoParser#integerNumberLiteral}.
	 * @param ctx the parse tree
	 */
	void enterIntegerNumberLiteral(JLoParser.IntegerNumberLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link JLoParser#integerNumberLiteral}.
	 * @param ctx the parse tree
	 */
	void exitIntegerNumberLiteral(JLoParser.IntegerNumberLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code trueLiteral}
	 * labeled alternative in {@link JLoParser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void enterTrueLiteral(JLoParser.TrueLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code trueLiteral}
	 * labeled alternative in {@link JLoParser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void exitTrueLiteral(JLoParser.TrueLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code falseLiteral}
	 * labeled alternative in {@link JLoParser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void enterFalseLiteral(JLoParser.FalseLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code falseLiteral}
	 * labeled alternative in {@link JLoParser#booleanLiteral}.
	 * @param ctx the parse tree
	 */
	void exitFalseLiteral(JLoParser.FalseLiteralContext ctx);
}