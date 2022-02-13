package com.homework.task.ui.component;

import com.homework.task.entities.Client;
import com.homework.task.service.ClientService;
import com.homework.task.ui.util.Notice;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.RegexpValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import javax.annotation.PostConstruct;
import java.util.Optional;

@SpringComponent
@UIScope
public class ClientComponent {

    private Dialog dialog;

    private TextField name;
    private TextField surname;
    private TextField patronymic;
    private EmailField email;
    private TextField numberPhone;
    private TextField passport;

    private Button ok;
    private Button cancel;

    private Binder<Client> binder;

    private Notice notice;

    private Client client;

    private final ClientService clientService;

    public ClientComponent(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostConstruct
    private void initialization() {
        dialog = new Dialog();

        name = new TextField("Имя");
        surname = new TextField("Фамилия");
        patronymic = new TextField("Отчество");
        email = new EmailField("Почта");
        numberPhone = new TextField("Номер телефона");
        passport = new TextField("Паспорт");

        ok = new Button("Ok!", VaadinIcon.CHECK.create());
        cancel = new Button("Cancel", VaadinIcon.BAN.create());

        notice = new Notice();

        binder = new Binder<>(Client.class);
        binder.bindInstanceFields(this);

        name.setPrefixComponent(VaadinIcon.PENCIL.create());
        surname.setPrefixComponent(VaadinIcon.PENCIL.create());
        patronymic.setPrefixComponent(VaadinIcon.PENCIL.create());
        email.setPrefixComponent(VaadinIcon.PIN_POST.create());
        numberPhone.setPrefixComponent(VaadinIcon.PIN.create());
        passport.setPrefixComponent(VaadinIcon.BOOKMARK.create());

        numberPhone.setMaxLength(11);
        passport.setMaxLength(10);


        initializationDialogWindow();
        validationField();
        eventsButtons();
    }

    private void eventsButtons() {
        ok.addClickListener(click -> {
            save();
            notice.setSuccessNotice("Успешно!");
            UI.getCurrent().getPage().reload();
        });

        cancel.addClickListener(click -> dialog.close());
    }

    private void initializationDialogWindow() {
        H2 title = new H2("Add or Edit");

        HorizontalLayout textFields = new HorizontalLayout();

        textFields.add
                (
                        name,
                        surname,
                        patronymic,
                        email,
                        numberPhone,
                        passport
                );

        HorizontalLayout buttonsLayout = new HorizontalLayout();

        buttonsLayout.add(ok, cancel);

        VerticalLayout general = new VerticalLayout();

        general.add(
                textFields,
                buttonsLayout
        );

        dialog.add(title, general);
    }

    private void validationField() {
        RegexpValidator forbiddenSymbols = new RegexpValidator(
                "Недопустимые символы",
                "[a-zA-Zа-яА-Я]+");

        binder.forField(name)
                .withValidator(v -> v.length() >= 3, "Имя короткое")
                .withValidator(forbiddenSymbols)
                .asRequired()
                .bind(Client::getName, Client::setName);

        binder.forField(surname)
                .withValidator(v -> v.length() >= 5, "Фамилия короткая")
                .withValidator(forbiddenSymbols)
                .asRequired()
                .bind(Client::getSurname, Client::setSurname);

        binder.forField(patronymic)
                .withValidator(v -> v.length() >= 5, "Отчество слишком короткое")
                .withValidator(forbiddenSymbols)
                .asRequired()
                .bind(Client::getPatronymic, Client::setPatronymic);

        binder.forField(email)
                .withValidator(
                        new EmailValidator
                                ("Некорректный формат")
                )
                .asRequired()
                .bind(Client::getEmail, Client::setEmail);

        binder.forField(numberPhone)
                .withValidator(
                        new RegexpValidator
                                ("Некорректный формат", "\\+?\\d{11}"))
                .asRequired()
                .bind(Client::getNumber, Client::setNumber);

        binder.forField(passport)
                .withValidator(v -> v.length() == 10, "Цифр должно быть 10")
                .withValidator(
                        new RegexpValidator
                                ("Недопустимые символы", "\\d{10}")
                )
                .asRequired()
                .bind(Client::getPassport, Client::setPassport);

        binder.setBean(client);
    }

    public void save() {
        if (!binder.validate().hasErrors()) {
            clientService.save(client);
        }
    }

    public void edit(Client changedClient) {
        if (changedClient == null) {
            return;
        }

        if (changedClient.getId() != null) {
            Optional<Client> doctor = clientService.findById(changedClient.getId());

            Client temp = new Client();

            if (doctor.isPresent()) {
                temp = doctor.get();
            }

            this.client = temp;

        } else {
            this.client = changedClient;
        }

        binder.setBean(client);
    }

    public Client getClient() {
        return client;
    }

    public void open() {
        dialog.open();
    }
}
