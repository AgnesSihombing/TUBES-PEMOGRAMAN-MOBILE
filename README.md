# 📦 Stockly

### Smart Stock Management for F&B

Stockly adalah aplikasi mobile untuk membantu bisnis **Food & Beverage (F&B)** dalam mengelola persediaan bahan baku secara lebih mudah, terstruktur, dan efisien.

Stockly tidak hanya mencatat stok masuk dan keluar, tetapi juga menghubungkan **penjualan menu dengan resep (Recipe/Bill of Materials)** sehingga penggunaan bahan baku dapat dihitung dan stok dapat dikurangi secara otomatis.

---

## 🎯 Tujuan

Stockly dibuat untuk membantu bisnis F&B:

* Mengelola stok bahan baku
* Mencatat bahan masuk dan keluar
* Menghubungkan menu dengan resep
* Mengurangi stok berdasarkan bahan yang digunakan
* Melakukan stock opname
* Mencatat waste/spoilage
* Memantau tanggal kedaluwarsa
* Mendapatkan notifikasi ketika stok menipis

---

## ❗ Problem

Dalam bisnis F&B, pencatatan stok tidak cukup hanya dengan mencatat barang masuk dan keluar.

Ketika sebuah menu terjual, bahan baku yang digunakan juga harus ikut berkurang berdasarkan resep.

Contohnya:

> 1 Kopi Susu terjual

Resep:

```text
Susu  → 150 ml
Kopi  → 18 gr
Gula  → 10 gr
```

Maka stok bahan akan berkurang sesuai jumlah tersebut.

Tanpa sistem yang terintegrasi, proses ini dapat menyebabkan:

* Kesalahan pencatatan stok
* Perbedaan stok sistem dengan stok fisik
* Bahan baku habis tanpa diketahui
* Kesulitan melacak waste
* Kesulitan mengetahui bahan yang mendekati kedaluwarsa
* Proses stock opname yang memakan waktu

---

## 💡 Solusi

Stockly menghubungkan **menu, resep, dan inventory** dalam satu aplikasi.

### Alur utama

```text
Menu Terjual
     ↓
Recipe / BOM
     ↓
Hitung Bahan
     ↓
Stock Deduction
     ↓
Update Stok
     ↓
Cek Minimum Stock
     ↓
Low Stock Alert
```

---

## ✨ Features

### 📦 1. Stock Management

Mengelola seluruh bahan baku yang digunakan dalam bisnis.

* Tambah bahan
* Edit bahan
* Hapus bahan
* Kategori bahan
* Satuan bahan
* Jumlah stok
* Minimum stock
* Riwayat stok

---

### 📥 2. Stock In

Mencatat bahan baku yang masuk.

Contoh:

```text
Bahan       : Susu UHT
Jumlah      : 20 Liter
Supplier    : Supplier A
Tanggal     : 30 September 2026
```

---

### 🍽️ 3. Recipe / Bill of Materials

Menghubungkan menu dengan bahan baku yang digunakan.

Contoh:

```text
Kopi Susu
│
├── Susu UHT : 150 ml
├── Kopi     : 18 gr
└── Gula     : 10 gr
```

---

### 📉 4. Automatic Stock Deduction

Ketika sebuah menu terjual, Stockly menghitung penggunaan bahan berdasarkan resep.

Contoh:

```text
1x Kopi Susu terjual

Susu UHT : -150 ml
Kopi     : -18 gr
Gula     : -10 gr
```

Stok kemudian diperbarui secara otomatis.

---

### 📊 5. Stock Opname

Membandingkan stok yang ada di sistem dengan stok fisik.

```text
System Stock   : 100 pcs
Physical Stock : 97 pcs
Variance       : -3 pcs
```

Fitur ini membantu pengguna mengetahui adanya selisih stok.

---

### 🗑️ 6. Waste / Spoilage

Mencatat bahan yang terbuang atau rusak.

Contoh:

* Bahan kedaluwarsa
* Bahan rusak
* Produk gagal
* Bahan tumpah

Pengguna juga dapat menambahkan foto sebagai dokumentasi.

---

### ⏰ 7. Expiry Date

Mencatat tanggal kedaluwarsa bahan sehingga pengguna dapat memantau bahan yang akan expired.

---

### 🔔 8. Low Stock Notification

Stockly dapat memberikan notifikasi ketika stok bahan berada di bawah batas minimum.

Contoh:

```text
⚠️ Low Stock

Susu UHT
Stok saat ini : 4.8 L
Minimum stok  : 5 L
```

---

### 📷 9. Scan Bahan

