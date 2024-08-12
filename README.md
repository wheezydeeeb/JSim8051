# 8051 Microcontroller Emulator

## Overview

This project is an educational and simplified emulator for the 8051 microcontroller. It simulates the basic functionalities of the 8051, including a subset of its instruction set, registers, program counter, and status registers. The project is designed to help students and developers understand the inner workings of the 8051 microcontroller by providing a more accessible and manageable version of the original architecture.

## Features

- **Instruction Set Support**: Implements a basic subset of the 8051 instruction set, including `MOV`, `INC`, `ADD`, `CLR`, `DJNZ`, and more.
- **Register Simulation**: Simulates the 8051's registers, including the accumulator (`reg_A`), B register (`reg_B`), and register banks (`reg_R`).
- **Program Status Word (PSW)**: Provides a simplified version of the PSW, including flags like carry, auxiliary carry, overflow, and register bank selectors.
- **Program Loader**: Loads assembly-like programs from text files and supports different character encodings and file metadata display.
- **Instruction Execution**: Executes loaded programs sequentially, updating the state of the emulator with each instruction.

## Project Structure

```
        .
        ├── src/
        │   ├── CheckValidCommand.java       # Main class orchestrating the program execution
        │   ├── RegisterBank.java            # Manages the registers and program counter
        │   ├── PSW.java                     # Manages the Program Status Word (PSW) flags
        │   ├── Instruction.java             # Represents an individual instruction with mnemonic and operands
        │   ├── InstructionSet.java          # Implements the supported instruction set and their operations
        │   ├── ProgramLoader.java           # Loads programs from files, with error handling and metadata support
        └── README.md                        # Project documentation
```

        ## Getting Started

        ### Prerequisites

        - Java Development Kit (JDK) 8 or higher
        - A text editor or IDE (e.g., IntelliJ IDEA, Eclipse)
        - Git for version control

        ### Installation

        1. **Clone the Repository**:
           ```sh
           git clone https://github.com/your-username/8051-emulator.git
           cd 8051-emulator
           ```

        2. **Compile the Code**:
           ```sh
           javac src/*.java
           ```

        3. **Run the Emulator**:
           ```sh
           java src.CheckValidCommand
           ```

        ### Usage

        1. **Load a Program**: Place your assembly-like code in a text file under the `tests/` directory. When prompted, enter the filename (without the `.txt` extension) to load and execute the program.

        2. **Supported Instructions**: The emulator currently supports a subset of 8051 instructions like `MOV`, `INC`, `ADD`, `CLR`, `DJNZ`. The program counter (`reg_PC`) is incremented after each instruction.

        ## Future Features

        ### TODO List

        1. **Interrupt Handling**: Implement an `InterruptController` to simulate 8051 interrupts, allowing specific events to trigger interrupt service routines (ISRs).
        2. **Timers/Counters**: Add support for 8051 timers and counters to enable time-based operations and event counting.
        3. **I/O Ports (P0 to P3)**: Simulate the 8051's I/O ports for interfacing with external devices.
        4. **Serial Communication (UART)**: Implement a `UART` class for simulating serial communication.
        5. **Memory Architecture**: Extend the emulator to simulate the 8051's internal RAM and ROM, including direct and indirect addressing.
        6. **Bit-Level Operations**: Support bit-level operations on bit-addressable registers and memory locations.
        7. **Stack Operations**: Implement stack operations (`PUSH`, `POP`) to support function calls and interrupt handling.
        8. **Expanded Instruction Set**: Continue adding more 8051 instructions, including logical operations, multiplication, and division.

        ## Contributing

        Contributions are welcome! Please fork the repository and submit a pull request with your changes. Ensure that your code is well-documented and adheres to the project's coding standards.

        ### Coding Standards

        - Follow Java's standard naming conventions.
        - Write clear and concise comments, particularly for complex logic or algorithms.
        - Use `Javadoc` comments for all public classes and methods.
        - Keep methods small and focused; avoid long, monolithic methods.

        ## License

        This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

        ## Acknowledgments

        This project is inspired by the desire to create an accessible and educational tool for learning about microcontrollers, specifically the 8051. Special thanks to the open-source community for providing tools and libraries that make projects like this possible.
