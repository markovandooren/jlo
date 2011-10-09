lexer grammar JLoL;
@header {
  package subobjectjava.input;
}
@members {
  protected boolean enumIsKeyword = true;
  protected boolean assertIsKeyword = true;
}

    
Export
    	:	'export'
    	;

Connector
    	:	'connector'
    	;

Connect
	:	'connect'
	;    	

Name
  	:	'name'
  	;

Overrides
    	:	'overrides'
    	;