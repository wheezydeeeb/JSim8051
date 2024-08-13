public class Instruction {
    private String mnemonic;
    private String op1;
    private String op2;

    private boolean isOp1Acc;
    private boolean isOp1Reg;
    private boolean isOp1B;

    private boolean isOp2Acc;
    private boolean isOp2Val;
    private boolean isOp2Reg;
    private boolean isOp2Lbl;

    public Instruction(String instructionLine) {
        int wspIdx = instructionLine.indexOf(" ");
        mnemonic = wspIdx != -1 ? instructionLine.substring(0, wspIdx).trim() : "";
        String operands = instructionLine.substring(wspIdx + 1).trim();

        int commaIdx = operands.indexOf(",");
        op1 = commaIdx != -1 ? operands.substring(0, commaIdx).trim() : operands;
        op2 = commaIdx != -1 ? operands.substring(commaIdx + 1).trim() : "";

        isOp1Acc = op1.charAt(0) == 'A' || op1.charAt(0) == 'a';
        isOp1Reg = op1.charAt(0) == 'R' || op1.charAt(0) == 'r';
        isOp1B = op1.charAt(0) == 'B' || op1.charAt(0) == 'b';

        isOp2Acc = op2.charAt(0) == 'A' || op2.charAt(0) == 'a';
        isOp2Val = op2.charAt(0) == '#';
        isOp2Reg = op2.charAt(0) == 'R' || op2.charAt(0) == 'r';
        isOp2Lbl = Character.isDigit(op2.charAt(0));
    }

    public String getMnemonic() { return mnemonic; }
    public String getOp1() { return op1; }
    public String getOp2() { return op2; }

    public boolean isOp1Acc() { return isOp1Acc; }
    public boolean isOp1Reg() { return isOp1Reg; }
    public boolean isOp1B() { return isOp1B; }

    public boolean isOp2Acc() { return isOp2Acc; }
    public boolean isOp2Val() { return isOp2Val; }
    public boolean isOp2Reg() { return isOp2Reg; }
    public boolean isOp2Lbl() { return isOp2Lbl; }
}

