# Week 8 - OOP Fundamental Practice Problems

Five standalone Java solutions demonstrating polymorphism. Each problem has its own folder, `Main.java`, and compiled `.class` files.

## Solutions

| Folder | Problem |
| --- | --- |
| `Problem1_PaymentFees` | Payment processing fees for cards, wallets, and bank transfers |
| `Problem2_LibraryDueDates` | Borrowing due dates for books, DVDs, and magazines |
| `Problem3_DeliveryFees` | Standard, express, and international delivery fees |
| `Problem4_QuestionGrader` | MCQ, true/false, and essay grading |
| `Problem5_TransportFares` | Bus, train, and metro fares |

Each program uses type-specific implementations behind a shared operation. A registry constructs the appropriate implementation, and the processing loop handles each item uniformly.

## Compile and Run

Run these commands from the repository root. Replace the folder name with the problem you want to run:

```powershell
cd Problem1_PaymentFees
javac Main.java
java Main
```

Enter the input described in that problem's specification. The same commands work in each of the five folders. Compiling creates the `.class` files in the current folder.

## Problem 3 Note

The implementation follows the written fee rules. Those rules produce `EXPRESS: 24.00` and `INTERNATIONAL: 145.00` for the provided sample, while the sample's expected output says `29.00` and `155.00`. The written formulas and sample output do not agree.