
# Expense Tracker (Java CLI)

## Project URL
https://github.com/timothy-bautista/Expense-Tracker

## Overview
A simple Java command-line app to record expenses, list them, delete by ID, and view totals (overall or by month). Data is saved in `data.csv`.

## Requirements
- Java JDK (17+ recommended)
- Terminal

## Run
```bash
javac Main.java
java Main <command> [options]
```
## Commands

Add:

```bash
java Main add --description "Empanada" --amount 25
```

List:

```bash
java Main list
```

Delete:

```bash
java Main delete --id 3
```

Total:

```bash
java Main summary
```

Monthly total:

```bash
java Main summary --month 2
```

## Data Format

`data.csv` (one per line):
`id,date,description,amount`

## Notes

* Use quotes for descriptions with spaces.
* Month values: `1` to `12`.
* Commas in descriptions may break CSV parsing.
