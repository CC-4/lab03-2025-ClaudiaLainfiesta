/******************************************************************************
 * Nombre             : Maria Claudia Lainfiesta Herrera
 * Carnet             : 24000149
 * Curso              : Ciencias de la Computacion IV
 * 
 * Este archivo forma parte de: Laboratorio No.3(RDP).
 *****************************************************************************/

import java.util.LinkedList;
import java.util.Stack;

public class Parser {
    private int next;
    private Stack<Double> operandos;
    private Stack<Token> operadores;
    private LinkedList<Token> tokens;

    public boolean parse(LinkedList<Token> tokens) {
        this.tokens = tokens;
        this.next = 0;
        this.operandos = new Stack<Double>();
        this.operadores = new Stack<Token>();

        boolean aceptada = S();

        if (aceptada) {
            System.out.println("Aceptada? true");
            System.out.println("Resultado: " + this.operandos.peek());
        } else {
            System.out.println("Aceptada? false");
            System.out.println("Error :(");
        }

        if(this.next != this.tokens.size()) {
            return false;
        }
        return true;
    }

    private boolean term(int id) {
        if(this.next < this.tokens.size() && this.tokens.get(this.next).getId() == id) {
            Token token = this.tokens.get(this.next);

            if (id == Token.NUMBER) {
                operandos.push(token.getVal());
            } else if (id == Token.SEMI) {
                while (!this.operadores.empty()) {
                    popOp();
                }
            } else if (id == Token.LPAREN) {
                operadores.push(token);
            } else if (id == Token.RPAREN) {
                while (!operadores.empty() && operadores.peek().getId() != Token.LPAREN) {
                    popOp();
                }
                if (operadores.empty()) {
                    return false;
                }
                operadores.pop();
            } else {
                pushOp(token);
            }

            this.next++;
            return true;
        }
        return false;
    }

    private int pre(Token op) {

        switch(op.getId()) {
        	case Token.UNARY:
        		return 4;
        	case Token.EXP:
        		return 3;
            case Token.MULT:
        		return 2;
            case Token.DIV:
        		return 2;
            case Token.MOD:
        		return 2;
            case Token.MINUS:
                return 1;
            case Token.PLUS:
                return 1;
        	default:
        		return -1;
        }
    }

    private void popOp() {
        Token op = this.operadores.pop();

        if (op.equals(Token.PLUS)) {
        	double b = this.operandos.pop();
        	double a = this.operandos.pop();
        	this.operandos.push(a + b);

        } else if (op.equals(Token.MULT)) {
        	double b = this.operandos.pop();
        	double a = this.operandos.pop();
        	this.operandos.push(a * b);

        } else if (op.equals(Token.MINUS)){
            double b = this.operandos.pop();
            double a = this.operandos.pop();
            this.operandos.push(a - b);

        } else if (op.equals(Token.DIV)){
            double b = this.operandos.pop();
            double a = this.operandos.pop();
            this.operandos.push(a / b);

        } else if (op.equals(Token.MOD)){
            double b = this.operandos.pop();
            double a = this.operandos.pop();
            this.operandos.push(a % b);

        }else if (op.equals(Token.EXP)){
            double b = this.operandos.pop();
            double a = this.operandos.pop();
            this.operandos.push(Math.pow(a, b));
            
        }else if (op.equals(Token.UNARY)){
            double a = this.operandos.pop();
            this.operandos.push(-a);
        }
    }

    private void pushOp(Token op) {

        while (!this.operadores.empty() && pre(this.operadores.peek()) >= pre(op)) {
            popOp();
        }
        this.operadores.push(op);

    }

    private boolean S() {
        return E() && term(Token.SEMI);
    }


    private boolean E1(){
        return term(Token.LPAREN) && E() && term(Token.RPAREN) && N();
    }

    private boolean E2(){
        return term(Token.UNARY) && E() && N();
    }

    private boolean E3(){
        return term(Token.NUMBER) && N();
    }

    private boolean E() {
        int save = next;
        if (E1()) return true;
        next = save;
        if (E2()) return true;
        next = save;
        if (E3()) return true;
        next = save;
        return false;
    }
    
    private boolean N1(){
        return term(Token.PLUS) && E() && N();
    }
    
    private boolean N2(){
        return term(Token.MINUS) && E() && N();
    }
    
    private boolean N3(){
        return term(Token.MULT) && E() && N();
    }
    
    private boolean N4(){
        return term(Token.DIV) && E() && N();
    }
    
    private boolean N5(){
        return term(Token.MOD) && E() && N();
    }
    
    private boolean N6(){
        return term(Token.EXP) && E() && N();
    }
    
    private boolean N7(){
        return true;
    }
    
    private boolean N() {
        int save = next;

        if (N1()) return true;
        next = save;
        if (N2()) return true;
        next = save;
        if (N3()) return true;
        next = save;
        if (N4()) return true;
        next = save;
        if (N5()) return true;
        next = save;
        if (N6()) return true;
        next = save;
        if (N7()) return true;
        next = save;
        return false;
    }
}
