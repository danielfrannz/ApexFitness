# ApexFitness 30-Day Shred & Nutrition Tracker

Aplikasi olahraga dan kebugaran performa tinggi untuk program latihan 30 hari terstruktur, pelacakan defisit kalori harian terintegrasi, dan pemantauan tren penurunan berat badan dengan grafis analitik interaktif dalam balutan UI mode gelap minimalis atletis berkecepatan tinggi.

## User Review & Critical Decisions

> [!IMPORTANT]
> Tinjauan keputusan arsitektur dan preferensi atlet yang dikonfirmasi:
> - **Tema Visual Atletis**: *Dark Stealth OLED* dengan latar belakang arang pekat (`#0D0F12`), aksen *Hyper Neon Lime* (`#CCFF00`) dan *Cyber Cyan* (`#00F0FF`) untuk memberikan kesan instrumen telemetri atlet profesional dengan nol distraksi mata di malam hari.
> - **Program Latihan 30 Hari**: 4 fase progresif (Fondasi Pembakaran Lemak, Akselerasi Metabolik, Intensitas Tinggi/HIIT, dan Peak Conditioning Shred) dengan timer interaktif, panduan gerakan, dan estimasi pembakaran kalori otomatis.
> - **Integrasi Kalori & Berat Badan**: Kalkulasi defisit harian otomatis (TDEE/BMR) dari target penurunan berat badan, logging makanan cepat, pemantauan kalori bersih (Asupan vs Terbakar), dan pencatatan riwayat timbangan.
> - **Performa Super Ringan**: Menggunakan arsitektur lokal Room Database dan Jetpack Compose murni tanpa ketergantungan eksternal yang lambat agar aplikasi berjalan instan, responsif, dan hemat baterai.

---

### 1. Overview & Core Concept

- **What It Does**: Memberikan panduan latihan harian selama 1 bulan penuh (Day 1 - 30) yang adaptif, mencatat asupan makanan serta pembakaran kalori harian secara komparatif, dan menyajikan visualisasi grafik penurunan berat badan berkala terhadap target akhir pengguna.
- **Target Audience**: Atlet, pegiat kebugaran, dan individu yang ingin menurunkan berat badan secara konsisten dan terukur dengan aplikasi yang cepat, intuitif, dan tidak membebani perangkat.
- **Key Value**: Mengintegrasikan tiga pilar penurunan berat badan (latihan harian, neraca kalori harian, dan tren berat badan) ke dalam satu dashboard performa gelap yang presisi tanpa lag.

---

### 2. User Experience & Visual Design

#### Key User Flows
1. **Onboarding & Target Setting**: Pengguna memasukkan berat awal, target berat badan, tinggi badan, dan durasi target. Aplikasi menghitung BMI dan rekomendasi defisit kalori harian.
2. **Daily Workout Execution**: Pengguna memilih hari latihan aktif dari kalender 30 hari. Membuka detail sesi latihan (set, repetisi/durasi, animasi/instruksi, timer istirahat interaktif), menyelesaikan latihan, dan otomatis menambahkan kalori terbakar ke neraca harian.
3. **Calorie & Nutrition Tracking**: Pengguna mencatat makanan harian (Sarapan, Makan Siang, Makan Malam, Camilan). Meteran cincin telemetri memperlihatkan *Target Kalori - Asupan + Terbakar = Sisa Kalori*.
4. **Weight Progress Analytics**: Pengguna menimbang badan dan mencatat berat terkini. Grafik kanvas kinetik menampilkan kurva riwayat timbangan, garis target, sisa kg menuju target, dan kecepatan penurunan rata-rata mingguan.

#### Visual Identity & Theme
- **Aesthetic Direction**: *Minimalist Cyber Athlete* — terinspirasi oleh perangkat telemetri Garmin dan estetika training pro. Latar belakang hitam pekat untuk menghemat daya layar OLED dan nyaman di mata saat malam hari.
- **Color Palette**:
  - Background Utama: `#0A0C0E` (Deep Void Black)
  - Surface Card: `#14171C` (Titanium Surface) dengan border `#202530`
  - Aksen Primer: `#CCFF00` (Electric Volt Lime — energik & kontras tinggi)
  - Aksen Sekunder: `#00F0FF` (Aero Cyan — status pemulihan & hidrasi)
  - Text: `#FFFFFF` (Primary High Emphasis), `#94A3B8` (Slate Muted Secondary)
  - Danger / Alert: `#FF4D4D` (Burnout Red)
