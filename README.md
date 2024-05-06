# Gestra

This application allows users to control and interact with a glove device. The glove device connects via BLE (Bluetooth Low Energy) and uses TensorFlow Lite for gesture recognition. This README provides an overview of the project's file structure and how to get started.

## Table of Contents

- [Overview](#overview)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Screens and Composables](#screens-and-composables)
- [Data Layer](#data-layer)
- [Domain Layer](#domain-layer)
- [Util and Theme](#util-and-theme)
- [Contact](#contact)

## Overview

The application consists of three main layers:
- **UI Layer**: Contains composables and screens for the user interface.
- **Domain Layer**: Contains the core logic and use cases.
- **Data Layer**: Manages BLE communication and TensorFlow Lite model.

## Getting Started

To get started with the project, follow these steps:

1. Clone the repository: ```git clone https://github.com/hackfest-Queentylion/gestra-revamp.git```
2. Open the project in Android Studio.
3. Build and run the application on your preferred device or emulator.

Ensure you have the required dependencies installed and the project is properly configured.

## Project Structure

Here's an overview of the project's file structure:

- **MainActivity.kt**: Main entry point for the application.

- **data**: Contains data-related classes such as BLE communication and TensorFlow Lite integration.

- **domain**: Contains the core logic and use cases.

- **ui**: Contains UI-related components such as composables and screens.

- **util**: Contains utility classes and enums.

- **theme**: Contains theme-related files for the app.

## Screens and Composables

- **ui/composables**: Contains reusable Jetpack Compose components for building the user interface.

- **ui/screens**: Contains different screens for the app, including home, settings, conversation, and translate screens.

## Data Layer

- **data/ble**: Manages BLE communication with the glove device.

- **data/tflite**: Integrates with TensorFlow Lite for gesture recognition.

## Domain Layer

- **domain/ble**: Contains interfaces and models for BLE communication.

- **domain/tflite**: Contains interfaces for TensorFlow Lite integration.

## Util and Theme

- **util**: Contains utility classes and enums used throughout the project.

- **theme**: Contains theme-related files for the app's design.

## Contact

For any questions or feedback regarding this project, please reach out to [your contact email].

Thank you for using Gestra!