/******************************************************************************
 * Nombre             : Maria Claudia Lainfiesta Herrera
 * Carnet             : 24000149
 * Curso              : Ciencias de la Computacion IV
 * 
 * Este archivo forma parte de: Laboratorio No.3(RDP).
 *****************************************************************************/

import java.io.StringReader;
import java.io.IOException;

%%

%{

    public static void main(String[] args) throws IOException {
        String input = "";
        for(int i=0; i < args.length; i++) {
            input += args[i];
        }
        Lexer lexer = new Lexer(new StringReader(input));
        Token token;
        while((token = lexer.nextToken()) != null) {
            System.out.println(token);
        }
    }


    /*

        ****************** LEER ********************

        - %public es para que la clase sea publica y se pueda utilizar en otros paquetes
        - %class Lexer es para que la clase generada se llame "Lexer"
        - %function nextToken el lexer generado tendra una funcion nextToken() para obtener
           el siguiente token del input
        - %type Token es para que la clase tome en cuenta que vamos a devolver un objeto Token

        todo esto no se modifica por ningun motivo :)

        *** Despues de "%type Token" pueden definir sus ER o tokens, van a encontrar
        el ejemplo para SEMI (";") y para WHITESPACE
    */
%}

%public
%class Lexer
%function nextToken
%type Token

SEMI = ";"
WHITE = (" "|\t|\n)
PLUS = "+"
MINUS = "-"
MULT = "*"
DIV = "/"
MOD = "%"
EXP = "^"
LPAREN = "("
RPAREN = ")"
NUMBER = ((([0-9]*\.[0-9]+)|([0-9]+\.)|\.[0-9]+|[0-9]+)([eE][+-]?[0-9]+)?)
UNARY = "~"

%%
<YYINITIAL>{NUMBER}       { return new Token(Token.NUMBER, yytext());   }

<YYINITIAL>{SEMI}         { return new Token(Token.SEMI);   }

<YYINITIAL>{PLUS}         { return new Token(Token.PLUS);   }

<YYINITIAL>{MINUS}        { return new Token(Token.MINUS);   }

<YYINITIAL>{MULT}         { return new Token(Token.MULT);   }

<YYINITIAL>{DIV}          { return new Token(Token.DIV);   }

<YYINITIAL>{MOD}          { return new Token(Token.MOD);   }

<YYINITIAL>{EXP}          { return new Token(Token.EXP);   }

<YYINITIAL>{LPAREN}       { return new Token(Token.LPAREN);   }

<YYINITIAL>{RPAREN}       { return new Token(Token.RPAREN);   }

<YYINITIAL>{UNARY}        { return new Token(Token.UNARY);   }

<YYINITIAL>{WHITE}        { /* NO HACER NADA */   }

<YYINITIAL>.              { return new Token(Token.ERROR);
                             /* todo lo demas es ERROR */   }
