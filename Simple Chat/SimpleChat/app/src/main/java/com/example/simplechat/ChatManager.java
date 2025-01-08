package com.example.simplechat;

import java.util.ArrayList;
import java.util.List;

public class ChatManager {
    // Membuat variabel "instance" yang akan digunakan untuk menyimpan satu-satunya objek ChatManager
    private static ChatManager instance;
    // List untuk menyimpan semua pesan yang diterima atau dikirim
    private final List<String> messages;

    // Variabel untuk menentukan apakah pengguna aktif saat ini adalah ADIT
    private boolean isAditActive;

    private ChatManager() {
        // Membuat ArrayList untuk menyimpan/menampung pesan
        messages = new ArrayList<>();
    }

    // Mendapatkan instance dari ChatManager
    public static synchronized ChatManager getInstance() {
        if (instance == null) {
            instance = new ChatManager();
        }
        return instance;
    }

    // Menambahkan pesan ke dalam list messages
    public void addMessage(String message) {
        messages.add(message);
    }

    // Mendapatkan semua pesan dari list messages
    public List<String> getMessages() {
        return messages;
    }

    // Mengatur pengguna aktif saat ini
    public void setAditActive(boolean isActive) {
        this.isAditActive = isActive;
    }

    // Mendapatkan status pengguna aktif saat ini
    public boolean isAditActive() {
        return isAditActive;
    }

    // Metode untuk memodifikasi label pesan sesuai dengan pengguna aktif
    public String formatMessage(String message) {
        if (isAditActive) {
            if (message.startsWith("ADIT:")) {
                return message.replace("ADIT:", "ME:");
            } else if (message.startsWith("ME:")) {
                return message.replace("ME:", "RAYHAN:");
            }
        } else {
            // Jika pengguna aktif adalah RAYHAN, biarkan format default
            return message;
        }
        return message;
    }
}