Bahan dapat diidentifikasi menggunakan barcode atau QR code untuk mempercepat proses pencatatan.

---

## 📱 Main Menu

```text
┌──────────────────────────┐
│         STOCKLY          │
│                          │
│  📷 Scan Bahan           │
│  📥 Input Masuk          │
│  🍽️ Recipe Menu          │
│  📊 Stock Opname         │
│  📦 Stok Masuk           │
│                          │
└──────────────────────────┘
```

---

## 👥 Target Users

Stockly ditujukan untuk bisnis F&B skala kecil hingga menengah, seperti:

* ☕ Cafe
* 🍽️ Restoran kecil
* 🥐 Bakery
* 🧋 Kedai minuman
* 🍱 Cloud kitchen
* 🍜 UMKM kuliner

---

# 🛠️ Teknologi yang Digunakan

### Frontend

* **Flutter**
* **Dart**
* **Material Design**

Flutter digunakan untuk membangun aplikasi mobile Stockly.

### Backend & Database

Stockly menggunakan layanan **Firebase** sehingga tidak membutuhkan server backend.

* **Cloud Firestore** — menyimpan data stok, bahan, menu, resep, supplier, dan transaksi.
* **Firebase Authentication** — login dan autentikasi pengguna.
* **Firebase Storage** — menyimpan foto waste atau dokumentasi bahan.
* **Firebase Cloud Messaging (FCM)** — notifikasi low stock dan expiry.

### Additional

* **mobile_scanner** — barcode/QR code scanning.
* **Git & GitHub** — version control.
* **Android Studio / VS Code** — development environment.

---

## 🏗️ Architecture

```text
┌────────────────────────────┐
│       Stockly Mobile       │
│                            │
│      Flutter + Dart        │
└──────────────┬─────────────┘
               │
               ▼
┌────────────────────────────┐
│          Firebase          │
│                            │
│ ┌────────────────────────┐ │
│ │   Firebase Auth        │ │
│ │   Cloud Firestore      │ │
│ │   Firebase Storage     │ │
│ │   Firebase Cloud Msg   │ │
│ └────────────────────────┘ │
└────────────────────────────┘
```

---

## 🗂️ Data Structure

Data utama yang digunakan dalam Stockly:

```text
Users
 │
 ├── Raw Materials
 │      ├── Category
 │      ├── Supplier
 │      ├── Stock
 │      ├── Unit
 │      └── Expiry Date
 │
 ├── Menu
 │      └── Recipe
 │              └── Raw Materials
 │
 ├── Stock Transactions
 │
 ├── Stock Opname
 │
 └── Waste
```

---

## 🔄 Example Workflow

### 1. Tambahkan bahan

```text
Nama       : Susu UHT
Kategori   : Dairy
Stok       : 20 L
Minimum    : 5 L
```

### 2. Buat resep

```text
Menu: Kopi Susu

Susu UHT → 150 ml
Kopi     → 18 gr
Gula     → 10 gr
```

### 3. Menu terjual

```text
Kopi Susu × 1
```

### 4. Sistem menghitung bahan

```text
Susu UHT → -150 ml
Kopi     → -18 gr
Gula     → -10 gr
```

### 5. Stok diperbarui

```text
Susu UHT
20 L → 19.85 L
```

### 6. Sistem mengecek minimum stock

Jika stok berada di bawah minimum:

```text
⚠️ Low Stock Alert
```

---

## 📌 Project Scope

Versi awal Stockly berfokus pada:

* Inventory management
* Raw material management
* Recipe/BOM
* Automatic stock deduction
* Stock opname
* Waste recording
* Expiry tracking
* Low-stock notification
---

## 🎯 Expected Impact

Stockly diharapkan dapat membantu bisnis F&B mengurangi kesalahan pencatatan dan memberikan informasi stok yang lebih akurat.

Dengan menghubungkan **penjualan → resep → penggunaan bahan → stok**, pengelolaan inventory dapat dilakukan secara lebih praktis melalui perangkat mobile.

---

## 📖 Project Status

> 🚧 **Under Development**

Stockly sedang dalam tahap pengembangan sebagai aplikasi mobile untuk manajemen stok F&B.

---

## 👩‍💻 Tech Stack

```text
Flutter
Dart
Firebase
├── Firebase Authentication
├── Cloud Firestore
├── Firebase Storage
└── Firebase Cloud Messaging

mobile_scanner
Git
GitHub
Android Studio / VS Code
```

---

# Stockly

**Smart Stock Management for F&B**

> *Track your stock. Control your ingredients. Run your business smarter.*
