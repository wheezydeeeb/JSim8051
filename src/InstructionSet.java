public class InstructionSet {
    private final RegisterBank registers;
    private final PSW psw;

    public InstructionSet(RegisterBank registers, PSW psw) {
        this.registers = registers;
        this.psw = psw;
    }

    public void executeMOV(Instruction instruction) {
        if (instruction.isOp1Acc() && instruction.isOp2Val()) {
            registers.setRegA(Integer.parseInt(instruction.getOp2().substring(1)));
        } else if (instruction.isOp1Acc() && instruction.isOp2Reg()) {
            registers.setRegA(registers.getRegister((psw.getFlag(4) ? 2 : 0) + (psw.getFlag(3) ? 1 : 0), instruction.getOp2().charAt(1) - '0'));
        } else if (instruction.isOp1Reg() && instruction.isOp2Acc()) {
            registers.setRegister((psw.getFlag(4) ? 2 : 0) + (psw.getFlag(3) ? 1 : 0), instruction.getOp1().charAt(1) - '0', registers.getRegA());
        } else if (instruction.isOp1Reg() && instruction.isOp2Val()) {
            registers.setRegister((psw.getFlag(4) ? 2 : 0) + (psw.getFlag(3) ? 1 : 0), instruction.getOp1().charAt(1) - '0', Integer.parseInt(instruction.getOp2().substring(1).trim()));
        } else {
            System.out.println("Syntax error at line " + (registers.getRegPC() + 1));
            System.exit(1);
        }
    }

    public void executeINC(Instruction instruction) {
        if (instruction.isOp1Acc()) registers.setRegA(registers.getRegA() + 1);
        else if (instruction.isOp1Reg()) registers.setRegister((psw.getFlag(4) ? 2 : 0) + (psw.getFlag(3) ? 1 : 0), instruction.getOp1().charAt(1) - '0', registers.getRegister((psw.getFlag(4) ? 2 : 0) + (psw.getFlag(3) ? 1 : 0), instruction.getOp1().charAt(1) - '0') + 1);
    }

    public void executeADD(Instruction instruction) {
        if (instruction.isOp1Acc()) {
            if (instruction.isOp2Val()) {
                registers.setRegA(registers.getRegA() + Integer.parseInt(instruction.getOp2().substring(1)));
            } else if (instruction.isOp2Reg()) {
                registers.setRegA(registers.getRegA() + registers.getRegister((psw.getFlag(4) ? 2 : 0) + (psw.getFlag(3) ? 1 : 0), instruction.getOp2().charAt(1) - '0'));
            }
        }
    }

    public void executeCLR(Instruction instruction) {
        if (instruction.isOp1Acc()) {
            registers.setRegA(0);
        } else {
            System.out.println("Syntax error at line " + (registers.getRegPC() + 1));
            System.exit(1);
        }
    }

    public void executeDJNZ(Instruction instruction) {
        if (instruction.isOp2Lbl()) {
            int regValue = registers.getRegister((psw.getFlag(4) ? 2 : 0) + (psw.getFlag(3) ? 1 : 0), instruction.getOp1().charAt(1) - '0') - 1;
            registers.setRegister((psw.getFlag(4) ? 2 : 0) + (psw.getFlag(3) ? 1 : 0), instruction.getOp1().charAt(1) - '0', regValue);
            if (regValue != 0) {
                registers.setRegPC((short) (Short.parseShort(instruction.getOp2()) - 1));
            }
        }
    }

    public void executeInstruction(Instruction instruction) {
        switch (instruction.getMnemonic().toUpperCase()) {
            case "MOV":
                executeMOV(instruction);
                break;
            case "INC":
                executeINC(instruction);
                break;
            case "ADD":
                executeADD(instruction);
                break;
            case "CLR":
                executeCLR(instruction);
                break;
            case "DJNZ":
                executeDJNZ(instruction);
                break;
            // Additional mnemonics can be added here
            default:
                System.out.println("Unknown instruction: " + instruction.getMnemonic());
        }
        registers.incrementPC();
    }
}