- **Typography & Hierarchy**: Angka metrik menggunakan display bold tabular untuk kesan instrumen stopwatch presisi. Label menggunakan uppercase tracking lebar (`0.5.sp`).
- **Layout Precision**: M3 NavigationBar berprofil ramping, kartu metrik berkontur tajam dengan sudut 14dp, dan touch-target minimal 48dp.

---

### 3. Key Product Decisions & Trade-Offs

- **Room Database untuk Persistensi Data**:
  - *Chosen Approach*: SQLite lokal teroptimasi via AndroidX Room KSP dengan `Flow` reaktif.
  - *Why*: Menjamin performa secepat kilat (lancar 60+ FPS), berfungsi penuh secara offline tanpa jeda jaringan, serta sangat ringan bagi memori perangkat.
  - *Alternative Considered*: Firestore cloud sync dihindari untuk mematuhi mandat utama pengguna ("buat aplikasinya ringan dan juga lancar tanpa membebani perangkat").
- **Custom Canvas Interactive Weight Chart**:
  - *Chosen Approach*: Grafik garis kanvas buatan sendiri (Native Jetpack Compose DrawScope) dengan gradien aksen dan penanda titik timbangan interaktif.
  - *Why*: Sangat ringan, nol penambahan ukuran pustaka (library size), animasi transisi halus, dan rendering instan tanpa alokasi memori berlebih.

---

### 4. Technical Architecture & Data Strategy

```
┌─────────────────────────────────────────────────────────────────┐
│                       ApexFitness UI Layer                      │
│                                                                 │
│ ┌────────────────┐ ┌─────────────────┐ ┌──────────────────────┐ │
│ │ WorkoutScreen  │ │ NutritionScreen │ │ AnalyticsWeightScreen│ │
│ │ (30-Day Plan & │ │ (Calorie Tracker│ │ (Interactive Canvas   │ │
│ │ Active Workout)│ │  & Macro Meter) │ │  Trend Chart)        │ │
│ └───────┬────────┘ └────────┬────────┘ └──────────┬───────────┘ │
│         │                   │                     │             │
│         └───────────────────┼─────────────────────┘             │
│                             ▼                                   │
│              ApexFitnessViewModel (StateFlow)                   │
└─────────────────────────────┬───────────────────────────────────┘
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                      Repository Layer                           │
│  - WorkoutPlanRepository (30 Days Program & State)              │
│  - NutritionRepository (Meal Logs & Daily Calorie Balance)      │
│  - WeightProgressRepository (Weight Logs & Milestones)          │
│  - UserProfileRepository (Target Weight, Deficit Settings)      │
└─────────────────────────────┬───────────────────────────────────┘
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│               AppDatabase (Room Database + KSP)                 │
│  ┌───────────────────────┐  ┌────────────────────────────────┐  │
│  │ WorkoutDayEntity      │  │ CalorieLogEntity               │  │
│  │ (Day 1-30, exercises) │  │ (mealType, calories, timestamp)│  │
│  ├───────────────────────┤  ├────────────────────────────────┤  │
│  │ WeightLogEntity       │  │ UserProfileEntity              │  │
│  │ (weightKg, date, note)│  │ (startKg, targetKg, tdee)      │  │
│  └───────────────────────┘  └────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
```

#### Komponen Interaktif & State Mapping
1. **Program 30 Hari**:
   - `DayCard`: Menampilkan nomor hari, nama fokus (e.g. "Full Body Metabolic Burn"), durasi, jumlah kalori terbakar, dan status selesai (Checkmark Volt Lime).
   - `ActiveWorkoutDialog / Screen`: Timer hitung mundur istirahat, tombol lanjut set, dan konfirmasi penyelesaian.
2. **Monitoring Kalori**:
   - `CalorieGauge`: Tampilan cincin persentase kalori dinamis (Asupan vs Target Defisit).
   - `AddMealDialog`: Dialog cepat memilih jenis makanan (Sarapan, Makan Siang, Malam, Camilan) dan nilai kalori.
3. **Grafik Berat Badan**:
   - `WeightChart`: Kanvas grafik dengan grid vertikal tanggal, kurva bezier halus berat badan, garis horizontal putus-putus target berat badan, dan kartu ringkasan (Total Turun, Sisa Target, BMI Terkini).
   - `LogWeightDialog`: Input berat badan harian yang langsung memperbarui kurva grafik.
