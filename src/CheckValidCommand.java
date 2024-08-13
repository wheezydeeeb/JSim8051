import java.io.IOException;

public class CheckValidCommand {
    private RegisterBank registers = new RegisterBank();
    private PSW psw = new PSW();
    private ProgramLoader loader = new ProgramLoader();
    private InstructionSet instructionSet = new InstructionSet(registers, psw);

    public void run() throws IOException {
        while (!loader.getProgram()[registers.getRegPC()].equalsIgnoreCase("END")) {
            String instructionLine = loader.getProgram()[registers.getRegPC()];
            Instruction instruction = new Instruction(instructionLine);
            instructionSet.executeInstruction(instruction);
            registers.displayRegisters();
        }
    }

    public void reset() {
        registers.reset();
        psw.reset();
    }

    public static void main(String[] args) throws IOException {
        CheckValidCommand ck = new CheckValidCommand();
        do {
            ck.loader.getProgramPath();
            ck.loader.loadProgram();
            ck.loader.displayProgram();
            ck.run();
            ck.reset();
        } while (true);
    }
}
