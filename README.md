# LaporKampus

### Aplikasi Mobile Pengaduan Fasilitas Kampus 

---

##  1. Deskripsi Proyek

**LaporKampus** merupakan aplikasi mobile berbasis Android yang dirancang untuk memfasilitasi mahasiswa dalam menyampaikan pengaduan terkait fasilitas kampus yang mengalami kerusakan atau gangguan.

Aplikasi ini memiliki dua jenis pengguna utama, yaitu **Mahasiswa (User)** dan **Admin**, dengan hak akses serta tanggung jawab yang berbeda. Mahasiswa dapat mengirimkan serta memantau pengaduan, sedangkan admin bertugas mengelola laporan, memperbarui status, dan memberikan tanggapan terhadap pengaduan.

---

##  2. Latar Belakang

Fasilitas kampus memiliki peran yang sangat penting dalam menunjang kegiatan akademik sehari-hari. Namun, kerusakan fasilitas sering kali tidak terdokumentasi dengan baik karena penyampaian laporan masih dilakukan secara langsung atau melalui komunikasi informal.

LaporKampus dikembangkan sebagai solusi digital yang menyediakan sistem pengaduan terpusat agar proses pelaporan, pengelolaan, dan pemantauan pengaduan menjadi jauh lebih terstruktur dan transparan.

---

##  3. Tujuan Pengembangan

-  Mempermudah mahasiswa dalam melaporkan kerusakan fasilitas kampus.
-  Menyediakan sistem pengelolaan pengaduan yang efisien bagi pihak admin.
-  Meningkatkan transparansi proses penanganan laporan dari awal hingga selesai.
-  Mempermudah pemantauan status pengaduan secara *real-time*.
-  Mendukung pemeliharaan fasilitas kampus agar tetap optimal.

---

##  4. Jenis Pengguna dan Hak Akses

###  A. Mahasiswa (User)
Mahasiswa merupakan pengguna yang menyampaikan laporan terkait permasalahan fasilitas kampus.

**Hak Akses:**
-  Registrasi dan login akun.
-  Melihat halaman beranda/dashboard.
-  Membuat pengaduan fasilitas kampus baru.
-  Memilih kategori dan lokasi fasilitas yang rusak.
-  Mengunggah foto bukti kerusakan.
-  Melihat riwayat pengaduan pribadi.
-  Memantau status pengaduan.
-  Melihat detail dan tanggapan dari admin.
-  Mengelola profil akun.

###  B. Admin
Admin merupakan pengguna yang bertanggung jawab mengelola dan menindaklanjuti pengaduan yang masuk.

**Hak Akses:**
-  Login menggunakan akun khusus admin.
-  Melihat dashboard pengelolaan pengaduan.
-  Melihat seluruh pengaduan yang masuk dari mahasiswa.
-  Melihat detail pengaduan beserta bukti foto.
-  Memeriksa dan memvalidasi laporan.
-  Memperbarui status pengaduan.
-  Memberikan tanggapan atau informasi kepada mahasiswa.
-  Melihat rekapitulasi pengaduan.

---

##  5. Fitur Utama Aplikasi

| Fitur Utama | Mahasiswa | Admin |
| :--- | :---: | :---: |
|  **Login** | ✓ | ✓ |
|  **Registrasi** | ✓ | - |
|  **Dashboard** | ✓ | ✓ |
|  **Membuat Pengaduan** | ✓ | - |
|  **Melihat Pengaduan** | Milik Sendiri | Semua Laporan |
|  **Mengunggah Foto** | ✓ | - |
|  **Memperbarui Status** | - | ✓ |
|  **Memberikan Tanggapan** | - | ✓ |
|  **Riwayat Pengaduan** | ✓ | ✓ |
|  **Profil** | ✓ | ✓ |

---

##  6. Status Pengaduan

-  **Menunggu:** Laporan telah dikirim oleh mahasiswa dan sedang menunggu pemeriksaan awal dari admin.
-  **Diproses:** Laporan telah diperiksa, disetujui, dan sedang ditindaklanjuti oleh petugas terkait.
-  **Selesai:** Permasalahan fasilitas telah tuntas ditangani.
-  **Ditolak:** Laporan tidak dapat ditindaklanjuti (disertai dengan alasan penolakan yang jelas).

---

##  7. Alur Kerja Aplikasi

1.  Mahasiswa melakukan registrasi akun baru atau langsung *login*.
2.  Mahasiswa mengisi formulir pengaduan fasilitas yang rusak.
3.  Mahasiswa mengirimkan laporan lengkap beserta foto pendukung kerusakan.
4.  Admin menerima dan memeriksa laporan yang masuk.
5.  Admin memperbarui status pengaduan sesuai kondisi penanganan di lapangan.
6.  Admin memberikan tanggapan atau informasi tindak lanjut kepada mahasiswa.
7.  Mahasiswa memantau perkembangan penanganan laporan melalui aplikasi.
8.  Pengaduan ditandai selesai setelah perbaikan fasilitas berhasil dilakukan.

---

##  8. Teknologi yang Digunakan

| Komponen | Teknologi |
| :--- | :--- |
|  **Platform** | Android |
|  **Bahasa Pemrograman** | Kotlin  |
|  **IDE** | Android Studio |
|  **UI Framework** | XML / Jetpack Compose |
|  **Database** | Firebase |
|  **Version Control** | Git & GitHub |

---

##  9. Pengembang

- **Nama Proyek:** LaporKampus
- **Kategori:** Mobile Application
- **Platform:** Android
- **Tahun:** 2026

---

> *“LaporKampus – Sampaikan Pengaduan, Wujudkan Kampus Lebih Baik.”*
