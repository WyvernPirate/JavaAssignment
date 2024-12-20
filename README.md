## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. You can add, remove, and update dependencies using the following steps:

1. Open the Command Palette by pressing `Ctrl+Shift+P` (Windows/Linux) or `Cmd+Shift+P` (macOS).
2. Type "Java: Manage Dependencies" and select the command.
3. In the `JAVA PROJECTS` view, right-click on the project and select "Add Dependency".
4. Enter the dependency details, such as the group ID, artifact ID, and version.
5. Click "Add" to add the dependency to your project.

More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).'

## Project Dependencies
- MySQL Connector
- XAMPP
- MySQL Server

## Project Title
Transcript Generating GUI Application

## Project Description
Objective: Develop a Java-based GUI application that generates academic transcripts for university students. This application will be used by both students and the Directorate of Registry Services to manage and generate transcripts efficiently.

### Key Features

#### 1. User Authentication
The application will have a login system for both students and the Directorate of Registry Services.

#### 2. Classes and Objects

##### Student Class
Includes instance variables such as:
- Full names
- Program
- Year of study
- Student ID
- Date of birth

##### Module Class
Includes instance variables such as:
- Module code
- Module name
- Module mark
- Number of credits
- Module year
- Module semester

#### 3. User Roles and Views

##### Registry View
- View all students
- View and save transcripts for any student

##### Student View
- Enter personal details (only once)
- Enter and save module information
- Calculate SGPA (Semester Grade Point Average) and CGPA (Cumulative Grade Point Average)
- Generate and write transcripts to a file in both TXT and PDF formats

#### Grade Calculation Logic
Methods to calculate final grades, SGPA, and CGPA

#### Error Handling
- Input validation
- File-related exception handling
- Ensure all inputs are validated in the GUI

## DOCUMENTATION OF WORK DONE SO FAR

### Completed Features

#### 1. User Authentication
- Implemented login system for both students and the Directorate of Registry Services
- Validated user credentials and authorized access to respective views

#### 2. Classes and Objects

##### Student Class
- Defined instance variables:
  - Full names
  - Program
  - Year of study
  - Student ID
  - Date of birth
- Implemented methods for getting and setting instance variables

##### Module Class
- Defined instance variables:
  - Module code
  - Module name
  - Module mark
  - Number of credits
  - Module year
  - Module semester
- Implemented methods for getting and setting instance variables

#### 3. User Roles and Views

##### Registry View
- Implemented view to display all students
- Implemented functionality to view and save transcripts for any student

##### Student View
- Implemented view to enter personal details (only once)
- Implemented functionality to enter and save module information
- Implemented methods to calculate SGPA (Semester Grade Point Average) and CGPA (Cumulative Grade Point Average)

#### Grade Calculation Logic
- Implemented methods to calculate final grades, SGPA, and CGPA

#### Error Handling
- Implemented input validation for all user inputs
- Implemented file-related exception handling
- Ensured all inputs are validated in the GUI

## transcript part has been completed and error handling
### In-Progress Features

#### 1. Transcript Generation
- Currently implementing functionality to generate and write transcripts to a file in both TXT and PDF formats

### Upcoming Features

#### 1. Additional Error Handling
- Plan to implement additional error handling for edge cases and unexpected user inputs

#### 2. User Interface Enhancements
- Plan to enhance the user interface to improve user experience and accessibility