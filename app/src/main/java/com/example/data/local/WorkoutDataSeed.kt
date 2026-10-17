package com.example.data.local

import com.example.data.model.WorkoutDay

object WorkoutDataSeed {
    fun generate30Days(): List<WorkoutDay> {
        val days = mutableListOf<WorkoutDay>()

        // Helper to format exercises
        fun ex(name: String, sets: Int, repsOrTime: String, muscle: String, tips: String): String {
            return "$name|$sets|$repsOrTime|$muscle|$tips"
        }

        // ==========================================
        // FASE 1: Fondasi & Pembakaran Lemak (Day 1 - 7)
        // ==========================================
        days.add(
            WorkoutDay(
                dayNumber = 1,
                phaseNumber = 1,
                phaseName = "Fase 1: Fondasi Pembakaran Lemak",
                title = "Full Body Ignition",
                focusDescription = "Membangun ketahanan kardiovaskular awal dan mengaktifkan seluruh kelompok otot.",
                durationMinutes = 25,
                caloriesBurnEst = 260,
                exercisesRaw = listOf(
                    ex("Jumping Jacks", 3, "45 Detik", "Kardio & Betis", "Pendaratan lembut dengan ujung kaki."),
                    ex("Bodyweight Squats", 3, "15 Repetisi", "Paha Depan & Glute", "Jaga dada tetap tegak, tumit menempel di lantai."),
                    ex("Push-ups", 3, "12 Repetisi", "Dada & Trisep", "Jaga tubuh lurus dari kepala hingga tumit."),
                    ex("Mountain Climbers", 3, "30 Detik", "Core & Bahu", "Tarik lutut cepat ke arah dada tanpa menaikkan pinggul."),
                    ex("Plank Hold", 3, "40 Detik", "Inti Tubuh (Core)", "Kencangkan perut dan bokong sepanjang waktu.")
                ).joinToString(";;")
            )
        )

        days.add(
            WorkoutDay(
                dayNumber = 2,
                phaseNumber = 1,
                phaseName = "Fase 1: Fondasi Pembakaran Lemak",
                title = "Core & Abs Sculpt",
                focusDescription = "Fokus pada pembentukan otot perut dan stabilitas tulang belakang.",
                durationMinutes = 22,
                caloriesBurnEst = 220,
                exercisesRaw = listOf(
                    ex("Bicycle Crunches", 3, "20 Repetisi", "Obliques & Perut", "Siku menyentuh lutut berlawanan dengan rotasi torso."),
                    ex("Leg Raises", 3, "12 Repetisi", "Perut Bawah", "Jangan biarkan punggung bawah terangkat dari matras."),
                    ex("Russian Twists", 3, "24 Repetisi", "Obliques", "Putar bahu sepenuhnya ke sisi kiri dan kanan."),
                    ex("Hollow Body Hold", 3, "30 Detik", "Core Total", "Tekan punggung bawah rapat ke lantai."),
                    ex("Elbow Plank", 3, "45 Detik", "Stabilitas Core", "Tarik pusar ke dalam ke arah tulang belakang.")
                ).joinToString(";;")
            )
        )

        days.add(
            WorkoutDay(
                dayNumber = 3,
                phaseNumber = 1,
                phaseName = "Fase 1: Fondasi Pembakaran Lemak",
                title = "Lower Body Fat Shred",
                focusDescription = "Menguatkan otot kaki besar untuk meningkatkan laju metabolisme pembakaran kalori.",
                durationMinutes = 28,
                caloriesBurnEst = 290,
                exercisesRaw = listOf(
                    ex("Forward Lunges", 3, "16 Repetisi", "Paha & Glute", "Lutut depan membentuk sudut 90 derajat."),
                    ex("Wall Sit", 3, "45 Detik", "Quadriceps", "Paha sejajar lantai, punggung rata di dinding."),
                    ex("Glute Bridges", 3, "15 Repetisi", "Bokong & Hamstring", "Tekan dari tumit dan kunci glute di puncak gerakan."),
                    ex("Calf Raises", 4, "20 Repetisi", "Betis", "Tahan 1 detik di posisi jinjit tertinggi."),
                    ex("Sumo Squats", 3, "15 Repetisi", "Paha Dalam & Glute", "Posisi kaki lebar dengan ujung kaki menghadap ke luar.")
                ).joinToString(";;")
            )
        )

        days.add(
            WorkoutDay(
                dayNumber = 4,
                phaseNumber = 1,
                phaseName = "Fase 1: Fondasi Pembakaran Lemak",
                title = "Active Recovery & Mobility",
                focusDescription = "Hari pemulihan aktif untuk meredakan nyeri otot dan menjaga mobilitas sendi.",
                durationMinutes = 18,
                caloriesBurnEst = 110,
                isRestDay = true,
                exercisesRaw = listOf(
                    ex("Cat-Cow Stretch", 2, "60 Detik", "Tulang Belakang", "Tarik napas saat melengkung, hembuskan saat membulat."),
                    ex("Child's Pose", 2, "90 Detik", "Punggung & Pinggul", "Rilekskan dahi ke lantai dan panjangkan kedua lengan."),
                    ex("Cobra Stretch", 2, "45 Detik", "Perut & Dada", "Bahu rileks menjauhi telinga."),
                    ex("Seated Hamstring Stretch", 2, "60 Detik", "Hamstring & Betis", "Raih ujung kaki tanpa memaksa membungkuk berlebihan.")
                ).joinToString(";;")
            )
        )

        days.add(
            WorkoutDay(
                dayNumber = 5,
                phaseNumber = 1,
                phaseName = "Fase 1: Fondasi Pembakaran Lemak",
                title = "Upper Body Conditioning",
                focusDescription = "Membangun postur atletis pada dada, bahu, punggung, dan lengan.",
                durationMinutes = 26,
                caloriesBurnEst = 270,
                exercisesRaw = listOf(
                    ex("Standard Push-ups", 3, "12 Repetisi", "Dada", "Rentang gerak penuh hingga dada hampir menyentuh lantai."),
                    ex("Pike Push-ups", 3, "10 Repetisi", "Bahu & Deltoid", "Pinggul tinggi ke atas membentuk huruf V terbalik."),
                    ex("Tricep Dips (Chair/Floor)", 3, "15 Repetisi", "Trisep", "Siku menekuk lurus ke belakang, bukan ke samping."),
                    ex("Plank Shoulder Taps", 3, "20 Repetisi", "Core & Bahu", "Minimalkan goyangan pada panggul saat menepuk bahu."),
                    ex("Superman Hold", 3, "40 Detik", "Punggung Bawah & Glute", "Angkat dada dan paha serentak dari lantai.")
                ).joinToString(";;")
            )
        )

        days.add(
            WorkoutDay(
                dayNumber = 6,
                phaseNumber = 1,
                phaseName = "Fase 1: Fondasi Pembakaran Lemak",
                title = "Cardio HIIT Surge",
                focusDescription = "Kombinasi interval cepat untuk memacu pembakaran kalori pasca latihan (EPOC).",
                durationMinutes = 24,
                caloriesBurnEst = 310,
                exercisesRaw = listOf(
                    ex("High Knees Sprint", 4, "30 Detik", "Kardio & Psoas", "Lutut setinggi pinggang dengan ritme konstan."),
                    ex("Burpees", 3, "10 Repetisi", "Seluruh Tubuh", "Lompat vertikal saat berdiri dari posisi push-up."),
                    ex("Skater Jumps", 3, "20 Repetisi", "Agilitas & Glute", "Lompat menyamping dengan stabilisasi satu kaki."),
                    ex("Butt Kicks", 3, "40 Detik", "Hamstring & Kardio", "Tumit menyentuh atau mendekati pantat."),
                    ex("Plank Jacks", 3, "30 Detik", "Core & Kardio", "Buka tutup kaki seperti jumping jack dalam posisi plank.")
                ).joinToString(";;")
            )
        )

        days.add(
            WorkoutDay(
                dayNumber = 7,
                phaseNumber = 1,
                phaseName = "Fase 1: Fondasi Pembakaran Lemak",
                title = "Rest & Reflection",
                focusDescription = "Istirahat total akhir pekan pertama. Evaluasi asupan kalori dan timbang berat badan.",
                durationMinutes = 15,
                caloriesBurnEst = 80,
                isRestDay = true,
                exercisesRaw = listOf(
                    ex("Jalan Santai 15 Menit", 1, "15 Menit", "Pemulihan Aktif", "Hirup udara segar dengan tempo santai."),
                    ex("Peregangan Leher & Bahu", 2, "60 Detik", "Relaksasi Trapezius", "Putar kepala perlahan dan regangkan kedua sisi leher.")
                ).joinToString(";;")
            )
        )

        // ==========================================
        // FASE 2: Akselerasi Metabolik (Day 8 - 14)
        // ==========================================
        days.add(
            WorkoutDay(
                dayNumber = 8,
                phaseNumber = 2,
                phaseName = "Fase 2: Akselerasi Metabolik",
                title = "Metabolic Full Body 2.0",
                focusDescription = "Meningkatkan intensitas repetisi untuk memaksimalkan defisit kalori harian.",
                durationMinutes = 30,
                caloriesBurnEst = 330,
                exercisesRaw = listOf(
                    ex("Jump Squats", 3, "12 Repetisi", "Kaki Eksplosif", "Mendarat dengan lembut menggunakan ujung telapak kaki."),
                    ex("Push-up with Rotation", 3, "12 Repetisi", "Dada & Obliques", "Buka satu tangan ke atas setelah setiap push-up."),
                    ex("Mountain Climbers Cepat", 4, "40 Detik", "Core & Stamina", "Kecepatan tinggi dengan postur punggung stabil."),
                    ex("Reverse Lunges", 3, "16 Repetisi", "Paha & Keseimbangan", "Langkah mundur yang stabil dan kontrol lutut."),
                    ex("Side Plank", 3, "30 Detik / Sisi", "Obliques & Pinggul", "Jaga garis lurus dari bahu hingga pergelangan kaki.")
                ).joinToString(";;")
            )
        )

        days.add(
            WorkoutDay(
                dayNumber = 9,
                phaseNumber = 2,
                phaseName = "Fase 2: Akselerasi Metabolik",
                title = "Abs V-Taper Shred",
                focusDescription = "Penguatan dinding perut secara 3 dimensi untuk tampilan perut lebih ramping.",
                durationMinutes = 25,
                caloriesBurnEst = 240,
                exercisesRaw = listOf(
                    ex("V-Ups", 3, "12 Repetisi", "Rectus Abdominis", "Sentuh ujung kaki di atas dengan tubuh membentuk huruf V."),
                    ex("Flutter Kicks", 3, "45 Detik", "Perut Bawah", "Gerakan kaki naik turun kecil tanpa menekuk lutut."),
                    ex("Plank to Push-up", 3, "10 Repetisi", "Lengan & Core", "Naik turun dari posisi siku ke telapak tangan bergantian."),
                    ex("Crossbody Mountain Climbers", 3, "30 Detik", "Obliques", "Arahkan lutut ke siku yang berseberangan."),
                    ex("Dead Bug", 3, "16 Repetisi", "Stabilitas Inti", "Gerakkan lengan dan kaki berlawanan dengan kontrol lambat.")
                ).joinToString(";;")
            )
        )

        days.add(
            WorkoutDay(
                dayNumber = 10,
                phaseNumber = 2,
                phaseName = "Fase 2: Akselerasi Metabolik",
                title = "Glute & Leg Power",
                focusDescription = "Peningkatan kekuatan fungsional tubuh bagian bawah dan pembakaran lemak paha.",
                durationMinutes = 28,
                caloriesBurnEst = 310,
                exercisesRaw = listOf(
                    ex("Bulgarian Split Squats (Chair)", 3, "10 Repetisi / Kaki", "Quad & Glute", "Satu kaki ditumpu di kursi, turunkan pinggul perlahan."),
                    ex("Sumo Squat Pulses", 3, "20 Repetisi", "Paha Dalam", "Tahan di bagian bawah dan lakukan denyut kecil."),
                    ex("Single Leg Glute Bridge", 3, "12 Repetisi / Sisi", "Glute & Hamstring", "Angkat satu kaki lurus dan dorong dengan kaki lainnya."),
                    ex("Jump Lunges", 3, "12 Repetisi", "Daya Ledak Kaki", "Tukar kaki di udara dengan pendaratan terkontrol."),
                    ex("Wall Sit March", 3, "40 Detik", "Stabilitas Quad", "Angkat tumit bergantian saat menahan wall sit.")
                ).joinToString(";;")
            )
        )

        days.add(
            WorkoutDay(
                dayNumber = 11,
                phaseNumber = 2,
                phaseName = "Fase 2: Akselerasi Metabolik",
                title = "Recovery & Core Mobility",
                focusDescription = "Peregangan mendalam pada fleksor pinggul dan dekompresi tulang belakang.",
                durationMinutes = 20,
                caloriesBurnEst = 120,
                isRestDay = true,
                exercisesRaw = listOf(
                    ex("Pigeon Pose", 2, "60 Detik / Sisi", "Fleksor Pinggul", "Buka sendi pinggul untuk meredakan kekakuan."),
                    ex("Downward Dog to Cobra", 2, "90 Detik", "Seluruh Rantai Belakang", "Transisi mengalir antara dua pose."),
                    ex("Thoracic Spine Twist", 2, "60 Detik / Sisi", "Punggung Tengah", "Rotasi dada secara santai dalam posisi berbaring.")
                ).joinToString(";;")
            )
        )

        days.add(
            WorkoutDay(
                dayNumber = 12,
                phaseNumber = 2,
                phaseName = "Fase 2: Akselerasi Metabolik",
                title = "Upper Body Hypertrophy",
                focusDescription = "Ketahanan otot dada, bahu, dan punggung atas untuk postur tegap atlet.",
                durationMinutes = 28,
                caloriesBurnEst = 280,
                exercisesRaw = listOf(
                    ex("Diamond Push-ups", 3, "10 Repetisi", "Trisep & Dada Tengah", "Ibu jari dan telunjuk membentuk segitiga di lantai."),
                    ex("Wide Arm Push-ups", 3, "12 Repetisi", "Dada Luar", "Posisi tangan lebih lebar dari bahu."),
                    ex("Pike Hold", 3, "30 Detik", "Bahu & Core", "Tahan posisi V terbalik dengan kepala di antara lengan."),
                    ex("Prone Cobra / Y-Raises", 3, "15 Repetisi", "Punggung Atas & Postur", "Angkat lengan berbentuk Y sambil meremas belikat."),
                    ex("Bench/Bed Dips", 3, "15 Repetisi", "Trisep", "Turunkan hingga siku membentuk sudut 90 derajat.")
                ).joinToString(";;")
            )
        )

        days.add(
            WorkoutDay(
                dayNumber = 13,
                phaseNumber = 2,
                phaseName = "Fase 2: Akselerasi Metabolik",
                title = "Tabata Fat Meltdown",
                focusDescription = "Protokol Tabata: 20 detik usaha maksimal, 10 detik istirahat selama 8 putaran.",
                durationMinutes = 26,
                caloriesBurnEst = 340,
                exercisesRaw = listOf(
                    ex("Sprint di Tempat", 4, "20s Kerja / 10s Istirahat", "Kardio Ekstrem", "Gerakkan tangan dan angkat lutut setinggi mungkin."),
                    ex("Burpee No-Pushup", 4, "20s Kerja / 10s Istirahat", "Daya Tahan Penuh", "Cepat berdiri dan lompat dari posisi squat thrust."),
                    ex("Lateral Hops", 4, "20s Kerja / 10s Istirahat", "Agilitas Kaki", "Lompat melompati garis imajiner bolak-balik."),
                    ex("Fast Mountain Climbers", 4, "20s Kerja / 10s Istirahat", "Core & Kalori", "Tempo secepat mungkin dengan punggung lurus.")
                ).joinToString(";;")
            )
        )

        days.add(
            WorkoutDay(
                dayNumber = 14,
                phaseNumber = 2,
                phaseName = "Fase 2: Akselerasi Metabolik",
                title = "Mid-Month Rest & Review",
                focusDescription = "Titik tengah program! Istirahat penuh, hidrasi cukup, dan cek penurunan berat badan.",
                durationMinutes = 15,
                caloriesBurnEst = 80,
                isRestDay = true,
                exercisesRaw = listOf(
                    ex("Peregangan Hamstring & Betis", 2, "60 Detik", "Pemulihan Kaki", "Peregangan pasif dengan napas teratur."),
                    ex("Deep Diaphragmatic Breathing", 1, "5 Menit", "Sistem Saraf Parasimpatik", "Tarik napas 4 detik, tahan 4 detik, buang 6 detik.")
                ).joinToString(";;")
            )
        )

        // ==========================================
        // FASE 3: Intensitas Tinggi & Ketahanan (Day 15 - 21)
        // ==========================================
        days.add(
            WorkoutDay(
                dayNumber = 15,
                phaseNumber = 3,
                phaseName = "Fase 3: Athletic Conditioning",
                title = "Spartan Bodyweight Assault",
                focusDescription = "Latihan sirkuit intensitas tinggi untuk menguji kekuatan dan ketahanan atletis.",
                durationMinutes = 32,
                caloriesBurnEst = 370,
                exercisesRaw = listOf(
                    ex("Burpee to Jump Squat", 3, "10 Repetisi", "Seluruh Tubuh", "Gabungkan burpee penuh dengan lompatan squat bertenaga."),
                    ex("Plyometric Push-ups", 3, "8 Repetisi", "Dada Eksplosif", "Dorong lantai hingga telapak tangan terangkat sejenak."),
                    ex("Walking Lunges", 3, "20 Langkah", "Kaki & Glute", "Langkah mantap tanpa jeda."),
                    ex("Bear Crawl Hold", 3, "40 Detik", "Core & Bahu", "Lutut melayang 2 cm di atas lantai, punggung datar."),
                    ex("High Knee Sprint", 3, "45 Detik", "Kardio Peak", "Pertahankan kecepatan tinggi hingga detik terakhir.")
                ).joinToString(";;")
            )
        )

        days.add(
            WorkoutDay(
                dayNumber = 16,
                phaseNumber = 3,
                phaseName = "Fase 3: Athletic Conditioning",
                title = "Iron Core Lockdown",
                focusDescription = "Ketahanan isometrik ekstrem untuk mengunci perut bagian depan dan samping.",
                durationMinutes = 26,
                caloriesBurnEst = 260,
                exercisesRaw = listOf(
                    ex("Plank Up-Downs", 4, "12 Repetisi", "Lengan & Core", "Ganti tangan awal setiap 6 repetisi."),
                    ex("Windshield Wipers (Lantai)", 3, "16 Repetisi", "Obliques & Panggul", "Ayunkan kaki lurus ke kiri dan kanan dengan terkontrol."),
                    ex("Hollow Body Rock", 3, "20 Goyangan", "Core Absolut", "Goyangkan tubuh seperti perahu tanpa mengubah bentuk c-curve."),
                    ex("Side Plank Knee-to-Elbow", 3, "10 Repetisi / Sisi", "Obliques Aktif", "Tarik lutut atas ke siku atas saat menahan plank."),
                    ex("Extended Plank", 3, "40 Detik", "Peregangan Perut", "Letakkan telapak tangan lebih jauh ke depan dari bahu.")
                ).joinToString(";;")
            )
        )

        days.add(
            WorkoutDay(
                dayNumber = 17,
                phaseNumber = 3,
                phaseName = "Fase 3: Athletic Conditioning",
                title = "Lower Body Sprint Mechanics",
                focusDescription = "Pengembangan tenaga dorong kaki, stabilitas engkel, dan pemangkasan lemak paha.",
                durationMinutes = 30,
                caloriesBurnEst = 340,
                exercisesRaw = listOf(
                    ex("Single Leg Romanian Deadlift", 3, "12 Repetisi / Kaki", "Hamstring & Keseimbangan", "Turunkan torso sambil meluruskan satu kaki ke belakang."),
                    ex("Jump Squats Tempo", 3, "15 Repetisi", "Daya Ledak", "Turun lambat 3 detik, lalu meledak melompat ke atas."),
                    ex("Curtsy Lunges", 3, "16 Repetisi", "Glute Medius", "Silangkan kaki ke belakang menyamping seperti memberi hormat."),
                    ex("Wall Sit Single Leg", 3, "20 Detik / Kaki", "Kekuatan Isolasi Quad", "Luruskan satu kaki ke depan saat menempel di dinding."),
                    ex("Speed Skaters", 3, "30 Detik", "Agilitas Lateral", "Lompat lebar menyamping dengan dorongan tumit luar.")
                ).joinToString(";;")
            )
        )

        days.add(
            WorkoutDay(
                dayNumber = 18,
                phaseNumber = 3,
                phaseName = "Fase 3: Athletic Conditioning",
                title = "Decompression & Mobility",
                focusDescription = "Pelepasan ketegangan otot panggul dan pemulihan sistem saraf pusat.",
                durationMinutes = 20,
                caloriesBurnEst = 110,
                isRestDay = true,
                exercisesRaw = listOf(
                    ex("Frog Stretch", 2, "60 Detik", "Paha Dalam & Selangkangan", "Buka lutut selebar mungkin di matras dan turunkan pinggul."),
                    ex("Pretzel Stretch", 2, "60 Detik / Sisi", "Glute & Tulang Belakang", "Silangkan kaki di dada dan tarik ke bahu."),
                    ex("Lizard Pose", 2, "60 Detik / Sisi", "Fleksor Pinggul Dalam", "Langkah satu kaki jauh ke depan dan turunkan siku ke lantai.")
                ).joinToString(";;")
            )
        )

        days.add(
            WorkoutDay(
                dayNumber = 19,
                phaseNumber = 3,
                phaseName = "Fase 3: Athletic Conditioning",
                title = "Upper Body Dominance",
                focusDescription = "Kekuatan dorong dan tahanan tubuh bagian atas dengan variasi kalistenik lanjutan.",
                durationMinutes = 30,
                caloriesBurnEst = 310,
                exercisesRaw = listOf(
                    ex("Archer Push-up Progression", 3, "10 Repetisi", "Kekuatan Unilateral Dada", "Luruskan satu tangan menyamping saat mendorong."),
                    ex("Hindu Push-ups", 3, "10 Repetisi", "Bahu, Dada & Tulang Belakang", "Gerakan menyelam ke bawah lalu mendorong ke atas."),
                    ex("Shoulder Tap Plank Cepat", 3, "30 Detik", "Stabilitas Bahu", "Sentuh bahu dengan tempo cepat tanpa goyang."),
                    ex("Bear Crawl Maju Mundur", 3, "45 Detik", "Koordinasi Total", "Merangkak dengan lutut melayang tanpa menyentuh lantai."),
                    ex("Push-up Iso Hold", 3, "20 Detik", "Daya Tahan Dada", "Tahan posisi 3 cm di atas lantai.")
                ).joinToString(";;")
            )
        )

        days.add(
            WorkoutDay(
                dayNumber = 20,
                phaseNumber = 3,
                phaseName = "Fase 3: Athletic Conditioning",
                title = "VO2 Max Destroyer",
                focusDescription = "Interval kardio tanpa henti untuk memaksimalkan kapasitas paru-paru dan pembakaran kalori.",
                durationMinutes = 28,
                caloriesBurnEst = 380,
                exercisesRaw = listOf(
                    ex("Burpee Box/Tuck Jump", 4, "10 Repetisi", "Daya Ledak Maksimal", "Lompat dengan menarik kedua lutut ke dada."),
                    ex("Shadow Boxing Fast Combos", 4, "45 Detik", "Bahu & Kardio", "Kombinasi jab-cross-hook terus menerus dengan cepat."),
                    ex("Mountain Climber Sprint", 4, "30 Detik", "Frekuensi Langkah", "Usaha maksimal 100% kecepatan."),
                    ex("Jumping Lunges", 4, "12 Repetisi", "Asam Laktat Kaki", "Tukar kaki secepat mungkin di udara."),
                    ex("Plank Walkout", 3, "8 Repetisi", "Core Panjang", "Jalan tangan ke depan sejauh mungkin lalu kembali.")
                ).joinToString(";;")
            )
        )

        days.add(
            WorkoutDay(
                dayNumber = 21,
                phaseNumber = 3,
                phaseName = "Fase 3: Athletic Conditioning",
                title = "Active Reset & Hydration",
                focusDescription = "Pemulihan menjelang minggu puncak terakhir. Isi elektrolit dan siapkan mental.",
                durationMinutes = 15,
                caloriesBurnEst = 85,
                isRestDay = true,
                exercisesRaw = listOf(
                    ex("Peregangan Bahu & Lengan", 2, "60 Detik", "Relaksasi Bahu", "Tarik lengan menyilang dada dan tahan."),
                    ex("Jalan Santai Sore", 1, "15 Menit", "Sirkulasi Darah", "Berjalan dengan postur tegap dan rileks.")
                ).joinToString(";;")
            )
        )

        // ==========================================
        // FASE 4: Peak Shred & Final Transformation (Day 22 - 30)
        // ==========================================
        days.add(
            WorkoutDay(
                dayNumber = 22,
                phaseNumber = 4,
                phaseName = "Fase 4: Peak Shredding",
                title = "Championship Calorie Melter",
                focusDescription = "Memulai minggu final dengan sirkuit pembakaran kalori tertinggi.",
                durationMinutes = 35,
                caloriesBurnEst = 420,
                exercisesRaw = listOf(
                    ex("100 Jumping Jacks Challenge", 1, "100 Repetisi", "Kardio Panas", "Selesaikan dalam waktu sesingkat mungkin."),
                    ex("Burpees Non-Stop", 3, "15 Repetisi", "Seluruh Tubuh", "Ritme napas stabil tanpa jeda di bawah."),
                    ex("Jump Squats", 4, "15 Repetisi", "Quad Pembakar", "Kedalaman squat maksimal sebelum melompat."),
                    ex("Push-up to Knee Tuck", 3, "12 Repetisi", "Dada & Perut", "Lompatkan kedua lutut ke dada setelah push-up."),
                    ex("Plank Saw", 3, "45 Detik", "Core Ekstrem", "Dorong tubuh maju mundur dengan ujung jari kaki.")
                ).joinToString(";;")
            )
        )

        days.add(
            WorkoutDay(
                dayNumber = 23,
                phaseNumber = 4,
                phaseName = "Fase 4: Peak Shredding",
                title = "Washboard Abs Protocol",
                focusDescription = "Pemahatan definisi otot perut tajam dengan variasi kontraksi puncak.",
                durationMinutes = 26,
                caloriesBurnEst = 270,
                exercisesRaw = listOf(
                    ex("V-Up Hold", 3, "20 Detik Tahan", "Puncak Kontraksi", "Tahan tubuh di posisi V di detik terakhir."),
                    ex("Bicycle Crunch Sprint", 3, "30 Detik Cepat", "Obliques & Kardio Perut", "Kecepatan tinggi namun tetap sentuh siku ke lutut."),
                    ex("Reverse Crunch", 3, "15 Repetisi", "Perut Bawah", "Angkat pinggul dari matras menggunakan kekuatan otot perut."),
                    ex("Side Plank Dip", 3, "12 Repetisi / Sisi", "Pinggang Ramping", "Turunkan dan angkat pinggul dari matras."),
                    ex("Hollow Body Flutter", 3, "30 Detik", "Stabilitas Baja", "Kombinasi hollow hold dengan gerakan flutter kick.")
                ).joinToString(";;")
            )
        )

        days.add(
            WorkoutDay(
                dayNumber = 24,
                phaseNumber = 4,
                phaseName = "Fase 4: Peak Shredding",
                title = "Leg Shred & Speed Agility",
                focusDescription = "Menghabiskan glikogen kaki untuk memicu lipolisis lemak membandel.",
                durationMinutes = 32,
                caloriesBurnEst = 360,
                exercisesRaw = listOf(
                    ex("Explosive Jump Lunges", 4, "16 Repetisi", "Kaki & Glute", "Lompatan tinggi dengan kontrol stabilitas lutut."),
                    ex("Squat Hold with Pulse", 4, "40 Detik", "Bakar Paha", "Pertahankan posisi paha sejajar lantai."),
                    ex("Broad Jump to Backpedal", 3, "8 Repetisi", "Daya Ledak Atletis", "Lompat jauh ke depan lalu lari mundur cepat."),
                    ex("Single Leg Calf Burnout", 3, "20 Repetisi / Sisi", "Betis Atlet", "Jinjit maksimal dengan tumpuan satu kaki."),
                    ex("Skater Jump Sprint", 3, "30 Detik", "Agilitas & Kardio", "Lompat menyamping selebar mungkin.")
                ).joinToString(";;")
            )
        )

        days.add(
            WorkoutDay(
                dayNumber = 25,
                phaseNumber = 4,
                phaseName = "Fase 4: Peak Shredding",
                title = "Active Reset & Dynamic Stretch",
                focusDescription = "Pemulihan singkat sebelum 5 hari pertempuran terakhir.",
                durationMinutes = 18,
                caloriesBurnEst = 100,
                isRestDay = true,
                exercisesRaw = listOf(
                    ex("World's Greatest Stretch", 2, "5 Repetisi / Sisi", "Mobilitas Total", "Lunge panjang, siku ke lantai, putar dada ke langit."),
                    ex("Couch Stretch (Quad & Hip)", 2, "60 Detik / Kaki", "Paha Depan", "Tempelkan lutut ke dinding untuk regangan dalam."),
                    ex("Child's Pose Side Reach", 2, "60 Detik / Sisi", "Lat & Punggung", "Jangkau tangan ke samping untuk regangan latissimus.")
                ).joinToString(";;")
            )
        )

        days.add(
            WorkoutDay(
                dayNumber = 26,
                phaseNumber = 4,
                phaseName = "Fase 4: Peak Shredding",
                title = "Upper Body Armor",
                focusDescription = "Membangun ketebalan dan definisi pada dada, bahu, dan trisep.",
                durationMinutes = 30,
                caloriesBurnEst = 320,
                exercisesRaw = listOf(
                    ex("Clapping Push-ups / Plyo", 3, "8 Repetisi", "Daya Ledak Dada", "Tepuk tangan di udara jika mampu, atau dorong kuat."),
                    ex("Diamond Push-up Drop-set", 3, "12 Repetisi", "Trisep Maksimal", "Lanjutkan dengan push-up lutut jika lelah."),
                    ex("Pike Push-ups Tempo", 3, "10 Repetisi", "Bahu Depan", "Turun 3 detik lambat untuk intensitas maksimal."),
                    ex("Plank Walkout to Push-up", 3, "8 Repetisi", "Core & Dada", "Jalan tangan ke depan, lakukan push-up, jalan kembali."),
                    ex("Superman Iso Pulse", 3, "30 Detik", "Punggung Baja", "Denyutkan lengan dan kaki di titik tertinggi.")
                ).joinToString(";;")
            )
        )

        days.add(
            WorkoutDay(
                dayNumber = 27,
                phaseNumber = 4,
                phaseName = "Fase 4: Peak Shredding",
                title = "Ultimate Tabata Inferno",
                focusDescription = "Interval Tabata 8 putaran dengan intensitas 100% tanpa kompromi.",
                durationMinutes = 28,
                caloriesBurnEst = 400,
                exercisesRaw = listOf(
                    ex("Sprint High Knees", 4, "20s Kerja / 10s Istirahat", "Denyut Jantung Maks", "Kecepatan sprint seolah berlari 100 meter."),
                    ex("Burpee Box Jumps", 4, "20s Kerja / 10s Istirahat", "Seluruh Tubuh", "Transisi cepat dari lantai ke lompatan."),
                    ex("Mountain Climbers Sprint", 4, "20s Kerja / 10s Istirahat", "Core & Kalori", "Lutut terus memompa tanpa henti."),
                    ex("Jump Squat Pulses", 4, "20s Kerja / 10s Istirahat", "Pembakaran Lemak", "Denyut di bawah sebelum melompat tinggi.")
                ).joinToString(";;")
            )
        )

        days.add(
            WorkoutDay(
                dayNumber = 28,
                phaseNumber = 4,
                phaseName = "Fase 4: Peak Shredding",
                title = "Core Strength & Balance",
                focusDescription = "Penyempurnaan kekuatan inti tubuh dan stabilitas fungsional menyeluruh.",
                durationMinutes = 24,
                caloriesBurnEst = 250,
                exercisesRaw = listOf(
                    ex("Bird Dog Iso Hold", 3, "12 Repetisi / Sisi", "Punggung & Glute", "Tahan 2 detik di posisi terentang sempurna."),
                    ex("Hollow Rock to V-Up", 3, "10 Repetisi", "Core Lanjutan", "Kombinasi goyangan hollow dan angkatan V."),
                    ex("Side Plank Rotations", 3, "10 Repetisi / Sisi", "Obliques Tajam", "Selipkan lengan atas ke bawah torso."),
                    ex("Plank to Pike", 3, "12 Repetisi", "Bahu & Perut Bawah", "Tarik pinggul ke atas menggunakan kekuatan perut.")
                ).joinToString(";;")
            )
        )

        days.add(
            WorkoutDay(
                dayNumber = 29,
                phaseNumber = 4,
                phaseName = "Fase 4: Peak Shredding",
                title = "The Pre-Final Gauntlet",
                focusDescription = "Uji tantangan sirkuit ketahanan atletis sebelum hari kelulusan 30 hari.",
                durationMinutes = 35,
                caloriesBurnEst = 430,
                exercisesRaw = listOf(
                    ex("50 Push-ups Total", 1, "50 Repetisi", "Uji Kekuatan Dada", "Bagi dalam beberapa set dengan istirahat minimal."),
                    ex("75 Squats Total", 1, "75 Repetisi", "Uji Daya Tahan Kaki", "Kedalaman penuh dan ritme stabil."),
                    ex("30 Burpees Total", 1, "30 Repetisi", "Kapasitas Aerobik", "Selesaikan dengan semangat pantang menyerah."),
                    ex("100 Mountain Climbers", 1, "100 Repetisi", "Uji Ketahanan Core", "50 repetisi per kaki secepat mungkin."),
                    ex("2 Menit Plank Challenge", 1, "120 Detik", "Kekuatan Mental", "Tahan selama mungkin tanpa menurunkan lutut.")
                ).joinToString(";;")
            )
        )

        days.add(
            WorkoutDay(
                dayNumber = 30,
                phaseNumber = 4,
                phaseName = "Fase 4: Peak Shredding",
                title = "Day 30: Victory Transformation",
                focusDescription = "Selamat! Hari terakhir program 30 hari! Rayakan kemenangan disiplin & transformasi fisik Anda!",
                durationMinutes = 30,
                caloriesBurnEst = 450,
                exercisesRaw = listOf(
                    ex("Victory Jumping Jacks", 2, "50 Repetisi", "Pemanasan Juara", "Bersenang-senang dengan ritme penuh energi."),
                    ex("Full Body Calisthenics Flow", 3, "15 Repetisi", "Gerakan Atlet Sempurna", "Squat, Push-up, Lunge dalam satu alur."),
                    ex("Championship Burpees", 3, "12 Repetisi", "Puncak Daya Tahan", "Lompatan tertinggi untuk menyelesaikan 30 hari."),
                    ex("Final Plank of Triumph", 1, "90 Detik", "Piala Disiplin", "Tahan dengan bangga atas dedikasi 1 bulan penuh!"),
                    ex("Full Body Cool-Down Stretch", 1, "10 Menit", "Pemulihan & Refleksi", "Tarik napas dalam, nikmati pencapaian berat badan baru.")
                ).joinToString(";;")
            )
        )

        return days
    }
}
