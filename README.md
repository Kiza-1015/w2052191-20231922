# Ticket Simulation System

This repository contains a comprehensive ticket simulation system implemented across three main components:

1. **Frontend**: Built with React (located in the `front-end` folder).
2. **Backend**: Built with Spring Boot (located in the `TicketSystem` folder).
3. **CLI**: A command-line interface for simulating the system, implemented in Java.

## Table of Contents

1. [Overview](#overview)
2. [Features](#features)
3. [Installation](#installation)
4. [Usage](#usage)
5. [Project Structure](#project-structure)
6. [Contributing](#contributing)
7. [License](#license)

---

## Overview

The Ticket Simulation System is designed to simulate real-world scenarios involving vendors releasing tickets and customers purchasing them. The system comprises:

- A **React-based frontend** to configure simulation parameters and control the simulation process.
- A **Spring Boot backend** that processes frontend inputs and coordinates the simulation.
- A **Java CLI tool** for simulating the system independently.

### Workflow
1. Users input configuration data (e.g., ticket pool size, release rate, retrieval rate) via the React frontend.
2. The frontend sends these inputs to the Spring Boot backend.
3. When the **Run Simulation** button is clicked, the backend triggers the simulation, orchestrating:
   - Vendors releasing tickets randomly.
   - Customers purchasing tickets in real time.

---

## Features

- **Frontend**: Intuitive UI for configuration and simulation control.
- **Backend**: Efficient processing of ticketing logic using Spring Boot.
- **CLI**: Independent simulation tool for debugging or standalone use.
- **Dynamic Simulation**: Real-time ticket release and purchase behavior.
- **Modular Architecture**: Clearly separated components for scalability and maintainability.

---

## Installation

### Prerequisites
Ensure the following are installed on your system:
- [Node.js](https://nodejs.org/)
- [Java JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) (Version 11 or higher)
- [Maven](https://maven.apache.org/)
- [Spring Boot CLI](https://spring.io/tools)

### Steps

#### 1. Clone the Repository
```bash
git clone https://github.com/Kiza-1015/w2052191-20231922.git
cd w2052191-20231922
```

#### 2. Set Up the Frontend
```bash
cd front-end
npm install
```

#### 3. Set Up the Backend
```bash
cd ../TicketSystem
mvn clean install
```

#### 4. Set Up the CLI
- Navigate to the CLI project directory and build the Java project using your preferred IDE or a terminal command like:
```bash
javac -d bin src/*.java
```

---

## Usage

### Running the System

#### 1. Start the Backend
Navigate to the backend folder (`TicketSystem`) and run:
```bash
mvn spring-boot:run
```

#### 2. Start the Frontend
Navigate to the frontend folder (`front-end`) and run:
```bash
npm start
```

#### 3. Simulate Using the CLI
Navigate to the CLI folder and run:
```bash
java -cp bin Main
```

### Simulation Process
1. Open the React frontend in your browser.
2. Configure the simulation parameters (e.g., ticket pool size, release rate).
3. Click the **Run Simulation** button.
4. Observe the simulation in real time as vendors release tickets and customers purchase them.

---

## Project Structure

```plaintext
w2052191-20231922
├── front-end/       # React frontend
├── TicketSystem/    # Spring Boot backend
├── CLI/             # Java command-line interface
├── README.md        # Project documentation
└── LICENSE          # License information
```

---

## Contributing

Contributions are welcome! Follow these steps to contribute:
1. Fork the repository.
2. Create a feature branch: `git checkout -b feature-name`.
3. Commit your changes: `git commit -m 'Add feature-name'`.
4. Push to your branch: `git push origin feature-name`.
5. Submit a pull request.

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## Contact

For any questions or feedback, feel free to contact:
- **Kiza-1015**: [GitHub Profile](https://github.com/Kiza-1015)

