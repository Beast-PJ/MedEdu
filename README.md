# Medical 3D Body Viewer - Android App

## Overview

This Android application provides an interactive 3D human body visualization tool for medical professionals and patients, similar to ZygoteBody. It allows users to:

- Explore detailed anatomical structures in 3D
- Annotate specific areas with medical notes
- Share visual medical information with doctors
- View different body systems (skeletal, muscular, circulatory, etc.)

## Features

- **3D Human Body Visualization**:
  - Rotate, zoom, and pan the 3D model
  - Toggle different anatomical layers
  - Highlight specific body structures

- **Medical Annotation Tools**:
  - Add notes to specific body parts
  - Mark areas of pain or concern
  - Attach images or drawings

- **Doctor-Patient Collaboration**:
  - Save and share annotated models
  - Generate medical reports
  - Cloud synchronization (optional)

- **Educational Content**:
  - Anatomical information
  - Medical terminology
  - Common conditions visualization

## Technology Stack

- **3D Rendering**: Filament Engine (by Google)
- **3D Model Format**: GLB (binary GLTF)
- **Network**: Retrofit + OkHttp
- **Dependency Injection**: Hilt
- **Architecture**: MVVM with Clean Architecture
- **Minimum SDK**: Android 8.0 (API 26)

## Getting Started

### Prerequisites

- Android Studio Arctic Fox (2020.3.1) or later
- Android SDK 26+
- Java 11 or Kotlin 1.5+

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/medical-3d-viewer.git
   ```

2. Open the project in Android Studio

3. Add your 3D model files to `app/src/main/res/raw/`:
   - `human_male.glb` (required)
   - `human_female.glb` (optional)
   - `skeleton.glb` (optional)
   - `organs.glb` (optional)

4. Build and run the app

### Configuration

For cloud features, create a `secrets.properties` file in the root directory with:
```properties
API_BASE_URL="your_api_base_url"
API_KEY="your_api_key"
```

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/medical3dviewer/
│   │   │   ├── activities/          # Android Activities
│   │   │   ├── adapters/            # RecyclerView Adapters
│   │   │   ├── di/                  # Dependency Injection
│   │   │   ├── models/              # Data models
│   │   │   ├── repositories/        # Data repositories
│   │   │   ├── services/            # Network services
│   │   │   ├── utils/               # Utility classes
│   │   │   ├── viewmodels/          # ViewModels
│   │   │   ├── views/               # Custom Views
│   │   │   └── App.kt               # Application class
│   │   ├── res/
│   │   │   ├── layout/              # UI layouts
│   │   │   ├── menu/                # Menu definitions
│   │   │   ├── raw/                 # 3D model files
│   │   │   └── ...                  # Other resources
│   │   └── AndroidManifest.xml
├── build.gradle                     # Module-level build config
└── ...
```

## Adding New 3D Models

1. Obtain GLB format models (see [Resources](#resources) below)
2. Place the `.glb` files in `app/src/main/res/raw/`
3. Update `ModelRepository.kt` to include new models
4. Add model metadata in `res/values/models.xml`

Example model metadata:
```xml
<resources>
    <string-array name="available_models">
        <item>human_male</item>
        <item>human_female</item>
        <item>skeleton</item>
    </string-array>
</resources>
```

## Contributing

We welcome contributions! Please follow these steps:

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Resources

- [Filament Documentation](https://google.github.io/filament/)
- [GLTF/GLB Specifications](https://www.khronos.org/gltf/)
- [Anatomical Model Sources](#) *(add links to model sources)*

## License

This project is licensed under the [MIT License](LICENSE).

## Acknowledgments

- Google Filament team for the 3D engine
- Zygote Media Group for inspiration
- Medical professionals who provided feedback
