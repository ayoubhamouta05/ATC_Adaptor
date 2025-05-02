# ATC-ADAPTOR

A mobile healthcare tool designed to assist in calculating drug dosages for hepatic and renal cancer patients, based on patient-specific conditions. Built with modern Android technologies, it enables quick and accurate decision-making for medical professionals.

---

## 🚀 Features

- **Medical Dosage Calculator** for hepatic and renal cancer cases
- **QR Code Support**: Generate and scan QR codes containing patient information
- **Patient Data Security**: Encoded data using bitmap representation
- **Real-time Calculation** based on user inputs and conditions
- **Modern UI** using Jetpack Compose
- **Offline Capability** with local caching and storage
- Clean **MVI + MVVM** Architecture

---

## 🛠 Tech Stack

| Layer         | Tools Used                                                                 |
|---------------|------------------------------------------------------------------------------|
| Language      | Kotlin                                                                      |
| UI            | Jetpack Compose                                                             |
| Navigation    | Voyager (for Kotlin Multiplatform readiness)                                |
| Backend       | Custom backend with PHP + MySQL                                             |
| Networking    | Ktor client                                                                 |
| DI            | Dagger-Hilt                                                                 |
| Notifications | Firebase Cloud Messaging                                                    |
| QR Handling   | Bitmap + QR Scanner integration                                             |
| State & Flow  | Kotlin Coroutines, StateFlow                                                |
| Version Control | Git + GitHub                                                              |

---

## 📷 Screenshots

<img src="https://github.com/user-attachments/assets/40aaa2cc-05e7-4d42-8c24-10e0b16ee9d9" width="300" />
<img src="https://github.com/user-attachments/assets/1042af38-ffaa-4d5d-a1c4-fd76b653d3ff" width="300" />
<img src="https://github.com/user-attachments/assets/0855f4fc-226f-4265-8e75-40b10532e536" width="300" />
<img src="https://github.com/user-attachments/assets/32f450e8-2166-4179-a59c-2952b440ed53" width="300" />

<img src="https://github.com/user-attachments/assets/88247f09-2711-429d-bd3d-681e3d55c79f" width="300" />
<img src="https://github.com/user-attachments/assets/ec7f9382-668e-456c-aa1f-b396142b3c5e" width="300" />
<img src="https://github.com/user-attachments/assets/4c04336f-f1d4-4d00-ab40-2b59e0a831a3" width="300" />
<img src="https://github.com/user-attachments/assets/c0a362d8-664c-4d3c-aea9-f62f034ee52f" width="300" />
<img src="https://github.com/user-attachments/assets/8b22056b-6277-4881-96e0-53f418bb65aa" width="300" />
<img src="https://github.com/user-attachments/assets/4e793885-ca8a-44dd-9ddf-1ec04860fe56" width="300" />
<img src="https://github.com/user-attachments/assets/0312acaf-3d57-4d5b-944a-193ba5a0aa15" width="300" />
<img src="https://github.com/user-attachments/assets/acba08db-f4b0-4495-ba49-963ba887c997" width="300" />
<img src="https://github.com/user-attachments/assets/a743f2ec-fd75-4297-852d-38fe3b17ae33" width="300" />
<img src="https://github.com/user-attachments/assets/9f1440dd-dac1-400f-9bf1-751b2a9da981" width="300" />
<img src="https://github.com/user-attachments/assets/6514947d-10f3-407c-898a-63667fa3babf" width="300" />
<img src="https://github.com/user-attachments/assets/ee3d9da8-a54e-48c2-ab7f-ef7899f0e0c1" width="300" />
<img src="https://github.com/user-attachments/assets/64548eb9-cc0a-45f7-92f9-c2575c655138" width="300" />
<img src="https://github.com/user-attachments/assets/1b7b78d5-59e6-44ad-9205-0bb0c3730406" width="300" />
<img src="https://github.com/user-attachments/assets/c9886cc1-f2a4-42d1-833a-8e6768e1a3a5" width="300" />
<img src="https://github.com/user-attachments/assets/d4dedbb2-2041-4d3d-b6d8-4fa1f3f0bf56" width="300" />
<img src="https://github.com/user-attachments/assets/c1502869-0709-40f6-8aaf-d909060c8781" width="300" />
<img src="https://github.com/user-attachments/assets/e7b3e28b-4989-4749-b621-789f65378094" width="300" />
<img src="https://github.com/user-attachments/assets/502a9f02-bac2-4ada-96f0-73624220b4c0" width="300" />
<img src="https://github.com/user-attachments/assets/96d0b2ae-b470-4545-b4e2-ba717074c7be" width="300" />
<img src="https://github.com/user-attachments/assets/6ba067d5-247c-4529-ac1e-b7cd7fcfa5f7" width="300" />
<img src="https://github.com/user-attachments/assets/d8c4bcc9-da8d-4a48-ba29-517a768e56ba" width="300" />
<img src="https://github.com/user-attachments/assets/d6532fcc-e489-45ba-ba72-9b0138a5d3c5" width="300" />
<img src="https://github.com/user-attachments/assets/8f62d5b4-ec53-4bf8-b7c7-fa12efcc7b36" width="300" />


---

## 🏗 Architecture

The app uses a **hybrid MVI + MVVM** architecture:
- **MVVM** for general state separation
- **MVI** pattern for specific screens where state mutation and flow tracking are essential

---

## 📦 Installation

Clone the repository:
```bash
git clone https://github.com/ayoubhamouta05/ATC_Adaptor.git
