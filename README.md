# Bank Account Management System & OOP Lab Assignments

**Student Name:** Gary Munamba  
**Registration Number:** H250094f  
**Department:** School of Information and Sciences  
**Assignment Title:** Bank Account Management System And Lab Assignment Using Java  

---

## 📌 Project Overview

This repository contains:
1. **Bank Account Management System (`Bank Assignment/`)**: An Object-Oriented Banking system demonstrating class hierarchies, inheritance, abstract classes, encapsulation, polymorphism, overdraft limits, minimum balance constraints, and interest/fee processing.
2. **Comprehensive OOP Lab Modules (10 Topics)**: Practical demonstrations covering core Java OOP principles from fundamental classes and objects to functional streams, generics, records, and advanced polymorphism.

---

## 🚀 How to Run the Project

### Option 1: Master Interactive Launcher (Recommended)

Run the master launcher which provides an interactive menu to run the Bank Assignment or any lab module:

```powershell
# Using Java directly:
java Main.java

# Or on Windows Command Prompt:
run.bat

# Or using PowerShell:
.\run.ps1
```

### Option 2: Quick Command-Line Execution

You can run specific modules directly from the command line:

```bash
# Run the Bank Account Management System:
java Main.java bank

# Run ALL modules sequentially:
java Main.java all

# Run a specific module by number (1 to 11):
java Main.java 1    # Bank Assignment
java Main.java 2    # Abstraction
java Main.java 6    # Exception Handling
java Main.java 7    # File IO, Lambdas & Streams
java Main.java 8    # Generics & Collections
```

### Option 3: Running Individual Modules Manually

You can also navigate into any module folder and compile/run directly:

```bash
# Bank Assignment
cd "Bank Assignment"
javac *.java
java BankDemo

# Exception Handling
cd "Exception Handling"
javac ExceptionHandlingExamples.java
java ExceptionHandlingExamples

# File IO, Lambdas & Streams
cd "File IO, Lambdas and Streams"
javac FileIOLambdasStreamsExamples.java
java FileIOLambdasStreamsExamples
```

### Option 4: Visual Studio Code

Preconfigured launch configurations are included in `.vscode/launch.json`. Open the project folder in VS Code, go to the **Run & Debug** pane (`Ctrl+Shift+D`), select **"Main (Interactive Menu)"** or any specific class, and click **Run**.

---

## 📁 Repository Structure

```
.
├── Main.java                                              # Master launcher & CLI runner
├── run.bat                                                # Windows Batch script launcher
├── run.ps1                                                # PowerShell script launcher
├── README.md                                              # Project documentation
├── .gitignore                                             # Git ignore rules for Java builds
│
├── Bank Assignment/                                       # Bank Management System
│   ├── Account.java                                       # Abstract base class
│   ├── CurrentAccount.java                                # Overdraft & monthly fee logic
│   ├── SavingsAccount.java                                # Minimum balance & interest logic
│   └── BankDemo.java                                      # Polymorphic demonstration
│
├── Abstraction, Abstract Classes and Interfaces/          # Module 2: Abstract classes, interfaces, default methods
│   └── AbstractionExamples.java
├── classes and objects/                                   # Module 3: Object state, reference semantics, composition
│   └── PracticalOOPExamples.java
├── constructors/                                          # Module 4: Overloading, copy constructors, static/instance init blocks
│   └── ConstructorsExamples.java
├── Encapsulation and Access Modifiers/                    # Module 5: Defensive copying, immutability, validation
│   └── EncapsulationExamples.java
├── Exception Handling/                                    # Module 6: Custom checked/unchecked exceptions, try-catch-finally
│   └── ExceptionHandlingExamples.java
├── File IO, Lambdas and Streams/                          # Module 7: NIO.2 file I/O, CSV parsing, streams & lambdas
│   ├── FileIOLambdasStreamsExamples.java
│   └── learners.csv
├── Generics and the Collections Framework/                # Module 8: Generic classes/methods, sets, maps, custom generic stack
│   └── CollectionsGenericsExamples.java
├── Inheritance/                                           # Module 9: Constructor chaining, 'super', composition vs inheritance
│   └── InheritanceExamples.java
├── Nested Types, Enums, Records and the Object Contract/  # Module 10: Enums, records, equals/hashCode, inner classes
│   └── NestedTypesExamples.java
└── Polymorphism/                                          # Module 11: Static & dynamic polymorphism, subtype polymorphism
    └── PolymorphismExamples.java
```

---

## 🏦 Bank Account Management System Architecture

- **`Account` (Abstract Base Class)**:
  - Encapsulates `accountNumber` and `balance`.
  - Concrete method `deposit(double amount)` with validation.
  - Abstract methods `withdraw(double amount)` and `endOfMonth()` to enforce specialized behavior.
- **`SavingsAccount` (Subclass)**:
  - Enforces `minimumBalance` constraint; rejects withdrawals that violate the threshold.
  - Implements `endOfMonth()` to calculate and credit interest (`5%`).
- **`CurrentAccount` (Subclass)**:
  - Enforces `overdraftLimit`; permits balance to go negative down to the configured limit.
  - Implements `endOfMonth()` to deduct maintenance fee (`$20.00`).
- **`BankDemo` (Driver Class)**:
  - Instantiates heterogeneous accounts (`List<Account>`).
  - Processes withdrawals and end-of-month cycles polymorphically.
