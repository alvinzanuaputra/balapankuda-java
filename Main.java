import java.util.*;

// Kelas yang merepresentasikan kuda
class Horse {
    private String name;    // Nama kuda
    private float speed;    // Kecepatan kuda
    private int nomorBalap; // Nomor balap kuda
    private double posisi;  // Posisi kuda dalam balapan
    private int motivasi;   // Motivasi kuda untuk berlari
    private String icon = "kuda";

    // Konstruktor untuk inisialisasi kuda
    public Horse(String name, int speed) {
        this.name = name;
        this.speed = speed;
        this.nomorBalap = new Random().nextInt(88) + 11;  // Menghasilkan nomor balap acak
        this.posisi = 0;
        this.motivasi = 99;  // Motivasi awal kuda
    }

    // Getter
    public String getName() {
        return name;
    }

    public double getPosisi() {
        return posisi;
    }

    public int getNomorBalap() {
        return nomorBalap;
    }

    public String getIcon() {
        return icon;
    }

    // Metode untuk menggerakkan kuda
    public void lari() {
        motivasi = new Random().nextInt(3);  // Mengubah motivasi secara acak
        posisi += speed * motivasi;  // Update posisi kuda berdasarkan kecepatan dan motivasi
    }
}

// Kelas kuda dengan kecepatan turbo (warisan dari kelas Horse)
class TurboHorse extends Horse {
    public TurboHorse(String name) {
        super(name, 2);  // Kecepatan turbo 2
    }
}

// Kelas kuda dengan kecepatan rendah (warisan dari kelas Horse)
class LowHorse extends Horse {
    public LowHorse(String name) {
        super(name, 1);  // Kecepatan rendah 1
    }
}

// Kelas untuk mengelola permainan balapan kuda
class GameManager {
    private List<Horse> horses;  // Daftar kuda yang ikut balapan
    private int trackLength;  // Panjang lintasan balapan
    private boolean raceStarted = false;  // Status apakah balapan sudah dimulai

    // Konstruktor GameManager
    public GameManager() {
        horses = new ArrayList<>();
        trackLength = 100;  // Panjang lintasan balapan

        // Nama-nama kuda
        String[] horseNames = { "Thunder", "Lightning", "Blaze", "Storm", "Shadow" };

        Random rnd = new Random();
        for (String name : horseNames) {
            // Menambahkan kuda dengan jenis turbo atau low secara acak
            if (rnd.nextInt(10) % 2 == 0)
                horses.add(new TurboHorse(name));
            else
                horses.add(new LowHorse(name));
        }
    }

    // Metode untuk menjalankan balapan
    private void race() {
        // Membersihkan layar (Java tidak memiliki metode untuk membersihkan layar secara langsung)
        System.out.print("\033[H\033[2J");  
        System.out.flush();

        // Menampilkan pesan bahwa balapan telah dimulai jika belum pernah dimulai
        if (!raceStarted) {
            raceStarted = true;
        }

        System.out.println("\nRacing Game Kuda Telah Dimulai -> \n");

        // Menjalankan balapan untuk setiap kuda
        for (Horse horse : horses) {
            horse.lari();  // Memperbarui posisi kuda
            char[] track = new char[trackLength];
            Arrays.fill(track, '_');  // Membuat lintasan balapan

            // Mengganti karakter pada lintasan dengan ikon kuda jika kuda berada di posisi tersebut
            if (horse.getPosisi() < trackLength)
                track[(int) Math.floor(horse.getPosisi())] = horse.getIcon().charAt(0);

            System.out.println(new String(track) + " " + horse.getName());  // Menampilkan lintasan dan nama kuda
        }

        try {
            Thread.sleep(500);  // Jeda satu detik antara setiap update balapan
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Metode untuk memulai permainan
    public void start() {
        // Terus menjalankan balapan sampai setidaknya satu kuda mencapai atau melewati panjang lintasan
        while (horses.stream().allMatch(horse -> horse.getPosisi() < trackLength)) {
            race();
        }

        // Mengurutkan kuda berdasarkan posisi (urutan menurun)
        horses.sort(Comparator.comparingDouble(Horse::getPosisi).reversed());

        // Menampilkan hasil balapan
        System.out.println("\nDUARRRR PERTANDINGAN BERAKHIR TETTTT \nHasil Balapan Kuda Tercepat: \n");
        for (int i = 0; i < horses.size(); i++) {
            System.out.println("- Juara " + (i + 1) + " Diraih Oleh " + horses.get(i).getName());
            System.out.println("Dengan Rata-Rata Kecepatan Sekitar " + horses.get(i).getNomorBalap() + " Km/Jam");
        }
    }
}

// Kelas utama untuk menjalankan program
public class Main {
    public static void main(String[] args) {
        GameManager game = new GameManager();  // Membuat instance GameManager
        game.start();  // Memulai permainan
    }
}
