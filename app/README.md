# Beekeepers Notebook

**Beekeepers Notebook** is a native Android mobile application 
developed in Kotlin, designed to help beekeepers efficiently
manage and monitor their apiaries, hives, and queen bees. 
The application allows users to keep detailed records of 
inspections, inventories, and the health status of their hives.
* **
## Key Features

*   ## 📱 Key Features

*   **User Management:** Secure registration and login (Authentication).
*   **Apiary Control (Zones):** Record the geographic location (latitude/longitude), address, and details of each apiary.
*   **Inventory Management:** Control equipment, tools, and supplies.
*   **Queen Monitoring:** Tracking the age and breed of queen bees.
*   **Cloud Synchronization:** Data is stored and synchronized in real time using Firestore Database.

## 🛠️ Tech Stack

*   **Language:** [Kotlin](https://kotlinlang.org/)
*   **Architecture:** MVVM (Model-View-ViewModel) / Clean Architecture Layers (Entity, Data, Interface, Controller).
*   **User Interface (UI):** XML Layouts, Material Design Components.
*   **Network and API:**
    *   [Retrofit2](https://square.github.io/retrofit/): For consuming REST services.
    *   [Gson](https://github.com/google/gson): For serializing and deserializing JSON data.
    *   OkHttp Logging Interceptor: For debugging network calls.
*   **Backend / Database:**
    *   **Firebase Authentication:** Identity management.
    *   **Firebase Firestore:** NoSQL database in the cloud.
    *   **Custom REST API:** Consumed via Retrofit (Node.js/Express).
*   **Concurrency:** `kotlinx-coroutines` for handling asynchronous operations.
*   **Jetpack Libraries:**
    *   Core KTX
    *   Lifecycle & ViewModel
    *   ConstraintLayout
    *   Activity KTX

## 📂 Project Structure

The code is organized into the following main packages:

*   `Entity`: Contains data models and DTOs (e.g., `Person`, `InventoryItem`, `DTOZone`).
*   `Data`: Data source implementations (e.g., `RestInventoryDataManager`, `FirebasePersonDataManager`).
*   `Interfaces`: API definitions (`InventoryApi`) and data contracts (`IDataManager`).
*   `Controller`: Business logic that connects the UI with the data (e.g., `PersonController`).

---

# 🌐 Beekeepers Notebook API

RESTful API built with **Node.js, Express, and Firebase Firestore** for beekeeping management.
link http://IP_DE_TU_PC_EN_LA_RED:3001/
### 👤 Users
Application user management.

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| GET | `/users` | Get all users. |
| GET | `/users/:id` | Get a user by their ID. |
| POST | `/users` | Create a new user. |
| PUT | `/users/:id` | Update an existing user. |
| DELETE | `/users/:id` | Delete a user. |

<details>
<summary>View JSON Body Example</summary>

JSON body (POST/PUT example):
{
“name”: “Juan”,
“fLastName”: “Perez”,
“sLastName”: “Gomez”,
“email”: “juan@mail.com”,
“password”: “secretpassword”,
“phonePerson”: “8888-8888”,
“photoBase64”: “data:image/png;base64,...”
}
</details>

### 📍 Zones / Apiaries
Physical locations where hives are located.

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| GET | `/zones` | Get all zones. |
| GET | `/zones/:id` | Get a zone by ID. |
| GET | `/zones/by-person/:personID` | Filter zones by beekeeper ID. |
| POST | `/zones` | Create a new zone. |
| PUT | `/zones/:id` | Update a zone. |
| DELETE | `/zones/:id` | Delete a zone. |

<details>
<summary>View JSON Body Example</summary>
</details>
JSON body (POST/PUT example):
{
“personID”: “USER_ID”,
“name”: “North Apiary”,
“state”: “Alajuela”,
“district”: “San Carlos”,
“address”: “Finca La Esperanza”,
“latitude”: 10.12345,
“longitude”: -84.12345
}
<details>

</details>

### 🐝 Hives
Individual boxes where bees live.

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| GET | `/hives` | Get all hives. |
| GET | `/hives/:id` | Get a hive by ID. |
| POST | `/hives` | Create a new hive. |
| PUT | `/hives/:id` | Update a hive. |
| DELETE | `/hives/:id` | Delete a hive. |

<details>
<summary>View JSON Body Example</summary>

</details>

JSON body (POST/PUT example):
{
“name”: “Hive 01”,
“zoneId”: “ZONE_ID”,
“boxType”: “Langstroth”,
“queenId”: “QUEEN_ID”
} 
<details>
</details>

### 🍯 Harvests
Honey harvest log.

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| GET | `/harvests` | Get all harvests. |
| GET | `/harvests/:id` | Get a harvest by ID. |
| GET | `/harvests/by-hive/:beehiveId` | Filter harvests by Hive. |
| GET | `/harvests/by-zone/:zoneID` | Filter harvests by Zone. |
| POST | `/harvests` | Register a new harvest. |
| PUT | `/harvests/:id` | Update a harvest. |
| DELETE | `/harvests/:id` | Delete a harvest. |

<details>
<summary>View JSON Body Example</summary>

JSON body (POST/PUT example):
{
“beehiveId”: “ID_OF_THE_HIVE”,
“zoneID”: “ID_OF_THE_ZONE”,
“personId”: “ID_OF_THE_USER”,
“honeyFrames”: 5,
“honeyAmountKg”: 12.5,
“honeyAmountKgNetHarvest”: 10.0,
“DateHarvest”: “2024-12-14”
}
</details>

### 📦 Inventory
Beekeeper's tools and materials.

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| GET | `/inventory` | Get all inventory. |
| GET | `/inventory/:id` | Get an item by ID. |
| GET | `/inventory/by-person/:personId` | Filter inventory for a specific user. |
| POST | `/inventory` | Create a new item. |
| PUT | `/inventory/:id` | Update an item. |
| DELETE | `/inventory/:id` | Delete an item. |

<details>
<summary>View JSON Body Example</summary>

JSON body (POST/PUT example):
{
“personId”: “USER_ID”,
“nameInventoryItem”: “Protective suit”,
“inventoryItemTotalQuantity”: 2,
“inventoryDataType”: “Equipment”
}
</details>

### 👑 Queens
Management of queen bees.

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| GET | `/queens` | Get all queens. |
| GET | `/queens/:id` | Get a queen by ID. || GET | `/queens/by-person/:personId` | Filter queens by User. |
| GET | `/queens/by-hive/:hiveId` | Filter queens by Assigned hive. |
| POST | `/queens` | Register a new queen. |
| PUT | `/queens/:id` | Update a queen. |
| DELETE | `/queens/:id` | Delete a queen. |

<details>
<summary>View JSON Body Example</summary>

json { "hiveId": "HIVE_ID", 
"personId": "USER_ID", 
"zoneID": "ZONE_ID", 
"typeInternal": "Italian", 
"entryDateInternal": "2024-05-20" }
* **