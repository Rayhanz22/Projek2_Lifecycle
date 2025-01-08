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

public class MainActivity extends AppCompatActivity {

    private EditText editTextMessage;
    private Button buttonSend, buttonReplyInChatActivity;
    private LinearLayout chatContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);
        chatContainer = findViewById(R.id.chatContainer);
        buttonReplyInChatActivity = findViewById(R.id.buttonReplyInChatActivity);

        loadChatHistory(); //untuk me-load chat ketika kembali ke activity ini

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTextMessage.getText().toString(); //mengambil teks dari EditText

                if (!message.isEmpty()) {
                    String formattedMessage = "ME: " + message;
                    ChatManager.getInstance().addMessage(formattedMessage);
                    addMessageToChat(formattedMessage, true);

                    editTextMessage.setText("");
                }
            }
        });

        buttonReplyInChatActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatManager.getInstance().setAditActive(true);
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadChatHistory() {
        chatContainer.removeAllViews();
        for (String message : ChatManager.getInstance().getMessages()) {
            addMessageToChat(message, message.startsWith("ME: "));
        }
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
            textViewMessage.setBackgroundResource(R.drawable.bubble_user); // bubble user (ME)
        } else {
            params.gravity = Gravity.START;
            textViewMessage.setBackgroundResource(R.drawable.bubble_other); // bubble chat other user(ADIT)
        }

        params.setMargins(0, 16, 0, 16);
        textViewMessage.setLayoutParams(params);

        chatContainer.addView(textViewMessage);
    }
}
