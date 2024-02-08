import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CheckValidCommand {

    /**
     * instruction line variables
     * FORM --> [label:] mnemonic operands[1/2] [;comment]
     */
    String[] program = new String[1024];
    String instruction = "";
    String mnemonic = "";
    String op_1 = "";
    String op_2 = "";
    boolean is_op1_acc = false;
    boolean is_op1_reg = false;
    boolean is_op2_acc = false;
    boolean is_op_2_val = false;
    boolean is_op_2_reg = false;
    boolean is_op_2_lbl = false;
    int wsp_idx = -1;
    int comma_idx = -1;

    /**
     * registers
     */

    // register banks
    private int[][] reg_R = new int[4][8];
    private int reg_A = 0;
    private int reg_B = 0;
    // program counter
    private short reg_PC = 0;

    // ROM
    // private byte[][] rom = new byte[]


    //private final String[] reg_R = {A, B, R0, R1, R2, R3, R4, R5, R6, R7};

    /**
     * -----Program Status Word (PSW, Flag) Register-----
     * PSW.7 = CY  = Carry Bit
     * PSW.6 = AC  = Auxiliary Carry
     * PSW.5 = CY  = Carry Bit
     * PSW.4 = RS1 = Register Bank Selector 1
     * PSW.3 = RS0 = Register Bank Selector 0
     * PSW.2 = OV  = Overflow
     * PSW.1 = CY  = Carry Bit
     * PSW.0 = P   = Parity
     */
    private boolean[] PSW = new boolean[8];

    // path of the program to be executed
    private String program_path = "";

    public void get_program_path() throws IOException {
        program_path = "tests/" + (new BufferedReader(new InputStreamReader(System.in))).readLine().trim() + ".txt";
    }

    // read program from file to 'String program[]'
    public void get_program() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(program_path));
        String line;
        int count = 0;
        while (( line = br.readLine() ) != null){
            program[count++] = line;
        }
    }

    public void disp_program(){
        int count = 0;
        System.out.println("PROGRAM:\n--------------");
        while(program[count++] != null){
            System.out.println(program[count - 1]);
        }
        System.out.println("------------");
    }

    public void set_instruction() throws IOException {
        try {
            // complete instruction given by the user and index of whitespace in it
            instruction = program[reg_PC];
            wsp_idx = instruction.indexOf(" ");

            // mnemonic and operands present in the instruction
            mnemonic = wsp_idx != -1 ? instruction.substring(0, wsp_idx).trim() : "";
            String operands = instruction.substring(wsp_idx + 1).trim();

            // index of comma (if any) present in 'String operands'
            comma_idx = operands.indexOf(",");

            // resolved operands (1 / 2) from 'String operands'
            op_1 = comma_idx != -1 ? operands.substring(0, comma_idx).trim() : operands;
            op_2 = comma_idx != -1 ? operands.substring(comma_idx + 1).trim() : "";

            // Check the type of operand 1.
            is_op1_acc = op_1.charAt(0) == 'A' || op_1.charAt(0) == 'a';
            if (!is_op1_acc) is_op1_reg = op_1.charAt(0) == 'R' || op_1.charAt(0) == 'r';

            // Check for type of operand 2.
            is_op2_acc = op_2.charAt(0) == 'A' || op_2.charAt(0) == 'a';
            is_op_2_val = op_2.charAt(0) == '#';
            is_op_2_reg = op_2.charAt(0) == 'R' || op_2.charAt(0) == 'r';
            is_op_2_lbl = Character.isDigit(op_2.charAt(0));
        }
        catch (Exception ignored){
        }
    }

        public void disp_instruction_variables() {
            System.out.println();
        }

    // called on mnemonic = "MOV"
    public void on_MOV() {
        if (is_op1_acc) {
            // immediate addressing mode
            if (is_op_2_val) {
                reg_A += Byte.parseByte(op_2.substring(1).trim());
            }
            // register to register addressing mode
            else if (is_op_2_reg) {
                // reg_R[(PSW[4] ? 2 : 0) + (PSW[3] ? 1 : 0)][op_1.charAt(1) - '0'] = reg_R[(PSW[4] ? 2 : 0) + (PSW[3] ? 1 : 0)][op_2.charAt(1) - '0'];
                reg_A += reg_R[(PSW[4] ? 2 : 0) + (PSW[3] ? 1 : 0)][op_2.charAt(1) - '0'];
            }
        }
        else if (is_op2_acc){
            if (is_op1_reg) reg_R[(PSW[4] ? 2 : 0) + (PSW[3] ? 1 : 0)][op_1.charAt(1) - '0'] += reg_A;
        }
        System.out.println("MOV executed");
    }

    // called on mnemonic = "INC"
    public void on_INC() {
        if (is_op1_acc) reg_A++;
        else if (is_op1_reg) reg_R[(PSW[4] ? 2 : 0) + (PSW[3] ? 1 : 0)][op_1.charAt(1) - '0']++;
    }

    // called on mnemonic = "ADD"
    public void on_ADD() {
        System.out.println("ADD executed");
        boolean is_correct_operand = op_1.equals("A");
        if (is_correct_operand) {
            if (is_op_2_val) {
                reg_R[0][0] += Byte.parseByte(op_2.substring(1));
            } else if (is_op_2_reg) {
                reg_R[0][0] += reg_R[(PSW[4] ? 2 : 0) + (PSW[3] ? 1 : 0)][op_2.charAt(1) - '0'];
            }

        }
    }

    //called on mnemonic = "DJNZ"
    public void on_DJNZ() {
        if (is_op_2_lbl) {
            if (--reg_R[(PSW[4] ? 2 : 0) + (PSW[3] ? 1 : 0)][op_1.charAt(1) - '0'] != 0) {
                reg_PC = (short) (Byte.parseByte(op_2) - 1);
            }
        }
        System.out.println("DJNZ executed");
    }

    

    public void on_DEC() {
        reg_R[(PSW[4] ? 2 : 0) + (PSW[3] ? 1 : 0)][op_1.charAt(1) - '0']--;
    }

    public void disp_reg() {
        System.out.println("A = " + reg_A);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(reg_R[i][j] + "\t");
            }
            System.out.println();
        }
    }

    // execution of the instruction line happens here
    public void execute_cmd() {
        System.out.println("execute_cmd executed");
            switch (mnemonic) {
                case "MOV", "mov":
                    on_MOV();
                    break;
                case "SETB", "setb":
                    PSW[4] = op_1.equals("PSW.4");
                    PSW[3] = op_1.equals("PSW.3");
                    break;
                case "CLR", "clr":
                    // rs1 = !dst.equals("PSW.4");
                    // rs2 = !dst.equals("PSW.3");
                    break;
                case "DEC", "dec":
                    on_DEC();
                    break;
                case "INC", "inc":
                    on_INC();
                    break;
                case "DJNZ", "djnz":
                    on_DJNZ();
                    break;
                case "ADD", "add":
                    on_ADD();
                    break;
                default:
            }

            // increment Program Counter (PC)
            reg_PC++;

    }
    /* public boolean checkValidCommand(String cmd_gvn){
        String cmd = cmd_gvn.substring(0, cmd_gvn.indexOf(" "));
        return Arrays.binarySearch(valid_cmds, cmd) != -1;
    }
     */
    public void run() throws IOException {
        while(program[reg_PC] != null) {
            System.out.println("run loop executed");
            set_instruction();
            execute_cmd();
            disp_reg();
        }
    }

    public void reset(){
        /**
         * instruction line variables
         * FORM --> [label:] mnemonic operands[1/2] [;comment]
         */
        program = new String[1024];
        instruction = "";
        mnemonic = "";
        op_1 = "";
        op_2 = "";
        is_op_2_val = false;
        is_op_2_reg = false;
        is_op_2_lbl = false;
        wsp_idx = -1;
        comma_idx = -1;

        /**
         * registers
         */

        // register banks
        reg_R = new int[4][8];
        reg_A = 0;
        reg_B = 0;
        // program counter
        reg_PC = 0;


        /**
         * -----Program Status Word (PSW, Flag) Register-----
         * PSW.7 = CY  = Carry Bit
         * PSW.6 = AC  = Auxiliary Carry
         * PSW.5 = CY  = Carry Bit
         * PSW.4 = RS1 = Register Bank Selector 1
         * PSW.3 = RS0 = Register Bank Selector 0
         * PSW.2 = OV  = Overflow
         * PSW.1 = CY  = Carry Bit
         * PSW.0 = P   = Parity
         */
        PSW = new boolean[8];
    }



    public static void main(String[] args) throws IOException {
        CheckValidCommand ck = new CheckValidCommand();
        do {
            ck.get_program_path();
            ck.get_program();
            ck.disp_program();
            ck.run();
            // ck.disp_reg();
            ck.reset();
        } while (true);
    }
}
