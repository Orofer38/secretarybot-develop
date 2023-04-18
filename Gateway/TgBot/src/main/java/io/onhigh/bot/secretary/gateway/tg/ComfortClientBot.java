package io.onhigh.bot.secretary.gateway.tg;

import io.onhigh.bot.secretary.gateway.tg.config.BotConfig;
import io.onhigh.bot.secretary.gateway.tg.model.Person;
import io.onhigh.bot.secretary.gateway.tg.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.glassfish.grizzly.http.util.TimeStamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PreDestroy;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marat Kadzhaev
 * @since 09 май 2022
 */
@Component
@RequiredArgsConstructor
public class ComfortClientBot extends TelegramLongPollingBot {

    @PreDestroy
    public void onClose() {
        onClosing();
    }

    @Autowired
    private PersonRepository personRepository;
    private BotConfig config;
    private static String HELP_TEXT = "Этот бот поможет тебе напомнить о важной дате и (или) перевести " +
            "(оплатить) нужный тебе счет.";
    private Connection connection;

    public ComfortClientBot(BotConfig config) {
        this.config = config;
        List<BotCommand> listbotCommand = new ArrayList<>();
        listbotCommand.add(new BotCommand("/start", "get a welcom message"));
        listbotCommand.add(new BotCommand("/help", " all command"));
        try {
            this.execute(new SetMyCommands(listbotCommand, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            //log.error("Ошибка добавления команд: " + e.getMessage());
        }

    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String textMessage = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (textMessage) {
                case "/start":
                    registeredPerson(update.getMessage());
                    startComandReciverd(chatId, update.getMessage().getChat().getFirstName());
                    sendMessage(chatId, "Ну же, скажи мне, что я могу запомнить для тебя?");

                    break;
                case "/help":
                    sendMessage(chatId, HELP_TEXT);
                    break;
                default:

                    sendMessage(chatId, "Мискузи, команда не найдена. Введите дату в формате ДД.ММ.ГГГГ.");

            }

        }
    }

// требуется привязка базы данных

    @Override
    public String getBotUsername() {
        return config.getBotUsername();
    }

    public String getBotToken() {
        return config.getBotToken();
    }

    // здесь отправка сообщения указана как метод, стоит ли просто указать просто сообщение
    public void startComandReciverd(long chatId, String name) {
        String hello = "Привет, " + name + " - мой забывчивый друг) ";
        //log.info("Добавлен пользователь: " + name);
        sendMessage(chatId, hello);
    }

    private void registeredPerson(Message msg) {
        if (personRepository.findById(msg.getChatId()).isEmpty()) {
            var chatId = msg.getChatId();
            var chat = msg.getChat();

            Person person = new Person();
            person.setChatId(chatId);
            person.setFirsName(chat.getFirstName());
            person.setLastName(chat.getLastName());
            person.setUseName(chat.getUserName());
            person.setRegistered(new TimeStamp());//System.currentTimeMillis()

            personRepository.save(person);
            //log.info("Добавлен пользователь: " + person);
        }
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            //log.error("Произошла ошибка: " + e.getMessage());


        }

    }
}
