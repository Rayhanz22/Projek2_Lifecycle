package com.example.simplechat;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity {

    private EditText editTextMessage;
    private Button buttonSend, buttonBackToMainActivity;
    private LinearLayout chatContainer;
    private boolean isUserAditActive; // Flag untuk pengguna aktif

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);
        chatContainer = findViewById(R.id.chatContainer);
        buttonBackToMainActivity = findViewById(R.id.buttonBackToMainActivity);

        // Cek pengguna aktif dari Intent
        isUserAditActive = getIntent().getBooleanExtra("isUserAditActive", false);

        loadChatHistory();

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTextMessage.getText().toString();

                if (!message.isEmpty()) {
                    String formattedMessage = (isUserAditActive ? "ME: " : "ADIT: ") + message;
                    ChatManager.getInstance().addMessage(formattedMessage);
                    addMessageToChat(formattedMessage, isUserAditActive);

                    editTextMessage.setText("");
                }
            }
        });

        buttonBackToMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this, MainActivity.class);
                intent.putExtra("isUserAditActive", false); // Set pengguna aktif kembali ke ME
                startActivity(intent);
            }
        });
    }

    private void loadChatHistory() {
        chatContainer.removeAllViews();
        ChatManager chatManager = ChatManager.getInstance();
        for (String message : chatManager.getMessages()) {
            // Memformat pesan sebelum menambahkannya ke tampilan
            String formattedMessage = chatManager.formatMessage(message);
            boolean isUserMessage = formattedMessage.startsWith("ME: ");
            addMessageToChat(formattedMessage, isUserMessage);
        }
    }

    private String modifyMessageLabels(String message) {
        if (isUserAditActive) {
            // Jika ADIT aktif, ganti label "ADIT:" menjadi "ME:" dan "ME:" menjadi "RAYHAN"
            if (message.startsWith("ADIT:")) {
                return message.replace("ADIT:", "ME:");
            } else if (message.startsWith("ME:")) {
                return message.replace("ME:", "RAYHAN:");
            }
        }
        return message;
    }

    private void addMessageToChat(String message, boolean isUser) {
        TextView textViewMessage = new TextView(this);
        SpannableString spannableMessage = new SpannableString(message);
        spannableMessage.setSpan(new StyleSpan(Typeface.BOLD), 0, message.indexOf(':') + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textViewMessage.setText(spannableMessage);
        textViewMessage.setPadding(16, 16, 16, 16);
        textViewMessage.setTextSize(16);
        textViewMessage.setTextColor(getResources().getColor(android.R.color.white));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        if (isUser) {
            params.gravity = Gravity.END;
            textViewMessage.setBackgroundResource(R.drawable.bubble_user);
        } else {
            params.gravity = Gravity.START;
            textViewMessage.setBackgroundResource(R.drawable.bubble_other);
        }

        params.setMargins(0, 16, 0, 16);
        textViewMessage.setLayoutParams(params);

        chatContainer.addView(textViewMessage);
    }
}
