package com.example.homeprod.assistent_begins;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    /**
     * Элемент в котором отображается чат
     */
    private TextView chat;

    /**
     * Поле для ввода сообщение
     */
    private EditText message;

    /**
     * Кнопка "отправить"
     */
    private Button send;

    /**
     * Кнопка "очистить"
     */
    private Button clear;

    /**
     * объект для форматирования даты/времени
     */
    private DateFormat fmt;


    /**
     * Метод, который вызывается при создании Activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Найдем нужные элементы интерфейса по id
         */
        chat = (TextView) findViewById(R.id.chatView);
        chat.setMovementMethod(new ScrollingMovementMethod());
        message = (EditText) findViewById(R.id.editText);
        send = (Button) findViewById(R.id.sendBtn);
        clear = (Button) findViewById(R.id.cleatBtn);

        /**
         * Назначим обработчик события "Клик" на кнопке "отправить"
         */
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question = message.getText().toString(); // Получим текст вопроса
                message.setText(""); // Очистим поле
                chat.append("\n<< " + question); // Отобразим вопрос в чате
                String answer = answerQuestion(question); // Вычислим тексто ответа
                chat.append("\n>> " + answer); // Отобразим ответ в чате
            }
        });

        /**
         * Назначим обработчик события "Клик" на кнопке "очистить"
         */
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chat.setText("");
            }
        });

    }

    protected String answerQuestion(String question) {
        question = question.toLowerCase(); // Привидем текст к нижнему регистру
        Map<String, String> questions = new HashMap<String, String>() {{ // Заполним "карту ответов"
            put("привет", "Привет! ");
            put("как дела", "Шикарно");
            put("чем занимаешься", "Отвечаю на дурацкие вопросы");
            put("как тебя зовут", "Меня зовут Ассистентий");
            put("лал", "кек");
            put("кек", "чебурек");
            put("спасибо", "пожалуйста! обращайся ;)");
            put("в чем смысл жизни", "вычисление ответа займет....около 100500+ лет, но это не точно");
            put("пока", "пока! " + goodbyeMsg(new Date()) + "");

        }};

        List<String> result = new ArrayList<>(); // В этом списке будем хранить ответы

        for (String quest : questions.keySet()) { // Пройдем циклом по карте ответов и найдем совпадающие вопросы
            if (question.contains(quest)) {
                result.add(questions.get(quest)); // Если в тексте содержится вопрос, то запишем в список соотв. ответ
            }
        }
        if (question.contains("сколько времени")) { // Отдельно предусмотрим случай, когда пользователь спрашивает время
            fmt = new SimpleDateFormat("HH:mm:ss");
            String time = fmt.format(new Date()); // Отформатируем текущую дату
            result.add("Сейчас " + time);  // Запишем ответ в список

        }
        if (question.contains("какой сегодня день")) {
            fmt = new SimpleDateFormat("EEEE, dd MMMM", new Locale("ru"));// Отдельно предусмотрим случай, когда пользователь спрашивает текущий день
            String time = fmt.format(new Date()); // Отформатируем текущую дату
            result.add("Сегодня: " + time);  // Запишем ответ в список

        }

        return TextUtils.join(", ", result); // Все получившиеся ответы объединим через запятую и вернем как результат метода
    }

    /**
     * возвращает прощание относительно времени суток
     *
     * @param date
     * @return
     */
    private String goodbyeMsg(Date date) {

        Date editDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(editDate);
        cal.set(Calendar.HOUR_OF_DAY, 17);  // устанавливаем время для ориентации во времени суток
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return date.after(cal.getTime()) ? "хорошего вечера!" : "хорошего дня!";    //возвращаем ответ в зависимости от текущего времени
    }
}
