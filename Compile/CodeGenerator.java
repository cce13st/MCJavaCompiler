package Compile;

import Absyn.*;

import java.util.Iterator;

/**
 * Created by yeonni on 15. 6. 7..
 */
public class CodeGenerator {
	public Program root;
	private int regNum = 1;

    private final String SP = "SP";
    private final String FP = "FP";

    private final String MOVE = "MOVE";
    private final String ADD = "ADD";
    private final String SUB = "SUB";
    private final String MUL = "MUL";
    private final String DIV = "DIV";
    private final String I2F = "I2F";
    private final String FADD = "FADD";
    private final String FSUB = "FSUB";
    private final String FMUL = "FMUL";
    private final String FDIV = "FDIV";
    private final String F2I = "F2I";
    private final String TOZ = "TOZ";
    private final String JMP = "JMP";
    private final String JMPZ = "JMPZ";
    private final String JMPN = "JMPN";
    private final String LAB = "LAB";
    private final String READI = "READI";
    private final String READF = "READF";
    private final String WRITE = "WRITE";


    public CodeGenerator(Program p) {
		this.root = p;
	}
			
	
	public String emit() {
        String decl = "AREA\t\tSP\n";
        decl += "AREA\t\tFP\n";
        decl += "AREA\t\tVR\n";
        decl += "AREA\t\tMEM\n";

        String instr = "LAB\tSTART\n";
        //TODO initialize SP, FP
        //TODO call main function
        instr += emit(this.root);
        instr += "LAB\tEND\n";
        return decl + instr;
	}


    /* Helper Code for printing T-machine code */
	private int newRegister() {
		return this.regNum++;
	}

    private String Value(int num) {
        return Integer.toString(num);
    }

    private String Value(float num) {
        return Float.toString(num);
    }
	
	private String Reg(int num) {
		return "VR(" + num + ")";
	}
	
	private String Mem(int num) {
		return "MEM(" + num + ")";
	}
	
	private String MemRef(int num) {
		return "MEM(" + num + ")@";
	}

    /* make formatted T code */
    private String makeCode(String instr, String m1, String m2, String m3) {
        return instr + "\t\t" + m1 + "\t" + m2 + "\t" + m3 + "\n";
    }
    
    private String makeCode(String instr, String m1, String m2) {
        return instr + "\t\t" + m1 + "\t" + m2 + "\n";
    }

    private String makeCode(String instr, String m1) {
        return instr + "\t\t" + m1 + "\n";
    }

    private String pushStack(int n) {
        String instr = "";

        int tmp = newRegister();
        instr += makeCode(MOVE, Value(n), Reg(tmp));
        instr += makeCode(ADD, SP, Reg(tmp), SP);
        return instr;
    }

    private String popStack(int n) {
        String instr = "";

        int tmp = newRegister();
        instr += makeCode(MOVE, Value(n), Reg(tmp));
        instr += makeCode(SUB, SP, Reg(tmp), SP);
        return instr;
    }

    /* emit functions */

    private String emit(Program p) {
		
		DeclList dl = p.dlist;
		FuncList fl = p.flist;
        String instr = "";

        if (dl != null) {
            instr += emit(dl);
        }
        if (fl != null) {
            instr += emit(fl);
        }
		return instr;
	}

    private String emit(DeclList ast) {
        String instr = pushStack(ast.length);

        Iterator<Decl> iter = ast.list.iterator();
        Decl item;
        while (iter.hasNext()) {
            item = iter.next();
            instr += emit(item);
        }
        return instr;
    }

    private String emit(FuncList ast) {
        String instr = "";

        Iterator<Function> iter = ast.list.iterator();
        Function item;
        while (iter.hasNext()) {
            item = iter.next();
            instr += emit(item);
        }
        return instr;
    }

    private String emit(Decl ast) {
        return emit(ast.ilist);
    }

    private String emit(IdentList ast) {
        String instr = "";
        Iterator<Identifier> iter = ast.list.iterator();
        Identifier item;
        while (iter.hasNext()) {
            item = iter.next();
            instr += emit(item);
        }
        return instr;
    }

    private String emit(Identifier ast) {
        return "";//TODO
    }

    private String emit(Function ast) {
        String instr = makeCode(LAB, ast.id);

        if (ast.paramList != null) {
            instr += emit(ast.paramList);
        }
        instr += emit(ast.compoundStmt);

        instr += makeCode(LAB, ast.id + "EXIT");
        return instr;
    }

    private String emit(ParamList ast) {
        String instr = "";
        Iterator<Identifier> iter = ast.ilist.iterator();
        Identifier item;
        while (iter.hasNext()) {
            item = iter.next();
            instr += emit(item);
        }
        return instr;
    }

    private String emit(StmtList ast) {
        String instr = "";
        Iterator<Stmt> iter = ast.list.iterator();
        Stmt item;
        while(iter.hasNext()) {
            item = iter.next();
            instr += emit(item);
        }
        return instr;
    }

    private String emit(Stmt ast) {
        if (ast instanceof AssignStmt) {
            return emit((AssignStmt) ast);
        } else if (ast instanceof CallStmt) {
            return emit((CallStmt) ast);
        } else if (ast instanceof CompoundStmt) {
            return emit((CompoundStmt) ast);
        } else if (ast instanceof EmptyStmt) {
            return emit((EmptyStmt) ast);
        } else if (ast instanceof ForStmt) {
            return emit((ForStmt) ast);
        } else if (ast instanceof IfStmt) {
            return emit((IfStmt) ast);
        } else if (ast instanceof RetStmt) {
            return emit((RetStmt) ast);
        } else if (ast instanceof SwitchStmt) {
            return emit((SwitchStmt) ast);
        } else if (ast instanceof WhileStmt) {
            return emit((WhileStmt) ast);
        }
        return "";
    }

    private String emit(AssignStmt ast) {

    }

    private String emit(CallStmt ast) {

    }

    private String emit(CompoundStmt ast) {

    }

    private String emit(EmptyStmt ast) {
        return "";
    }

    private String emit(ForStmt ast) {

    }

    private String emit(IfStmt ast) {

    }

    private String emit(RetStmt ast) {

    }

    private String emit(SwitchStmt ast) {

    }

    private String emit(WhileStmt ast) {

    }

	private String emit(Exp ast) {
		if (ast instanceof ArrayExp) {
			return emit((ArrayExp) ast);
		}
		else if (ast instanceof BinOpExp) {
            return emit((BinOpExp) ast);
		}
		else if (ast instanceof CallExp) {
			return emit((CallExp) ast);
		}
		else if (ast instanceof FloatExp) {
			return emit((FloatExp) ast);
		}
		else if (ast instanceof IdExp) {
            return emit((IdExp) ast);
		}
		else if (ast instanceof IntExp) {
			return emit((IntExp) ast);
		}
		else if (ast instanceof UnOpExp) {
			return emit((UnOpExp) ast);
		}
		else if (ast instanceof F2IExp) {
			return emit((F2IExp) ast);
		}
		else if (ast instanceof I2FExp) {
			return emit((I2FExp) ast);
		}
        return "";
	}

    private String emit(ArrayExp ast) {

    }

    private String emit(BinOpExp ast) {

    }

    private String emit(CallExp ast) {

    }

    private String emit(FloatExp ast) {

    }

    private String emit(IdExp ast) {

    }

    private String emit(IntExp ast) {

    }

    private String emit(UnOpExp ast) {

    }

    private String emit(F2IExp ast) {

    }

    private String emit(I2FExp ast) {

    }

}