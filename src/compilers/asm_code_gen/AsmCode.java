package compilers.asm_code_gen;

import java.util.LinkedList;
import java.util.List;

import compilers.int_code_gen.ICGInstruction;

public class AsmCode {

	private List<ICGInstruction> instructionCodeList; // codigo intermedio
	private List<String> asmCodeList; // codigo assembler

	public AsmCode(List<ICGInstruction> instList) {
		this.asmCodeList = new LinkedList<>();
		this.instructionCodeList = instList;
	}

	public List<String> generateAsmCode() {
		asmCodeList = new LinkedList<>();// limpio la lista de cod asm
		if (instructionCodeList != null && !instructionCodeList.isEmpty()) {
			for (ICGInstruction inst : instructionCodeList) {
				switch (inst.getOp()) {

				case ADD:
					this.add(inst);
					break;
				case AND:
					this.and(inst);
					break;
				case ASSIGN:
					this.assign(inst);
					break;
				case CALL:
					this.call(inst);
					break;
				case CLASS:
					this.classDef(inst);
					break;
				case CMP:
					this.cmp(inst);
					break;
				case DEF:
					this.def(inst);
					break;
				case DISTINCT:
					this.distinct(inst);
					break;
				case DIV:
					this.div(inst);
					break;
				case EQUAL:
					this.equalDef(inst);
					break;
				case FUNCTION:
					this.function(inst);
					break;
				case GDEF:
					this.gdef(inst);
					break;
				case HIGH:
					this.high(inst);
					break;
				case HIGH_EQ:
					this.highEq(inst);
					break;
				case INC:
					this.inc(inst);
					break;
				case JE:
					this.je(inst);
					break;
				case JMP:
					this.jmp(inst);
					break;
				case JNE:
					this.jne(inst);
					break;
				case LABEL:
					this.label(inst);
					break;
				case LESS:
					this.less(inst);
					break;
				case LESS_EQ:
					this.lessEq(inst);
					break;
				case MIN:
					this.min(inst);
					break;
				case MOD:
					this.mod(inst);
					break;
				case MUL:
					this.mul(inst);
					break;
				case NOT:
					this.not(inst);
					break;
				case OR:
					this.or(inst);
					break;
				case RETURN:
					this.returnDef(inst);
					break;
				case SUB:
					this.sub(inst);
					break;

				}

			}

		}
		return asmCodeList;
	}

	private void sub(ICGInstruction inst) {
		// TODO Auto-generated method stub

	}

	private void returnDef(ICGInstruction inst) {
		// TODO Auto-generated method stub

	}

	private void or(ICGInstruction inst) {
		// TODO Auto-generated method stub

	}

	private void not(ICGInstruction inst) {
		// TODO Auto-generated method stub

	}

	private void mul(ICGInstruction inst) {
		// TODO Auto-generated method stub

	}

	private void mod(ICGInstruction inst) {
		// TODO Auto-generated method stub

	}

	private void min(ICGInstruction inst) {
		// TODO Auto-generated method stub

	}

	private void lessEq(ICGInstruction inst) {
		// TODO Auto-generated method stub

	}

	private void less(ICGInstruction inst) {
		// TODO Auto-generated method stub

	}

	private void label(ICGInstruction inst) {
		// TODO Auto-generated method stub

	}

	private void jne(ICGInstruction inst) {
		// TODO Auto-generated method stub

	}

	private void jmp(ICGInstruction inst) {
		// TODO Auto-generated method stub

	}

	private void je(ICGInstruction inst) {
		// TODO Auto-generated method stub

	}

	private void inc(ICGInstruction inst) {
		// TODO Auto-generated method stub

	}

	private void highEq(ICGInstruction inst) {
		// TODO Auto-generated method stub

	}

	private void high(ICGInstruction inst) {
		// TODO Auto-generated method stub

	}

	private void gdef(ICGInstruction inst) {
		// TODO Auto-generated method stub

	}

	private void function(ICGInstruction inst) {
		// TODO Auto-generated method stub

	}

	private void equalDef(ICGInstruction inst) {
		// TODO Auto-generated method stub

	}

	private void div(ICGInstruction inst) {
		// TODO Auto-generated method stub

	}

	private void distinct(ICGInstruction inst) {
		// TODO Auto-generated method stub

	}

	private void def(ICGInstruction inst) {
		// TODO Auto-generated method stub

	}

	private void cmp(ICGInstruction inst) {
		// TODO Auto-generated method stub

	}

	private void classDef(ICGInstruction inst) {
		// TODO Auto-generated method stub

	}

	private void call(ICGInstruction inst) {
		// TODO Auto-generated method stub

	}

	private void assign(ICGInstruction inst) {
		// TODO Auto-generated method stub

	}

	private void and(ICGInstruction inst) {
		// TODO Auto-generated method stub

	}

	private void add(ICGInstruction inst) {
		// TODO Auto-generated method stub

	}

}