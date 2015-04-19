// Generated from /home/marko/git/workspace/jlo/src/org/aikodi/jlo/input/JLo.g4 by ANTLR 4.5
package org.aikodi.jlo.input;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link JLoParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface JLoVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link JLoParser#compilationUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompilationUnit(JLoParser.CompilationUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link JLoParser#namespace}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamespace(JLoParser.NamespaceContext ctx);
	/**
	 * Visit a parse tree produced by {@link JLoParser#importDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportDeclaration(JLoParser.ImportDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JLoParser#namespaceReference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamespaceReference(JLoParser.NamespaceReferenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link JLoParser#klass}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKlass(JLoParser.KlassContext ctx);
	/**
	 * Visit a parse tree produced by {@link JLoParser#classBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBody(JLoParser.ClassBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link JLoParser#bodyElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBodyElement(JLoParser.BodyElementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code memberMethod}
	 * labeled alternative in {@link JLoParser#member}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberMethod(JLoParser.MemberMethodContext ctx);
	/**
	 * Visit a parse tree produced by the {@code memberSubobject}
	 * labeled alternative in {@link JLoParser#member}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberSubobject(JLoParser.MemberSubobjectContext ctx);
	/**
	 * Visit a parse tree produced by the {@code memberField}
	 * labeled alternative in {@link JLoParser#member}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberField(JLoParser.MemberFieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link JLoParser#method}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethod(JLoParser.MethodContext ctx);
	/**
	 * Visit a parse tree produced by {@link JLoParser#methodHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodHeader(JLoParser.MethodHeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link JLoParser#returnType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnType(JLoParser.ReturnTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JLoParser#keywordBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKeywordBlock(JLoParser.KeywordBlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code abstractModifier}
	 * labeled alternative in {@link JLoParser#modifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAbstractModifier(JLoParser.AbstractModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link JLoParser#parameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameters(JLoParser.ParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link JLoParser#parameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterList(JLoParser.ParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link JLoParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(JLoParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprImplementation}
	 * labeled alternative in {@link JLoParser#implementation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprImplementation(JLoParser.ExprImplementationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code abstractImplementation}
	 * labeled alternative in {@link JLoParser#implementation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAbstractImplementation(JLoParser.AbstractImplementationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nativeImplementation}
	 * labeled alternative in {@link JLoParser#implementation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNativeImplementation(JLoParser.NativeImplementationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code blockImplementation}
	 * labeled alternative in {@link JLoParser#implementation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockImplementation(JLoParser.BlockImplementationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JLoParser#subobject}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubobject(JLoParser.SubobjectContext ctx);
	/**
	 * Visit a parse tree produced by {@link JLoParser#inheritanceRelation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInheritanceRelation(JLoParser.InheritanceRelationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JLoParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(JLoParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JLoParser#qualifiedName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedName(JLoParser.QualifiedNameContext ctx);
	/**
	 * Visit a parse tree produced by the {@code shiftExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShiftExpression(JLoParser.ShiftExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code identifierExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierExpression(JLoParser.IdentifierExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code qualifiedCallExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedCallExpression(JLoParser.QualifiedCallExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lowPriorityNumbericalExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLowPriorityNumbericalExpression(JLoParser.LowPriorityNumbericalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code selfCallExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelfCallExpression(JLoParser.SelfCallExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code highPriorityNumbericalExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHighPriorityNumbericalExpression(JLoParser.HighPriorityNumbericalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code superExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuperExpression(JLoParser.SuperExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpression(JLoParser.OrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpression(JLoParser.AndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code selfExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelfExpression(JLoParser.SelfExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nullExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullExpression(JLoParser.NullExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orderExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrderExpression(JLoParser.OrderExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code equalityExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpression(JLoParser.EqualityExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exponentiationExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExponentiationExpression(JLoParser.ExponentiationExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code literalExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralExpression(JLoParser.LiteralExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parExpression}
	 * labeled alternative in {@link JLoParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParExpression(JLoParser.ParExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link JLoParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(JLoParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link JLoParser#arguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArguments(JLoParser.ArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionStatement}
	 * labeled alternative in {@link JLoParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionStatement(JLoParser.ExpressionStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code returnStatement}
	 * labeled alternative in {@link JLoParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(JLoParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignmentStatement}
	 * labeled alternative in {@link JLoParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentStatement(JLoParser.AssignmentStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolLiteral}
	 * labeled alternative in {@link JLoParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolLiteral(JLoParser.BoolLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code integerLiteral}
	 * labeled alternative in {@link JLoParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegerLiteral(JLoParser.IntegerLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code floatingPointLiteral}
	 * labeled alternative in {@link JLoParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatingPointLiteral(JLoParser.FloatingPointLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code characterLiteral}
	 * labeled alternative in {@link JLoParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharacterLiteral(JLoParser.CharacterLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringLiteral}
	 * labeled alternative in {@link JLoParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteral(JLoParser.StringLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link JLoParser#integerNumberLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegerNumberLiteral(JLoParser.IntegerNumberLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code trueLiteral}
	 * labeled alternative in {@link JLoParser#booleanLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrueLiteral(JLoParser.TrueLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code falseLiteral}
	 * labeled alternative in {@link JLoParser#booleanLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalseLiteral(JLoParser.FalseLiteralContext ctx);
}