# 📱 Mobile Development Labs — ENSA Fes

A comprehensive collection of mobile development projects covering both **cross-platform development with .NET MAUI** and **native Android development with Java**. These projects were completed as part of the Computer Science Engineering curriculum at **ENSA Fes**.

---

## 🗂️ Projects Overview

### 🔷 .NET MAUI Projects

Cross-platform mobile applications built with .NET MAUI and C#.

| Project | Description | Location |
|---------|-------------|----------|
| **Quiz App** | Multi-page quiz application with category selection, interactive questions, and result tracking | Root directory |
| **Phoneword** | Phone number to word translator app demonstrating MAUI fundamentals | `phoneword/` |

**Tech Stack:**
- .NET MAUI / C#
- XAML (Extensible Application Markup Language)
- Visual Studio 2022
- Multi-platform targets (Windows, Android, iOS)

---

### 🔶 Android Projects (Java)

Native Android applications built with Java and Android Studio.

| Project | Description | Location |
|---------|-------------|----------|
| **Calculator** | Basic calculator application | `android_dev_rev/Calculator/` |
| **Hello World** | Introductory Android application | `android_dev_rev/helloWorld/` |
| **Intents Tutorial** | Multi-activity app demonstrating Android Intents | `android_dev_rev/IntentsTuto/` |
| **Quadratic Equation Solver** | Solves quadratic equations with UI for coefficient input | `android_dev_rev/QuadraticEquationSolver/` |
| **SQLite Tutorial** | Database operations demo using SQLite | `android_dev_rev/SQLiteTuto/` |
| **Movement Detection** | Advanced app featuring step detection, proximity sensing, and home/activity tracking | `Movement_Detection/` |

**Tech Stack:**
- Java
- Android Studio
- XML Layouts
- Android SDK

---

## 📁 Repository Structure

```
mobile-dev-labs/
│
├── QuizMauiApp.csproj           # Quiz App (MAUI)
├── App.xaml / App.xaml.cs
├── AppShell.xaml / AppShell.xaml.cs
├── MainPage.xaml / MainPage.xaml.cs
├── CategoriesPage.xaml / CategoriesPage.xaml.cs
├── QuizPage.xaml / QuizPage.xaml.cs
├── ResultPage.xaml / ResultPage.xaml.cs
├── QuizQuestion.cs              # Quiz data model
├── MauiProgram.cs
│
├── phoneword/                   # Phoneword MAUI App
│   ├── App.xaml
│   ├── AppShell.xaml
│   ├── MainPage.xaml
│   ├── MauiProgram.cs
│   ├── PhoneWordTranslator.cs
│   └── MicrosoftMauiCourse.csproj
│
├── android_dev_rev/             # Android Studio Projects (Java)
│   ├── Calculator/              # Simple calculator
│   ├── helloWorld/              # Hello World intro app
│   ├── IntentsTuto/             # Intent & multi-activity demo
│   ├── QuadraticEquationSolver/ # Quadratic equation solver
│   └── SQLiteTuto/              # SQLite database tutorial
│
├── Movement_Detection/          # Advanced Android project
│   ├── java/                    # Java source files
│   ├── layout/                  # XML layouts
│   ├── AndroidManifest.xml
│   └── [Activities: HomeActivity, MainActivity, ProximityActivity, StepActivity]
│
└── README.md
```

---

## ⚙️ Getting Started

### Prerequisites
- **For MAUI projects:** .NET 8 (or later), Visual Studio 2022
- **For Android projects:** Java JDK, Android Studio, Android SDK

### Running a MAUI Project

1. Clone or navigate to the repository
2. Open the project folder in **Visual Studio 2022**
3. Ensure .NET workload for MAUI is installed:
   ```bash
   dotnet workload restore
   ```
4. Build and run:
   ```bash
   dotnet build
   dotnet run -f net8.0-windows
   ```
   Or select your target platform (Android, iOS, Windows)

### Running an Android Project

1. Navigate to the project folder (e.g., `android_dev_rev/Calculator/`)
2. Open in **Android Studio**
3. Wait for Gradle sync to complete
4. Build & run on:
   - Android Emulator (recommended for testing)
   - Physical device (USB debugging enabled)

---

## 🎯 Skills Developed

Through these projects, the following skills were developed:

**MAUI Track:**
- Cross-platform app architecture design
- XAML layout and UI component usage (StackLayout, Grid, CollectionView)
- Navigation between pages (AppShell, routing)
- Event handling and user interaction
- Multi-page application patterns
- Data binding and state management

**Android Track:**
- Android app lifecycle and activity management
- XML layout design and resource management
- Intent usage and multi-activity navigation
- Database operations (SQLite)
- Sensor integration (step counter, proximity)
- Event handling and click listeners
- User input validation and forms

---

## 📌 Project Highlights

### Quiz App (MAUI)
- Dynamic category selection
- Interactive multi-question quiz interface
- Result page with score tracking
- Multi-page navigation with AppShell

### Movement Detection (Android)
- Four distinct activities for different features
- Integration with device sensors (accelerometer, proximity)
- Step counting and activity detection
- Home screen navigation

---

## 📚 Learning Path

1. **Start with MAUI:** Begin with `phoneword/` project to learn MAUI basics
2. **Progress to Complex UI:** Explore the `QuizApp` to understand multi-page navigation
3. **Transition to Android:** Start with `android_dev_rev/helloWorld/` for fundamentals
4. **Build Skills:** Progress through Calculator, Intents, and SQLite tutorials
5. **Advanced:** Explore `Movement_Detection/` for sensor integration

---

## 🚀 Future Enhancements

- Enhanced Quiz scoring and history
- User authentication and profiles
- Cloud backend integration (Firebase/REST APIs)
- Improved UI/UX with animations
- Data persistence and sync capabilities
- Localization support

---

## 🤝 Contributing

Contributions are welcome! Feel free to submit issues or pull requests.

---

## 📄 License

Educational use only.

---

## 👨‍💻 Author

**Yassine Idryssy**  
Computer Science Engineering Student — ENSA Fes

