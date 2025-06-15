public class RegisterBank {
    private int[][] reg_R = new int[4][8];
    private int reg_A = 0;
    private int reg_B = 0;
    private short reg_PC = 0;

    public int getRegA() { return reg_A; }
    public void setRegA(int value) { reg_A = value; }

    public int getRegB() { return reg_B; }
    public void setRegB(int value) { reg_B = value; }

    public int getRegister(int bank, int index) { return reg_R[bank][index]; }
    public void setRegister(int bank, int index, int value) { reg_R[bank][index] = value; }

    public short getRegPC() { return reg_PC; }
    public void incrementPC() { reg_PC++; }
    public void setRegPC(short value) { reg_PC = value; }

    public void reset() {
        reg_R = new int[4][8];
        reg_A = 0;
        reg_B = 0;
        reg_PC = 0;
    }

    public void displayRegisters() {
        System.out.println("A = " + reg_A);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(reg_R[i][j] + "\t");
            }
            System.out.println();
        }
    }
}

// This is a test comment

